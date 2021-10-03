<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@page import="POJO.fotosPaginasPOJO"%>
<%@page import="DAO.paginasDAO"%>
<%@page import="BEAN.paginasBEAN"%>
<%@page import="DAO.paginasDAO"%>
<%@page import="DAO.fotosPaginasDAO"%>
<%@page import="DAO.paginasDAO"%>
<%@page import="BEAN.paginasBEAN"%>
<%@page import="Until.functions"%>
<%@page import="java.util.Vector"%>
<%@page import="DAO.fotosPaginasDAO"%>
<%@page import="BEAN.fotosPaginasBEAN"%>
<%@page import="BD.Conexao"%>
<%@page import="BD.DadosConexao"%>
<!DOCTYPE html>
<link rel="stylesheet" type="text/css" href="estilosadm.css">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
        <title>Cadastro fotosPaginas</title>
        <link rel="shortcut icon" href="./midia_mix.png" type="image/x-icon" />
    </head>
    <body onload="relogio();">
        <center>
            <jsp:include page="./topo.jsp" />
            <%
                Conexao minhaConexao=new Conexao(Until.functions.CreateDataConection()); 
                fotosPaginasBEAN fotosPaginas_BEAN=new fotosPaginasBEAN();
                fotosPaginasDAO fotosPaginas_DAO=new fotosPaginasDAO(minhaConexao);
                Vector registros_fotosPaginas=new Vector();
                Until.functions request2=new Until.functions();
                request2.setRequest(request);

                String ID=request2.getParameter("id");
                String id_paginas=request2.getParameter("id_paginas");
                String acao=request2.getParameter("acao");                
                
                String mensagem_ID="";
                String mensagem_acao="";
                String mensagem_Titulo="";
                String mensagem_descricao="";
                String mensagem_Src="";
                String mensagem_id_paginas="";          
                  
                if (id_paginas==null)
                    id_paginas=request.getParameter("id_paginas");
                
                try{fotosPaginas_BEAN.setID(ID);}catch(Exception erro){mensagem_ID="a foto selecionada não existe";};
                try{fotosPaginas_BEAN.setId_Paginas(id_paginas);}catch(Exception erro){mensagem_id_paginas=erro.getMessage();}; 
                if((acao!=null)&&(acao.length()>0)){ 
                        String Titulo=request2.getParameter("Titulo");
                        String descricao=request2.getParameter("descricao");
						
                        try{fotosPaginas_BEAN.setTitulo(Titulo);}catch(Exception erro){mensagem_Titulo=erro.getMessage();};
                        try{fotosPaginas_BEAN.setDescricao(descricao);}catch(Exception erro){mensagem_descricao=erro.getMessage();}; 
                        
                        if(acao.equals("Buscar")){
                            fotosPaginasPOJO  fotosPaginas_POJO = new fotosPaginasPOJO();
                            fotosPaginas_POJO.setTitulo(Titulo);
                            fotosPaginas_POJO.setDescricao(descricao);
                            fotosPaginas_BEAN= new fotosPaginasBEAN(fotosPaginas_POJO);
                            try{registros_fotosPaginas=fotosPaginas_DAO.buscar(fotosPaginas_BEAN);}catch(Exception erro){mensagem_acao=erro.getMessage();};                            
                            mensagem_ID="";
                            mensagem_acao="";
                            mensagem_Titulo="";
                            mensagem_descricao="";
                            mensagem_Src="";
                            mensagem_id_paginas="";
                        }
                        else if(acao.equals("Salvar")){
                                if (
                                    (mensagem_Titulo.length()==0)
                                    &&
                                    (mensagem_descricao.length()==0)
                                    && 
                                    (mensagem_id_paginas.length()==0)                                                                                                                           
                                ){                             
                                    try{
                                        String acc="";
                                        if ((functions.path_upload!=null)&&(functions.path_upload.indexOf("\\")!=-1))
                                            acc="\\";
                                        else
                                            acc="/";
                                        String path=functions.path_upload+acc+"conteudo"+acc;
                                        String Src=request2.uploadJPGRedimenciona("Src",path);									
                                        try{fotosPaginas_BEAN.setSrc(Src);}catch(Exception erro){mensagem_Src=erro.getMessage();}; 
                                        if ((ID!=null)&&(ID.length()!=0))
                                            mensagem_Src="";
                                        if(mensagem_Src.length()==0){
                                            fotosPaginas_BEAN=fotosPaginas_DAO.salvar(fotosPaginas_BEAN,functions.path_upload);
                                            registros_fotosPaginas.add(fotosPaginas_BEAN);
                                        }
                                        else{
                                            functions.deletaImagensRedimencionadas(Src,functions.path_upload);
                                        }
                                        mensagem_acao="Foto salva com sucesso";
                                        mensagem_ID=""; 
                                    }
                                    catch(Exception erro){
                                        mensagem_acao=erro.getMessage();
                                    }
                                }
                                else
                                    fotosPaginas_BEAN.Clear();
                            }                        
                            else if(acao.equals("Excluir")){
                                if (
                                    (mensagem_ID.length()==0)                                                                                  
                                )
                                {
                                    fotosPaginas_BEAN=fotosPaginas_DAO.excluir(fotosPaginas_BEAN,functions.path_upload); 
                                    if((fotosPaginas_BEAN!=null)&&(fotosPaginas_BEAN.getIDStr()!=null)){
                                        mensagem_acao="Foto excluída com sucesso";
                                    }
                                    else{
                                        mensagem_acao="Foto não encontrada";
                                    }                                    
                                }
                                mensagem_ID=""; 
                                mensagem_Titulo="";
                                mensagem_descricao="";
                                mensagem_Src="";
                                mensagem_id_paginas="";
                                fotosPaginas_BEAN.Clear();
                            }
                            else if(acao.equals("editar")){                                
                                fotosPaginas_BEAN=((fotosPaginasBEAN)fotosPaginas_DAO.buscarID(fotosPaginas_BEAN));
                                if((fotosPaginas_BEAN!=null)&&(fotosPaginas_BEAN.getIDStr()!=null)){
                                    registros_fotosPaginas.add(fotosPaginas_BEAN);
                                    mensagem_id_paginas="";
                                }
                                else{
                                    fotosPaginas_BEAN.Clear();
                                    try{fotosPaginas_BEAN.setId_Paginas(id_paginas);}catch(Exception erro){mensagem_id_paginas=erro.getMessage();}; 
                                    mensagem_id_paginas="";
                                    registros_fotosPaginas=fotosPaginas_DAO.buscarPorPagina(fotosPaginas_BEAN);
                                }
                                mensagem_ID=""; 
                                mensagem_Titulo="";
                                mensagem_descricao="";
                                mensagem_Src="";
                                mensagem_id_paginas="";                                                               
                            }
                            else if(acao.equals("novo")){
                                mensagem_ID=""; 
                                mensagem_Titulo="";
                                mensagem_descricao="";
                                mensagem_Src="";
                                mensagem_id_paginas="";
                                fotosPaginas_BEAN.Clear();
                            }
                    }
                    else{
                        mensagem_ID="";
                        fotosPaginas_BEAN.Clear();
                        try{fotosPaginas_BEAN.setId_Paginas(id_paginas);}catch(Exception erro){mensagem_id_paginas=erro.getMessage();}; 
                        mensagem_id_paginas="";
                        try{registros_fotosPaginas=fotosPaginas_DAO.buscarPorPagina(fotosPaginas_BEAN);}catch(Exception erro){}
                    }
                   
            %>
            <a class="texto1">Cadastro fotosPaginas</a><br><br>
            <form method="post" enctype="multipart/form-data" >
            <div style="clear:both;color:red"><%=mensagem_ID%></div>
            <input type="hidden" name="id_categoria" value="<%=id_paginas%>"><br>
               <div style="clear:both;color:red"><%=mensagem_id_paginas%></div>
               <input type="hidden" name="id" value="<%=fotosPaginas_BEAN.getIDStr()%>" id="id"><br>
             <a class="texto3">Titulo:</a> <input type="text" name="Titulo" value="<%=fotosPaginas_BEAN.getTitulo()%>"><br>
               <div style="clear:both;color:red"><%=mensagem_Titulo%></div>
               <a class="texto3">Descrição:</a> <input type="text" name="descricao" value="<%=fotosPaginas_BEAN.getDescricao()%>"><br>
               <div style="clear:both;color:red"><%=mensagem_descricao%></div>
             <a class="texto3">Foto</a>: <input type="file" name="Src" value="<%=fotosPaginas_BEAN.getSrc()%>"><br>
               <div style="clear:both;color:red"><%=mensagem_Src%></div>
               <input type="submit" value="novo" onclick="document.getElementById('id').value=''">
               <input type="submit" value="Salvar" name="acao">              
               <input type="submit" value="Buscar" name="acao">
               <div style="clear:both;color:red"><%=mensagem_acao%></div>
            </form>
 
                <table cellpadding= "4" cellspacing = "0" border= "1" width= "400px">
                    <tr bgcolor="#CCCCCC">                           
                            
                            <td> <a class="texto3">Titulo</a> </td>
                      <td><a class="texto3"> Descrição</a> </td>
                            <td><a class="texto3"> acao </a> </td>
                            
		    </tr>
                  <%
                            if((registros_fotosPaginas!=null)&&(registros_fotosPaginas.size()>0)){
                                for(int i=0;i<registros_fotosPaginas.size();i++){
                                    fotosPaginas_BEAN=((fotosPaginasBEAN)registros_fotosPaginas.get(i));
                                    if(fotosPaginas_BEAN.getIDStr()==null)
                                        break;
                        %>
                        <tr>
                            <td> <%=Until.functions.removenull(fotosPaginas_BEAN.getTitulo())%> </td>     
                            <td> <%=Until.functions.removenull(fotosPaginas_BEAN.getDescricao())%> </td> 
                            <td width="150px">
                                <form method="post" >
                                    <input type="hidden" name="id" value="<%=fotosPaginas_BEAN.getIDStr()%>">
                                    <input type="submit" name="acao" value="editar">
                                    <input type="submit" value="Excluir" name="acao">
                                </form>
                            </td>
                        </tr>
                        <tr>
                            <td COLSpan="3" align="center">
                                <a target="_black" src="./arquivo.jsp?nome=h_<%=fotosPaginas_BEAN.getSrc()%>&pasta=conteudo" ><img align="center" src="./arquivo.jsp?nome=m_<%=fotosPaginas_BEAN.getSrc()%>&pasta=conteudo"></a>
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
