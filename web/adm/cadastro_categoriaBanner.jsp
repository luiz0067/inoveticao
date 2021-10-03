<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@page import="POJO.categoriaBannersPOJO"%>
<%@page import="Until.functions"%>
<%@page import="java.util.Vector"%>
<%@page import="DAO.categoriaBannersDAO"%>
<%@page import="BEAN.categoriaBannersBEAN"%>
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
        <title>Cadastro categoria Banners</title>
        <link rel="shortcut icon" href="./midia_mix.png" 	type="image/x-icon" />
    </head>
    <body onload="relogio();">
        <center>
            <jsp:include page="./topo.jsp" />
            <%
                Conexao minhaConexao=new Conexao(functions.CreateDataConection()); 
                categoriaBannersBEAN categoriaBanners_BEAN=new categoriaBannersBEAN();
                categoriaBannersDAO categoriaBanners_DAO=new categoriaBannersDAO(minhaConexao);
                Vector registros_categoriaBanners=new Vector();
                
                String ID=request.getParameter("id");
                String Titulo=request.getParameter("Titulo");
                String acao=request.getParameter("acao");
                
                String mensagem_ID="";
                String mensagem_Titulo="";
                String mensagem_acao="";
                
                try{categoriaBanners_BEAN.setID(ID);}catch(Exception erro){mensagem_ID="O categoriaBanners selecionado não existe";};
                if((acao!=null)&&(acao.length()>0)){  
                    try{categoriaBanners_BEAN.setTitulo(Titulo);}catch(Exception erro){mensagem_Titulo=erro.getMessage();};
                    if(acao.equals("Buscar")){
                        categoriaBannersPOJO  categoriaBanners_POJO=new categoriaBannersPOJO();
                        categoriaBanners_POJO.setTitulo(Titulo);
                        categoriaBanners_BEAN=new categoriaBannersBEAN(categoriaBanners_POJO);
                        try{registros_categoriaBanners=categoriaBanners_DAO.buscar(categoriaBanners_BEAN);}catch(Exception erro){mensagem_acao=erro.getMessage();};                            
                        mensagem_ID="";
                        mensagem_Titulo="";
                        mensagem_acao="";                        
                    }
                    else if(acao.equals("Salvar")){
                        if (
                            (mensagem_Titulo.length()==0)
                        ){
                            try{
                                categoriaBanners_BEAN=categoriaBanners_DAO.salvar(categoriaBanners_BEAN);
                                registros_categoriaBanners.add(categoriaBanners_BEAN);
                                mensagem_acao="Categoria salva com sucesso";
                            }
                            catch(Exception erro){
                                mensagem_acao=erro.getMessage();
                            } 
                        }
                        else
                            categoriaBanners_BEAN.Clear();
                        mensagem_ID="";
                    }
                    else if (acao.equals("editar")){
                        mensagem_ID="";
                        mensagem_Titulo="";
                        mensagem_acao="";
                        categoriaBanners_BEAN=((categoriaBannersBEAN)categoriaBanners_DAO.buscarID(categoriaBanners_BEAN));
                        if((categoriaBanners_BEAN!=null)&&(categoriaBanners_BEAN.getIDStr()!=null)){
                            registros_categoriaBanners.add(categoriaBanners_BEAN);
                        }
                    }
                    else if(acao.equals("Excluir")){
                        if (
                            (mensagem_ID.length()==0)                                                                                  
                        )
                        {
                            try{
                                categoriaBanners_BEAN=categoriaBanners_DAO.excluir(categoriaBanners_BEAN);
                                if((categoriaBanners_BEAN!=null)&&(categoriaBanners_BEAN.getIDStr()!=null)){
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
                        registros_categoriaBanners.clear();
                        registros_categoriaBanners=categoriaBanners_DAO.buscarTodos();
                        categoriaBanners_BEAN.Clear();
                }
            %>
        <a class="texto1">Cadastro categoria Banners</a><br><br>
            <form method="post">
                <input type="hidden" value="<%=categoriaBanners_BEAN.getIDStr()%>" name="id">
               <div style="clear:both;color:red"><%=mensagem_ID%></div>
             <a class="texto3">Titulo:</a> <input type="text" name="Titulo" value="<%=categoriaBanners_BEAN.getTitulo()%>"><br>
               <div style="clear:both;color:red"><%=mensagem_Titulo%></div>
               <input type="submit" value="Novo" >
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
                            if((registros_categoriaBanners!=null)&&(registros_categoriaBanners.size()>0)){
                                for(int i=0;i<registros_categoriaBanners.size();i++){
                                    categoriaBanners_BEAN=((categoriaBannersBEAN)registros_categoriaBanners.get(i));
                        %>
                        <tr>
                            
                            <td><%=categoriaBanners_BEAN.getTitulo()%></td>
                            <td>
                                <form method="post" >
                                    <input type="hidden" name="id" value="<%=categoriaBanners_BEAN.getIDStr()%>">
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

