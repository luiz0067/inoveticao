<%@page import="BEAN.videosBEAN"%>
<%@page import="DAO.videosDAO"%>
<%@page import="Until.functions"%>
<%@page import="BD.Conexao"%>
<%@page import="DAO.categoriaVideosDAO"%>
<%@page import="java.util.Vector"%>
<%@page import="BEAN.categoriaVideosBEAN"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="estilos.css" />

<div class="linhascentro"> 
  <!-- inicio da primeira carreira, um video e a lista de videos -->
  <div class="branco" style="height:auto; width:907px; background-color:#000000;">
    <%
                    Conexao minhaConexao=null;
                    try{
                        minhaConexao=(Conexao)session.getAttribute("minhaConexao");
                    }
                    catch(Exception erro){
                    }
                    videosBEAN  video1=new videosBEAN();
                    videosBEAN  video2=new videosBEAN();
                    videosBEAN  video3=new videosBEAN();
                    categoriaVideosBEAN categoriaVideos_BEAN=new categoriaVideosBEAN();
                    Vector registros_categoriaVideos=new Vector();
                    categoriaVideosDAO categoriaVideos_DAO = new categoriaVideosDAO(minhaConexao); 
                    Vector registros_Videos=new Vector();
                    videosDAO videos_DAO = new videosDAO(minhaConexao); 
                    
                    try{
                        try{registros_categoriaVideos=categoriaVideos_DAO.buscarTodos();}catch(Exception erro){};                            
                        try{registros_Videos=videos_DAO.buscarUltimos();}catch(Exception erro){};                            
                        if(registros_Videos.size()>=1){
                           video1=((videosBEAN)registros_Videos.get(0));
                        }
                        if(registros_Videos.size()>=2){
                           video2=((videosBEAN)registros_Videos.get(1));
                        }
                        if(registros_Videos.size()>=3){
                           video3=((videosBEAN)registros_Videos.get(2));
                        }
                    }
                    catch(Exception erro2){};  
            %>
    
    <!-- primeiro video, fica ao lado da lista -->
    <div class="branco" style=" width:7px; height:291px;"></div>
    <div class="videos">
      <div class="videotopo">
        <div class="branco" style="width:41px; height:39px; background-color:#0187DC;"></div>
        <div class="titulovideo"> <a href="http://www.youtube.com/embed/<%=functions.getFile(video1.getSrc())%>" style="text-decoration:none;color:#FFF; font-family:Verdana, Geneva, sans-serif;" target="_blank"> <%=video1.getTitulo_Categoria()%> - <font style="text-decoration:none; color:#F00; font-style:italic;"> <%=video1.getTitulo()%></font> </a> </div>
        <div class="branco" style="clear:both; height:242px; width:428px; padding-top:10px; display:inline;">
          <iframe width="428" height="242" src="http://www.youtube.com/embed/<%=functions.getFile(video1.getSrc())%>" frameborder="0" allowfullscreen></iframe>
        </div>
      </div>
    </div>
    <!-- fim do primeiro video, fica ao lado da lista -->
    <div class="branco" style="width:37px; height:291px; background-color:transparent;"></div>
    
    <!-- inicio da  lista de videos -->
    <div class="listavideos">
      <div class="branco" style="height:40px; width:428px;">
        <div class="branco" style="height:40px; width:400px; line-height:40px; color:#F00; font-size:18px; font-family:'myriadpro'; font-style:italic;">Vídeos Anteriores</div>
        <div class="branco" style="height:40px; width:28px;"><a style="cursor:default;cursor:hand;" onclick="rolagemVoltar('listavideos',39)"><img src="imagens/setas-rolagem.jpg" width="28" height="40" border="0" /></a></div>
      </div>
      <div class="branco" style="height:220px;overflow:hidden;" id="listavideos">
        <%
                                if((registros_Videos!=null)&&(registros_Videos.size()>3)){
                                    for(int i=3;i<registros_Videos.size();i++){
                                        videosBEAN video=((videosBEAN)registros_Videos.get(i));
                                        if(video.getIDStr()==null)
                                            break;
                        %>
        <div class="linhaslistavideos" style="background-color:#1D282E;">
          <div class="quadradinhovermelho"></div>
          <div class="itenslistavideos"><a href="http://www.youtube.com/embed/<%=functions.getFile(video.getSrc())%>" style="text-decoration:none;color:#FFF;" target="_blank"><%=video.getTitulo_Categoria()%> - <%=video.getTitulo()%></a></div>
        </div>
        <%
                                    }
                                }
                            
                        %>
      </div>
      <div class="branco" style="height:40px; width:428px;">
        <div class="branco" style="height:40px; width:400px;"></div>
        <div class="branco" style="height:40px; width:28px;"><a style="cursor:default;cursor:hand;" onclick="rolagemAvancar('listavideos',39)"><img src="imagens/setas-rolagem1.jpg" width="28" height="40" border="0" /></a></div>
      </div>
    </div>
    <!--  fim da inicio da  lista de videos --> 
    
  </div>
  <!-- fim da primeira carreira, um video e a lista de videos -->
  <div class="divisorlinhas" style="height:16px; background-color:#000000;"></div>
  <!-- inicio da segunda linha de conteudo, aqui tem 2 videos -->
  <div class="branco" style="height:291px; width:907px; background-color:#000000;">
    <div class="branco" style=" width:7px; height:291px;"></div>
    <!-- primeiro video -->
    <div class="videos">
      <div class="videotopo">
        <div class="branco" style="width:41px; height:39px; background-color:#0187DC;"></div>
        <div class="titulovideo"> <a href="http://www.youtube.com/embed/<%=functions.getFile(video2.getSrc())%>" style="text-decoration:none;color:#FFF;" target="_blank"> <%=video2.getTitulo_Categoria()%> - <font style="text-decoration:none; color:#F00; font-style:italic;"> <%=video2.getTitulo()%></font> </a> </div>
        <div class="branco" style="clear:both; height:242px; width:428px; padding-top:10px; display:inline;">
          <iframe width="428" height="242" src="http://www.youtube.com/embed/<%=functions.getFile(video2.getSrc())%>" frameborder="0" allowfullscreen></iframe>
        </div>
      </div>
    </div>
    <!-- fim do primeiro video -->
    <div class="branco" style="width:37px; height:291px; background-color:transparent;"></div>
    <!-- segundo video video -->
    <div class="videos">
      <div class="videotopo">
        <div class="branco" style="width:41px; height:39px; background-color:#0187DC;"></div>
        <div class="titulovideo"> <a href="http://www.youtube.com/embed/<%=functions.getFile(video3.getSrc())%>" style="text-decoration:none;color:#FFF;" target="_blank"> <%=video3.getTitulo_Categoria()%> - <font style="text-decoration:none; color:#F00; font-style:italic;"> <%=video3.getTitulo()%></font> </a> </div>
        <div class="branco" style="clear:both; height:242px; width:428px; padding-top:10px; display:inline;">
          <iframe width="428" height="242" src="http://www.youtube.com/embed/<%=functions.getFile(video3.getSrc())%>" frameborder="0" allowfullscreen></iframe>
        </div>
      </div>
    </div>
    <!-- fim do segundo video --> 
  </div>
  <!-- fim dos dois videos de baixo --> 
</div>
<%
   
%>
