var fs = require("fs");
var path = require("path");

var parserTools = require("./parser-tools.js");

var directory = __dirname.split(path.sep);

console.log(directory);

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

    var javaStringFileSource = uncommentedFileSource.replace(/\r?\n/g, " ").replace(/"/g, "\\\"");

    fs.writeFileSync(compiledResultDirectory + "/" + javaFileName, processTemplate(template, className, javaStringFileSource));
}

function processTemplate(template, className, javaStringFileSource) {
    return template.replace("{{className}}", className).replace("{{javaStringFileSource}}", javaStringFileSource);
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