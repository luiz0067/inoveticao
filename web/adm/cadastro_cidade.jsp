<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@page import="BEAN.estadoBEAN"%>
<%@page import="DAO.estadoDAO"%>
<%@page import="java.util.Vector"%>
<%@page import="BD.DadosConexao"%>
<%@page import="DAO.cidadeDAO"%>
<%@page import="BEAN.cidadeBEAN"%>
<%@page import="POJO.cidadePOJO"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="BD.Conexao"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="shortcut icon" href="./midia_mix.png" 	type="image/x-icon" />
    </head>
    <body onload="relogio();" >
        <center>
            <jsp:include page="./topo.jsp" />
            <%
                
                Conexao minhaConexao=new Conexao(Until.functions.CreateDataConection()); 
                cidadeBEAN cidade_BEAN=new cidadeBEAN();
                cidadeDAO cidade_DAO=new cidadeDAO(minhaConexao);
                Vector registros=new Vector();
                
                String ID=request.getParameter("id");
                String nome=request.getParameter("nome");
                String estado=request.getParameter("estado");
                String acao=request.getParameter("acao");

                    if((acao!=null)&&(acao.length()>0)){                       
                            if(acao.equals("Inserir")){
                                //insert                               
                                cidade_BEAN.setNome(nome);
                                cidade_BEAN.setEstado(estado);                            
                                cidade_DAO.inserir(cidade_BEAN);
                                registros.add(cidade_DAO.buscarUltimo());
                            }
                            else if(acao.equals("Alterar")){
                                //update
                                cidade_BEAN.setID(ID);
                                cidade_BEAN.setNome(nome);
                                cidade_BEAN.setEstado(estado);                              
                                cidade_DAO.alterar(cidade_BEAN);
                                registros.add(cidade_BEAN);
                            }
                            else if(acao.equals("Excluir")){
                                //delete
                                cidade_BEAN.setID(ID);                            
                                cidade_DAO.excluir(cidade_BEAN);                                                               
                            }
                            else if(acao.equals("Buscar")){                             
                                registros=cidade_DAO.buscarNome(nome);                            
                               
                            }                      
                    }
                   else if((ID!=null)&&(ID.length()>0)){
                            cidade_BEAN.setID(ID);
                            cidade_BEAN=((cidadeBEAN)cidade_DAO.buscarID(cidade_BEAN.getID()));
                            ID=cidade_BEAN.getIDStr();
                            nome=cidade_BEAN.getNome();
                            estado=cidade_BEAN.getEstado();
                            registros.add(cidade_BEAN);
                        }
                        else{
                             
                            ID="";
                            nome="";
                            estado="";
                            registros=cidade_DAO.buscarTodos();
                        }
                   
            %>
            <form method="post">
               Código: <input type="text" name="id" value="<%=ID%>"><br>
               Nome: <input type="text" name="nome" value="<%=nome%>"><br>
               Estado: <select name="estado">
              <%
                    Vector registros_estados=(new estadoDAO(minhaConexao)).buscarTodos();
                   if((registros_estados!=null)&&(registros_estados.size()>0)){
                     for(int i=0;i<registros_estados.size();i++){
                                  estadoBEAN estado_BEAN=((estadoBEAN)registros_estados.get(i));      
                %>
                <option value="<%=estado_BEAN.getIDStr()%>" <%if((estado!=null)&&(estado.equals(estado_BEAN.getIDStr()))) out.write("checked");%>>
                       <%=estado_BEAN.getNome()%>-<%=estado_BEAN.getSigla()%>
                </option>
             <%
                        }
                    }
             %>
               </select>
                   
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
                            <td valign= "top"> estado </td>		
			</tr>
                        <%
                            if((registros!=null)&&(registros.size()>0)){
                                for(int i=0;i<registros.size();i++){
                                    cidade_BEAN=((cidadeBEAN)registros.get(i));
                                    %>
                        <tr>
                            <td><a href="?id=<%=cidade_BEAN.getIDStr()%>"> <%=cidade_BEAN.getIDStr()%> </td>
                            <td> <%=cidade_BEAN.getNome()%> </td>
                            <td> <%=cidade_BEAN.getEstado()%> </td>	
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
