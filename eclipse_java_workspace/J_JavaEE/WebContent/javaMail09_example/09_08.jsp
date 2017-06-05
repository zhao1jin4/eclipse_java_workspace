<html>
<head>
<title>显示e-mail的详细信息</title>
</head>
<%@ page contentType="text/html;charset=GB2312"%>
<%@ page import="javax.mail.Part,javax.mail.*,java.util.*,java.io.*"%>
<body>
<center><h2>
  接收带附件的e-mail
</h2></center>
<%
  //得到要察看的e-mail的编号msg，即要看第几封e-mail
  String msg=request.getParameter("msg");
  //得到邮件服务器要验证的参数
  String host=request.getParameter("host");
  String user=request.getParameter("user");
  String password=request.getParameter("password");	
  try{
     //获得一个session对象，并设置其属性为true
     Properties props=new Properties(); 
     Session recesession=Session.getInstance(props,null);
     recesession.setDebug(true);
     //创建一个Store对象，并根据得到的三个参数连接到邮件服务器中的用户邮箱
     Store store=recesession.getStore("pop3"); 
     store.connect(host,user,password);
     //打开收件箱
     Folder inbox=store.getFolder("INBOX");
     inbox.open(Folder.READ_ONLY);
     //根据msg参数得到该e-mail
     int imsg=Integer.parseInt(msg);
     Message message=inbox.getMessage(imsg);
     Address[] address;
%>
<table width=85% align="center">
  <tr>
    <td>发件人：</td>
    <td>    
    <%
       //得到发件人的地址
       address=message.getFrom();
       if(address!=null)
          for(int i=0;i<address.length;i++)
            out.print(address[i]+"&nbsp&nbsp");
       else
          out.println("无");
    %>
    </td>
  </tr>
  <tr>
    <td>发送时间：</td>
    <td>    
    <%
       //得到e-mail发出的时间
       Date sentdate=message.getSentDate();
       if(address!=null)
          out.print(sentdate.toString());
       else
          out.println("无");
    %>
    </td>
  </tr>
  <tr>
    <td>收件人：</td>
    <td> 
    <%
       //得到收件人的地址列表
       address=message.getRecipients(Message.RecipientType.TO);
       if(address!=null)
          for(int i=0;i<address.length;i++)
            out.print(address[i]+"&nbsp&nbsp");
       else
          out.println("无");
    %>
    </td>
  </tr>
  <tr>
    <td>抄送：</td>
    <td>
    <%
       //得到抄送人的地址
       address=message.getRecipients(Message.RecipientType.CC);
       if(address!=null)
          for(int i=0;i<address.length;i++) {
             out.print(address[i]+"&nbsp&nbsp");
          }
       else 
          out.println("无");
    %>
    </td>
  </tr>
  <tr>
    <td>标题：</td>
    <td><%=message.getSubject()%></td>
  </tr>
  <tr>
    <td colspan=2>
    <br>&nbsp&nbsp&nbsp;
    <%
       //body是一个用来标记是否点击了附件下载链接的参数，如果没有body参数，则只显示有几个附件以及它们的下载链接
       if(request.getParameter("body")==null)
       {
          //如果是一个多部分内容的e-mail
          if(message.isMimeType("multipart/*"))
          {
             //获得代表该e-mail的多部分内容的Multipart对象
             Multipart multipart = (Multipart)message.getContent();
             //依次获取Multipart对象的每个部分
             for(int i = 0;i < multipart.getCount();i++)
             {
               //得到每个部分的属性
               Part p = multipart.getBodyPart(i);
               String disposition = p.getDisposition();
               //如果该部分中是附件内容，则输出该附件的下载链接
               if ((disposition != null) &&(disposition.equals(Part.ATTACHMENT) || disposition.equals(Part.INLINE)))
               {
                  String filename=p.getFileName();
                  filename=javax.mail.internet.MimeUtility.decodeText(filename);
%>
<!--传递要看第几封e-mail的第几个附件-->
<p>
附件:
<a href="09_08.jsp?msg=<%=message.getMessageNumber()%>&host=<%=host%>&user=<%=user%>&password=<%=password%>&body=<%=i%>">
<%=filename%>
</a>
</p>
<%
               }
               else if(disposition==null)
               {
                  //如果该部分是普通文本内容（无附件），则输出其文本内容
                  if(p.isMimeType("text/plain")){
                      out.print(p.getContent());
                  }else {
                      //如果该部分是特殊附件，就不做处理
                  }
               }
             }
          }
          //如果是普通文本形式的e-mail，则显示其详细内容
          else if(message.isMimeType("text/plain")) 
          {
              out.print(message.getContent());
          }
       } 
     //如果有body参数，即表明用户点击了附件下载的链接，这时就可以下载相应的附件了
     else 
     {
        //得到要下载的附件的编号
	int attachNo=Integer.parseInt(request.getParameter("body"));
	Part p;
	//如果编号小于0，则表明是一个单部分的email
	if(attachNo<0) p=message;
	//否则是一个多部分组成的email，得到附件的对应部分
	else{
		Multipart multipart=(Multipart)message.getContent();
		p=multipart.getBodyPart(attachNo);
	}
	//设置附件的类型属性
	response.setContentType(p.getContentType());
	//生成下载附件的头信息
	String s=p.getFileName();
	s=javax.mail.internet.MimeUtility.decodeText(s);
	if (p.getFileName()!=null){
		response.setHeader("Content-Disposition","attachment; filename=\""+s+"\"");
	}
	//设置下载过程中的输入流和输出流
	OutputStream fout=response.getOutputStream();
	InputStream fin=p.getInputStream();
	//下载附件
	int c=fin.read();
	while(c!=-1) 
	{
	   fout.write(c); 
           c=fin.read();
	}
     }
%>
    </td>
  </tr>
</table>
<%	
   //关闭收件箱和到邮件服务器的连接
   inbox.close(true);
   store.close();
 }catch(MessagingException m) 
 { 
    out.println(m.toString()); 
 } 
%>
<p><center>
<a href="09_06.jsp?host=<%=host%>&user=<%=user%>&password=<%=password%>">返回收件箱<a>
</center></p>
</body>
</html> 