<%@page import="BEAN.menuBEAN"%>
<%@page import="DAO.menuDAO"%>
<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@page import="POJO.comentariosPOJO"%>
<%@page import="BEAN.comentariosBEAN"%>
<%@page import="DAO.comentariosDAO"%>
<%@page import="BEAN.comentariosBEAN"%>
<%@page import="DAO.comentariosDAO"%>
<%@page import="DAO.comentariosDAO"%>
<%@page import="Until.functions"%>
<%@page import="java.util.Vector"%>
<%@page import="DAO.comentariosDAO"%>
<%@page import="BEAN.comentariosBEAN"%>
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
            <%
                Conexao minhaConexao=new Conexao(Until.functions.CreateDataConection()); 
                comentariosBEAN comentarios_BEAN=new comentariosBEAN();
                menuBEAN menu_BEAN=new menuBEAN();
                comentariosDAO comentarios_DAO=new comentariosDAO(minhaConexao);
                Vector registros_comentarios=new Vector();

                String ID=request.getParameter("id");
                String id_menu=request.getParameter("id_menu");
                String acao=request.getParameter("acao");                
                
                String mensagem_ID="";
                String mensagem_id_menu="";
                String mensagem_acao="";
                     
                try{comentarios_BEAN.setID(ID);}catch(Exception erro){mensagem_ID="a comentarios selecionada não existe";};
                try{comentarios_BEAN.setid_menu(id_menu);}catch(Exception erro){mensagem_id_menu="a comentarios selecionada não existe";};
                try{menu_BEAN.setID(id_menu);}catch(Exception erro){mensagem_id_menu="a comentarios selecionada não existe";};
                    
                if((acao!=null)&&(acao.length()>0)){                       
                        if(acao.equals("Excluir")){
                                if (
                                    (mensagem_ID.length()==0)
                                                                                                                       
                                )
                                {
                                    comentarios_BEAN=comentarios_DAO.excluir(comentarios_BEAN); 
                                    if((comentarios_BEAN!=null)&&(comentarios_BEAN.getIDStr()!=null)){
                                        mensagem_acao="comentarios excluído com sucesso";
                                    }
                                    else{
                                        mensagem_acao="comentarios não encontrado";
                                    }                                    
                                }
                                mensagem_ID=""; 
                        }
                        else if(acao.equals("Ativar")){
                                if (
                                    (mensagem_ID.length()==0)                                                                                  
                                )
                                {
                                    comentarios_BEAN=comentarios_DAO.Ativar(comentarios_BEAN); 
                                }
                                mensagem_ID=""; 
                        }
                        else if(acao.equals("Desativar")){
                                if (
                                    (mensagem_ID.length()==0)                                                                                  
                                )
                                {
                                    comentarios_BEAN=comentarios_DAO.Desativar(comentarios_BEAN); 
                                }
                                mensagem_ID=""; 
                        }
                            
                    }
                    mensagem_ID="";
                    registros_comentarios=comentarios_DAO. buscarPorMenu(menu_BEAN);
            %>
            Cadastro Cometários<br><br>
            <form method="post" >
            <div style="clear:both;color:red"><%=mensagem_ID%></div>
            menu: <select name="id_menu" onchange="menuJump(this)">
            <%
                Vector registros_id_menu=(new menuDAO(minhaConexao)).buscarTodos();
               if((registros_id_menu!=null)&&(registros_id_menu.size()>0)){
                 for(int i=0;i<registros_id_menu.size();i++){
                             menu_BEAN=((menuBEAN)registros_id_menu.get(i));      
            %>
                <option value="<%=menu_BEAN.getIDStr()%>" <%
                    if(functions.equals(comentarios_BEAN.getid_menuStr(),menu_BEAN.getIDStr())) 
                        out.write("selected");
                %>>
                       <%=menu_BEAN.getTitulo()%>
                </option>
             <%
                        }
                    }
             %>
               </select><br>
               <input type="hidden" name="id" value="<%=Until.functions.removenull(comentarios_BEAN.getIDStr())%>"><br>
               <div style="clear:both;color:red"><%=mensagem_acao%></div>
            </form> 
                <table cellpadding= "4" cellspacing = "0" border= "1" width= "400px">
                    <tr>                   
                        <td> Titulo </td>
                        <td> acao </td>
		    </tr>
                        <%
                            if((registros_comentarios!=null)&&(registros_comentarios.size()>0)){
                                for(int i=0;i<registros_comentarios.size();i++){
                                    comentarios_BEAN=((comentariosBEAN)registros_comentarios.get(i));
                                    if(comentarios_BEAN.getIDStr()==null)
                                        break;
                        %>
                        <tr>
                            <td> <%=Until.functions.removenull(comentarios_BEAN.getTitulo())%> </td>     
                            <td width="150px">
                                <form method="post">
                                    <input type="hidden" name="id" value="<%=comentarios_BEAN.getIDStr()%>">
                                    <%if(comentarios_BEAN.getExibir()){%>
                                        <input type="submit" name="acao" value="Desativar">
                                    <%}else{%>
                                        <input type="submit" name="acao" value="Ativar">
                                    <%}%>
                                    <input type="submit" value="Excluir" name="acao">
                                </form>
                            </td>
                        </tr>
                        <tr>
                            <td COLSpan="2" align="center">
                                <%=Until.functions.removenull(comentarios_BEAN.getDescricao())%>
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
