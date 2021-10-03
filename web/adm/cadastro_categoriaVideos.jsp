<%@page import="BEAN.categoriaVideosBEAN"%>
<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@page import="POJO.categoriaVideosPOJO"%>
<%@page import="Until.functions"%>
<%@page import="java.util.Vector"%>
<%@page import="DAO.categoriaVideosDAO"%>
<%@page import="BEAN.categoriaVideosBEAN"%>
<%@page import="BD.Conexao"%>
<%@page import="BD.DadosConexao"%>
<!DOCTYPE html>
<style type="text/css">
	.texto1{font-family:Arial, Helvetica, sans-serif; font-size:14px; text-align:center;  font-size:16px; text-align:center; width:800px; height:auto; margin-right:auto; margin-left:auto; float:center; line-height:23px;}
	
	.texto2{font-family:Arial, Helvetica, sans-serif; font-size:14px; text-align:center; font-size:26px; text-align:center; width:800px; height:auto; margin-right:auto; margin-left:auto; float:center; line-height:29px;}
	
	.texto3{font-family:Arial, Helvetica, sans-serif; font-size:14px; text-align:center; font-size:15px; text-align:center; width:800px; height:auto; margin-right:auto; margin-left:auto; float:center; line-height:29px;}
</style>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro categoria Videos</title>
        <link rel="shortcut icon" href="./midia_mix.png" 	type="image/x-icon" />
    </head>
    <body onload="relogio();">
        <center>
            <%
               
                Conexao minhaConexao=new Conexao(functions.CreateDataConection()); 
                categoriaVideosBEAN categoriaVideos_BEAN=new categoriaVideosBEAN();
                categoriaVideosDAO categoriaVideos_DAO=new categoriaVideosDAO(minhaConexao);
                Vector registros_categoriaVideos=new Vector();
                
                String ID=request.getParameter("id");
                String Titulo=request.getParameter("Titulo");
                String acao=request.getParameter("acao");
                
                String mensagem_ID="";
                String mensagem_Titulo="";
                String mensagem_acao="";
                
                try{categoriaVideos_BEAN.setID(ID);}catch(Exception erro){mensagem_ID="O categoriaVideos selecionado não existe";};
                if((acao!=null)&&(acao.length()>0)){  
                    try{categoriaVideos_BEAN.setTitulo(Titulo);}catch(Exception erro){mensagem_Titulo=erro.getMessage();};
                    if(acao.equals("Buscar")){
                        categoriaVideosPOJO  categoriaVideos_POJO=new categoriaVideosPOJO();
                        categoriaVideos_POJO.setTitulo(Titulo);
                        categoriaVideos_BEAN=new categoriaVideosBEAN(categoriaVideos_POJO);
                        try{registros_categoriaVideos=categoriaVideos_DAO.buscar(categoriaVideos_BEAN);}catch(Exception erro){mensagem_acao=erro.getMessage();};                            
                        mensagem_ID="";
                        mensagem_Titulo="";
                        mensagem_acao="";                        
                    }
                    else if(acao.equals("Salvar")){
                        if (
                            (mensagem_Titulo.length()==0)
                        ){
                            try{
                                categoriaVideos_BEAN=categoriaVideos_DAO.salvar(categoriaVideos_BEAN);
                                registros_categoriaVideos.add(categoriaVideos_BEAN);
                                mensagem_acao="Categoria salva com sucesso";
                            }
                            catch(Exception erro){
                                mensagem_acao=erro.getMessage();
                            } 
                        }
                        else
                            categoriaVideos_BEAN.Clear();
                        mensagem_ID="";
                    }
                    else if (acao.equals("editar")){
                        mensagem_ID="";
                        mensagem_Titulo="";
                        mensagem_acao="";
                        categoriaVideos_BEAN=((categoriaVideosBEAN)categoriaVideos_DAO.buscarID(categoriaVideos_BEAN));
                        if((categoriaVideos_BEAN!=null)&&(categoriaVideos_BEAN.getIDStr()!=null)){
                            registros_categoriaVideos.add(categoriaVideos_BEAN);
                        }
                    }
                    else if(acao.equals("Excluir")){
                        if (
                            (mensagem_ID.length()==0)                                                                                  
                        )
                        {
                            try{
                                categoriaVideos_BEAN=categoriaVideos_DAO.excluir(categoriaVideos_BEAN);
                                if((categoriaVideos_BEAN!=null)&&(categoriaVideos_BEAN.getIDStr()!=null)){
                                    mensagem_acao="Categoria excluído com sucesso";
                                }
                                else{
                                    mensagem_acao="Categoria não encontrada";
                                }
                            }
                            catch(Exception erro){
                                mensagem_acao=erro.getMessage();
                            }
                        }
                        
                    }
                                                                                
                }
                else {
                    
                    
                    
                        mensagem_ID="";
                        registros_categoriaVideos.clear();
                        registros_categoriaVideos=categoriaVideos_DAO.buscarTodos();
                        categoriaVideos_BEAN.Clear();
                    
                }
            %>
        <a class="texto1">Cadastro categoria Videos</a><br><br>
            <form method="post">
                <input type="hidden" value="<%=categoriaVideos_BEAN.getIDStr()%>" name="id" id="id">
               <div style="clear:both;color:red"><%=mensagem_ID%></div>
             <a class="texto3">Titulo:</a> <input type="text" name="Titulo" value="<%=categoriaVideos_BEAN.getTitulo()%>"><br>
               <div style="clear:both;color:red"><%=mensagem_Titulo%></div>
               <input type="submit" value="Novo" onclick="document.getElementById('id').value=''">
               <input type="submit" value="Salvar" name="acao">
               <input type="submit" value="Buscar" name="acao">
               <div style="clear:both;color:red"><%=mensagem_acao%></div><br>
            </form>
 
                <table cellpadding= "4" cellspacing = "0" border= "1" width= "400px">
                    <tr bgcolor="#CCCCCC">                           
                            <td><a class="texto3"> Titulo </a></td>
                            <td><a class="texto3"> acao </a></td>
		    </tr>
                  <%
                            if((registros_categoriaVideos!=null)&&(registros_categoriaVideos.size()>0)){
                                for(int i=0;i<registros_categoriaVideos.size();i++){
                                    categoriaVideos_BEAN=((categoriaVideosBEAN)registros_categoriaVideos.get(i));
                        %>
                        <tr>
                            
                            <td><%=categoriaVideos_BEAN.getTitulo()%></td>
                            <td>
                                <form method="post" >
                                    <input type="hidden" name="id" value="<%=categoriaVideos_BEAN.getIDStr()%>">
                                    <input type="submit" name="acao" value="editar">
                                    <input type="submit" value="Excluir" name="acao">
                                </form>
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

