<%@page import="Until.functions"%>
<%@page import="BEAN.sorteioBEAN"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String acc="";
    if ((functions.path_upload!=null)&&(functions.path_upload.indexOf("\\")!=-1))
        acc="\\";
    else
        acc="/";
    String path=functions.path_upload+acc+"anuncio"+acc;
    String anuncio_5=path+"anuncio_5.jpg";
    int largura=0;
    try{
        largura=functions.getLarguraImagem(anuncio_5);
    }
    catch(Exception erro){
    }
    sorteioBEAN sorteio_BEAN=new sorteioBEAN();
    if(sorteio_BEAN.getPopup()){
%>
<div style="position:absolute;width:<%=largura+30%>px;height:435px;background-color:black;color:white;margin-left:120px;_margin-left:-440px;display:block;overflow:hidden;margin-top:100px;_margin-top:50px;" ID="popup">
    <div style="float:left;font-weight:bold;text-align:center;width:100%" >
        <div style="float:right;font-weight:bold;width:30px;background-color:black;height:20px;overflow:hidden;" onclick="document.getElementById('popup').style.display='none'">[ X ] &nbsp;</div>
    </div>
    <div style="height:400px;width:<%=largura%>px;margin-left:auto;margin-right:auto;float:center;text-align:center;">
        <img src="./arquivo.jsp?nome=anuncio_5.jpg&pasta=anuncio" width="<%=largura%>px" height="400px" align="center">
    </div>
</div>
<%  }%>
