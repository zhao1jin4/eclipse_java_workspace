import smtplib
from email.mime.text import MIMEText
from email.header import Header

sender = 'from@qq.com'
receivers = ['to@qq.com']
mailHost='smtp.qq.com'  #未测试
mailPort=25 # 25 为 SMTP 端口号
mail_user="280785883"    #用户名
mail_pass="XXXXXX"   #口令

# 第一个为文本内容，第二个 plain 设置文本格式，第三个 utf-8 设置编码
message = MIMEText('文本内容.', 'plain', 'utf-8')

'''
mail_msg = """
<p>Python 邮件发送测试...</p>
<p><a href="http://www.runoob.com">这是一个链接</a></p>
"""
message = MIMEText(mail_msg, 'html', 'utf-8')
'''

message['From'] = Header("From来自", 'utf-8')
message['To'] = Header("To目的", 'utf-8')
message['Subject'] = Header("Subject标题", 'utf-8')

try:
    smtpObj = smtplib.SMTP()
    smtpObj.connect(mailHost, mailPort)
    smtpObj.login(mail_user, mail_pass)
    smtpObj.sendmail(sender, receivers, message.as_string())
    print("邮件发送成功")
except smtplib.SMTPException:
    print("Error: 无法发送邮件")