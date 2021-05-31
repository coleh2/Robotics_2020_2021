var fs = require("fs");
var path = require("path");
var os = require("os");
var crypto = require("crypto");

var aaParser = require("./aa-parser.js");
var astJavaify = require("./ast-tools.js");
var parserTools = require("./parser-tools.js");

var GITIGNORED = ["*__autoauto.java"];

var DEFAULT_SERVOS = ["wobbleArmRight","wobbleArmLeft" , "wobbleGrabRight","wobbleGrabLeft", "shooterStop", "shooterArm"];
var DEFAULT_CRSERVOS = [];
var HASH_SECRET = "autoauto family";

var computerUniqueIdentifier = os.cpus()[0].model + "/" + os.hostname() + "/" + os.platform();

var computerHash = crypto.createHmac("sha256", HASH_SECRET)
    .update(computerUniqueIdentifier)
    .digest("base64");

var familyTreeRecordsDirectory = path.join(__dirname, "genealogy");
var familyLineFile = path.join(familyTreeRecordsDirectory, computerHash + ".json");

if(!fs.existsSync(familyTreeRecordsDirectory)) fs.mkdirSync(familyTreeRecordsDirectory);
if(!fs.existsSync(familyLineFile)) fs.writeFileSync(familyLineFile, "{}");

var directory = __dirname.split(path.sep);

var PACKAGE_DECLARATION = "package org.firstinspires.ftc.teamcode.__compiledautoauto;";

var template = fs.readFileSync(__dirname + path.sep + "template.notjava").toString();

var rootDirectory = directory.slice(0, directory.indexOf("TeamCode")).join(path.sep);


//update gitignore with autoauto files
var gitignore = fs.readFileSync(path.join(rootDirectory, ".gitignore")).toString();
var gitignoreLines = gitignore.split(/\r?\n/);

for(var i = 0; i < GITIGNORED.length; i++) {
    if(gitignoreLines.indexOf(GITIGNORED[i]) == -1) gitignoreLines.push(GITIGNORED[i]);
}

gitignore = gitignoreLines.join("\n");
fs.writeFileSync(path.join(rootDirectory, ".gitignore"), gitignore);

var srcDirectory = directory.slice(0, directory.indexOf("src") + 1);
var compiledResultDirectory = path.join(srcDirectory.join(path.sep), "main/java/org/firstinspires/ftc/teamcode/__compiledautoauto");
if(!fs.existsSync(compiledResultDirectory)) fs.mkdirSync(compiledResultDirectory);

var autoautoFiles = loadAutoautoFilesFromFolder(srcDirectory.join(path.sep));

for(var i = 0; i < autoautoFiles.length; i++) {
    var fileSource = fs.readFileSync(autoautoFiles[i]).toString();
    var fileName = autoautoFiles[i].substring(autoautoFiles[i].lastIndexOf(path.sep) + 1);
    var className = fileName.replace(".autoauto", "__autoauto");
    var javaFileName = className + ".java";

    var resultFile = compiledResultDirectory + "/" + javaFileName;

    if(fileSource.trim() == "") {
        console.warn("WARNING: Empty autoauto file " + className)
        fs.writeFileSync(resultFile, "");
        continue;
    }

    var uncommentedFileSource = parserTools.stripComments(fileSource);

    var frontMatter = stripAndParseFrontMatter(uncommentedFileSource);

    console.log("frontmatter : " + JSON.stringify(frontMatter.frontMatter));

    var javaStringFileSource = frontMatter.stripped;

    try {
        var parsedModel = aaParser.parse(fileSource);

        var javaCreationCode = astJavaify(parsedModel);

        fs.writeFileSync(resultFile, processTemplate(template, className, frontMatter.frontMatter, javaStringFileSource, javaCreationCode, autoautoFiles[i]));
    } catch(e) {
        console.error(autoautoFiles[i] + ":" + (e.location ? e.location.start.line + ":" + e.location.start.column + ":" : "") + "\t" + e.toString());
        if(!e.location) console.error(e.stack);
        process.exit(1);
    }

}

function processTemplate(template, className, frontMatter, javaStringFileSource, javaCreationCode, sourceFileName) {
    return template
        .replace("public class template", "public class " + className)
        .replace("/*NSERVO_NAMES*/", buildServoNames(frontMatter.servos))
        .replace("/*NSERVOS*/", buildServos(frontMatter.servos))
        .replace("/*JAVA_CREATION_CODE*/", javaCreationCode)
        .replace("/*CRSERVO_NAMES*/", buildCrServoNames(frontMatter.crServos))
        .replace("/*CRSERVOS*/", buildCrServos(frontMatter.crServos))
        .replace("/*PACKAGE_DECLARATION*/", PACKAGE_DECLARATION)
        .replace("/*TEST_ITERATIONS*/",  (frontMatter.testIterations === undefined ? 3 : frontMatter.testIterations))
        .replace("/*BUILD_NAME*/", genRandomBuildName())
        .replace("/*SOURCE_FILE_NAME*/", JSON.stringify(sourceFileName).slice(1, -1));
}

function buildServoNames(servos) {
    if(servos === undefined) servos = DEFAULT_SERVOS;
    return servos.map(x=> `"${x}"`).join(", ");
}

function buildCrServoNames(crServos) {
    if(crServos === undefined) crServos = DEFAULT_CRSERVOS;
    return crServos.map(x=> `"${x}"`).join(", ");
}

function buildCrServos(crServos) {
    if(crServos === undefined) crServos = DEFAULT_CRSERVOS;
    return crServos.map(x=> `hardwareMap.get(CRServo.class, "${x}")`).join(", ");
}

function buildServos(servos) {
    if(servos === undefined) servos = DEFAULT_SERVOS;
    return servos.map(x=> `hardwareMap.get(Servo.class, "${x}")`).join(", ");
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

function genRandomBuildName() {
    var nouns = [ "Armour","Barrymore","Cabot","Catholicism","Chihuahua","Christianity","Easter","Frenchman","Lowry","Mayer","Orientalism","Pharaoh","Pueblo","Pullman","Rodeo","Saturday","Sister","Snead","Syrah","Tuesday","Woodward","abbey","absence","absorption","abstinence","absurdity","abundance","acceptance","accessibility","accommodation","accomplice","accountability","accounting","accreditation","accuracy","acquiescence","acreage","actress","actuality","adage","adaptation","adherence","adjustment","adoption","adultery","advancement","advert","advertisement","advertising","advice","aesthetics","affinity","aggression","agriculture","aircraft","airtime","allegation","allegiance","allegory","allergy","allies","alligator","allocation","allotment","altercation","ambulance","ammonia","anatomy","anemia","ankle","announcement","annoyance","annuity","anomaly","anthropology","anxiety","apartheid","apologise","apostle","apparatus","appeasement","appellation","appendix","applause","appointment","appraisal","archery","archipelago","architecture","ardor","arrears","arrow","artisan","artistry","ascent","assembly","assignment","association","asthma","atheism","attacker","attraction","attractiveness","auspices","authority","avarice","aversion","aviation","babbling","backlash","baker","ballet","balls","banjo","baron","barrier","barrister","bases","basin","basis","battery","battling","bedtime","beginner","begun","bending","bicycle","billing","bingo","biography","biology","birthplace","blackberry","blather","blossom","boardroom","boasting","bodyguard","boldness","bomber","bondage","bonding","bones","bonus","bookmark","boomer","booty","bounds","bowling","brainstorming","breadth","breaker","brewer","brightness","broccoli","broth","brotherhood","browsing","brunch","brunt","building","bullion","bureaucracy","burglary","buyout","by-election","cabal","cabbage","calamity","campaign","canonization","captaincy","carcass","carrier","cartridge","cassette","catfish","caught","celebrity","cemetery","certainty","certification","charade","chasm","check-in","cheerleader","cheesecake","chemotherapy","chili","china","chivalry","cholera","cilantro","circus","civilisation","civility","clearance","clearing","clerk","climber","closeness","clothing","clutches","coaster","coconut","coding","collaborator","colleague","college","collision","colors","combustion","comedian","comer","commander","commemoration","commenter","commissioner","commune","competition","completeness","complexity","computing","comrade","concur","condominium","conduit","confidant","configuration","confiscation","conflagration","conflict","consist","consistency","consolidation","conspiracy","constable","consul","consultancy","contentment","contents","contractor","conversation","cornerstone","corpus","correlation","councilman","counselor","countdown","countryman","coverage","covering","coyote","cracker","creator","criminality","crocodile","cropping","cross-examination","crossover","crossroads","culprit","cumin","curator","curfew","cursor","custard","cutter","cyclist","cyclone","cylinder","cynicism","daddy","damsel","darkness","dawning","daybreak","dealing","dedication","deduction","defection","deference","deficiency","definition","deflation","degeneration","delegation","delicacy","delirium","deliverance","demeanor","demon","demonstration","denomination","dentist","departure","depletion","depression","designation","despotism","detention","developer","devolution","dexterity","diagnosis","dialect","differentiation","digger","digress","dioxide","diploma","disability","disarmament","discord","discovery","dishonesty","dismissal","disobedience","dispatcher","disservice","distribution","distributor","diver","diversity","docking","dollar","dominance","domination","dominion","donkey","doorstep","doorway","dossier","downside","drafting","drank","drilling","driver","drumming","drunkenness","duchess","ducking","dugout","dumps","dwelling","dynamics","eagerness","earnestness","earnings","eater","editor","effectiveness","electricity","elements","eloquence","emancipation","embodiment","embroidery","emperor","employment","encampment","enclosure","encouragement","endangerment","enlightenment","enthusiasm","environment","environs","envoy","epilepsy","equation","equator","error","espionage","estimation","evacuation","exaggeration","examination","exclamation","expediency","exploitation","extinction","eyewitness","falls","fascism","fastball","feces","feedback","ferocity","fertilization","fetish","finale","firing","fixing","flashing","flask","flora","fluke","folklore","follower","foothold","footing","forefinger","forefront","forgiveness","formality","formation","formula","foyer","fragmentation","framework","fraud","freestyle","frequency","friendliness","fries","frigate","fulfillment","function","functionality","fundraiser","fusion","futility","gallantry","gallery","genesis","genitals","girlfriend","glamour","glitter","glucose","google","grandeur","grappling","greens","gridlock","grocer","groundwork","grouping","gunman","gusto","habitation","hacker","hallway","hamburger","hammock","handling","hands","handshake","happiness","hardship","headcount","header","headquarters","heads","headset","hearth","hearts","heath","hegemony","height","hello","helper","helping","helplessness","hierarchy","hoarding","hockey","homeland","homer","honesty","horror","horseman","hostility","housing","humility","hurricane","iceberg","ignition","illness","illustration","illustrator","immunity","immunization","imperialism","imprisonment","inaccuracy","inaction","inactivity","inauguration","indecency","indicator","inevitability","infamy","infiltration","influx","iniquity","innocence","innovation","insanity","inspiration","instruction","instructor","insurer","interact","intercession","intercourse","intermission","interpretation","intersection","interval","intolerance","intruder","invasion","investment","involvement","irrigation","iteration","jenny","jogging","jones","joseph","juggernaut","juncture","jurisprudence","juror","kangaroo","kingdom","knocking","laborer","larceny","laurels","layout","leadership","leasing","legislation","leopard","liberation","licence","lifeblood","lifeline","ligament","lighting","likeness","line-up","lineage","liner","lineup","liquidation","listener","literature","litigation","litre","loathing","locality","lodging","logic","longevity","lookout","lordship","lustre","ma'am","machinery","madness","magnificence","mahogany","mailing","mainframe","maintenance","majority","manga","mango","manifesto","mantra","manufacturer","maple","martin","martyrdom","mathematician","matrix","matron","mayhem","mayor","means","meantime","measurement","mechanics","mediator","medics","melodrama","memory","mentality","metaphysics","method","metre","miner","mirth","misconception","misery","mishap","misunderstanding","mobility","molasses","momentum","monarchy","monument","morale","mortality","motto","mouthful","mouthpiece","mover","movie","mowing","murderer","musician","mutation","mythology","narration","narrator","nationality","negligence","neighborhood","neighbour","nervousness","networking","nexus","nightmare","nobility","nobody","noodle","normalcy","notification","nourishment","novella","nucleus","nuisance","nursery","nutrition","nylon","oasis","obscenity","obscurity","observer","offense","onslaught","operation","opportunity","opposition","oracle","orchestra","organisation","organizer","orientation","originality","ounce","outage","outcome","outdoors","outfield","outing","outpost","outset","overseer","owner","oxygen","pairing","panther","paradox","parliament","parsley","parson","passenger","pasta","patchwork","pathos","patriotism","pendulum","penguin","permission","persona","perusal","pessimism","peter","philosopher","phosphorus","phrasing","physique","piles","plateau","playing","plaza","plethora","plurality","pneumonia","pointer","poker","policeman","polling","poster","posterity","posting","postponement","potassium","pottery","poultry","pounding","pragmatism","precedence","precinct","preoccupation","pretense","priesthood","prisoner","privacy","probation","proceeding","proceedings","processing","processor","progression","projection","prominence","propensity","prophecy","prorogation","prospectus","protein","prototype","providence","provider","provocation","proximity","puberty","publicist","publicity","publisher","pundit","putting","quantity","quart","quilting","quorum","racism","radiance","ralph","rancher","ranger","rapidity","rapport","ratification","rationality","reaction","reader","reassurance","rebirth","receptor","recipe","recognition","recourse","recreation","rector","recurrence","redemption","redistribution","redundancy","refinery","reformer","refrigerator","regularity","regulator","reinforcement","reins","reinstatement","relativism","relaxation","rendition","repayment","repentance","repertoire","repository","republic","reputation","resentment","residency","resignation","restaurant","resurgence","retailer","retention","retirement","reviewer","riches","righteousness","roadblock","robber","rocks","rubbing","runoff","saloon","salvation","sarcasm","saucer","savior","scarcity","scenario","scenery","schism","scholarship","schoolboy","schooner","scissors","scolding","scooter","scouring","scrimmage","scrum","seating","sediment","seduction","seeder","seizure","self-confidence","self-control","self-respect","semicolon","semiconductor","semifinal","senator","sending","serenity","seriousness","servitude","sesame","setup","sewing","sharpness","shaving","shoplifting","shopping","siding","simplicity","simulation","sinking","skate","sloth","slugger","snack","snail","snapshot","snark","soccer","solemnity","solicitation","solitude","somewhere","sophistication","sorcery","souvenir","spaghetti","specification","specimen","specs","spectacle","spectre","speculation","sperm","spoiler","squad","squid","staging","stagnation","staircase","stairway","stamina","standpoint","standstill","stanza","statement","stillness","stimulus","stocks","stole","stoppage","storey","storyteller","stylus","subcommittee","subscription","subsidy","suburb","success","sufferer","supposition","suspension","sweater","sweepstakes","swimmer","syndrome","synopsis","syntax","system","tablespoon","taker","tavern","technology","telephony","template","tempo","tendency","tendon","terrier","terror","terry","theater","theology","therapy","thicket","thoroughfare","threshold","thriller","thunderstorm","ticker","tiger","tights","to-day","tossing","touchdown","tourist","tourney","toxicity","tracing","tractor","translation","transmission","transmitter","trauma","traveler","treadmill","trilogy","trout","tuning","twenties","tycoon","tyrant","ultimatum","underdog","underwear","unhappiness","unification","university","uprising","vaccination","validity","vampire","vanguard","variation","vegetation","verification","viability","vicinity","victory","viewpoint","villa","vindication","violation","vista","vocalist","vogue","volcano","voltage","vomiting","vulnerability","waistcoat","waitress","wardrobe","warmth","watchdog","wealth","weariness","whereabouts","whisky","whiteness","widget","width","windfall","wiring","witchcraft","withholding","womanhood","words","workman","youngster" ];
    var infinitiveVerbs = ["accept","add","admire","admit","advise","afford","agree","alert","allow","amuse","analyse","announce","annoy","answer","apologise","appear","applaud","appreciate","approve","argue","arrange","arrest","arrive","ask","attach","attack","attempt","attend","attract","avoid","back","bake","balance","ban","bang","bare","bat","bathe","battle","beam","beg","behave","belong","bleach","bless","blind","blink","blot","blush","boast","boil","bolt","bomb","book","bore","borrow","bounce","bow","box","brake","branch","breathe","bruise","brush","bubble","bump","burn","bury","buzz","calculate","call","camp","care","carry","carve","cause","challenge","change","charge","chase","cheat","check","cheer","chew","choke","chop","claim","clap","clean","clear","clip","close","coach","coil","collect","colour","comb","command","communicate","compare","compete","complain","complete","concentrate","concern","confess","confuse","connect","consider","consist","contain","continue","copy","correct","cough","count","cover","crack","crash","crawl","cross","crush","cry","cure","curl","curve","cycle","dam","damage","dance","dare","decay","deceive","decide","decorate","delay","delight","deliver","depend","describe","desert","deserve","destroy","detect","develop","disagree","disappear","disapprove","disarm","discover","dislike","divide","double","doubt","drag","drain","dream","dress","drip","drop","drown","drum","dry","dust","earn","educate","embarrass","employ","empty","encourage","end","enjoy","enter","entertain","escape","examine","excite","excuse","exercise","exist","expand","expect","explain","explode","extend","face","fade","fail","fancy","fasten","fax","fear","fence","fetch","file","fill","film","fire","fit","fix","flap","flash","float","flood","flow","flower","fold","follow","fool","force","form","found","frame","frighten","fry","gather","gaze","glow","glue","grab","grate","grease","greet","grin","grip","groan","guarantee","guard","guess","guide","hammer","hand","handle","hang","happen","harass","harm","hate","haunt","head","heal","heap","heat","help","hook","hop","hope","hover","hug","hum","hunt","hurry","identify","ignore","imagine","impress","improve","include","increase","influence","inform","inject","injure","instruct","intend","interest","interfere","interrupt","introduce","invent","invite","irritate","itch","jail","jam","jog","join","joke","judge","juggle","jump","kick","kill","kiss","kneel","knit","knock","knot","label","land","last","laugh","launch","learn","level","license","lick","lie","lighten","like","list","listen","live","load","lock","long","look","love","man","manage","march","mark","marry","match","mate","matter","measure","meddle","melt","memorise","mend","mess up","milk","mine","miss","mix","moan","moor","mourn","move","muddle","mug","multiply","murder","nail","name","need","nest","nod","note","notice","number","obey","object","observe","obtain","occur","offend","offer","open","order","overflow","owe","own","pack","paddle","paint","park","part","pass","paste","pat","pause","peck","pedal","peel","peep","perform","permit","phone","pick","pinch","pine","place","plan","plant","play","please","plug","point","poke","polish","pop","possess","post","pour","practise","pray","preach","precede","prefer","prepare","present","preserve","press","pretend","prevent","prick","print","produce","program","promise","protect","provide","pull","pump","punch","puncture","punish","push","question","queue","race","radiate","rain","raise","reach","realise","receive","recognise","record","reduce","reflect","refuse","regret","reign","reject","rejoice","relax","release","rely","remain","remember","remind","remove","repair","repeat","replace","reply","report","reproduce","request","rescue","retire","return","rhyme","rinse","risk","rob","rock","roll","rot","rub","ruin","rule","rush","sack","sail","satisfy","save","saw","scare","scatter","scold","scorch","scrape","scratch","scream","screw","scribble","scrub","seal","search","separate","serve","settle","shade","share","shave","shelter","shiver","shock","shop","shrug","sigh","sign","signal","sin","sip","ski","skip","slap","slip","slow","smash","smell","smile","smoke","snatch","sneeze","sniff","snore","snow","soak","soothe","sound","spare","spark","sparkle","spell","spill","spoil","spot","spray","sprout","squash","squeak","squeal","squeeze","stain","stamp","stare","start","stay","steer","step","stir","stitch","stop","store","strap","strengthen","stretch","strip","stroke","stuff","subtract","succeed","suck","suffer","suggest","suit","supply","support","suppose","surprise","surround","suspect","suspend","switch","talk","tame","tap","taste","tease","telephone","tempt","terrify","test","thank","thaw","tick","tickle","tie","time","tip","tire","touch","tour","tow","trace","trade","train","transport","trap","travel","treat","tremble","trick","trip","trot","trouble","trust","try","tug","tumble","turn","twist","type","undress","unfasten","unite","unlock","unpack","untidy","use","vanish","visit","wail","wait","walk","wander","want","warm","warn","wash","waste","watch","water","wave","weigh","welcome","whine","whip","whirl","whisper","whistle","wink","wipe","wish","wobble","wonder","work","worry","wrap","wreck","wrestle","wriggle","x-ray","yawn","yell","zip","zoom"];

    return "Build Codename: " + titleCase(randomFrom(infinitiveVerbs)) + " the " + titleCase(randomFrom(nouns));
}

function titleCase(str) {
    return str.charAt(0).toUpperCase() + str.substring(1).toLowerCase();
}

function randomFrom(arr) {
    return arr[Math.floor(Math.random() * arr.length)];
}