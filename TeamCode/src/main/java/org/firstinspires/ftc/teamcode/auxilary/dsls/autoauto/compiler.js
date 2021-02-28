var fs = require("fs");
var path = require("path");

var directory = __dirname.split(path.sep);

console.log(directory);

var template = fs.readFileSync(__dirname + path.sep + "template.notjava").toString();

var srcDirectory = directory.slice(0, directory.indexOf("src") + 1);

console.log("srcdirectory", srcDirectory);

var autoautoFiles = loadAutoautoFilesFromFolder(srcDirectory.join(path.sep));

for(var i = 0; i < autoautoFiles.length; i++) {
    var fileSource = fs.readFileSync(autoautoFiles[i]).toString();
    var fileName = autoautoFiles[i].substring(autoautoFiles[i].lastIndexOf(path.sep) + 1);
    var folder = autoautoFiles[i].replace(path.sep + fileName, "");
    var packageName = folder.substring(folder.indexOf("org")).replace(new RegExp(path.sep.replace("\\", "\\\\"), "g"), ".");
    var className = fileName.replace(".autoauto", "__autoauto");
    var javaFileName = autoautoFiles[i].replace(/\.autoauto$/, "__autoauto.java");

    var javaStringFileSource = fileSource.replace(/\r?\n/g, " ").replace(/"/g, "\\\"");

    fs.writeFileSync(javaFileName, processTemplate(template, className, packageName, javaStringFileSource));
}

function processTemplate(template, className, packageName, javaStringFileSource) {
    return template.replace("{{className}}", className).replace("{{packageName}}", packageName).replace("{{javaStringFileSource}}", javaStringFileSource);
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