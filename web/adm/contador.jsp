<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %> 
<%@page import="POJO.contagemPOJO"%>
<%@page import="BEAN.contagemBEAN"%>
<%@page import="DAO.contagemDAO"%>
<%@page import="BEAN.contagemBEAN"%>
<%@page import="DAO.contagemDAO"%>
<%@page import="DAO.contagemDAO"%>
<%@page import="Until.functions"%>
<%@page import="java.util.Vector"%>
<%@page import="DAO.contagemDAO"%>
<%@page import="BEAN.contagemBEAN"%>
<%@page import="BD.Conexao"%>
<%@page import="BD.DadosConexao"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro COMENTÁRIOS</title>
        <link rel="shortcut icon" href="./midia_mix.png" 	type="image/x-icon" />
    </head>
    <body onload="relogio();">    
        <center>
            <jsp:include page="./topo.jsp" />
            <%
                Conexao minhaConexao=new Conexao(Until.functions.CreateDataConection()); 
                contagemBEAN contagem_BEAN=new contagemBEAN();
                contagemDAO contagem_DAO=new contagemDAO(minhaConexao);
                int Acessos_totais=contagem_DAO.contarTodos();
                int Acessos_hoje=contagem_DAO.contarHoje();
                int Acessos_ontem=contagem_DAO.contarOntem();
                int Acessos_semana=contagem_DAO.contarEstaSemana();
                int Acessos_mes=contagem_DAO.contarEsteMes();
                int Acessos_IP_totais=contagem_DAO.contarTodosIPS();
                int Acessos_IP_Hoje=contagem_DAO.contarHojeIPS();
            %>
            Contator de visitas<br><br>
                <table cellpadding= "4" cellspacing = "0" border= "1" width= "400px">
                    <tr>                   
                        <td style="text-align:right;">Acessos totais</td>
                        
                        <td><%=Acessos_totais%></td>
                    </tr>
                    <tr>     
                        <td style="text-align:right;">Acessos hoje</td>
                        
                        <td><%=Acessos_hoje%></td>
		    </tr>
                    <tr>                   
                        <td style="text-align:right;">Acessos ontem</td>
                        
                        <td><%=Acessos_ontem%></td>
		    </tr>
                    <tr>                   
                        <td style="text-align:right;">Da esta semana</td>
                        
                        <td><%=Acessos_semana%></td>
		    </tr>
                    <tr>                   
                        <td style="text-align:right;">Do esta mes</td>
                        
                        <td><%=Acessos_mes%></td>
		    </tr>
                    <tr>                   
                        <td style="text-align:right;">IP Hoje</td>
                        
                        <td><%=Acessos_IP_Hoje%></td>
		    </tr>
                    <tr>                   
                        <td style="text-align:right;">Todos os ips</td>
                        
                        <td><%=Acessos_IP_totais%></td>
		    </tr>
                </table>
        </center>
    </body>
</html>
