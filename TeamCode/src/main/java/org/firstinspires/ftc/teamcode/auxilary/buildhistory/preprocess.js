var os = require("os");
var crypto = require("crypto");
var path = require("path");
var fs = require("fs");

var HASH_SECRET = "autoauto family";
var GITIGNORED = ["**/buildhistory/BuildHistory.java"];

var directory = __dirname.split(path.sep);
var rootDirectory = directory.slice(0, directory.indexOf("TeamCode")).join(path.sep);

//update gitignore with build history files
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

    if(!familyLine.cognomen) familyLine.cognomen = generateCognomen(computerHash);

    if(!familyLine.builds) familyLine.builds = [];

    var name = getName(familyLine.builds.length, familyLine.cognomen);
    var time = (new Date()).toISOString();
    familyLine.builds.push({
        name: name,
        time: time
    });

    familyLine.builds = familyLine.builds.slice(0, 100);

    familyLine.lastBuildTimeUnixMs = Date.now();

    fs.writeFileSync(familyLineFile, JSON.stringify(familyLine, null, 4));

    var history = fs.readdirSync(familyTreeRecordsDirectory) //
        .filter(x=>x.endsWith(".json")) //only load JSON files
        .map(x=>require(path.join(familyTreeRecordsDirectory, x))) //load the data
        .map(x=>x.builds) //narrow it down to just the build data
        .flat() //transform the array of family-build-data arrays into just 1 array
        .sort((a, b) => a.time > b.time ? -1 : 1) //sort descending by time; alphabetical will work well for ISO timestamps
        .slice(0, 100) //only get last 100 builds
        .map(x=>x.name + "," + x.time) //transform to CSV
        .join("\n") //join CSV rows together

    updateTemplate(familyLine, time, name, history);
})();

function updateTemplate(familyLine, time, name, history) {
    var template = fs.readFileSync(path.join(__dirname, "BuildHistory.notjava")).toString();
    fs.writeFileSync(path.join(__dirname, "BuildHistory.java"), template
                                .replace("BUILDER_BROWSER_FINGERPRINT", familyLine.browser)
                                .replace("BUILD_TIME_ISO", time)
                                .replace("BUILD_NAME", name)
                                .replace("BUILD_HISTORY", JSON.stringify(history).slice(1,-1))
                            )
}

function getName(index, lastName) {
    var firstNames = ["Nathan","Robert","Olivia","Carlos","Cheyenne","Miguel","Elias","Ava","Catherine","Abraham","Jordyn","Taylor","Tyler","Lydia","Cody","Serenity","Kylie","Manuel","Brian","Logan","Alicia","Brooke","Sofia","Kevin","Caitlyn","Liam","Rachel","Briana","Juan","Gianna","Lucas","Jonathan","Devin","Leah","Cooper","Collin","Sean","Jacob","Jayla","Jonah","Mya","Amber","Jacqueline","Caesar","Trevor","Christina","Francisco","Summer","Edgar","Cassidy","Garrett","Dakota","Owen","Mckenzie","Dalton","Bryan","Eduardo","Elizabeth","Kaylee","Sophia","Sebastian","Diego","Kaleb","Cassandra","Max","Eva","Sergio","Juliana","Isabel","Alondra","Reagan","Courtney","Andres","Gregory","Angelina","Vanessa","Lauren","Andrea","Layla","Savannah","Nevaeh","Wyatt","Josiah","Ayden","Abigail","Edwin","Alejandro","Jordan","Mario","Zachary","Grant","Cristian","Preston","Julia","Payton","Hannah","Miranda","Gabrielle","Marcus","Donovan","Jade","Alyssa","Marco","Caitlin","Genesis","Kaden","Ashlyn","Hector","Landon","Danielle","Maya","Erica","Mary","Brayden","Maria","Hayden","Daniela","Sydney","Gage","Mikayla","Colton","Victoria","Caleb","Mark","Carson","Ian","Kylee","Valeria","Audrey","Melissa","Jackson","Isaac","Henry","Delaney","Sarah","Connor","Giovanni","Levi","Kimberly","Damian","Paul","John","Samuel","Aaliyah","Jasmin","Crystal","Alexia","Sara","Angela","Hope","Lilly","Jared","Gavin","Leonardo","Jennifer","Oscar","Destiny","Bryce","Micah","Sophie","Evelyn","Adrian","Anna","Eric","Jada","Peyton","Julian","Kathryn","Mackenzie","Karen","Jeffrey","Kelly","Dylan","Conner","Derek","Angelica","Grace","Addison","Bianca","Kaitlyn","Jaden","Madelyn","Adam","Hailey","Karina","Edward","Colin","Skylar","Faith","Antonio","Jesse","Caroline","Dominic","Alexa","Daisy","Sadie","Christopher","Tanner","Victor","Kayla","Kennedy","Thomas","Jenna","Kate","Jillian","Amy","Andrew","Travis","Hunter","Chloe","Ana","William","Bradley","Michelle","Makayla","Jocelyn","Jeremiah","David","Stephanie","Nathaniel","Autumn","Justin","Ethan","Madison","Cole","Braden","Liliana","Ellie","Amanda","Ruby","Trinity","Amelia","Bryson","Matthew","Joel","Katelyn","Tiffany","Gabriella","Noah","Cameron","Bailey","Alana","Angel","Christian","Jayden","Sabrina","Anthony","Brendan","Jason","Kiara","Rebecca","Joshua","Ashley","Adriana","Jasmine","Laura","Parker","Natalia","Brittany","Seth","Steven","Chase","Paige","Javier","Tristan","Aaron","Alexandria","George","Veronica","Joseph","Nicholas","Claire","Naomi","Allison","Brandon","Jalen","Diana","Peter","Erik","Oliver","Kelsey","Brady","Alexander","Ryan","Elijah","Giselle","Daniel","Xavier","Mariah","Abby","Melanie","Raymond","Alan","Nicole","Alexandra","Trenton","Timothy","Benjamin","Wesley","Lucy","Marissa","Aidan","Evan","Valerie","Eli","Natalie","Malachi","Charlotte","Maxwell","Shane","Riley","Jake","Morgan","Lily","Rylee","Colby","Caden","Chelsea","Gabriela","Isaiah","Luke","Avery","Breanna","Carter","Ashton","Brooklyn","Jazmin","Alex","Jose","Gracie","Camila","Kyle","Jesus","Fernando","Martin","Shawn","Emily","Megan","Alexis","Kendall","Blake","Erick","Lindsey","Austin","Adrianna","Zoey","Kenneth","Jeremy","Jack","Haley","Isabella","Richard","Stephen","Omar","Madeline","Isabelle","Emmanuel","Devon","Brody","Ricardo","Lillian","Michael","Arianna","Ivan","Emma","Nolan","Johnathan","Samantha","Patrick","Ariana","James","Margaret","Aubrey","Nicolas","Charles","Spencer","Jessica","Luis","Molly","Makenzie","Mason","Vincent","Sierra","Zoe","Ella","Gabriel","Katie","Katherine","Mia","Jorge","Shelby","Leslie","Erin"];

    //shuffle in a pseudo-random way according to the last name
    //each cognomen has its own pattern of praenomens
    var r = random(parseInt(lastName, 36));
    firstNames = firstNames.sort((a, b) =>r() - 0.5);

    var juniorness = Math.floor(index / firstNames.length);
    if(juniorness <= 1) return firstNames[index] + " " + lastName;
    else return firstNames[index] + " " + lastName + " " + romanNumeral(index);
}

function romanNumeral(i) {
      var numerals = {M:1000,CM:900,D:500,CD:400,C:100,XC:90,L:50,XL:40,X:10,IX:9,V:5,IV:4,I:1}, roman = '', digit;
      for ( letter in numerals ) {
        while ( i >= lookup[letter] ) {
          roman += letter;
          num -= lookup[letter];
        }
      }
      return roman;
}

function generateCognomen(hash) {
    var hashNum = parseInt(hash.slice(-5), 16);

    var name = "";

    var syllables = [
        ["din", "", "eln", "dor", "a", "smi", "thi", "pai", "an", "rot", "fe"],
        ["sen", "bra", "gou", "ger", "sek", "low", "sho", "ll", "roc", "fe"],
        ["son", "ner", "man", "maner", "", "alt", "ba", "lo", "ick", "an"]
    ];

    for(var i = 0; i < syllables.length; i++) {
        var rank = syllables[i];
        name += rank[hashNum % rank.length];
        hashNum /= rank.length;
        hashNum = Math.floor(hashNum);
    }

    return name.charAt(0).toUpperCase() + name.substring(1).toLowerCase();

}

function random(seed) {
    if(seed % Math.PI == 0) seed++;

    return function() {
        var x = Math.sin(seed++) * 10000;
        seed = x;
        return x - Math.floor(x);
    }
}