<%@page language="java" contentType="text/html;charset=UTF-8"	pageEncoding="UTF-8" %>
<%@page import="POJO.conteudoPOJO"%>
<%@page import="BEAN.paginasBEAN"%>
<%@page import="DAO.paginasDAO"%>
<%@page import="Until.functions"%>
<%@page import="java.util.Vector"%>
<%@page import="DAO.conteudoDAO"%>
<%@page import="BEAN.conteudoBEAN"%>
<%@page import="BD.Conexao"%>
<%@page import="net.fckeditor.FCKeditor"%>
<%@page import="net.fckeditor.FCKeditorConfig"%>
<link rel="stylesheet" type="text/css" href="estilosadm.css">
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro conteudo</title>
        <link rel="shortcut icon" href="./midia_mix.png" type="image/x-icon" />
    </head>
    <body onload="relogio();">
        <center>
            <jsp:include page="./topo.jsp" />
            <%
                Conexao minhaConexao=new Conexao(Until.functions.CreateDataConection()); 
                conteudoBEAN conteudo_BEAN=new conteudoBEAN();
                conteudoDAO conteudo_DAO=new conteudoDAO(minhaConexao);
                Vector registros_conteudo=new Vector();

                String ID=request.getParameter("id");
                String Id_paginas=request.getParameter("id_paginas");
                String acao=request.getParameter("acao");                
                
                String mensagem_ID="";
                String mensagem_acao="";
                String mensagem_Titulo="";
                
                String mensagem_Conteudo="";
                String mensagem_Id_paginas="";          
                     
                try{conteudo_BEAN.setID(ID);}catch(Exception erro){mensagem_ID="O conteudo selecionado nÃ£o existe";};
                try{conteudo_BEAN.setId_paginas(Id_paginas);}catch(Exception erro){mensagem_Id_paginas=erro.getMessage();}; 
                    
                if((acao!=null)&&(acao.length()>0)){ 
                        String Titulo=request.getParameter("Titulo");           
                        try{conteudo_BEAN.setTitulo(Titulo);}catch(Exception erro){mensagem_Titulo=erro.getMessage();};
                        if(acao.equals("Buscar")){
                            conteudoPOJO conteudo_pojo= new conteudoPOJO();
                            conteudo_pojo.setTitulo(Titulo);
                            conteudoPOJO  conteudo_POJO = new conteudoPOJO();
                            conteudo_BEAN= new conteudoBEAN(conteudo_POJO);
                            try{registros_conteudo=conteudo_DAO.buscar(conteudo_BEAN);}catch(Exception erro){mensagem_acao=erro.getMessage();};                            
                            mensagem_ID="";
                            mensagem_acao="";
                            mensagem_Titulo="";
                            mensagem_Conteudo="";
                            mensagem_Id_paginas=""; 
                        }
                        else if(acao.equals("Salvar")){
                                if (
                                    (mensagem_Titulo.length()==0)
                                    &&
                                    (mensagem_Id_paginas.length()==0)                                                                                                                           
                                ){
                                    //insert                               
                                    try{
                                        String Conteudo=request.getParameter("conteudo");
                                        try{conteudo_BEAN.setConteudo(Conteudo);}catch(Exception erro){mensagem_Conteudo=erro.getMessage();}; 
                                        if(mensagem_Conteudo.length()==0){
                                            conteudo_BEAN=conteudo_DAO.salvar(conteudo_BEAN);
                                            registros_conteudo.add(conteudo_BEAN);
                                        }
                                    }
                                    catch(Exception erro){
                                        mensagem_acao=erro.getMessage();
                                        mensagem_acao="Conteudo salva com sucesso";
                                    }
                                }
                                else
                                    conteudo_BEAN.Clear();
                                mensagem_Id_paginas="";
                                mensagem_ID="";                                 
                            }                        
                            else if(acao.equals("Excluir")){
                                if (
                                    (mensagem_ID.length()==0)                                                                                  
                                )
                                {
                                    conteudo_BEAN=conteudo_DAO.excluir(conteudo_BEAN); 
                                    if((conteudo_BEAN!=null)&&(conteudo_BEAN.getIDStr()!=null)){
                                        mensagem_acao="Conteudo excluÃ­do com sucesso";
                                    }
                                    else{
                                        mensagem_acao="Conteudo nÃ£o encontrado";
                                    }                                    
                                }
                                mensagem_ID=""; 
                                mensagem_Titulo="";
                                mensagem_Conteudo="";
                                mensagem_Id_paginas="";
                            }
                            else if(acao.equals("editar")){                                
                                conteudo_BEAN=((conteudoBEAN)conteudo_DAO.buscarID(conteudo_BEAN));
                                if((conteudo_BEAN!=null)&&(conteudo_BEAN.getIDStr()!=null)){
                                    registros_conteudo.add(conteudo_BEAN);
                                    mensagem_Id_paginas="";
                                }
                                else{
                                    conteudo_BEAN.Clear();
                                    try{conteudo_BEAN.setId_paginas(Id_paginas);}catch(Exception erro){mensagem_Id_paginas=erro.getMessage();}; 
                                    mensagem_Id_paginas="";
                                    registros_conteudo=conteudo_DAO.buscarUltimasPaginas(conteudo_BEAN);
                                }
                                mensagem_ID=""; 
                                mensagem_Titulo="";
                                mensagem_Conteudo="";
                                mensagem_Id_paginas="";                                                               
                            }
                            else if(acao.equals("novo")){
                                mensagem_ID=""; 
                                mensagem_Titulo="";
                                mensagem_Conteudo="";
                                mensagem_Id_paginas="";
                                conteudo_BEAN.Clear();
                            }
                    }
                    else{
                            mensagem_ID="";
                            conteudo_BEAN.Clear();
                            try{conteudo_BEAN.setId_paginas(Id_paginas);}catch(Exception erro){mensagem_Id_paginas=erro.getMessage();}; 
                            mensagem_Id_paginas="";
                            try{registros_conteudo=conteudo_DAO.buscarUltimasPaginas(conteudo_BEAN);;}catch(Exception erro){}; 
                    }
                   
            %>
            <a class="texto1">Cadastro conteudo</a><br><br>
            <form method="post" >
            <input type="hidden" name="id" value="<%=conteudo_BEAN.getIDStr()%>">    
            <div style="clear:both;color:red"><%=mensagem_ID%></div>
            <a class="texto3">paginas:</a> <select name="id_paginas" onchange="menuJumpConteudo(this)" style="width:150px;">
            <%
                Vector registros_paginas=(new paginasDAO(minhaConexao)).buscarTodos();
               if((registros_paginas!=null)&&(registros_paginas.size()>0)){
                 for(int i=0;i<registros_paginas.size();i++){
                              paginasBEAN paginas_BEAN=((paginasBEAN)registros_paginas.get(i));      
            %>
                <option value="<%=paginas_BEAN.getIDStr()%>" <%
                    if(functions.equals(conteudo_BEAN.getId_paginasStr(),paginas_BEAN.getIDStr())) 
                        out.write("selected");
                %>>
                       <%=paginas_BEAN.getTitulo()%>
                </option>
             <%
                        }
                    }
             %>
               </select><br>
               <div style="clear:both;color:red"><%=mensagem_Id_paginas%></div>
               <input type="hidden" name="id" id="id" value="<%=conteudo_BEAN.getIDStr()%>"><br>
               <a class="texto3">Titulo: </a><input type="text" name="Titulo" value="<%=Until.functions.removenull(conteudo_BEAN.getTitulo())%>"><br>
               <div style="clear:both;color:red"><%=mensagem_Titulo%></div>
               <a class="texto3">Conteudo:</a><br> 
               <%             
                    FCKeditor fckEditor = new FCKeditor(request, "conteudo");
                    fckEditor.setConfig("UserFilesAbsolutePath", "/home/inoveticao/webapps/ROOT/upload/");
                    fckEditor.setConfig("UserFilesAbsolutePath", "/home/inoveticao/webapps/ROOT/upload/");
                    fckEditor.setConfig("UserFilesPath", "/upload/");
                    fckEditor.setConfig("SkinPath", "skins/office2003/");
                    fckEditor.setConfig("DefaultLanguage", "pt-br/");
                    
                    fckEditor.setValue(Until.functions.removenull(conteudo_BEAN.getConteudo()));
                    fckEditor.setBasePath("/fckeditor");
                    fckEditor.setHeight("400px");
                    fckEditor.createHtml();
                    out.println(fckEditor);
               %>
               <div style="clear:both;color:red"><%=mensagem_Conteudo%></div>
               <input type="submit" value="novo" name="acao" onclick="document.getElementById('id').value=''">
               <input type="submit" value="Salvar" name="acao">              
               <input type="submit" value="Buscar" name="acao">
               <div style="clear:both;color:red"><%=mensagem_acao%></div>
            </form>
                <table cellpadding= "4" cellspacing = "0" border= "1" width= "900px">
                    <tr>   
                        <td> Titulo </td>
                        <td> acao </td>
		    </tr>
                        <%
                            if((registros_conteudo!=null)&&(registros_conteudo.size()>0)){
                                for(int i=0;i<registros_conteudo.size();i++){
                                    conteudo_BEAN=((conteudoBEAN)registros_conteudo.get(i));
                                    if(conteudo_BEAN.getIDStr()==null)
                                        break;
                        %>
                        <tr>
                            <td> <%=Until.functions.removenull(conteudo_BEAN.getTitulo())%> </td>     
                            <td width="150px">
                                <form method="post" >
                                    <input type="hidden" name="id" value="<%=conteudo_BEAN.getIDStr()%>">
                                    <input type="submit" name="acao" value="editar">
                                    <input type="submit" value="Excluir" name="acao">
                                </form>
                            </td>
                        </tr>
                        <tr>
                            <td COLSpan="3" align="center">
                                <%=Until.functions.removenull(conteudo_BEAN.getConteudo())%>
                            </td>
                        </tr>
                        <%
                                }
                            }
                            minhaConexao.Fechar();
                        %>
                </table>
        </center>
    </body>
</html>