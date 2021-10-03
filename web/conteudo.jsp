<!----------------------------------------------------inicio do conteudo ------------------------------>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="conteudo" style="height:auto;">
	<%
            String pg=request.getParameter("pg");   
            pg=(pg==null)?"":pg;
	%>
	<%if(pg.equals("inoveticao")){%>
	<jsp:include page="inoveticao.jsp" />
	<%}else if(pg.equals("videos")){%>
	<jsp:include page="videos.jsp" />
	<%}else if(pg.equals("agenda")){%>
	<jsp:include page="agenda.jsp" />
	<%}else if(pg.equals("fotos")){%>
	<jsp:include page="fotos.jsp" />
	<%}else if(pg.equals("contato")){%>
	<jsp:include page="contato.jsp" />
	<%}else{%>
	<jsp:include page="home.jsp" />
	<%}%>
</div>
<!----------------------------------------------------fim do conteudo ------------------------------>