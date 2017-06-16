var alphas = [
    {"name": "Stakeholders" },
    {"name": "Oportunnity" },
    {"name": "Software System"},
    {"name": "Work"},
    {"name": "Way of Working"},
    {"name": "Team"},
    {"name": "Requirements"}
];
var states = [ [
    {"name": "Recognized"},
    {"name": "Represented"},
    {"name": "Involved"},
    {"name": "In Agreement"},
    {"name": "Satisfied for Deployment"},
    {"name": "Satisfied in Use"}
],[
    {"name": "Identified"},
    {"name": "Solution Needed"},
    {"name": "Value Established"},
    {"name": "Viable"},
    {"name": "Addressed"},
    {"name": "Benefit Accrued"}
],[
    {"name": "Architecture Selected"},
    {"name": "Demonstrable"},
    {"name": "Usable"},
    {"name": "Ready"},
    {"name": "Operational"},
    {"name": "Retired"}
],[
    {"name": "Initiated"},
    {"name": "Prepared"},
    {"name": "Started"},
    {"name": "Under Control"},
    {"name": "Concluded"},
    {"name": "Closed"}
],[
    {"name": "Principles Established"},
    {"name": "Foundation Established"},
    {"name": "In Use"},
    {"name": "In Place"},
    {"name": "Working Well"},
    {"name": "Retired"}
],[
    {"name": "Seeded"},
    {"name": "Formed"},
    {"name": "Collaborating"},
    {"name": "Performing"},
    {"name": "Adjourned"}
],[
    {"name": "Conceived"},
    {"name": "Bounded"},
    {"name": "Coherent"},
    {"name": "Acceptable"},
    {"name": "Addressed"},
    {"name": "Fulfilled"}
]
];
var checklists = [ [ [
    {"name" : "Stakeholders groups identified"},
    {"name": "Key stakeholder groups represented"},
    {"name": "Responsibilities defined"}
],[
    {"name": "Responsibilities agreed"},
    {"name": "Representatives authorized"},
    {"name": "Collaboration approach agreed"},
    {"name": "Way of working supported & respected"}
],[
    {"name": "Representatives assist the team"},
    {"name": "Timely feedback and decisions provided"},
    {"name": "Changes promptly communicated"}
],[
    {"name": "Minimal expectations agreed"},
    {"name": "Rep's happy with their involvement"},
    {"name": "Rep's input valued"},
    {"name": "Team's input valued"},
    {"name": "Priorities clear & perspectives balanced"}
],[
    {"name": "Stakeholder feedback provided"},
    {"name": "System ready for deployment"}
],[
    {"name": "Feedback on system use available"},
    {"name": "System meets expectations"}
],
], [ [
    {"name": "Idea behind opportunity identified"},
    {"name": "At least one investing stakeholder interested"},
    {"name": "Other stakeholders identified"}
], [
    {"name": "Solution identified"},
    {"name": "Stakeholder's needs established"},
    {"name": "Problems and root causes identified"},
    {"name": "Need for a solution confirmed"},
    {"name": "At least one solution proposed"}
],[
    {"name": "Opportunity value quantified"},
    {"name": "Solution impact understood"},
    {"name": "System value understood"},
    {"name": "Success criteria clear"},
    {"name": "Outcomes clear and quantified"}
], [
    {"name": "Solution outlined"},
    {"name": "Solution possible within constraints"},
    {"name": "Risk acceptable & manageable"},
    {"name": "Solution profitable"},
    {"name": "Reasons to develop solution understood"},
    {"name": "Pursuit viable"}
], [
    {"name": "Opportunity addressed"},
    {"name": "Solution worth deploying"},
    {"name": "Stakeholders satisfied"}
],[
    {"name": "Solution accrues benefits"},
    {"name": "ROI acceptable"}
]
], [ [
    {"name": "Architecture selection criteria agrees"},
    {"name": "HW platforms identified"},
    {"name": "Technologies selected"},
    {"name": "System boundary known "},
    {"name": "Decisions on system organization made"},
    {"name": "Buy, build, reuse decisions made"},
    {"name": "Key technical risks agreed to"}
],[
    {"name": "Key architectural characteristics demonstrated"},
    {"name": "System exercised & performance measured"},
    {"name": "Critical HW configurations demonstrated"},
    {"name": "Critical interfaces demonstrated"},
    {"name": "Integration with enviroment monstrated"},
    {"name": "Architecture accepted as fit-for-purpose"}
],[
    {"name": "System can be operated"},
    {"name": "System functionality tested"},
    {"name": "System performance acceptable"},
    {"name": "Defect levels acceptable"},
    {"name": "System fully documented"},
    {"name": "Release content known"},
    {"name": "Added value clear"}
], [
    {"name": "User documentation available"},
    {"name": "System accepted as fit-for-purpose"},
    {"name": "Stakeholders want the system"},
    {"name": "Operational support in place"}
], [
    {"name": "System available for use"},
    {"name": "System live"},
    {"name": "Agreed service levels supported"}
], [
    {"name": "Replaced or discontinued"},
    {"name": "No longer supported"},
    {"name": "No authorized users"},
    {"name": "Updates stopped"}
]
], [ [
    {"name": "Required result clear"},
    {"name": "Constraints clear"},
    {"name": "Funding stakeholders known"},
    {"name": "Initiator identified"},
    {"name": "Accepting stakeholders known"},
    {"name": "Source of funding clear"},
    {"name": "Priority clear"}
], [
    {"name": "Commitment made"},
    {"name": "Cost and effort estimated"},
    {"name": "Resource availability understood"},
    {"name": "Risk exposure understood"},
    {"name": "Acceptance criteria established"},
    {"name": "Sufficiently broken down to start"},
    {"name": "Tasks identified and prioritized"},
    {"name": "Credible plan in place"},
    {"name": "Funding in place"},
    {"name": "At least one team member ready"},
    {"name": "Integration points defined"}
], [
    {"name": "Development started"},
    {"name": "Progress monitored"},
    {"name": "Definition of done in place"},
    {"name": "Tasks being progressed"}
], [
    {"name": "Tasks being completed"},
    {"name": "Unplanned work under control"},
    {"name": "Risks under control"},
    {"name": "Estimates revised to reflect performance"},
    {"name": "Progress measured"},
    {"name": "Re-work under control"},
    {"name": "Commitments consistently meet"}
], [
    {"name": "Only admin tasks left"},
    {"name": "Results achieved"},
    {"name": "Resulting system accepted"}
], [
    {"name": "Lessons learned"},
    {"name": "Metrics available"},
    {"name": "Everything archived"},
    {"name": "Budget reconciled & closed"},
    {"name": "Team released"},
    {"name": "No outstanding, uncompleted tasks"}
]
], [ [
    {"name": "Team actively support principles"},
    {"name": "Stakeholders agree with principles"},
    {"name": "Tool needs agreed"},
    {"name": "Approach recommended"},
    {"name": "Operational context understood"},
    {"name": "Practice & tool constraints known"}
], [
    {"name": "Key practices & tools selected"},
    {"name": "Practices needed to start work agrees"},
    {"name": "Non-negotiable practices & tools identified"},
    {"name": "Gaps between available and needed way of working understood"},
    {"name": "Gaps in capability understood"},
    {"name": "Integrated way of working available"}
], [
    {"name": "Practices & tools in use"},
    {"name": "Regularly inspected"},
    {"name": "Adapted to context"},
    {"name": "Supported by team"},
    {"name": "Feedback mechanisms in place"},
    {"name": "Practices & tools support collaboration"}
], [
    {"name": "Used by whole team"},
    {"name": "Accessible to whole team"},
    {"name": "Inspected and adapted by whole team"}
], [
    {"name": "Predictable progress being made"},
    {"name": "Practices naturally applied"},
    {"name": "Tools naturally support way-of-working"},
    {"name": "Continually tuned"}
], [
    {"name": "No longer in use"},
    {"name": "Lessons learned shared"}
]
], [ [
    {"name": "Mission defined"},
    {"name": "Constraints known and defined"},
    {"name": "Growth mechanisms in place"},
    {"name": "Composition defined"},
    {"name": "Responsibilitied outlined"},
    {"name": "Required commitment level clear"},
    {"name": "Required competencies identified"},
    {"name": "Size determined"},
    {"name": "Governance rules defines"},
    {"name": "Leadership model selected"}
], [
    {"name": "Enough members recruited"},
    {"name": "Roles understood"},
    {"name": "How to work understood"},
    {"name": "Members introduced"},
    {"name": "Individual responsibilities accepted and aligned to competencies"},
    {"name": "Members accepting work"},
    {"name": "External collaborators identified"},
    {"name": "Communication mechanisms defined"},
    {"name": "Members commit to team"}
], [
    {"name": "Works as one unit"},
    {"name": "Communication open and honest"},
    {"name": "Focused on mission"},
    {"name": "Members know each other"}
], [
    {"name": "Consistently meeting commitments"},
    {"name": "Continuosly adapting to change"},
    {"name": "Addresses problems"},
    {"name": "Rework and backtracking minimized"},
    {"name": "Waste continuously eliminated"}
], [
    {"name": "Responsibilities fulfilled"},
    {"name": "Members available to other teams"},
    {"name": "Mission concluded"}
]
], [ [
    {"name": "Stakeholders agree system is to be produces"},
    {"name": "Users identified"},
    {"name": "Funding stakeholders identified"},
    {"name": "Opportunity clear"}
], [
    {"name": "Development stakeholders identified"},
    {"name": "System purpose agreed"},
    {"name": "System success clear"},
    {"name": "Shared solution understanding exists"},
    {"name": "Requirements management in place"},
    {"name": "Prioritization scheme clear"},
    {"name": "Constraints identified & considered"},
    {"name": "Assumptions clear"}
], [
    {"name": "Requirements shared"},
    {"name": "Requirements' origin clear"},
    {"name": "Rationale clear"},
    {"name": "Conflicts addressed"},
    {"name": "Essential characteristics clear"},
    {"name": "Key usage scenarios explained"},
    {"name": "Priorities clear"},
    {"name": "Impact understood"},
    {"name": "Team knows & agrees on what to deliver"}
], [
    {"name": "Acceotable solution described"},
    {"name": "Change under control"},
    {"name": "Value to be realized clear"},
    {"name": "Clear how opportunity addressed"},
    {"name": "Testable"}
], [
    {"name": "Enough addressed to be acceptable"},
    {"name": "Requirements and system match"},
    {"name": "Value realized clear"},
    {"name": "System worth making operational"}
], [
    {"name": "Stakeholders accept requirements"},
    {"name": "No hindering requirements"},
    {"name": "Requirements fully satisfied"}
]
]
];

var MongoClient = require('mongodb').MongoClient;
var ObjectID = require('mongodb').ObjectID;

// Connect to the db
MongoClient.connect("mongodb://localhost:27017/sekc", function(err, db) {
    if (err) {
        return console.dir(err);
    }

    var collectionAlphas = db.collection('alphas');
    var collectionStates = db.collection('states');
    var collectionCheckpoints = db.collection('checkpoints');

    var insertDocToCollection = function (collection, doc, callback) {
        collection.insertOne(doc, function (err, result) {
            callback(ObjectID(result.insertedId));
        });
    };
    var insertAlphas = function () {
        var i = 0;
        var checkAndContinue = function () {
            if (i < alphas.length) {
                console.log("Constructing documents for ALPHA " + alphas[i]['name']);
                insertDocToCollection(collectionAlphas, alphas[i], function (insertedID) {
                    insertStates(i, insertedID, function (insertedIDs) {
                        //Enlaces por referencia
                        var formatedDocs = [];
                        insertedIDs.forEach(function (part, index, theArray) {
                            formatedDocs.push({
                                "$ref": "states",
                                "$id": insertedIDs[index],
                                "$db": "sekc"
                            })
                        });
                        collectionAlphas.updateOne({"_id": insertedID}, {$set: {"states": formatedDocs}});

                        i++;
                        checkAndContinue();
                    });
                });
            } else {
                db.close();
                process.exit();
            }
        };
        checkAndContinue();
    };
    var insertStates = function (indice, idDocAlpha, callback) {
        var statedInsertedIDs = [];
        var j = 0;
        var checkAndContinue = function () {
            if (j < states[indice].length) {
                var docState = states[indice][j];
                docState['alpha'] = idDocAlpha;
                insertDocToCollection(collectionStates, docState, function (insertedID) {
                    statedInsertedIDs.push(insertedID);
                    insertCheckPoints(indice, j, insertedID, function (insertedIDs) {

                        //Crear enlaces por referencia
                        var formatedDocs = [];
                        insertedIDs.forEach(function (part, index, theArray) {
                            formatedDocs.push({
                                "$ref": "checkpoints",
                                "$id": insertedIDs[index],
                                "$db": "sekc"
                            })
                        });
                        collectionStates.updateOne({"_id": insertedID}, {$set: {"checkListItem": formatedDocs}});
                        j++;
                        checkAndContinue();
                    });
                });
            } else
                callback(statedInsertedIDs);
        };
        checkAndContinue();

    };
    var insertCheckPoints = function (indice, indicej, idDocState, callback) {
        var checkPointInsertedIDs = [];
        var k = 0;
        var checkAndContinue = function () {
            if (k < checklists[indice][indicej].length) {
                var docCheckPoint = checklists[indice][indicej][k];
                docCheckPoint['description'] = docCheckPoint['name'];
                docCheckPoint['shortDescription'] = '';
                docCheckPoint['state'] = idDocState;
                insertDocToCollection(collectionCheckpoints, docCheckPoint, function (insertedID) {
                    checkPointInsertedIDs.push(insertedID);
                    k++;
                    checkAndContinue();
                });
            } else
                callback(checkPointInsertedIDs);
        };
        checkAndContinue();
    };
    insertAlphas();
});
