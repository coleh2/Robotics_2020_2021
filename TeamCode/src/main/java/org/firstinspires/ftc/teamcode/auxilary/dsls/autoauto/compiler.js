var fs = require("fs");
var path = require("path");

var aaParser = require("./aa-parser.js");
var astJavaify = require("./ast-tools.js");
var parserTools = require("./parser-tools.js");

var directory = __dirname.split(path.sep);

var PACKAGE_DECLARATION = "package org.firstinspires.ftc.teamcode.__compiledautoauto;";

var template = fs.readFileSync(__dirname + path.sep + "template.notjava").toString();

var srcDirectory = directory.slice(0, directory.indexOf("src") + 1);
var compiledResultDirectory = path.join(srcDirectory.join(path.sep), "main/java/org/firstinspires/ftc/teamcode/__compiledautoauto");
if(!fs.existsSync(compiledResultDirectory)) fs.mkdirSync(compiledResultDirectory);

var autoautoFiles = loadAutoautoFilesFromFolder(srcDirectory.join(path.sep));

for(var i = 0; i < autoautoFiles.length; i++) {
    var fileSource = fs.readFileSync(autoautoFiles[i]).toString();
    var fileName = autoautoFiles[i].substring(autoautoFiles[i].lastIndexOf(path.sep) + 1);
    var className = fileName.replace(".autoauto", "__autoauto");
    var javaFileName = className + ".java";

    var uncommentedFileSource = parserTools.stripComments(fileSource);

    var frontMatter = stripAndParseFrontMatter(uncommentedFileSource);

    console.log("frontmatter : " + JSON.stringify(frontMatter.frontMatter));

    var javaStringFileSource = frontMatter.stripped;
    
    try {
        var parsedModel = aaParser.parse(uncommentedFileSource);
        
        var javaCreationCode = astJavaify(parsedModel);

        fs.writeFileSync(compiledResultDirectory + "/" + javaFileName, processTemplate(template, className, frontMatter.frontMatter, javaStringFileSource, javaCreationCode));
    } catch(e) {
        console.error("AUTOAUTOERROR: Could not parse " + className + "\n" + (e.location ? e.location.start.line + ":" + e.location.start.column : "") + "\t" + e.toString());
        process.exit(1);
    }
    
}

function processTemplate(template, className, frontMatter, javaStringFileSource, javaCreationCode) {
    return template
        .replace("public class template", "public class " + className)
        .replace("/*NSERVO_NAMES*/", buildServoNames(frontMatter.servos))
        .replace("/*NSERVOS*/", buildServos(frontMatter.servos))
        .replace("/*JAVA_CREATION_CODE*/", javaCreationCode)
        .replace("/*CRSERVO_NAMES*/", buildCrServoNames(frontMatter.crServos))
        .replace("/*CRSERVOS*/", buildCrServos(frontMatter.crServos))
        .replace("/*PACKAGE_DECLARATION*/", PACKAGE_DECLARATION)
        .replace("/*TESTITERATIONS*/", frontMatter.testIterations === undefined ? 3 : frontMatter.testIterations);
}

function buildServoNames(servos) {
    if(servos === undefined) return `"wobbleArmRight","wobbleArmLeft" , "wobbleGrabRight","wobbleGrabLeft", "shooterArm"`;
    else return servos.map(x=> `"${x}"`).join(", ");
}

function buildCrServoNames(crServos) {
    if(crServos === undefined) return ``;
    else return servos.map(x=> `"${x}"`).join(", ");
}

function buildCrServos(crServos) {
    if(crServos === undefined) return ``;
    else return crServos.map(x=> `hardwareMap.get(CRServo.class, "${x}")`).join(", ");
}

function buildServos(servos) {
    if(servos === undefined) return `hardwareMap.get(Servo.class, "wobbleArmRight"), hardwareMap.get(Servo.class, "wobbleArmLeft"), hardwareMap.get(Servo.class, "wobbleGrabRight"), hardwareMap.get(Servo.class, "wobbleGrabLeft"), hardwareMap.get(Servo.class, "shooterArm")`;
    else return servos.map(x=> `hardwareMap.get(Servo.class, "${x}")`).join(", ");
}

function stripAndParseFrontMatter(src) {
    var startDollarSign = parserTools.findUngroupedSubstring(src, "$");
    if(startDollarSign == -1) return { stripped: src, frontMatter: {} };

    var endDollarSign = startDollarSign + 1 + parserTools.findUngroupedSubstring(src.substring(startDollarSign + 1), "$");
    if(endDollarSign == -1) throw src;

    var frontMatter = eval("({" + src.substring(startDollarSign + 1, endDollarSign) + "})");

    return {
        stripped: src.substring(parserTools.findUngroupedSubstring(src, "#")),
        frontMatter: frontMatter
    }
}

function loadAutoautoFilesFromFolder(folder) {
    let results = [];

    let folderContents = fs.readdirSync(folder, {
        withFileTypes: true
    });

    for(var i = 0; i < folderContents.length; i++) {
        let subfile = folderContents[i];

        if(subfile.isDirectory()) {
            results = results.concat(loadAutoautoFilesFromFolder(path.resolve(folder, subfile.name)));
        } else if(subfile.isFile() && subfile.name.endsWith(".autoauto")) {
            results.push(path.resolve(folder, subfile.name));
        }
    }

    return results;
}