#--!/usr/bin/python3

import pymysql

# 打开数据库连接
#db = pymysql.connect("localhost", "testuser", "test123", "TESTDB")
db = pymysql.connect("172.16.35.10", "OP", "abcd_1234", "srm")

cursor = db.cursor()
cursor.execute("SELECT VERSION()")

# 使用 fetchone() 方法获取单条数据.
data = cursor.fetchone()
print("Database version : %s " % data)

try:
 #   cursor.execute("DROP TABLE IF EXISTS EMPLOYEE")
 sql = """CREATE TABLE EMPLOYEE (
             FIRST_NAME  CHAR(20) NOT NULL,
             LAST_NAME  CHAR(20),
             AGE INT,  
             SEX CHAR(1),
             INCOME FLOAT )"""
 cursor.execute(sql)

except :
    pass



sql = """INSERT INTO EMPLOYEE(FIRST_NAME,
         LAST_NAME, AGE, SEX, INCOME)
         VALUES ('Mac', 'Mohan', 20, 'M', 2000)"""
try:
   cursor.execute(sql)
   db.commit()
except:
   db.rollback()

sql = "SELECT * FROM EMPLOYEE \
          WHERE INCOME > '%d'" % (1000)
try:
   cursor.execute(sql)
   # 获取所有记录列表
   results = cursor.fetchall()
   for row in results:
       fname = row[0]
       lname = row[1]
       age = row[2]
       sex = row[3]
       income = row[4]
       print("fname=%s,lname=%s,age=%d,sex=%s,income=%d" % \
             (fname, lname, age, sex, income))
except:
   print("Error: unable to fetch data")


db.close()
