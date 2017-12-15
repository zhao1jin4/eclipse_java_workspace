<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html>
			<head>
				<title>学生成绩单</title>
			</head>
			<body>
				<table border ="2">
					<tr>
						<th>id</th>
						<th>姓名</th>
						<th>性别</th>
						<th>成绩</th>
						<th>等级</th>
						<th>技能</th>
					</tr>
					<xsl:apply-templates select="roster/student[position() &lt; 5 ]">
						<xsl:sort select="score" order="descending"/>
					</xsl:apply-templates>
				</table>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="roster/student">
		<tr>
			<td><xsl:value-of select="attribute::ID"/></td>
			<td><xsl:value-of select="name"/></td>
			<td><xsl:value-of select="sex"/></td>
			<td><xsl:value-of select="score"/></td>
			<td>
				<xsl:choose>
					<xsl:when test="score[. >= 90]">A
					</xsl:when>
					<xsl:when test="score[. >= 80]">B
					</xsl:when>
					<xsl:otherwise>C
					</xsl:otherwise>
				</xsl:choose>
			</td>
			<td>
				<xsl:for-each select="skill">
					<font color="red">
						<xsl:value-of select="."/>
						<xsl:if test="position() != last()">
						,
						</xsl:if>
					</font>
				</xsl:for-each>
			</td>
		</tr>
	</xsl:template>
</xsl:stylesheet>
