<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="
    org.jfree.chart.*,
    org.jfree.chart.servlet.*,
    org.jfree.chart.title.*,
    org.jfree.data.general.*,
    org.jfree.chart.plot.*,
    java.awt.*,
    org.jfree.util.PublicCloneable"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
DefaultPieDataset dpd = new DefaultPieDataset();

dpd.setValue("管理人员", 25);
dpd.setValue("市场人员", 25);
dpd.setValue("开发人员", 45);
dpd.setValue("其他人员", 10);

JFreeChart chart = ChartFactory.createPieChart3D("某公司人员组织结构图", dpd, true,
		true, false);
//------饼图中文支持
Font font = new Font("SimSun",Font.BOLD|Font.ITALIC,20); 
TextTitle tt = chart.getTitle(); 
tt.setFont(font); 
chart.getLegend().setItemFont(font); 
PiePlot pieplot = (PiePlot)chart.getPlot();
   pieplot.setLabelFont(font);  
//------
	
String imgFilename=ServletUtilities.saveChartAsJPEG(chart, 800, 600, session);
String url=request.getContextPath()+"/servlet/DisplayChart?filename="+imgFilename;
%>
<img src="<%=url%>" width="800" height="600"/>
</body>
</html>