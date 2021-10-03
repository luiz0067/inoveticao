<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@page import="java.util.Vector"%>
<%@page import="DAO.estadoDAO"%>
<%@page import="BEAN.estadoBEAN"%>
<%@page import="BD.Conexao"%>
<%@page import="BD.DadosConexao"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="shortcut icon" href="./midia_mix.png" 	type="image/x-icon" />
    </head>
    <body onload="relogio();">
        <center>
            <jsp:include page="./topo.jsp" />
             <%
                
                Conexao minhaConexao=new Conexao(Until.functions.CreateDataConection()); 
                estadoBEAN estado_BEAN=new estadoBEAN();
                estadoDAO estado_DAO=new estadoDAO(minhaConexao);
                Vector registros=new Vector();
                
                String ID=request.getParameter("id");
                String nome=request.getParameter("nome");
                String sigla=request.getParameter("sigla");
                String acao=request.getParameter("acao");

                    if((acao!=null)&&(acao.length()>0)){                       
                            if(acao.equals("Inserir")){
                                //insert                               
                                estado_BEAN.setNome(nome);
                                estado_BEAN.setSigla(sigla);                            
                                estado_DAO.inserir(estado_BEAN);
                                registros.add(estado_DAO.buscarUltimo());
                            }
                            else if(acao.equals("Alterar")){
                                //update
                                estado_BEAN.setID(ID);
                                estado_BEAN.setNome(nome);
                                estado_BEAN.setSigla(sigla);                              
                                estado_DAO.alterar(estado_BEAN);
                                registros.add(estado_BEAN);
                            }
                            else if(acao.equals("Excluir")){
                                //delete
                                estado_BEAN.setID(ID);                            
                                estado_DAO.excluir(estado_BEAN);                                                               
                            }
                            else if(acao.equals("Buscar")){                             
                                registros=estado_DAO.buscarNome(nome);                            
                               
                            }                      
                    }
                   else if((ID!=null)&&(ID.length()>0)){
                            estado_BEAN.setID(ID);
                            estado_BEAN=((estadoBEAN)estado_DAO.buscarID(estado_BEAN.getID()));
                            ID=estado_BEAN.getIDStr();
                            nome=estado_BEAN.getNome();
                            sigla=estado_BEAN.getSigla();
                            registros.add(estado_BEAN);
                        }
                        else{
                             
                            ID="";
                            nome="";
                            sigla="";
                            registros=estado_DAO.buscarTodos();
                        }
                   
            %>
            <form method="post">
               Código: <input type="text" name="id" value="<%=ID%>"><br>
               Nome: <input type="text" name="nome" value="<%=nome%>"><br>
               Sigla: <input type="text" name="sigla"value="<%=sigla%>" ><br><br>
               <input type="submit" value="Inserir" name="acao">
               <input type="submit" value="Alterar" name="acao">
               <input type="submit" value="Excluir" name="acao">
               <input type="submit" value="Buscar" name="acao">
               <input type="button" value="Buscar Todos" name="acao" onclick="self.location.href='?id'">
            </form>
                <table cellpadding= "4" cellspacing = "0" border= "1" width= "400px">
			<tr>
                            <td valign= "top"> Código </td>
                            <td valign= "top" > Nome </td>
                            <td valign= "top"> Sigla </td>		
			</tr>
                        <%
                            if((registros!=null)&&(registros.size()>0)){
                                for(int i=0;i<registros.size();i++){
                                    estado_BEAN=((estadoBEAN)registros.get(i));
                                    %>
                        <tr>
                            <td><a href="?id=<%=estado_BEAN.getIDStr()%>"> <%=estado_BEAN.getIDStr()%> </td>
                            <td> <%=estado_BEAN.getNome()%> </td>
                            <td> <%=estado_BEAN.getSigla()%> </td>	
                        </tr>
                                    <%
                                }
                            }
                         minhaConexao.Fechar();
                        %>
    </body>
</html>
