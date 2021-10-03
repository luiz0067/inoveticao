<%@page import="Until.functions"%>
<%@page import="BD.Conexao"%>
<%@page import="DAO.contatoDAO"%>
<%@page import="BEAN.contatoBEAN"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String id = request.getParameter("id");
    Conexao minhaConexao=null;
    try{
        minhaConexao=(Conexao)session.getAttribute("minhaConexao");
    }
    catch(Exception erro){
    }
    contatoDAO contato_DAO = new contatoDAO(minhaConexao);
    contatoBEAN contato_BEAN=new contatoBEAN();
    String mensagem="";
    try{
        contato_BEAN.setID(id);
        contato_BEAN=contato_DAO.Desativar(contato_BEAN);
        if((contato_BEAN!=null)&&(contato_BEAN.getIDStr()!=null)){
            mensagem="Email desativo com sucesso!";
        }
        else{
            mensagem="Email não cadastrado!";
        }
    }
    catch(Exception erro){
        mensagem="Erro ao desativar Email!";
    }
%>
<center>
    <h1><%=mensagem%></h1>
</center> 
<%
    try{
        minhaConexao=(Conexao)session.getAttribute("minhaConexao");
    }
    catch(Exception erro){
    }
    minhaConexao.Fechar();
%>