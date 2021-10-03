<%@page import="POJO.fotosPOJO"%>
<%@page import="DAO.categoriaFotosDAO"%>
<%@page import="BEAN.categoriaFotosBEAN"%>
<%@page import="DAO.categoriaFotosDAO"%>
<%@page import="DAO.fotosDAO"%>
<%@page import="DAO.categoriaFotosDAO"%>
<%@page import="BEAN.categoriaFotosBEAN"%>
<%@page import="Until.functions"%>
<%@page import="java.util.Vector"%>
<%@page import="DAO.fotosDAO"%>
<%@page import="BEAN.fotosBEAN"%>
<%@page import="BD.Conexao"%>
<%@page import="BD.DadosConexao"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro fotos</title>
        <link rel="shortcut icon" href="./midia_mix.png" 	type="image/x-icon" />
    </head>
    <body onload="relogio();">
        <center>
            <jsp:include page="./topo.jsp" />
            <%
                Conexao minhaConexao=new Conexao(Until.functions.CreateDataConection()); 
                fotosBEAN fotos_BEAN=new fotosBEAN();
                fotosDAO fotos_DAO=new fotosDAO(minhaConexao);
                Vector registros_fotos=new Vector();
                Until.functions request2=new Until.functions();
                request2.setRequest(request);

                String ID=request2.getParameter("id");
                String id_categoria=request2.getParameter("id_categoria");
                String acao=request2.getParameter("acao");                
                
                String mensagem_ID="";
                String mensagem_acao="";
                String mensagem_Titulo="";
                String mensagem_descricao="";
                String mensagem_Src="";
                String mensagem_id_categoria="";          
                     
                try{fotos_BEAN.setID(ID);}catch(Exception erro){mensagem_ID="a foto selecionada n�o existe";};
                try{fotos_BEAN.setId_Categoria(id_categoria);}catch(Exception erro){mensagem_id_categoria=erro.getMessage();}; 
                    
                if((acao!=null)&&(acao.length()>0)){                       
                        String Titulo=request2.getParameter("Titulo");
                        String descricao=request2.getParameter("descricao");

                        try{fotos_BEAN.setTitulo(Titulo);}catch(Exception erro){mensagem_Titulo=erro.getMessage();};
                        try{fotos_BEAN.setDescricao(descricao);}catch(Exception erro){mensagem_descricao=erro.getMessage();}; 
                        
                        if(acao.equals("Buscar")){
                            fotosPOJO  fotos_POJO = new fotosPOJO();
                            fotos_POJO.setTitulo(Titulo);
                            fotos_POJO.setDescricao(descricao);
                            fotos_BEAN= new fotosBEAN(fotos_POJO);
                            try{registros_fotos=fotos_DAO.buscar(fotos_BEAN);}catch(Exception erro){mensagem_acao=erro.getMessage();};                            
                            mensagem_ID="";
                            mensagem_acao="";
                            mensagem_Titulo="";
                            mensagem_descricao="";
                            mensagem_Src="";
                            mensagem_id_categoria="";
                        }
                        else if(acao.equals("Salvar")){
                                if (
                                    (mensagem_Titulo.length()==0)
                                    &&
                                    (mensagem_descricao.length()==0)
                                    && 
                                    (mensagem_id_categoria.length()==0)                                                                                                                           
                                ){                             
                                    try{
                                        String Src=request2.uploadJPGRedimenciona("Src",functions.path_upload);
                                        
                                        try{fotos_BEAN.setSrc(Src);}catch(Exception erro){mensagem_Src=erro.getMessage();}; 
                                        if ((ID!=null)&&(ID.length()!=0))
                                            mensagem_Src="";
                                        if(mensagem_Src.length()==0){
                                            fotos_BEAN=fotos_DAO.salvar(fotos_BEAN,functions.path_upload);
                                            registros_fotos.add(fotos_BEAN);
                                        }
                                        else{
                                            functions.deletaImagensRedimencionadas(Src,functions.path_upload);
                                        }
                                    }
                                    catch(Exception erro){
                                        mensagem_acao=erro.getMessage();
                                        mensagem_acao="Foto salva com sucesso";
                                    }
                                }
                                else
                                    fotos_BEAN.Clear();
                            }                        
                            else if(acao.equals("Excluir")){
                                if (
                                    (mensagem_ID.length()==0)                                                                                  
                                )
                                {
                                    fotos_BEAN=fotos_DAO.excluir(fotos_BEAN,functions.path_upload); 
                                    if((fotos_BEAN!=null)&&(fotos_BEAN.getIDStr()!=null)){
                                        mensagem_acao="Foto exclu�da com sucesso";
                                    }
                                    else{
                                        mensagem_acao="Foto n�o encontrada";
                                    }                                    
                                }
                                mensagem_ID=""; 
                                mensagem_Titulo="";
                                mensagem_descricao="";
                                mensagem_Src="";
                                mensagem_id_categoria="";
                                fotos_BEAN.Clear();
                            }
                            else if(acao.equals("editar")){                                
                                fotos_BEAN=((fotosBEAN)fotos_DAO.buscarID(fotos_BEAN));
                                if((fotos_BEAN!=null)&&(fotos_BEAN.getIDStr()!=null)){
                                    registros_fotos.add(fotos_BEAN);
                                    mensagem_id_categoria="";
                                }
                                else{
                                    fotos_BEAN.Clear();
                                    try{fotos_BEAN.setId_Categoria(id_categoria);}catch(Exception erro){mensagem_id_categoria=erro.getMessage();}; 
                                    mensagem_id_categoria="";
                                    registros_fotos=fotos_DAO.buscarPorCategoria(fotos_BEAN);
                                }
                                mensagem_ID=""; 
                                mensagem_Titulo="";
                                mensagem_descricao="";
                                mensagem_Src="";
                                mensagem_id_categoria="";                                                               
                            }
                            else if(acao.equals("novo")){
                                mensagem_ID=""; 
                                mensagem_Titulo="";
                                mensagem_descricao="";
                                mensagem_Src="";
                                mensagem_id_categoria="";
                                fotos_BEAN.Clear();
                            }
                            
                    }
                    else{
                            mensagem_ID="";
                            fotos_BEAN.Clear();
                            try{fotos_BEAN.setId_Categoria(id_categoria);}catch(Exception erro){mensagem_id_categoria=erro.getMessage();}; 
                            mensagem_id_categoria="";
                            registros_fotos=fotos_DAO.buscarPorCategoria(fotos_BEAN);
                    }
                   
            %>
            Cadastro fotos<br><br>
            <form method="post" enctype="multipart/form-data">
            <div style="clear:both;color:red"><%=mensagem_ID%></div>
            Categoria: <select name="id_categoria" onchange="menuJump(this)">
            <%
                Vector registros_categoria=(new categoriaFotosDAO(minhaConexao)).buscarTodos();
               if((registros_categoria!=null)&&(registros_categoria.size()>0)){
                 for(int i=0;i<registros_categoria.size();i++){
                              categoriaFotosBEAN categoria_BEAN=((categoriaFotosBEAN)registros_categoria.get(i));      
            %>
                <option value="<%=categoria_BEAN.getIDStr()%>" <%
                    if(functions.equals(fotos_BEAN.getId_CategoriaStr(),categoria_BEAN.getIDStr())) 
                        out.write("selected");
                %>>
                       <%=categoria_BEAN.getTitulo()%>
                </option>
             <%
                        }
                    }
             %>
               </select><br>
               <div style="clear:both;color:red"><%=mensagem_id_categoria%></div>
               <input type="hidden" name="id" value="<%=fotos_BEAN.getIDStr()%>"><br>
               Titulo: <input type="text" name="Titulo" value="<%=fotos_BEAN.getTitulo()%>"><br>
               <div style="clear:both;color:red"><%=mensagem_Titulo%></div>
               Descri��o: <input type="text" name="descricao" value="<%=fotos_BEAN.getDescricao()%>"><br>
               <div style="clear:both;color:red"><%=mensagem_descricao%></div>
               Foto: <input type="file" name="Src" value="<%=fotos_BEAN.getSrc()%>"><br>
               <div style="clear:both;color:red"><%=mensagem_Src%></div>
               <input type="submit" value="novo" name="acao">
               <input type="submit" value="Salvar" name="acao">              
               <input type="submit" value="Buscar" name="acao">
               <div style="clear:both;color:red"><%=mensagem_acao%></div>
            </form>
 
                <table cellpadding= "4" cellspacing = "0" border= "1" width= "400px">
                    <tr>                           
                            
                            <td> Titulo </td>
                            <td> Descri��o </td>
                            <td> acao </td>
                            
		    </tr>
                        <%
                            if((registros_fotos!=null)&&(registros_fotos.size()>0)){
                                for(int i=0;i<registros_fotos.size();i++){
                                    fotos_BEAN=((fotosBEAN)registros_fotos.get(i));
                                    if(fotos_BEAN.getIDStr()==null)
                                        break;
                        %>
                        <tr>
                            <td> <%=Until.functions.removenull(fotos_BEAN.getTitulo())%> </td>     
                            <td> <%=Until.functions.removenull(fotos_BEAN.getDescricao())%> </td> 
                            <td width="150px">
                                <form method="post" enctype="multipart/form-data">
                                    <input type="hidden" name="id" value="<%=fotos_BEAN.getIDStr()%>">
                                    <input type="submit" name="acao" value="editar">
                                    <input type="submit" value="Excluir" name="acao">
                                </form>
                            </td>
                        </tr>
                        <tr>
                            <td COLSpan="3" align="center">
                                <a target="_black" src="./arquivo.jsp?nome=h_<%=fotos_BEAN.getSrc()%>" ><img align="center" src="./arquivo.jsp?nome=m_<%=fotos_BEAN.getSrc()%>"></a>
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
