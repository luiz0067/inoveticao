<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>FCKeditor - JSP Sample</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="robots" content="noindex, nofollow" />
		<link href="./sample.css" rel="stylesheet" type="text/css" />
		<link rel="shortcut icon" href="./midia_mix.png" 	type="image/x-icon" />
		
	</head>
         <jsp:include page="./topo.jsp" />
	<%
		pageContext.setAttribute("skin","skins/office2003/");
	%>	
	<body onload="relogio();">
		<form  method="post" target="_blank">
			<FCK:editor instanceName="conteudo">
				<jsp:attribute name="value">aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</jsp:attribute>
				<jsp:body><FCK:config SkinPath="${skin}"/></jsp:body>
			</FCK:editor>
			<br />
			<input type="submit" value="Submit" />
		</form>
	</body>
</html>