<%@page import="DAO.bannerDAO"%>
<%@page import="BEAN.bannerBEAN"%>
<%@page import="Until.functions"%>
<!----------------------------------------------------inicio do titulo ------------------------------>
<%@page import="java.util.Random"%>
<%@page import="DAO.sorteioDAO"%>
<%@page import="BEAN.sorteioBEAN"%>
<%@page import="BEAN.sorteioBEAN"%>
<%@page import="java.util.Vector"%>
<%@page import="BD.Conexao"%>
<%@page import="BD.DadosConexao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<link rel="stylesheet" type="text/css" href="estilos.css" />

<script src="SpryAssets/SpryMenuBar.js" type="text/javascript"></script>
<link href="SpryAssets/SpryMenuBarHorizontal.css" rel="stylesheet" type="text/css" />


<%   
    Conexao minhaConexao=null;
    try{
        minhaConexao=(Conexao)session.getAttribute("minhaConexao");
    }
    catch(Exception erro){
    }
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
        if(acao.equals("Clique e concorra")){                        
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
                    mensagem_acao="Favor, Preencha todos os campos";
            }catch(Exception erro){mensagem_acao=erro.getMessage();}
            registros_sorteio.add(sorteio_BEAN);
        }                                                          
    }
    ID=(ID==null)?"":ID;
    nome=(nome==null)?"":nome;    
    email=(email==null)?"":email;
    telefone=(telefone==null)?"":telefone;
	
	String pg=request.getParameter("pg");   
	pg=(pg==null)?"":pg; 

	
		bannerBEAN banner_BEAN=new bannerBEAN();
        bannerDAO banner_DAO=new bannerDAO(minhaConexao);
        Vector registros_banner=new Vector();
        registros_banner=banner_DAO.buscarTodos();
%>
<script type="text/javascript">
    var vetor_fotos_banner=new Array(); 
    var posicao_banner=-1;
    function proximo_banner(){
        if(vetor_fotos_banner.length!=0){
            var img=document.getElementById('banner1');
            posicao_banner++;
            if (posicao_banner<vetor_fotos_banner.length){
               img.src=vetor_fotos_banner[posicao_banner]; 
            }
            else{
                posicao_banner=-1;
            }
        }
    }
    function iniciar_banner(){
        proximo_banner();
        setTimeout("iniciar_banner()",2000);
    }
<%
        if((registros_banner!=null)&&(registros_banner.size()>0)){
            for(int i=0;i<registros_banner.size();i++){
                banner_BEAN=((bannerBEAN)registros_banner.get(i));
%>
    vetor_fotos_banner.push("./arquivo.jsp?nome=<%=banner_BEAN.getsrc()%>&pasta=banners");
<%                
            }
        }
%>
</script>
<div class="linhascentro">
  <%if(pg.equals("inoveticao")){%>
  <div class="cabecalhopaginas">INOVE E TICÃO</div>
  <div class="divisorlinhas"></div>
  <div class="divisorlinhas" style="height:16px; background-color:#10191A;"></div>
  <%}else if(pg.equals("videos")){%>
  <div class="cabecalhopaginas">VÍDEOS</div>
  <div class="divisorlinhas"></div>
  <div class="divisorlinhas" style="height:16px; background-color:#10191A;"></div>
  <%}else if(pg.equals("agenda")){%>
  <div class="cabecalhopaginas">AGENDA</div>
  <div class="divisorlinhas"></div>
  <div class="divisorlinhas" style="height:16px; background-color:#10191A;"></div>
  <%}else if(pg.equals("fotos")){%>
  <div class="cabecalhopaginas">FOTOS</div>
  <div class="divisorlinhas"></div>
  <div class="divisorlinhas" style="height:16px; background-color:#10191A;"></div>
  <%}else if(pg.equals("contato")){%>
  <div class="cabecalhopaginas">CONTATO</div>
  <div class="divisorlinhas"></div>
  <div class="divisorlinhas" style="height:16px; background-color:#10191A;"></div>
  <%}else{%>
  <div id="cabecalho">
    <div class="divisorlinhas" style="background-color:#000000;"></div>
    <div class="branco">
      <div class="branco" style="height:302px; width:9px;"></div>
      <%if(sorteio_BEAN.getSorteio()){%>
      <div id="ingresso" style="height:300px;">
        <div class="topoingresso"> Concorra a Ingressos </div>
        <div class="branco" style="width:224px; background-color:#3A535A; height:128px; display:block;" >
          <div id="fotoingresso" ><img src="./arquivo.jsp?nome=anuncio_2.jpg&pasta=anuncio" width="129" height="122" /></div>
        </div>
        <div class="linhas" style="width:auto; height:114px; display:inline;width:200px;overflow:hidden">
          <form action="" method="post" style="display:inline;">
            <div class="linhasingresso">
              <div class="branco" style="width:30px; height:10px;"></div>
              <div class="texto_formulario">Nome:</div>
              <div style="float:left;">
                <input class="campoingresso" name="nome" onfocus="document.getElementById('mensagem_nome').style.display='none';"/>
              </div>
            </div>
            <div class="linhasingresso" style="height:3px;">
              <p style="color:red;position:absolute;font-weight:bold;margin-left:150px;margin-top:-10px;" id="mensagem_nome"><%=mensagem_nome%></p>
            </div>
            <div class="linhasingresso">
              <div class="branco" style="width:30px; height:10px;"></div>
              <div class="texto_formulario">Em@il:</div>
              <div style="float:left;">
                <input class="campoingresso" name="email" onfocus="document.getElementById('mensagem_email').style.display='none';"/>
              </div>
            </div>
            <div class="linhasingresso" style="height:3px;">
              <p style="color:red;position:absolute;font-weight:bold;margin-left:150px;margin-top:-10px;" id="mensagem_email"><%=mensagem_email%></p>
            </div>
            <div class="linhasingresso">
              <div class="branco" style="width:30px; height:10px;"></div>
              <div class="texto_formulario">Telefone:</div>
              <div style="float:left;">
                <input class="campoingresso" name="telefone" onfocus="mascaraIniciar(this)" onkeypress="return formataTelefone(this, event);" value="(__) ____-____" maxlength="14" />
              </div>
            </div>
            <div class="linhasingresso" style="height:3px;">
              <p style="color:red;position:absolute;font-weight:bold;margin-left:150px;margin-top:-10px;" id="mensagem_telefone"><%=mensagem_telefone%></p>
            </div>
            <div class="linhasingresso">
              <div class="branco" style="width:40px; height:10px;"></div>
              <input name="acao" type="submit" style="margin-left:10px;" value="Clique e concorra" />
            </div>
            <div class="linhasingresso" style="height:3px;">
              <p style="color:red;position:absolute;font-weight:bold;margin-left:150px;margin-top:-10px;" id="mensagem_telefone"><%=mensagem_acao%></p>
            </div>
          </form>
        </div>
      </div>
      <div class="branco" style="width:10px; height:302px;"></div>
      <div id="bannerprincipal"> <img id="banner1" width="650" height="302" /> </div>
      <%
                                }
                                else{
                                %>
      <div id="bannerprincipal" style="width:893px"> <img id="banner1" width="893" height="302" /> </div>
      <%}%>
    </div>
  </div>
  <div class="divisorlinhas" style="background-color:#000000;"></div>
  <div class="divisorlinhas"></div>
  <!-- parte do menu do meio -->
  <div class="linhascentro" style="background-color:#000000; height:59px;">
   <ul id="menumeio" class="MenuBarHorizontal">
  
  <li><a class="MenuBarItemSubmenu" href="#"><a class="itemmenumeio">Inove</a></a>
    <ul>
      <li><a target="_blank"><a class="itemmenumeio" href="http://www.madalyeventos.com.br/budgets/new?category_id=1">Inove</a></a></li>
      <li><a target="_blank"><a class="itemmenumeio" href="http://www.madalyeventos.com.br/budgets/new?category_id=1">Orçamento</a></a></li>
    </ul>
  </li>
  <li><a  target="_blank"><a class="itemmenumeio" href="http://www.madalyeventos.com.br">Madaly</a></a></li>
  <li><a target="_blank"><a class="itemmenumeio" href="?pg=contato" >Contato Inove</a></a>
  </li>
  <li><a  target="_blank"><a class="itemmenumeio2" href="http://www.facebook.com/profile.php?id=100002342391143" target="_blank">BLoco Ticão</a></a></li>
  <li><a target="_blank"><a class="itemmenumeio2" href="http://www.facebook.com/pages/Bar-do-Tic%C4%81o/206786736022651" >O bar</a></a></li>
</ul>

<script type="text/javascript">
var MenuBar1 = new Spry.Widget.MenuBar("menumeio", {imgDown:"SpryAssets/SpryMenuBarDownHover.gif", imgRight:"SpryAssets/SpryMenuBarRightHover.gif"});
</script>
  </div>
  <!-- fim do menu do meio-->
  <div class="divisorlinhas"></div>
  <div class="divisorlinhas" style="background-color:#000000;"></div>
  <%}%>
</div>
<!----------------------------------------------------fim do titulo ------------------------------>
<%
   
%>
