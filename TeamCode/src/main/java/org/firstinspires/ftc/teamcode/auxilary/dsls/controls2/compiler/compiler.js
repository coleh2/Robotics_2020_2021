var fs = require("fs");
var path = require("path");

var generateCode = require("./generate-code.js");
var peg = require("./pegjs");
var processTemplate = require("./template-processor.js");

//initiate folders, gitignore rules, etc. that are required
(require("./initializer.js"))();

var template = fs.readFileSync(path.join(__dirname, "template.notjava")).toString();

var controlsPegjs = fs.readFileSync(path.join(__dirname, "controls.pegjs")).toString();

var parser = peg.generate(controlsPegjs);

var directory = __dirname.split(path.sep);
var srcDirectory = directory.slice(0, directory.indexOf("src") + 1);

var controlFiles = loadControlsFilesFromFolder(srcDirectory.join(path.sep));

for(var i = 0; i < controlFiles.length; i++) {
    var filename = controlFiles[i];
    
    var fileContent = fs.readFileSync(filename).toString();
    var ast = parser.parse(fileContent);
    
    try {
        var code = generateCode(ast);
    } catch(e) {
        if(typeof e == "object" && e.location) {
            console.error(filename + ":" + e.location.start.line + ":\n" + e.message);
            break;
        }
        else {
            throw e;
        }
    }
    var opmode = processTemplate(template, code, filename);
    
    fs.writeFileSync(getResultFilename(filename), opmode);
}

function getResultFilename(filename) {
    var folder = path.join(srcDirectory.join(path.sep), "main/java/org/firstinspires/ftc/teamcode/__compiledcontrols");
    return path.join(folder, path.basename(filename, ".controls") + "__controls.java")
}

function loadControlsFilesFromFolder(folder) {
    let results = [];

    let folderContents = fs.readdirSync(folder, {
        withFileTypes: true
    });

    for(var i = 0; i < folderContents.length; i++) {
        let subfile = folderContents[i];

        if(subfile.isDirectory()) {
            results = results.concat(loadControlsFilesFromFolder(path.resolve(folder, subfile.name)));
        } else if(subfile.isFile() && subfile.name.endsWith(".controls")) {
            results.push(path.resolve(folder, subfile.name));
        }
    }

    return results;
}