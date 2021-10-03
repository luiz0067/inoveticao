<%@page import="BEAN.sorteioBEAN"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.DataInputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@page import="Until.functions"%>
<link rel="stylesheet" type="text/css" href="estilosadm.css">
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro fotos</title>
        <link rel="shortcut icon" href="./midia_mix.png" type="image/x-icon" />
        <script type="text/javascript">
            function chormeErroFoto(){
                //document.getElementById('formulario').enctype='';
            }
        </script>
    </head>
    <body onload="relogio();">
        <center>
            <%
                String acc="";
                if ((functions.path_upload!=null)&&(functions.path_upload.indexOf("\\")!=-1))
                    acc="\\";
                else
                    acc="/";
                String path=functions.path_upload+acc+"anuncio"+acc;
                Until.functions request2=new Until.functions();
                request2.setRequest(request);
                String acao=request2.getParameter("acao");
                String sorteio=request2.getParameter("sorteio");
                String popup=request2.getParameter("popup");
                if ((acao!=null)&&(acao.equals("Salvar"))){
                    try{
                        String anuncio_1="anuncio_1";
                        anuncio_1=request2.upload(anuncio_1,path,"p_"+anuncio_1+".jpg");
                        anuncio_1=anuncio_1.substring(2);
                        functions.redimensionarImagem(path+"p_"+anuncio_1,path+"p_"+anuncio_1,302,660,true);
                        functions.redimensionarImagem(path+"p_"+anuncio_1,path+"g_"+anuncio_1,302,900,true);
                    }
                    catch(Exception erro){
                    }
                    
                    try{
                        String anuncio_2="anuncio_2";
                        anuncio_2=request2.upload(anuncio_2,path,anuncio_2+".jpg");
                        functions.redimensionarImagem(path+anuncio_2,path+anuncio_2,122,129,true);
                    }
                    catch(Exception erro){
                    }  
                    try{
                        String anuncio_3="anuncio_3";
                        anuncio_3=request2.upload(anuncio_3,path,"p_"+anuncio_3+".jpg");
                        anuncio_3=anuncio_3.substring(2);
                        functions.redimensionarImagem(path+"p_"+anuncio_3,path+"p_"+anuncio_3,166,665,true);
                        functions.redimensionarImagem(path+"p_"+anuncio_3,path+"g_"+anuncio_3,166,907,true);
                    }
                    catch(Exception erro){
                    } 
                    try{
                        String anuncio_4="anuncio_4";
                        anuncio_4=request2.upload(anuncio_4,path,anuncio_4+".jpg");
                        functions.redimensionarImagem(path+anuncio_4,path+anuncio_4,203,200,true);
                    }
                    catch(Exception erro){
                    }
                    try{
                        String anuncio_5="anuncio_5";
                        anuncio_5=request2.upload(anuncio_5,path,anuncio_5+".jpg");
                        functions.redimensionarImagem(path+anuncio_5,path+anuncio_5,400,500,false);
                    }
                    catch(Exception erro){
                    }
                    if((sorteio!=null)&&(sorteio.equals("true"))){
                        sorteioBEAN sorteio_bean = new sorteioBEAN();
                        sorteio_bean.setSorteio(true);
                        sorteio="checked";
                    }
                    else{
                       sorteioBEAN sorteio_bean = new sorteioBEAN();
                       sorteio_bean.setSorteio(false);
                       sorteio="";
                    }
                    if((popup!=null)&&(popup.equals("true"))){
                        sorteioBEAN sorteio_bean = new sorteioBEAN();
                        sorteio_bean.setPopup(true);
                        popup="checked";
                    }
                    else{
                       sorteioBEAN sorteio_bean = new sorteioBEAN();
                       sorteio_bean.setPopup(false);
                       popup="";
                    }
                }
            %>
            <a class="texto1">Cadastro fotos</a><br><br>
            <form method="post" enctype="multipart/form-data" id="formulario">
               <a class="texto3">Ativar sorteio</a>     <input type="checkbox" <%=sorteio%> name="sorteio" value="true"><br>
               
               <a class="texto3">Anuncio 2:</a>         <input type="file" name="anuncio_2"><br>
             
               <a class="texto3">Anuncio 4:</a>         <input type="file" name="anuncio_4"><br>
               <a class="texto3">Popup:</a>             <input type="file" name="anuncio_5"><br>
               <a class="texto3">Ativar Popup</a>       <input type="checkbox" <%=popup%> name="popup" value="true"><br>
               <input type="submit" value="Salvar" name="acao" onclick="chormeErroFoto()">              
            </form>
           
            <img src="./arquivo.jsp?nome=anuncio_2.jpg&pasta=anuncio"><br>
            <br><hr><br>
            <img src="./arquivo.jsp?nome=anuncio_4.jpg&pasta=anuncio"><br>
            <br><hr><br>
            <img src="./arquivo.jsp?nome=anuncio_5.jpg&pasta=anuncio"><br>
        </center>
    </body>
</html>
