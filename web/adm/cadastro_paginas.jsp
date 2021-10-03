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
        <title>Cadastro Páginas</title>
        <link rel="shortcut icon" href="./midia_mix.png" 	type="image/x-icon" />
    </head>
    <body onload="relogio();">
        <center>
            <jsp:include page="./topo.jsp" />
            <%
                Conexao minhaConexao=new Conexao(functions.CreateDataConection()); 
                paginasBEAN paginas_BEAN=new paginasBEAN();
                paginasDAO paginas_DAO=new paginasDAO(minhaConexao);
                Vector registros_paginas=new Vector();
                Until.functions request2=new Until.functions();
                request2.setRequest(request);                
                
                String ID=request2.getParameter("id");
                String Titulo=request2.getParameter("Titulo");
                String acao=request2.getParameter("acao");
                
                String mensagem_ID="";
                String mensagem_Titulo="";
                String mensagem_acao="";
                String mensagem_Src="";
                
                try{paginas_BEAN.setID(ID);}catch(Exception erro){mensagem_ID="O paginas selecionado não existe";};
                if((acao!=null)&&(acao.length()>0)){  
                    try{paginas_BEAN.setTitulo(Titulo);}catch(Exception erro){mensagem_Titulo=erro.getMessage();};
                    if(acao.equals("Buscar")){
                        paginasPOJO Página_POJO= new paginasPOJO();
                        Página_POJO.setTitulo(Titulo);
                        paginas_BEAN= new paginasBEAN(Página_POJO);
                        registros_paginas.clear();
                        try{registros_paginas=paginas_DAO.buscar(paginas_BEAN);}catch(Exception erro){mensagem_acao=erro.getMessage();};                            
                        mensagem_ID="";
                        mensagem_Titulo="";
                        mensagem_acao="";    
                        mensagem_Src="";                                           
                    }
                    else if(acao.equals("Salvar")){
                        if (
                            (mensagem_Titulo.length()==0)
                        )
                        {
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
                                registros_paginas.clear();
                                registros_paginas.add(paginas_BEAN);
                                mensagem_acao="Página salva com sucesso";
                            }
                            catch(Exception erro){
                                mensagem_acao=erro.getMessage();
                            }
                        }
                        else
                            paginas_BEAN.Clear();
                        mensagem_ID="";                        
                    }
                    else if(acao.equals("Excluir")){
                        if (
                            (mensagem_ID.length()==0)                                                                                  
                        )
                        {
                            try{
                                paginas_BEAN=paginas_DAO.excluir(paginas_BEAN,functions.path_upload);
                                if((paginas_BEAN!=null)&&(paginas_BEAN.getIDStr()!=null)){
                                    mensagem_acao="Página excluída com sucesso";
                                }
                                else{
                                    mensagem_acao="Página não encontrada";
                                }
                            }
                            catch(Exception erro){
                                mensagem_acao=erro.getMessage();
                            }
                        }
                        mensagem_ID="";
                        paginas_BEAN.Clear();
                    }
                    mensagem_Titulo="";                                                             
                }
                else {
                    registros_paginas.clear();
                    paginas_BEAN=((paginasBEAN)paginas_DAO.buscarID(paginas_BEAN));
                    if((paginas_BEAN!=null)&&(paginas_BEAN.getIDStr()!=null)){
                        registros_paginas.add(paginas_BEAN);
                    }
                    else{
                        
                        mensagem_ID="";
                        registros_paginas=paginas_DAO.buscarTodos();
                        paginas_BEAN.Clear();
                    }
                }
            %>
        <a class="texto1">Cadastro Páginas</a><br><br>
            <form method="post" enctype="multipart/form-data"> 
                <input type="hidden" id="id" name="id" value="<%=paginas_BEAN.getIDStr()%>">
               <div style="clear:both;color:red"><%=mensagem_ID%></div>
               <a class="texto3">Titulo:</a> <input type="text" name="Titulo" value="<%=paginas_BEAN.getTitulo()%>"><br>
               <div style="clear:both;color:red"><%=mensagem_Titulo%></div>
                <a class="texto3">Foto Em Destaque:</a> <input type="file" name="src"><br>
               <div style="clear:both;color:red;"></div>
               <input type="submit" value="Novo" onclick="document.getElementById('id').value=''">
               <input type="submit" value="Salvar" name="acao">
               <input type="submit" value="Buscar" name="acao">
               <br>
               <div style="clear:both;color:red"><%=mensagem_acao%></div>
            </form>
 
                <table cellpadding= "4" cellspacing = "0" border= "1" width= "400px">
                    <tr bgcolor="#CCCCCC">                           
                            <td><a class="texto3"> Titulo </a></td>
                            <td><a class="texto3"> acao </a></td>
		    </tr>
                  <%
                            if((registros_paginas!=null)&&(registros_paginas.size()>0)){
                                for(int i=0;i<registros_paginas.size();i++){
                                    paginas_BEAN=((paginasBEAN)registros_paginas.get(i));
                        %>
                        <tr>
                            
                            <td><%=paginas_BEAN.getTitulo()%></td>
                            <td>
                                 <form method="post" >
                                    <input type="hidden" name="id" value="<%=paginas_BEAN.getIDStr()%>">
                                     <input type="submit" value="Editar">
                                    <input type="submit" value="Excluir" name="acao">
                                </form>   
                            </td>
                        </tr>
                        <tr>
                            <td COLSpan="2" align="center">
                                <a target="_black" src="./arquivo.jsp?nome=<%=paginas_BEAN.getSrc()%>&pasta=conteudo" >
                                    <img align="center" src="./arquivo.jsp?nome=<%=paginas_BEAN.getSrc()%>&pasta=conteudo" height="303" width="404">
                                </a>
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