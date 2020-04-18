var mysqlx = require('mysqlx');

function createTestTable(session, name) {
  var create = 'CREATE TABLE ';
  create += name;
  create += ' (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT)';

  return session
    .sql('DROP TABLE IF EXISTS ' + name)
    .execute()
    .then(function () {
      return session.sql(create).execute();
    });
}

var session;

mysqlx
  .getSession({
    user: 'zh',
    password: '123'
  })
  .then(function (s) {
    session = s;

    return session
      .sql('use mydb')
      .execute()
  })
  .then(function () {
    // Creates some tables
    return Promise.map([
      createTestTable(session, 'test1'),
      createTestTable(session, 'test2')
    ])
  })
  .then(function () {
    session.close();
  })
});