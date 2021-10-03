<%@page import="java.util.Random"%>
<%@page import="DAO.sorteioDAO"%>
<%@page import="BEAN.sorteioBEAN"%>
<%@page import="BEAN.sorteioBEAN"%>
<%@page import="java.util.Vector"%>
<%@page import="BD.Conexao"%>
<%@page import="BD.DadosConexao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <center>
            <%                
                Conexao minhaConexao=new Conexao(Until.functions.CreateDataConection()); 
                sorteioBEAN sorteio_BEAN=new sorteioBEAN();  
                sorteioDAO sorteio_DAO=new sorteioDAO(minhaConexao);
                Vector registros_sorteio=new Vector();
                
                String ID=request.getParameter("id");
                String nome=request.getParameter("nome");
                String email=request.getParameter("email");
                String telefone=request.getParameter("telefone");
                String acao=request.getParameter("acao");                
                
                String mensagem_ID="";
                String mensagem_nome="";
                String mensagem_email="";
                String mensagem_telefone="";
                String mensagem_acao="";

                if((acao!=null)&&(acao.length()>0)){ 
                    boolean valida=true;
                    if(acao.equals("Salvar")){                        
                        try{sorteio_BEAN.setID(ID);}catch(Exception erro){}
                        try{sorteio_BEAN.setNome(nome);}catch(Exception erro){mensagem_nome=erro.getMessage();valida=false;}
                        try{sorteio_BEAN.setEmail(email);}catch(Exception erro){mensagem_email=erro.getMessage();valida=false;}
                        try{sorteio_BEAN.setTelefone(telefone);}catch(Exception erro){mensagem_telefone=erro.getMessage();valida=false;}
                        try{
                            if(valida){
                                sorteio_BEAN=sorteio_DAO.salvar(sorteio_BEAN);
                                mensagem_acao="dados salvos com sucesso";
                            }
                            else
                                mensagem_acao="Alguns dados não Foram preenchidos corretamente";
                        }catch(Exception erro){mensagem_acao=erro.getMessage();}
                        registros_sorteio.add(sorteio_BEAN);
                    }                        
                    else if(acao.equals("Excluir")){
                        try{sorteio_BEAN.setID(ID);}catch(Exception erro){mensagem_ID=erro.getMessage();}                            
                        try{sorteio_DAO.excluir(sorteio_BEAN);}catch(Exception erro){mensagem_acao=erro.getMessage();}
                        ID="";                                                                                      
                    }
                    else if(acao.equals("Buscar")){                             
                        try{registros_sorteio=sorteio_DAO.buscarCampos(nome,email,telefone);}catch(Exception erro){mensagem_acao=erro.getMessage();}                                                         
                    }
                    else if(acao.equals("Editar")){
                        try{sorteio_BEAN.setID(ID);}catch(Exception erro){mensagem_ID=erro.getMessage();}
                        try{sorteio_BEAN=((sorteioBEAN)sorteio_DAO.buscarID(sorteio_BEAN));}catch(Exception erro){mensagem_acao=erro.getMessage();}
                        ID=sorteio_BEAN.getIDStr();
                        nome=sorteio_BEAN.getNome();  
                        email=sorteio_BEAN.getEmail();
                        telefone=sorteio_BEAN.getTelefone();
                        registros_sorteio.add(sorteio_BEAN);
                    }
                    else if(acao.equals("Sortear")){
                        registros_sorteio=sorteio_DAO.buscarTodos();
                        int contagem=registros_sorteio.size();
                        Random r = new Random () ;
                        int posicao=(int)(r.nextDouble () * (contagem)); 
                        sorteio_BEAN=(sorteioBEAN)registros_sorteio.get(posicao);
                        registros_sorteio=new Vector();
                        registros_sorteio.add(sorteio_BEAN);
                    }                                                            
                }                
                else{
                    registros_sorteio=sorteio_DAO.buscarTodos();
                }
                ID=(ID==null)?"":ID;
                nome=(nome==null)?"":nome;    
                email=(email==null)?"":email;
                telefone=(telefone==null)?"":telefone;                  
            %>
            <form method="post">
               Código: <input type="text" name="id" value="<%=ID%>"><br>
               <p style="color:red;"><%=mensagem_ID%></p>
               Nome: <input type="text" name="nome" value="<%=nome%>"><br>
               <p style="color:red;"><%=mensagem_nome%></p>
               Email: <input type="text" name="email" value="<%=email%>"><br>
               <p style="color:red;"><%=mensagem_email%></p>
               Telefone: <input type="text" name="telefone" value="<%=telefone%>"><br>
               <p style="color:red;"><%=mensagem_telefone%></p>
               <a href="?"><input type="button" value="Novo" name="acao"></a>
               <input type="submit" value="Salvar" name="acao">    
               <input type="submit" value="Buscar" name="acao">
               <input type="submit" value="Sortear" name="acao">
               <br>
               <p style="color:red;"><%=mensagem_acao%></p>
            </form>
                <table cellpadding= "4" cellspacing = "0" border= "1" width= "400px">
                    <tr>                           
                        <td>ID</td>
                        <td>Nome</td>
                        <td>Email</td>
                        <td>Telefone</td>
                        <td>acao</td>
		    </tr>
                        <%
                            if((registros_sorteio!=null)&&(registros_sorteio.size()>0)){
                                for(int i=0;i<registros_sorteio.size();i++){
                                    sorteio_BEAN=((sorteioBEAN)registros_sorteio.get(i));
                                    if(sorteio_BEAN.getIDStr()==null)
                                        break;
                        %>
                        <tr>
                            <td><%=sorteio_BEAN.getIDStr()%></a></td>
                            <td> <%=sorteio_BEAN.getNome()%> </td>     
                            <td> <%=sorteio_BEAN.getEmail()%> </td> 
                            <td> <%=sorteio_BEAN.getTelefone()%> </td> 
                            <td>
                                <FORM METHOD="POST">
                                    <input type="submit" name="acao" value="Editar">
                                    <input type="submit" name="acao" value="Excluir">
                                    <input type="hidden" name="id" value="<%=sorteio_BEAN.getIDStr()%>">
                                </form>
                            </td>
                        </tr>
                        <%
                                }
                            }
                        %>                     
                </table>
        </center>
    </body>
</html>
