<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@page import="POJO.bannersPOJO"%>
<%@page import="DAO.categoriaBannersDAO"%>
<%@page import="BEAN.categoriaBannersBEAN"%>
<%@page import="DAO.categoriaBannersDAO"%>
<%@page import="DAO.bannersDAO"%>
<%@page import="DAO.categoriaBannersDAO"%>
<%@page import="BEAN.categoriaBannersBEAN"%>
<%@page import="Until.functions"%>
<%@page import="java.util.Vector"%>
<%@page import="DAO.bannersDAO"%>
<%@page import="BEAN.bannersBEAN"%>
<%@page import="BD.Conexao"%>
<%@page import="BD.DadosConexao"%>
<link rel="stylesheet" type="text/css" href="estilosadm.css">
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
        <title>Cadastro Banners</title>
        <link rel="shortcut icon" href="./midia_mix.png" type="image/x-icon" />
    </head>
    <body onload="relogio();">
        <center>
            <jsp:include page="./topo.jsp" />
            <%
                Conexao minhaConexao=new Conexao(Until.functions.CreateDataConection()); 
                bannersBEAN Banners_BEAN=new bannersBEAN();
                bannersDAO Banners_DAO=new bannersDAO(minhaConexao);
                Vector registros_Banners=new Vector();
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
                     
                try{Banners_BEAN.setID(ID);}catch(Exception erro){mensagem_ID="a foto selecionada não existe";};
                try{Banners_BEAN.setId_Categoria(id_categoria);}catch(Exception erro){mensagem_id_categoria=erro.getMessage();}; 
                    
                if((acao!=null)&&(acao.length()>0)){     
						
                        String Titulo=request2.getParameter("Titulo");
                        String descricao=request2.getParameter("descricao");
						
                        try{Banners_BEAN.setTitulo(Titulo);}catch(Exception erro){mensagem_Titulo=erro.getMessage();};
                        try{Banners_BEAN.setDescricao(descricao);}catch(Exception erro){mensagem_descricao=erro.getMessage();}; 
                        
                        if(acao.equals("Salvar")){
                                if (
                                    (mensagem_Titulo.length()==0)
                                    &&
                                    (mensagem_descricao.length()==0)
                                    && 
                                    (mensagem_id_categoria.length()==0)                                                                                                                           
                                ){                             
                                    try{
                                        
                                        
                                        String acc="";
                                        if ((functions.path_upload!=null)&&(functions.path_upload.indexOf("\\")!=-1))
                                            acc="\\";
                                        else
                                            acc="/";
                                        String path=functions.path_upload+acc+"banner"+acc;
                                        
                                        String Src=request2.upload("Src",path);
                                        functions.redimensionarImagem(path+Src,path+Src,94,143,true);
                                        try{Banners_BEAN.setSrc(Src);}catch(Exception erro){mensagem_Src=erro.getMessage();}; 
                                        if ((ID!=null)&&(ID.length()!=0))
                                            mensagem_Src="";
                                        if(mensagem_Src.length()==0){
                                            Banners_BEAN=Banners_DAO.inserir(Banners_BEAN);
                                            registros_Banners.add(Banners_BEAN);
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
                                    Banners_BEAN.Clear();
                            }                        
                            else if(acao.equals("Excluir")){
                                if (
                                    (mensagem_ID.length()==0)                                                                                  
                                )
                                {
                                    Banners_BEAN=Banners_DAO.excluir(Banners_BEAN,functions.path_upload); 
                                    if((Banners_BEAN!=null)&&(Banners_BEAN.getIDStr()!=null)){
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
                                mensagem_id_categoria="";
                                Banners_BEAN.Clear();
                            }
                            else if(acao.equals("novo")){
                                mensagem_ID=""; 
                                mensagem_Titulo="";
                                mensagem_descricao="";
                                mensagem_Src="";
                                mensagem_id_categoria="";
                                Banners_BEAN.Clear();
                                Banners_BEAN.Clear();
                                try{Banners_BEAN.setId_Categoria(id_categoria);}catch(Exception erro){mensagem_id_categoria=erro.getMessage();}; 
                                mensagem_id_categoria="";
                                try{registros_Banners=Banners_DAO.buscarPorCategoria(Banners_BEAN);}catch(Exception erro){}
                            }
                    }
                    else{
                        mensagem_ID="";
                        Banners_BEAN.Clear();
                        try{Banners_BEAN.setId_Categoria(id_categoria);}catch(Exception erro){mensagem_id_categoria=erro.getMessage();}; 
                        mensagem_id_categoria="";
                        try{registros_Banners=Banners_DAO.buscarPorCategoria(Banners_BEAN);}catch(Exception erro){}
                    }
                   
            %>
            <a class="texto3">Cadastro Banners</a><br><br>
            <form method="post" enctype="multipart/form-data">
            <div style="clear:both;color:red"><%=mensagem_ID%></div>
            <a class="texto3">Categoria:</a> <select name="id_categoria" onchange="menuJump(this)">
            <%
                Vector registros_categoria=(new categoriaBannersDAO(minhaConexao)).buscarTodos();
               if((registros_categoria!=null)&&(registros_categoria.size()>0)){
                 for(int i=0;i<registros_categoria.size();i++){
                              categoriaBannersBEAN categoria_BEAN=((categoriaBannersBEAN)registros_categoria.get(i));      
            %>
                <option value="<%=categoria_BEAN.getIDStr()%>" <%
                    if(functions.equals(Banners_BEAN.getId_CategoriaStr(),categoria_BEAN.getIDStr())) 
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
               <input type="hidden" name="id" value="<%=Banners_BEAN.getIDStr()%>"><br>
               <input type="hidden" name="Titulo" value="banner<%=Banners_BEAN.getTitulo()%>">
               <div style="clear:both;color:red"><%=mensagem_Titulo%></div>
               <input type="hidden" name="descricao" value="banner<%=Banners_BEAN.getDescricao()%>">
               <div style="clear:both;color:red"><%=mensagem_descricao%></div>
               <a class="texto3">Foto:</a> <input type="file" name="Src" value="<%=Banners_BEAN.getSrc()%>"><br>
               <div style="clear:both;color:red"><%=mensagem_Src%></div>
               <input type="submit" value="novo" name="acao">
               <input type="submit" value="Salvar" name="acao">              
               <div style="clear:both;color:red"><%=mensagem_acao%></div>
            </form>
 
                <table cellpadding= "4" cellspacing = "0" border= "1" width= "400px">
                        <%
                            if((registros_Banners!=null)&&(registros_Banners.size()>0)){
                                for(int i=0;i<registros_Banners.size();i++){
                                    Banners_BEAN=((bannersBEAN)registros_Banners.get(i));
                                    if(Banners_BEAN.getIDStr()==null)
                                        break;
                        %>
                        <tr>
                            <td width="150px">
                                <form method="post" enctype="multipart/form-data">
                                    <input type="hidden" name="id" value="<%=Banners_BEAN.getIDStr()%>">
                                    <input type="submit" value="Excluir" name="acao">
                                </form>
                            </td>
                        </tr>
                        <tr>
                            <td COLSpan="3" align="center">
                                <a target="_black" src="./arquivo.jsp?nome=<%=Banners_BEAN.getSrc()%>&pasta=banner" ><img align="center" src="./arquivo.jsp?nome=<%=Banners_BEAN.getSrc()%>&pasta=banner" height="292" width="222"></a>
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
