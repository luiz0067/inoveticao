<%@page import="java.util.Date"%>
<%@page import="net.fckeditor.FCKeditor"%>
<%@page import="DAO.conteudoDAO"%>
<%@page import="BEAN.conteudoBEAN"%>
<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@page import="POJO.paginasPOJO"%>
<%@page import="Until.functions"%>
<%@page import="java.util.Vector"%>
<%@page import="DAO.paginasDAO"%>
<%@page import="BEAN.paginasBEAN"%>
<%@page import="BD.Conexao"%>
<%@page import="BD.DadosConexao"%>
<link rel="stylesheet" type="text/css" href="estilosadm.css">
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro Página Inove Ticão</title>
        <link rel="shortcut icon" href="./midia_mix.png" 	type="image/x-icon" />
    </head>
    <body onload="relogio();">
        <center>
            <%
                Conexao minhaConexao=new Conexao(functions.CreateDataConection()); 
                paginasBEAN paginas_BEAN=new paginasBEAN();
                paginasDAO paginas_DAO = new paginasDAO(minhaConexao); 
                try{paginas_BEAN=(paginasBEAN)paginas_DAO.buscarTitulo("Garota").get(0);}catch(Exception erro){};
                conteudoBEAN conteudo_BEAN=new conteudoBEAN();
                try{conteudo_BEAN.setId_paginas(paginas_BEAN.getIDStr());}catch(Exception erro){}
                conteudoDAO conteudo_dao=new conteudoDAO(minhaConexao);
                Vector registros_conteudo=new Vector();
                try{registros_conteudo=conteudo_dao.buscarUltimasPaginas(conteudo_BEAN);}catch(Exception erro){}
                if (registros_conteudo.size()>=1){
                    try{ conteudo_BEAN=(conteudoBEAN)registros_conteudo.get(0);}catch(Exception erro){}
                }
                Until.functions request2=new Until.functions();
                request2.setRequest(request); 
                String acao=request2.getParameter("acao");
                String mensagem_acao="";
                String mensagem_Src="";
                String mensagem_Titulo="";
                String mensagem_Conteudo="";
                if((acao!=null)&&(acao.length()>0)){  
                    if(acao.equals("Salvar")){                        
                        try{
                            String acc="";
                            if ((functions.path_upload!=null)&&(functions.path_upload.indexOf("\\")!=-1))
                                acc="\\";
                            else
                                acc="/";
                            String path=functions.path_upload+acc+"conteudo"+acc;
                            String Src=request2.upload("src",path);
                            try{
                                functions.redimensionarImagem(path+Src,path+Src,303,404,true);
                                try{paginas_BEAN.setSrc(Src);}catch(Exception erro){mensagem_Src=erro.getMessage();};
                            }
                            catch(Exception erro1){

                            }	
                            paginas_BEAN=paginas_DAO.salvar(paginas_BEAN,functions.path_upload);
                            mensagem_acao="Página salva com sucesso";
                        
                            
                            String Titulo=request2.getParameter("Titulo");
                            String Conteudo=request2.getParameter("conteudo");
                            try{conteudo_BEAN.setConteudo(Conteudo);}catch(Exception erro){mensagem_Conteudo=erro.getMessage();}; 
                            try{conteudo_BEAN.setTitulo(Titulo);}catch(Exception erro){mensagem_Titulo=erro.getMessage();}; 
                            conteudo_BEAN.setInicio(new Date());
                            conteudo_BEAN.setId_paginas(paginas_BEAN.getIDStr());
                            if(mensagem_Conteudo.length()==0){
                                conteudo_BEAN=conteudo_dao.salvar(conteudo_BEAN);
                            }
                        }
                        catch(Exception erro){
                            mensagem_acao=erro.getMessage();
                            mensagem_acao="Conteudo salva com sucesso";
                        }
                    }
                }
            %>
        <a class="texto1">Cadastro Páginas</a><br><br>
            <form method="post" enctype="multipart/form-data"> 
                <a class="texto3">Foto Em Destaque:</a> <input type="file" name="src"><br>
                <div style="clear:both;color:red;"></div>
                
                
                <table cellpadding= "4" cellspacing = "0" border= "1" width= "400px">
                        <tr>
                            <td  align="center">
                                <a target="_black" src="./arquivo.jsp?nome=<%=paginas_BEAN.getSrc()%>&pasta=conteudo" >
                                    <img align="center" src="./arquivo.jsp?nome=<%=paginas_BEAN.getSrc()%>&pasta=conteudo" height="303" width="404">
                                </a>
                            </td>
                        </tr>
                </table>
                <br>
                <a href="./cadastro_fotos_paginas.jsp?id_paginas=<%=paginas_BEAN.getIDStr()%>">Mais fotos</a>
                <br>
                <a class="texto3">Titulo: </a><input type="text" name="Titulo" value="<%=Until.functions.removenull(conteudo_BEAN.getTitulo())%>"><br>                
                
                <div style="clear:both;color:red"><%=mensagem_Titulo%></div>
                <%             
                    FCKeditor fckEditor = new FCKeditor(request, "conteudo");
                    //fckEditor.setConfig("UserFilesAbsolutePath", "/home/inoveticao/webapps/ROOT/upload/");
                    //fckEditor.setConfig("UserFilesAbsolutePath", "/home/inoveticao/webapps/ROOT/upload/");
                    //fckEditor.setConfig("UserFilesPath", "/upload/");
                    fckEditor.setConfig("SkinPath", "skins/office2003/");
                    fckEditor.setConfig("DefaultLanguage", "pt-br/");

                    fckEditor.setValue(Until.functions.removenull(conteudo_BEAN.getConteudo()));
                    fckEditor.setBasePath("/fckeditor");
                    fckEditor.setHeight("400px");
                    fckEditor.createHtml();
                    out.println(fckEditor);
                %>
                <div style="clear:both;color:red"><%=mensagem_acao%></div>
                <input type="submit" value="Salvar" name="acao">
                <br>
            </form> 
        </center>
    </body>
</html>