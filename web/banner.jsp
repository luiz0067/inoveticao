<%@page import="DAO.menuDAO"%>
<%@page import="BEAN.menuBEAN"%>
<%@page import="Until.functions"%>
<%@page import="DAO.banner2DAO"%>
<%@page import="BEAN.banner2BEAN"%>
<%@page import="DAO.fotosDAO"%>
<%@page import="BEAN.fotosBEAN"%>
<%@page import="DAO.comentariosDAO"%>
<%@page import="BEAN.comentariosBEAN"%>
<%@page import="java.util.Date"%>
<%@page import="Until.functions"%>
<%@page import="BD.Conexao"%>
<%@page import="BEAN.contatoBEAN"%>
<%@page import="java.util.Vector"%>
<%@page import="DAO.contatoDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
 
%>
<link rel="stylesheet" type="text/css" href="estilos.css" />

<div class="linhascentro">
 <%
Conexao minhaConexao=null;
try{
    minhaConexao=(Conexao)session.getAttribute("minhaConexao");
    
}
catch(Exception erro){
}
try{
    banner2BEAN banner2_BEAN=new banner2BEAN();
    banner2DAO banner2_DAO=new banner2DAO(minhaConexao);
    Vector registros_banner2=new Vector();
    registros_banner2=banner2_DAO.buscarTodos(); 
    %>
    <script type="text/javascript">
    var vetor_fotos_banner2=new Array(); 
    var posicao_banner2=-1;
    function proximo_banner2(){
        if(vetor_fotos_banner2.length!=0){
            var img=document.getElementById('banner2');
           
            posicao_banner2++;
            if (posicao_banner2<vetor_fotos_banner2.length){
               img.src=vetor_fotos_banner2[posicao_banner2]; 
            }
            else{
                posicao_banner2=-1;
            }
        }
    }
    function iniciar_banner2(){
        proximo_banner2();
        setTimeout("iniciar_banner2()",2000);
    }
<%
        if((registros_banner2!=null)&&(registros_banner2.size()>0)){
            for(int i=0;i<registros_banner2.size();i++){
                banner2_BEAN=((banner2BEAN)registros_banner2.get(i));
%>
    vetor_fotos_banner2.push("./arquivo.jsp?nome=<%=banner2_BEAN.getsrc()%>&pasta=banner2s");
<%                
            }
        }
%>
        </script>
<%     
    
}
catch(Exception erro){
    
}
String pg=request.getParameter("pg");   
pg=(pg==null)?"":pg; 

if(
	(pg.equals("inoveticao"))
	||
	(pg.equals("videos"))
	||
	(pg.equals("agenda"))
	||
	(pg.equals("fotos"))
){%>
  <div class="divisorlinhas"></div>
  <div class="linhascentro" style="height:123px;"> <img id="banner2" width="907" height="123" border="0" /></div>
  <div class="divisorlinhas" style="height:22px;"></div>
  <div class="linhascentro" style="height:400px;">
    <div class="comentarios">
      <div class="branco" style="width:572px; height:29px;">
        <div class="branco" style="width:200px; height:29px; line-height:29px; color:#F00; font-family:'myriadpro';font-size:19px; text-align:left; font-style:italic; text-indent:50px;"> COMENTÁRIOS </div>
        <div class="branco" style="width:322px; height:29px; line-height:29px; color:#F00; font-family:'myriadpro';font-size:12px; text-align:right; font-style:italic; padding-right:40px;"> <A onclick="document.getElementById('coment').style.display='block'"  style="text-decoration:none; color:#FFF;">DEIXE SEU COMENTÁRIO </A> <A onclick="document.getElementById('coment').style.display='block'" style="text-decoration:none; color:#F00;"> Clique Aqui</A> </div>
        <div style="position:absolute;width:450px;height:300px;background-color:black;color:white;margin-left:120px;_margin-left:-440px;display:none;overflow:hidden" ID="coment">
          <div style="clear:both;width:100%;">
            <div style="float:left;font-weight:bold;text-align:center;width:100%" >
              <div style="width:410px;float:left;">Digite seu comentário aqui </div>
              <div style="float:right;font-weight:bold;width:30px" onclick="document.getElementById('coment').style.display='none'">[ X ] &nbsp;</div>
            </div>
            <div style="padding-left:30px;padding-right:30px;clear: both;width:100%;">
              <form method="post">
                <div style="float:left;width:90px;">Nome:</div>
                <div style="float:left;">
                  <input type="text" name="titulo" style="width:290px;">
                </div>
                <div style="clear:both;">Comentário:</div>
                <div style="clear:both;">
                  <textarea name="comentario" style="width:380px;height:150px;"></textarea>
                </div>
                <br>
                <div style="clear:both;">
                  <input type="submit" name="acao_comentario" value="Enviar">
                </div>
              </form>
              <%
                                                comentariosBEAN comentarios_BEAN=new comentariosBEAN();
                                                comentariosDAO comentarios_DAO = new comentariosDAO(minhaConexao);
                                                    String pg_=request.getParameter("pg");
                                                    menuBEAN menu_BEAN= new menuBEAN();
                                                    menuDAO menu_DAO=new menuDAO(minhaConexao);
                                                String Comentario=request.getParameter("comentario");
                                                String Titulo=request.getParameter("titulo");
                                                String mensagem_comentario="";
                                                String mensagem_nome="";  
                                                String mensagem_acao="";                                                                                 
                                                String acao_comentario=request.getParameter("acao_comentario");
                                                if((acao_comentario!=null)&&(acao_comentario.equals("Enviar"))){
                                                    comentarios_BEAN.setExibir(false);
                                                    comentarios_BEAN.setid_menu(menu_BEAN.getIDStr());
                                                    Date Hoje_agora=new Date();
                                                    comentarios_BEAN.setData_Cadastro(Hoje_agora);
                                                    try{
                                                        comentarios_BEAN.setTitulo(Titulo);
                                                    }
                                                    catch(Exception erro){
                                                        mensagem_nome=erro.getMessage();
                                                    }
                                                    try{
                                                        comentarios_BEAN.setDescricao(Comentario);
                                                    }
                                                    catch(Exception erro){
                                                        mensagem_comentario=erro.getMessage();
                                                    }
                                                    try{
                                                        comentarios_BEAN= comentarios_DAO.salvar(comentarios_BEAN);
                                                        if ((comentarios_BEAN!=null)&&(comentarios_BEAN.getIDStr()!=null))
                                                            mensagem_acao="Seu comentario será analizado antes de ir para o ar";
                                                        else
                                                            mensagem_acao="Erro ao cadastrar";
                                                    }
                                                    catch(Exception erro){
                                                        mensagem_acao=erro.getMessage();
                                                    }
                                                    %>
              <script>
                                                        alert("<%=mensagem_acao%>");
                                                    </script>
                                                    
                                                    
              <%
                                                }                                             
                                            %>
            </div>
          </div>
        </div>
       <%
                              
                                try{
                                    menu_BEAN=(menuBEAN)menu_DAO.buscarTitulo(pg_).get(0);
                                }
                                catch(Exception meuerro0){
                                }
                                Vector registros_comentarios=comentarios_DAO.buscarUltimosAtivos(10,menu_BEAN);
                                for (int i=0;i<registros_comentarios.size();i++){
                                    try{
                                        comentarios_BEAN=(comentariosBEAN)registros_comentarios.get(i);
                                    }
                                    catch(Exception erro){
                                    }
                                    
                                %>
        <div style="clear:both;text-decoration:none; color:#FFF;"> <b style="font-variant:small-caps"><%=comentarios_BEAN.getTitulo()%></b> Escreveu: </div>
        <div style="clear:both;text-decoration:none; color:#FFF;text-indent:10px;"> <br>
          &nbsp; <%=comentarios_BEAN.getDescricao()%> <br>
          <br>
        </div>
        <%}%>
      </div>
    </div>
    <div class="branco" style="width:335px; height:400px; background-color:#09110F;">
      <div class="branco">
        <div id="topogalerafacebook" style="width:335px; text-indent:15px;"> Inove Ticão <a style="color:#FFF; font-size:14px; text-indent:15px;">- Galera do Facebook</a> </div>
      </div>
      <div class="branco" style="width:335px; height:340px;">
        <div class="branco" style="width:300px; padding-left:15px;">
          <div class="fb-like" data-href="http://pt-br.facebook.com/pages/Inove-Produ%C3%A7%C3%B5es-de-Eventos/236630606359532" data-send="true" data-width="300" data-show-faces="true" data-font="arial"></div>
        </div>
      </div>
    </div>
  </div>
  <%}else if(pg.equals("contato")){%>
  <div class="divisorlinhas"></div>
  <div class="linhascentro" style="height:123px;"> <img id="banner2" width="907" height="123" border="0" /></div>
  <div class="divisorlinhas" style="height:22px;"></div>
  <%}else{%>
  <div class="linhascentro" style="height:166px; background-color:transparent;">
    <div class="newslatter">
      <%
                            String Email=request.getParameter("email");
                            String Nome=request.getParameter("nome");
                            String mensagem_email="";
                            String mensagem_nome="";  
                            String mensagem_acao="";                                                                                 
                            String acao=request.getParameter("acao");
                            if((acao!=null)&&(acao.equals("ENVIAR"))){
                                contatoBEAN contato_BEAN=new contatoBEAN();
                                Vector registros_contato=new Vector();
                                contatoDAO contato_DAO = new contatoDAO(minhaConexao);
                                contato_BEAN.setAtivo(true);
                                Date Hoje_agora=new Date();
                                contato_BEAN.setData_Cadastro(Hoje_agora);
                                try{
                                    contato_BEAN.setNome(Nome);
                                }
                                catch(Exception erro){
                                    mensagem_nome=erro.getMessage();
                                }
                                try{
                                    contato_BEAN.setEmail(Email);
                                }
                                catch(Exception erro){
                                    mensagem_email=erro.getMessage();
                                }
                                try{
                                    contato_BEAN= contato_DAO.salvar(contato_BEAN);
                                    if ((contato_BEAN!=null)&&(contato_BEAN.getIDStr()!=null))
                                        mensagem_acao="email cadastrado com sucesso";
                                    else
                                        mensagem_acao="Erro ao cadastrar";
                                }
                                catch(Exception erro){
                                    mensagem_acao=erro.getMessage();
                                }
                            }

                                                       
                        %>
                        
                      
      <form id="form1" name="form1" method="post" action="#">
        <div class="toponewslatter">Receba informações sobre<br>
          nossos eventos</div>
        <div class="branco" style="width:201px; height:20px; padding-top:7px; padding-bottom:7px; padding-left:15px; padding-right:15px;">
          <div class="textonewslatter" style="width:40px;min-height:6px;overflow:hidden;">Nome:</div>
          <div class="branco">
            <input class="camponewslatter" type="text" name="nome"/>
          </div>
        </div>
        <div class="branco" style="width:201px; height:20px; padding-top:7px; padding-bottom:7px; padding-left:15px; padding-right:15px;">
          <div class="textonewslatter" style="width:40px;min-height:6px;overflow:hidden;">Em@il:</div>
          <input class="camponewslatter" type="text" name="email"/>
        </div>
        <div class="branco" style="width:201px; height:20px; padding-top:7px; padding-bottom:7px; padding-left:15px; padding-right:15px;">
          <input type="submit" name="acao" id="ENVIAR" value="ENVIAR" />
          <br>
          <div style="position:absolute;color:red;"><%=mensagem_acao%></div>
        </div>
      </form>
    </div>
    <div class="branco" style="width:11px; height:166px; background-color:transparent;"></div>
    <div id="bannerinferior"><a href="?pg=contato" target="_self"><img id="banner2" width="665" height="166" border="0" /></a></div>
  </div>
  <div class="divisorlinhas"></div>
  <div class="linhascentro" style="height:255px; background-color:#1D282E;">
    <div id="publicidade">
      <div id="topopublicidade">Publicidade</div>
      <div id="fotopublicidade"><a href="?pg=contato" target="_self"><img src="./arquivo.jsp?nome=anuncio_4.jpg&pasta=anuncio" width="200" height="203" border="0" /></a></div>
    </div>
    <!-- ultimas fotos -->
    <div id="ultimasfotos" style="height:255px">
      <div id="topoultimasfotos" style="text-indent:15px;">Ultimas fotos</div>
      <div class="branco" style="width:318px; height:162px;">
        <div class="branco" style="width:291px; height:142px; margin-top:5px; margin-bottom:5px; margin-left:15px; margin-right:14px;display:inline;">
          <div class="branco" style="width:291px; min-height:142px;height:auto; display:inline-block;">
           <%
                                                fotosDAO fotos_dao=new fotosDAO(minhaConexao);
                                                Vector fotos=fotos_dao.buscarUltimos(12);
                                                for(int i=0;i<fotos.size();i++){
                                                    fotosBEAN fotos_bean=new fotosBEAN();
                                                    try{
                                                        fotos_bean=(fotosBEAN)fotos.get(i);
                                                    }
                                                    catch(Exception erro){
                                                        
                                                    }
                                                %>
            <div class="miniaturasultimasfotos" style="margin-right:2px;"> <a href="?pg=fotos&id=<%=fotos_bean.getId_CategoriaStr()%>"><img src="./arquivo.jsp?nome=p_<%=fotos_bean.getSrc()%>" width="70" height="35" border="0"></a> </div>
            <%}%>
          </div>
        </div>
      </div>
      <div class="branco" style="width:303px; height:27px; padding-top:10px; padding-left:15px; display:inline;">
        <a href="?pg=fotos">
        <div class="branco" style="width:235px; height:27px; text-indent:10px;"><a href="?pg=fotos" target="_self" class="sublinhados">Veja todas as fotos</a></div>
        </a> </div>
    </div>
    <!-- fim das ultimas fotos -->
    <div class="branco" style="height:255px; width:12px; background-color:transparent;"></div>
    <!-- parte do facebook -->
    <div id="galerafacebook" style="height:255px">
      <div id="topogalerafacebook" style="text-indent:15px;">Inove Ticão <a style="color:#FFF; font-size:14px; text-indent:15px;">- Galera do Facebook</a></div>
      <div class="branco" style="width:318px; height:209px;">
        <div class="branco" style="width:300px; padding-left:15px;">
          <div class="fb-like" data-href="http://pt-br.facebook.com/pages/Inove-Produ%C3%A7%C3%B5es-de-Eventos/236630606359532" data-send="true" data-width="300" style="color:#FFF;" data-show-faces="true" data-font="arial"></div>
        </div>
      </div>
    </div>
    <!-- fim da parte do facebook -->
    <div class="branco" style="height:255px; width:15px; background-color:#000000;"></div>
  </div>
  <%}%>
</div>
<%
   
%>
