<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>FCKeditor - JSP Sample</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="robots" content="noindex, nofollow" />
		<link href="../sample.css" rel="stylesheet" type="text/css" />
		<link rel="shortcut icon" href="../fckeditor.gif" 	type="image/x-icon" />
		
	</head>
	<%
		pageContext.setAttribute("skin","skins/office2003/");
	%>	
	<body>
		<form  method="post" >
			<FCK:editor instanceName="conteudo">
				<jsp:attribute name="value">aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</jsp:attribute>
				<jsp:body><FCK:config SkinPath="${skin}"/></jsp:body>
			</FCK:editor>
			<br />
			<input type="submit" value="Enviar" />
		</form>
	</body>
</html>