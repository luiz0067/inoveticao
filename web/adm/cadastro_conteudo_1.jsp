<%@page import="net.fckeditor.FCKeditor"%>
<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@page import="POJO.conteudoPOJO"%>
<%@page import="BEAN.paginasBEAN"%>
<%@page import="DAO.paginasDAO"%>
<%@page import="Until.functions"%>
<%@page import="java.util.Vector"%>
<%@page import="DAO.conteudoDAO"%>
<%@page import="BEAN.conteudoBEAN"%>
<%@page import="BD.Conexao"%>
<link rel="stylesheet" type="text/css" href="estilosadm.css">
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro conteudo</title>
        <link rel="shortcut icon" href="./midia_mix.png" 	type="image/x-icon" />
    </head>
    <body >
        <center>
            
               <%             
                    FCKeditor fckEditor = new FCKeditor(request, "conteudo");
                    //fckEditor.setConfig("UserFilesAbsolutePath", "/home/inoveticao/webapps/ROOT/upload/");
                    fckEditor.setConfig("UserFilesAbsolutePath", "/home/inoveticao/webapps/ROOT/upload");
                    fckEditor.setConfig("UserFilesPath", "/upload");
                    fckEditor.setConfig("SkinPath", "skins/office2003/");
                    fckEditor.setConfig("DefaultLanguage", "pt-br/");
                    fckEditor.setBasePath("/fckeditor");
                    fckEditor.setHeight("400px");
                    fckEditor.createHtml();
                    out.println(fckEditor);
               %>
               <input type="submit" value="novo" name="acao">
               <input type="submit" value="Salvar" name="acao">              
               <input type="submit" value="Buscar" name="acao">
            </form>
                <table cellpadding= "4" cellspacing = "0" border= "1" width= "900px">
                    <tr bgcolor="#CCCCCC">                           
                            
                            <td><a class="texto3"> Titulo </a></td>
                            <td><a class="texto3"> acao </a></td>
                            
		    </tr>
                       
                </table>
        </center>
    </body>
</html>
