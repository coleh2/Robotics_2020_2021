var os = require("os");
var crypto = require("crypto");
var path = require("path");
var fs = require("fs");

var HASH_SECRET = "autoauto family";
var GITIGNORED = ["**/buildhistory/BuildHistory.java"];

var directory = __dirname.split(path.sep);
var rootDirectory = directory.slice(0, directory.indexOf("TeamCode")).join(path.sep);

//update gitignore with autoauto files
var gitignore = fs.readFileSync(path.join(rootDirectory, ".gitignore")).toString();
var gitignoreLines = gitignore.split(/\r?\n/);

for(var i = 0; i < GITIGNORED.length; i++) {
    if(gitignoreLines.indexOf(GITIGNORED[i]) == -1) gitignoreLines.push(GITIGNORED[i]);
}

gitignore = gitignoreLines.join("\n");
fs.writeFileSync(path.join(rootDirectory, ".gitignore"), gitignore);

 var computerUniqueIdentifier = os.cpus()[0].model + "/" + os.hostname() + "/" + os.platform();

 var computerHash = crypto.createHmac("sha256", HASH_SECRET)
     .update(computerUniqueIdentifier)
     .digest("hex");

 var familyTreeRecordsDirectory = path.join(__dirname, "genealogy");
 var familyLineFile = path.join(familyTreeRecordsDirectory, computerHash + ".json");

 if(!fs.existsSync(familyTreeRecordsDirectory)) fs.mkdirSync(familyTreeRecordsDirectory);
 if(!fs.existsSync(familyLineFile)) fs.writeFileSync(familyLineFile, "{}");

 var familyLine = require("./genealogy/" + computerHash + ".json");

(async function() {
    if(!familyLine.browser) {
     familyLine.browser = await require("./fingerprint-user.js")();
    }

    updateTemplate(familyLine);
})();

function updateTemplate(familyLine) {
    var template = fs.readFileSync(path.join(__dirname, "BuildHistory.notjava")).toString();
    fs.writeFileSync(path.join(__dirname, "BuildHistory.java"), template
                                .replace("BUILDER_BROWSER_FINGERPRINT", familyLine.browser))
}