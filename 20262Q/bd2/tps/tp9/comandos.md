

Lo que se ejecutó (inicio de la guía), dentro de `mongosh`:

```
# Comandos básicos
use lab
db.getCollectionNames()
db.players.insertOne({name:'Aaron Appindangoye', height: 182, weight:187})
db.getCollectionNames()
db.players.find()
db.players.insertOne({name:'Edinson Cavani', height: 182, dob: new Date(1987,2,14,0,0)})
db.players.deleteMany({})

# Inserts
db.players.insertOne({name:'Luka Modric', height: 180, weight:143, dob: new Date (1985,9,9,0,0), preferred_foot: 'right', hobbies:['Playing football','Watching TV series','Swimming']});
db.players.insertOne({name:'Harry Kane', weight:143, dob: new Date(1993,7,28,0,0),preferred_foot: 'right', hobbies:['Watching Movies','Swimming']});
db.players.insertOne({name:'Neymar', height: 175, weight:150, dob: new Date(1992,2,5,0,0), preferred_foot: 'right', hobbies:['Video games','Watching TVseries','Swimming']});
db.players.insertOne({name:'David Silva', height: 170, weight:148, dob: new Date(1986,8,1,0,0), preferred_foot: 'left', hobbies:['Playing football', 'Watching Movies']});
db.players.insertOne({name:'Eden Hazard', height: 172, weight:163, dob: new Date(1991,1,7,0,0), preferred_foot: 'right', hobbies:['Watching Movies','Video games','Watching TV series']});
db.players.insertOne({name:'Antoine Griezmann', height: 175, weight:148, dob: new Date(1991,3,21,0,0), preferred_foot: 'left', hobbies:['Video games','Swimming']});
db.players.insertOne({name:'Lionel Messi', height: 170, weight:159, dob: new Date(1987,6,24,0,0), preferred_foot: 'left', hobbies:['Watching Movies','Video games','Watching TV series','Swimming']});
db.players.insertOne({name:'Cristiano Ronaldo', height: 185, weight:176, dob: new Date(1985,2,5,0,0), preferred_foot: 'left', hobbies:['Video games','Swimming']});
db.players.insertOne({name:'Toni Kroos', height: 182, weight:172, dob: new Date(1990,1,4,0,0), preferred_foot: 'right', hobbies:['Watching Movies','Video games','Watching TV series']});
db.players.insertOne({name:'Sergio Ramos', height: 182, weight:165, dob: new Date(1986,3,30,0,0), preferred_foot: 'right', hobbies:['Video games','Watching TV series']});
db.players.insertOne({name:'Samuel Umtiti', height: 180, weight:165, dob: new Date(1993,11,14,0,0), preferred_foot: 'left', hobbies:['Playing football','Swimming']});
db.players.insertOne({name:'Paulo Dybala', height: 175, weight:161, dob: new Date(1993,11,15,0,0), preferred_foot: 'left', hobbies:['Playing football','Watching TV series','Swimming']});

# Selectores
db.players.find({preferred_foot: 'left', weight:{$gt:170}})
db.players.find({height:{$exists:false}})
db.players.find({preferred_foot:'right', $or:[{hobbies:'Playing football'}, {hobbies:'Video games'}, {weight:{$lt:150}}]})

# Expresiones regulares
db.players.find({name:{ $regex: "^S"}})
db.players.find({name: {$regex: "o$"}})

# Comandos de actualización
db.players.updateOne({name: 'Lionel Messi'},{$set:{weight:180}})
db.players.updateOne({name: 'Luka Modric'},{$inc:{height:-5}})
db.players.updateOne({name: 'David Silva'},{$push:{hobbies:'Swimming'}})
db.hits.updateOne({page: 'players'}, {$inc:{hits:1}}, {upsert:true});
db.hits.updateOne({page: 'players'}, {$inc:{hits:1}}, {upsert:true});
db.hits.find()
db.players.updateMany({}, {$set:{active:true}});

# Selección de campos
db.players.find({},{name:1});
db.players.find({},{name:1, _id:0});
db.players.find({},{name:1, height:1,_id:0}).sort({height:-1})
db.players.find().sort({weight:-1}).limit(2).skip(1)
db.players.countDocuments({hobbies:'Swimming'})
db.players.find({hobbies:'Swimming'}).count()

# Documentos embebidos
db.players.updateOne({name: 'Cristiano Ronaldo'},{$set:{team:{ team_long_name: 'Juventus', team_short_name: 'JUV'}}})
db.players.find({'team.team_short_name': 'JUV'})

# Índices y administración
db.players.ensureIndex({name:1})
db.players.dropIndex({name:1})
db.players.ensureIndex({name:1},{unique:true})
db.players.ensureIndex({name:1, weight:1})
db.players.find({name: 'Lionel Messi'}).explain()

```