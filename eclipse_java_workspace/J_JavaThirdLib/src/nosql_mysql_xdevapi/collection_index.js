
var mysqlx = require('mysqlx');
var session;
mysqlx.getSession({
    user: 'zh',
    password: '123'
  })
  .then(function (s) {
    session = s;
    return session
      .sql('use mydb')
      .execute()
  });


//OK
db.my_collection.createIndex("age", {fields:[{"field": "$.age", "type":"INT", "required":true}]});
db.my_collection.dropIndex("age");
//OK
db.my_collection.createIndex('myComIndex', {fields: [{field: '$.age', type: 'INT'},{field: '$.username', type: 'TEXT(10)'}]}) 
db.my_collection.dropIndex('myComIndex');
