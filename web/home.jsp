<%@page import="DAO.agendaDAO"%>
<%@page import="BEAN.agendaBEAN"%>
<%@page import="BEAN.videosBEAN"%>
<%@page import="DAO.videosDAO"%>
<%@page import="DAO.categoriaVideosDAO"%>
<%@page import="java.util.Vector"%>
<%@page import="Until.functions"%>
<%@page import="BEAN.categoriaVideosBEAN"%>
<%@page import="BD.Conexao"%>
<!----------------------------------------------------inicio do home ------------------------------>
<link rel="stylesheet" type="text/css" href="estilos.css" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Conexao minhaConexao=null;
    try{
        minhaConexao=(Conexao)session.getAttribute("minhaConexao");
    }
    catch(Exception erro){
    } 
    categoriaVideosBEAN categoriaVideos_BEAN=new categoriaVideosBEAN();
    Vector registros_categoriaVideos=new Vector();
    categoriaVideosDAO categoriaVideos_DAO = new categoriaVideosDAO(minhaConexao); 
    try{registros_categoriaVideos=categoriaVideos_DAO.buscarTodos();}catch(Exception erro){};                            
    Vector registros_Videos=new Vector();
    videosDAO videos_DAO = new videosDAO(minhaConexao); 
    try{registros_Videos=videos_DAO.buscarUltimos(3);}catch(Exception erro){};                            
    videosBEAN  video1=new videosBEAN();
    videosBEAN  video2=new videosBEAN();
    if(registros_Videos.size()>=1){
       video1=((videosBEAN)registros_Videos.get(0));
    }
    if(registros_Videos.size()>=2){
       video2=((videosBEAN)registros_Videos.get(1));
    }
    agendaDAO agenda_DAO = new agendaDAO(minhaConexao);            
    Vector vetor_imagens_pares =   agenda_DAO.buscarTodosPares();
    Vector vetor_imagens_impares = agenda_DAO.buscarTodosImpares();
%>
<script type="text/javascript">
    var vetor_imagens_pares=new Array(); 
    var vetor_imagens_impares=new Array(); 
    var posicao_par=-1;
    var posicao_impar=-1;
    <%for(int i=0;i<vetor_imagens_pares.size();i++){
        agendaBEAN agenda_BEAN=new agendaBEAN();
        try{
            agenda_BEAN=(agendaBEAN)vetor_imagens_pares.get(i);
        }
        catch(Exception erro){}
    %>
    vetor_imagens_pares.push("./arquivo.jsp?nome=m_<%=agenda_BEAN.getSrc()%>&pasta=agenda");
    <%}%>

    <%for(int i=0;i<vetor_imagens_impares.size();i++){
        agendaBEAN agenda_BEAN=new agendaBEAN();
        try{
            agenda_BEAN=(agendaBEAN)vetor_imagens_impares.get(i);
        }catch(Exception erro){}
    %>
    vetor_imagens_impares.push("./arquivo.jsp?nome=m_<%=agenda_BEAN.getSrc()%>&pasta=agenda");
    <%}%>
    function proximo_par(){
        if(vetor_imagens_pares.length!=0){
            var img=document.getElementById('imagem_par');
            posicao_par++;
            if (posicao_par<vetor_imagens_pares.length){
               img.src=vetor_imagens_pares[posicao_par]; 
            }
            else{
               posicao_par=0; 
               img.src=vetor_imagens_pares[posicao_par]; 
            } 
            setTimeout(proximo_par,2000);
        }
    }
    function proximo_impar(){
        if(vetor_imagens_impares.length!=0){
            var img=document.getElementById('imagem_impar');
            posicao_impar++;
            if (posicao_impar<vetor_imagens_impares.length){
               img.src=vetor_imagens_impares[posicao_impar]; 
            }
            else{
               posicao_impar=0; 
               img.src=vetor_imagens_impares[posicao_impar]; 
            } 
            setTimeout(proximo_impar,2000);
        }
    }
    function iniciar(){
        posicao_par=-1;
        posicao_impar=-1;
        proximo_par();
        proximo_impar();
    }
</script>
<div class="linhascentro">
  <!-- primeira linha de conteudo -->
  <div class="linhascentro" style="height:291px; background-color:#000000;">
    <div class="branco" style="width:7px; height:291px;"></div>
    <div class="musicas" style="height:290px">
      <div class="topomusicas">Músicas</div>
      <div class="listamusicas"><br>
        <br>
        <br>
        <br>
        <a style="font-size:18px; text-align:center;">Aguarde, em breve<br>
        você estará<br>
        ouvindo músicas<br>
        pelo nosso site.</a></div>
    </div>
    <div class="branco" style="width:10px; height:291px;"></div>
    <!-- primeiro video -->
    <div class="videos">
      <div class="videotopo">
        <div class="branco" style="width:41px; height:39px; background-color:#0187DC;"></div>
        <div class=" titulovideo"><a href="http://www.youtube.com/embed/<%=functions.getFile(video1.getSrc())%>" style="text-decoration:none;color:#FFF;" target="_blank"><%=video1.getTitulo_Categoria()%>-<font style="text-decoration:none; color:#F00; font-style:italic;"><%=video1.getTitulo()%></font></a></div>
        <div class="branco" style="clear:both; height:252px; width:428px;">
          <iframe width="428" height="252" src="http://www.youtube.com/embed/<%=functions.getFile(video1.getSrc())%>" frameborder="0" allowfullscreen></iframe>
        </div>
      </div>
    </div>
    <!-- fim do primeiro video -->
    <div class="branco" style="width:10px; height:291px;"></div>
    <div class="cartaz"><img id="imagem_impar" width="221" height="291" /></div>
    <div class="branco" style="width:7px; height:291px;"></div>
  </div>
  <!-- fim da primeira linha de conteudo -->
  <div class="divisorlinhas" style="background-color:#09110F;"></div>
  <div class="divisorlinhas" style="background-color:#09110F;"></div>
  <!-- segunda linha de conteudo -->
  <div class="linhascentro" style="height:347px; background-color:#09110F;">
    <div class="branco" style="height:347px; width:7px;"></div>
    <!-- garota colirio -->
    <div class="garotacolirio">
      <div id="topogarotacolirio">Garota Colírio</div>
      <div id="fotogarotacolirio"><img src="imagens/garotacolirio.jpg" width="223" height="267" /></div>
      <div id="nomecolirio"><a href="#" onClick="alert('Conteudo sendo desenvolvido, desculpe o transtorno.')" class="sublinhados">Em Breve >></a></div>
    </div>
    <!-- fim da parte garota colirio -->
    <div class="branco" style="width:10px; height:347px;"></div>
    <div class="branco" style="width:666px; height:347px;">
      <div class="branco" style="height:291px;width:666px;overflow: hidden;">
        <!-- segundo video, ao lado da garota colirio -->
        <div class="videos">
          <div class="videotopo">
            <div class="branco" style="width:41px; height:39px; background-color:#0187DC;"></div>
            <div class="titulovideo"><a href="http://www.youtube.com/embed/<%=functions.getFile(video2.getSrc())%>" style="text-decoration:none;color:#FFF;" target="_blank"><%=video2.getTitulo_Categoria()%>-<font style="text-decoration:none; color:#F00; font-style:italic;"><%=video2.getTitulo()%></font></a></div>
            <div class="branco" style="clear:both; height:252px; width:428px;">
              <iframe width="428" height="252" src="http://www.youtube.com/embed/<%=functions.getFile(video2.getSrc())%>" frameborder="0" allowfullscreen></iframe>
            </div>
          </div>
        </div>
        <!-- fim do segundo video -->
        <div class="branco" style="width:10px; height:291px;"></div>
        <div class="cartaz"><img id="imagem_par" /></div>
        <div class="branco" style="width:7px; height:291px;"></div>
      </div>
      <div class="branco" style="height:20px; width:659px;"></div>
      <div class="branco" style="height:32px; width:659px;">
        <div class="branco" style="height:36px; width:428px;text-align:right; font-family:Verdana, Geneva, sans-serif; font-size:13px; font-style:italic; color:#1D272D; line-height:36px;"><a href="?pg=videos" class="sublinhados">Veja todos os videos  >></a></div>
        <div class="branco" style="height:36px; width:231px;text-align:right; font-family:Verdana, Geneva, sans-serif; font-size:13px; font-style:italic; color:#1D272D; line-height:36px;"><a href="?pg=agenda" class="sublinhados">Veja a agenda completa  >></a></div>
      </div>
    </div>
  </div>
  <!-- fim da segunda linha de conteudo -->
  <div class="divisorlinhas"></div>
</div>
<!----------------------------------------------------fim do home------------------------------>
<%
   
%>
