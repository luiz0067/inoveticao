<%@page import="java.io.File"%>
<%@page import="POJO.bannerPOJO"%>
<%@page import="java.util.Vector"%>
<%@page import="DAO.bannerDAO"%>
<%@page import="BEAN.bannerBEAN"%>
<%@page import="Until.functions"%>
<%@page import="BD.Conexao"%>
<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<link rel="stylesheet" type="text/css" href="estilosadm.css">

<html>
    <head>
       <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro banner</title>
        <link rel="shortcut icon" href="./midia_mix.png" type="image/x-icon" />
        <script type="text/javascript" src="js/jquery-1.2.6.pack.js"></script>
        <script type="text/javascript" src="js/jquery.maskedinput-1.1.4.pack.js"/></script>
        <script type="text/javascript">
            function validajpg(imagem){
                var estencao=imagem.value.toUpperCase();
                estencao=estencao.substring(estencao.length-4,estencao.length);
                if (estencao!=".JPG"){
                    alert("Por favor Insira uma imagem JPG");
                    return false;
                }
                return true;
            }
        </script>
    </head>
    <body>
        <center>
              <%
                Conexao minhaConexao=new Conexao(Until.functions.CreateDataConection()); 
                bannerBEAN banner_BEAN=new bannerBEAN();
                bannerDAO banner_DAO=new bannerDAO(minhaConexao);
                Vector registros_banner=new Vector();
                Until.functions request2=new Until.functions();
                request2.setRequest(request);

                String  acc="/";
                if ((functions.path_upload!=null)&&(functions.path_upload.indexOf("\\")!=-1))
                    acc="\\";               
                String path=functions.path_upload+acc+"banners"+acc;
                
                
                
                
                String id=request2.getParameter("id");
                String acao=request2.getParameter("acao");                
                
                String mensagem_id="";
             
                String mensagem_src="";
                
                String mensagem_acao="";
                     
                try{banner_BEAN.setid(id);}catch(Exception erro){mensagem_id="a foto selecionada não existe";};
                
                    
                if((acao!=null)&&(acao.length()>0)){     
						
                        
                        
                        if(acao.equals("Buscar")){
                            bannerPOJO  banner_POJO = new bannerPOJO();
                           
                            
                            banner_BEAN= new bannerBEAN(banner_POJO);
                                                        
                            mensagem_id="";
                        
                            mensagem_src="";
                           
                            mensagem_acao="";
                        }
                        else if(acao.equals("Salvar")){
                                
                                                          
                                  try{ 									
                                        String src=request2.uploadJPG("src",path);
                                        functions.redimensionarImagem(path+src,path+src,302,893,false);
                                        
                                      
                                        try{banner_BEAN.setsrc(src);}catch(Exception erro){mensagem_src=erro.getMessage();}; 
                                        
                                    
                                        if ((id!=null)&&(id.length()!=0))
                                            mensagem_src="";
                                       {
                                            banner_BEAN=banner_DAO.salvar(banner_BEAN,path);
                                            registros_banner.add(banner_BEAN);
                                        }
                                       
                                        
                                        mensagem_acao="Foto salva com sucesso";
                                        mensagem_id=""; 
                                    }                                        
                                    catch(Exception erro){
                                        mensagem_acao=erro.getMessage();
                                    }
                                     
                            }                        
                            else if(acao.equals("Excluir")){
                                if (
                                    (mensagem_id.length()==0)                                                                                  
                                )
                                {
                                    banner_BEAN=banner_DAO.excluir(banner_BEAN,path); 
                                    if((banner_BEAN!=null)&&(banner_BEAN.getidStr()!=null)){
                                        mensagem_acao="Foto excluída com sucesso";
                                    }
                                    else{
                                        mensagem_acao="Foto não encontrada";
                                    }                                    
                                }
                                mensagem_id=""; 
                                
                                mensagem_src="";
                               
                                banner_BEAN.Clear();
                            }
                            else if(acao.equals("editar")){                                
                                banner_BEAN=((bannerBEAN)banner_DAO.buscarid(banner_BEAN));
                                if((banner_BEAN!=null)&&(banner_BEAN.getidStr()!=null)){
                                    registros_banner.add(banner_BEAN);
                                    
                                }
                                else{
                                    banner_BEAN.Clear();
                                    try{banner_BEAN.setid(id);}catch(Exception erro){mensagem_id=erro.getMessage();}; 
                                   
                              
                                }
                                mensagem_id=""; 
                              
                                mensagem_src="";
                                
                                                                                              
                            }
                            else if(acao.equals("novo")){
                                mensagem_id=""; 
                                
                                mensagem_src="";
                               
                                banner_BEAN.Clear();
                            }
                            
                    }
                    else{
                        mensagem_id="";
                        registros_banner.clear();
                        registros_banner=banner_DAO.buscarTodos();
                        banner_BEAN.Clear();
                    }
                   
            %>
        <a class="texto1">Cadastro categoria Fotos</a><br><br>
            <form method="post" enctype="multipart/form-data">
                <input type="hidden" value="<%=banner_BEAN.getidStr()%>" name="id" id="id">
               <div style="clear:both;color:red"><%=mensagem_id%></div>
                <a class="texto3">Banner</a>: <input type="file" name="src" value="<%=banner_BEAN.getsrc()%>" onchange="return validajpg(this)"><br>
               <div style="clear:both;color:red"><%=mensagem_src%></div>
               <input type="submit" value="Novo" onclick="document.getElementByid('id').value=''">
               <input type="submit" value="Salvar" name="acao">
               <input type="submit" value="Buscar" name="acao">
               <div style="clear:both;color:red"><%=mensagem_acao%></div><br>
            </form>
 
                <table cellpadding= "4" cellspacing = "0" border= "1" width= "400px">
                    <tr bgcolor="#CCCCCC">                           
                         
                            
                            <td><a class="texto3"> acao </a></td>
		    </tr>
                  <%
                            if((registros_banner!=null)&&(registros_banner.size()>0)){
                                for(int i=0;i<registros_banner.size();i++){
                                    banner_BEAN=((bannerBEAN)registros_banner.get(i));
                        %>
                        <tr>
                            
                            
                           
                            <td>
                                <form method="post" >
                                    <input type="hidden" name="id" value="<%=banner_BEAN.getidStr()%>">
                                    <input type="submit" name="acao" value="editar">
                                    <input type="submit" value="Excluir" name="acao">
                                </form>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2"><img src="./arquivo.jsp?nome=<%=banner_BEAN.getsrc()%>&pasta=banners"></td>
                            
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

