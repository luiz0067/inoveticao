<%@page import="java.util.Random"%>
<%@page import="DAO.sorteioDAO"%>
<%@page import="BEAN.sorteioBEAN"%>
<%@page import="BEAN.sorteioBEAN"%>
<%@page import="java.util.Vector"%>
<%@page import="BD.Conexao"%>
<%@page import="BD.DadosConexao"%>
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
        if(acao.equals("Enviar")){                        
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
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Inove Ticão</title>
<link rel="stylesheet" href="estilos.css" type="text/css" />
<style type="text/css">
	

					
</style>


<script src="Scripts/swfobject_modified.js" type="text/javascript"></script>
								
								

</head>

<body>
<div id="fb-root"></div>
<script>
	(function(d, s, id) {
	  var js, fjs = d.getElementsByTagName(s)[0];
	  if (d.getElementById(id)) {return;}
	  js = d.createElement(s); js.id = id;
	  js.src = "//connect.facebook.net/pt_BR/all.js#xfbml=1";
	  fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));
</script>
<div id="tudo">
        <div class="linhastotal">
           <div class="linhastotal" style="background-color:#C10100;">
           		<div class="linhas" style="height:9px; background-color:#C10100;"></div>
           </div>
           <div class="linhastotal" style="background-image:url(imagens/fundotoporepeat.jpg); background-repeat:repeat-x;">
            <div class="linhas">
            	<img src="imagens/topo.jpg" width="1024" height="191" />
            </div>
       	   </div>		
            <div class="linhastotal" style="background-color:#0187DC;"></div>
        	<div class="linhastotal" style="background-image:url(imagens/fundodivflash.jpg); background-repeat:repeat-x;">
            	<div class="linhas" style="height:44px; background-color:transparent;">
                	<div id="menutopo">
                   	  <object id="FlashID2" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="907" height="44">
                    	  <param name="movie" value="flash/menuflash.swf" />
                    	  <param name="quality" value="high" />
                    	  <param name="wmode" value="opaque" />
                    	  <param name="swfversion" value="6.0.65.0" />
                    	  <!-- This param tag prompts users with Flash Player 6.0 r65 and higher to download the latest version of Flash Player. Delete it if you don’t want users to see the prompt. -->
                    	  <param name="expressinstall" value="Scripts/expressInstall.swf" />
                    	  <!-- Next object tag is for non-IE browsers. So hide it from IE using IECC. -->
                    	  <!--[if !IE]>-->
                    	  <object type="application/x-shockwave-flash" data="flash/menuflash.swf" width="907" height="44">
                    	    <!--<![endif]-->
                    	    <param name="quality" value="high" />
                    	    <param name="wmode" value="opaque" />
                    	    <param name="swfversion" value="6.0.65.0" />
                    	    <param name="expressinstall" value="Scripts/expressInstall.swf" />
                    	    <!-- The browser displays the following alternative content for users with Flash Player 6.0 and older. -->
                    	    <div>
                    	      <h4>Content on this page requires a newer version of Adobe Flash Player.</h4>
                    	      <p><a href="http://www.adobe.com/go/getflashplayer"><img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash player" width="112" height="33" /></a></p>
                  	      </div>
                    	    <!--[if !IE]>-->
                  	    </object>
                    	  <!--<![endif]-->
                  	  </object>
                	</div>
              </div>
            </div>    
		</div>		
<!----------------------------fim do topo------------------------------------------------------------->

  

<!----------------------------------------------------inicio do conteudo ------------------------------>
  <div id="conteudo" style="height:1470px;">
                	<div id="cabecalho">
                    	<div class="divisorlinhas" style="background-color:#09110F;"></div>
                        <div class="branco">
                            <div class="branco" style="height:302px; width:9px;"></div>
							<div id="ingresso">
								<div class="topoingresso">
									Concorra a Ingressos
								</div>
								<div class="branco" style="width:224px; background-color:#3A535A; height:128px; display:block;" >
									<div id="fotoingresso" ><img src="imagens/fotoingresso/ingresso.jpg" width="129" height="122" /></div>
								</div>
								<div class="linhas" style="width:auto; height:114px; display:inline;width:200px;overflow:hidden">
									<form action="" method="post" style="display:inline;">
										<div class="linhasingresso">
											<div class="branco" style="width:30px; height:10px;"></div>
                                            <div class="texto_formulario">Nome:</div>
											<div style="float:left;"><input class="campoingresso" name="nome" onfocus="document.getElementById('mensagem_nome').style.display='none';"/></div>
										</div>
                                        <div class="linhasingresso" style="height:3px;"></div>
										<p style="color:red;position:absolute;margin-left:100px;margin-top:45px;font-weight:bold;" id="mensagem_nome"><%=mensagem_nome%></p>
										<div class="linhasingresso">
											<div class="branco" style="width:30px; height:10px;"></div>
                                            <div class="texto_formulario">Em@il:</div>
											<div style="float:left;"><input class="campoingresso" name="email" onfocus="document.getElementById('mensagem_email').style.display='none';"/></div>
										</div>
                                        <div class="linhasingresso" style="height:3px;"></div>
										<p style="color:red;position:absolute;margin-left:100px;margin-top:45px;font-weight:bold;" id="mensagem_email"><%=mensagem_email%></p>
										<div class="linhasingresso">
											<div class="branco" style="width:30px; height:10px;"></div>
                                            <div class="texto_formulario">Telefone:</div> 
											<div style="float:left;"><input class="campoingresso" name="telefone" onfocus="mascaraIniciar(this)" 
                                            onkeypress="return txtBoxFormat(this, '(99)9999-9999', event);" value="(__)____-____" maxlength="13" /></div>
										</div>
                                        <div class="linhasingresso" style="height:3px;"></div>
										<div class="linhasingresso">
                                        <p style="color:red;position:absolute;margin-left:100px;margin-top:45px;font-weight:bold;" id="mensagem_telefone"><%=mensagem_telefone%></p>                                        
										<div class="branco" style="width:40px; height:10px;"></div>
                                        <input name="acao" type="submit" style="margin-left:10px;" value="Clique e concorra" />
										</div>
                                    </form>
								</div>
							</div>
                            <div class="branco" style="width:10px; height:302px;"></div>
                            <div id="bannerprincipal">
                       	    <img src="imagens/banner-principal.jpg" width="650" height="302" /></div>	
                        </div>
                    </div>	
                    <div class="divisorlinhas" style="background-color:#09110F;"></div>
                    <div class="divisorlinhas"></div>
					<div class="linhascentro" style="background-color:#09110F; height:59px;">
                    	<div class="branco" style="width:66px; height:59px;">
                        	<img src="imagens/inovebranco.jpg" width="66" height="59" />
                        </div>
                          <div class="menumeioinove">
                            <a href="http://pt-br.facebook.com/pages/Inove-Produ%C3%A7%C3%B5es-de-Eventos/236630606359532"  target="_blank" style="text-decoration:none; color:#F00;">Inove</a>
                          </div>
                          <div class="menumeioinove">
                            <a href="http://www.madalyeventos.com.br/index.swf"  target="_blank" style="text-decoration:none; color:#F00;">Madaly</a>
                          </div>
                          <div class="branco" style="width:66px; height:59px;">
                   	<img src="imagens/ticaobranco.jpg" width="66" height="59" /></div>
                          <div class="menumeioticao">
                            <a href="http://www.facebook.com/profile.php?id=100002342391143" target="_blank" style="text-decoration:none; color:#0187DC;">Bloco do ticão</a>
                          </div>
                          <div class="menumeioticao">
                            <a href="http://www.facebook.com/profile.php?id=100002342391143" target="_blank" style="text-decoration:none; color:#0187DC;">O bar</a>
                          </div>
                        </div>
                    <div class="divisorlinhas"></div>
    				<div class="divisorlinhas" style="background-color:#09110F;"></div>
					<div class="linhascentro" style="height:291px; background-color:#09110F;">
                        <div class="branco" style="width:7px; height:291px;"></div>
                        <div class="musicas">
                        	<div class="topomusicas">Músicas</div>
                            <div class="listamusicas">
                                <br><br><br><br>
                                <a style="font-size:18px; text-align:center;">Aguarde, em breve <br>você estará <br>ouvindo músicas <br>pelo nosso site.</a>
                            </div>
                        </div>	
                      <div class="branco" style="width:10px; height:291px;"></div>
                      <div class="videos">
               	   	  <div class="videotopo">
                            	<div class="branco" style="width:41px; height:39px; background-color:#0187DC;"></div>
                                <div class="branco" style="width:386px; height:39px; text-indent:15px;">
                                	Hugo Pena e Gabriel<a style="text-decoration:none; color:#F00; font-style:italic;"> Cigana</a>
                                </div>
                                <div class="branco" style="clear:both; height:252px; width:428px;">
                                	<iframe width="428" height="252" src="http://www.youtube.com/embed/PpNUTqKCLGc" frameborder="0" allowfullscreen></iframe>
                                </div>
                        </div>
                      </div>
                      <div class="branco" style="width:10px; height:291px;"></div>
                        <div class="cartaz"><img src="imagens/cartaz.jpg" width="221" height="291" /></div>
                        <div class="branco" style="width:7px; height:291px;"></div>
                  </div>
                   <div class="divisorlinhas" style="background-color:#09110F;"></div>
                  <div class="divisorlinhas" style="background-color:#09110F;"></div>
                  <div class="linhascentro" style="height:347px; background-color:#09110F;">
                  	<div class="branco" style="height:347px; width:7px;"></div>
                    <div class="garotacolirio">
                    	<div id="topogarotacolirio">Garota Colírio</div>
                        <div id="fotogarotacolirio"><img src="imagens/garotacolirio.jpg" width="223" height="267" /></div>
                    	<div id="nomecolirio"><a onClick="alert('Conteudo sendo desenvolvido, desculpe o transtorno.')") style="text-decoration:none; color:#FFF;">Em Breve >></a></div>
                    </div>
                    <div class="branco" style="width:10px; height:347px;"></div>
                    <div class="branco" style="width:666px; height:347px;">
                    	<div class="branco" style="height:291px; width:666px;">
                            <div class="videos">
                                <div class="videotopo">
                                    <div class="branco" style="width:41px; height:39px; background-color:#0187DC;"></div>
                                    <div class="branco" style="width:386px; height:39px; text-indent:15px;">
                                    	Hugo Pena e Gabriel<a style="text-decoration:none; color:#F00; font-style:italic;"> Mala Pronta</a>
                                    </div>
                                    <div class="branco" style="clear:both; height:252px; width:428px;">
                                    	<iframe width="428" height="252" src="http://www.youtube.com/embed/o2DKCQHOQWo" frameborder="0" allowfullscreen></iframe>
                                    </div>
                               </div>
                           </div>
                           <div class="branco" style="width:10px; height:291px;"></div>
                           <div class="cartaz"><img src="imagens/cartaz2.jpg"/></div>
                           <div class="branco" style="width:7px; height:291px;"></div>	
                        </div>
                        <div class="branco" style="height:20px; width:659px;"></div>
                        <div class="branco" style="height:32px; width:659px;">
                        	<div class="branco" style="height:36px; width:428px; background-image:url(imagens/fundovideo.jpg); background-repeat:no-repeat;
                            text-align:right; font-family:'myriadpro'; font-size:13px; font-style:italic; color:#1D272D; line-height:36px;">
                            	<a href="videos.jsp" style="text-decoration:none;  color:#FFF;">Veja todos os videos  >></a>
                            </div>
                            <div class="branco" style="height:36px; width:231px; background-image:url(imagens/fundovideo2.jpg); background-repeat:no-repeat;
                             text-align:right; font-family:'myriadpro'; font-size:13px; font-style:italic; color:#1D272D; line-height:36px;">
                            	<a href="agenda.jsp" style="text-decoration:none; color:#FFF;">Veja a agenda completa  >></a>
                            </div>
                        </div>
                    </div>
                  </div>
                  <div class="divisorlinhas"></div>
                  <div class="linhascentro" style="height:166px; background-color:transparent;">
                  	<div class="newslatter">
                    	<div class="toponewslatter">Receba informações sobre<br>nossos eventos</div>
                  		<div class="branco" style="width:201px; height:20px; padding-top:7px; padding-bottom:7px; padding-left:15px; padding-right:15px;">
                        	<input class="camponewslatter" />
                        </div>
                        <div class="branco" style="width:201px; height:20px; padding-top:7px; padding-bottom:7px; padding-left:15px; padding-right:15px;">
                        	<input class="camponewslatter" />
                      </div>
                        <div class="branco" style="width:201px; height:20px; padding-top:7px; padding-bottom:7px; padding-left:15px; padding-right:15px;">
                          <form id="form1" name="form1" method="post" action="#">
                            <input type="submit" name="ENVIAR" id="ENVIAR" value="ENVIAR" />
                          </form>
                    </div>
                  	</div>
                    <div class="branco" style="width:11px; height:166px; background-color:transparent;"></div>
                    <div id="bannerinferior"><a href="contato.jsp" target="_self"><img src="imagens/banners/baner_home.jpg" width="665" height="166" border="0" /></a></div>
                  </div>
                  <div class="divisorlinhas"></div>
    <div class="linhascentro" style="height:255px; background-color:#1D282E;">
                    <div id="publicidade">
                    <div id="topopublicidade"> Publicidade</div>
                          	<div id="fotopublicidade"><a href="contato.jsp" target="_self"><img src="imagens/publicidade.jpg" width="200" height="203" border="0" /></a></div>
      </div>
                      <div id="ultimasfotos">
                      	  <div id="topoultimasfotos" style="text-indent:15px;">Ultimas fotos</div>
                      <div class="branco" style="width:318px; height:162px;">
                          	<div class="branco" style="width:291px; height:142px; margin-top:5px; margin-bottom:5px; margin-left:15px; margin-right:14px;
                            display:inline;">
                            	<div class="branco" style="width:291px; height:71px; display:inline-block;">
                                	<div class="miniaturasultimasfotos" style="margin-right:3px;"></div>
                                    <div class="miniaturasultimasfotos" style="margin-right:3px;"></div>
                                    <div class="miniaturasultimasfotos" style="margin-right:3px;"></div>
                                    <div class="miniaturasultimasfotos"></div>
                              </div>
                                <div class="branco" style="width:291px; height:71px; display:inline-block;">
                                	<div class="miniaturasultimasfotos" style="margin-right:3px;"></div>
                                    <div class="miniaturasultimasfotos" style="margin-right:3px;"></div>
                                    <div class="miniaturasultimasfotos" style="margin-right:3px;"></div>
                                    <div class="miniaturasultimasfotos"></div>
                              </div>	
                        </div>
                        </div>
                            <div class="branco" style="width:303px; height:27px; padding-top:10px; padding-left:15px; display:inline;">
                              <div class="branco" style="width:27px; height:27px; background-color:#1D91A6; color:#FFF; font-family:"myriadpro"; 
                              	text-align:center;"></div>
                              <div class="branco" style="width:40px; height:27px; color:#FFF; font-family:'corbel'; text-align:center; font-weight:bold;">Dez</div>
                              <div class="branco" style="width:235px; height:27px; color:#FFF; font-family:'corbel'; text-align:left; font-weight:bold;">
                          		Sem Fotos Cadastradas</div>
                          </div>	
      </div>
                       	<div class="branco" style="height:255px; width:12px; background-color:transparent;"></div>
						<div id="galerafacebook">
                      	  <div id="topogalerafacebook" style="text-indent:15px;">Inove Ticão <a style="color:#FFF; font-size:14px; text-indent:15px;">- Galera do Facebook</a></div>
                          <div class="branco" style="width:318px; height:209px;">
						  	<div class="branco" style="width:300px; padding-left:15px;">
                          <div class="fb-like" data-href="http://pt-br.facebook.com/pages/Inove-Produ%C3%A7%C3%B5es-de-Eventos/236630606359532" data-send="true" data-width="300" style="color:#FFF;" data-show-faces="true" data-font="arial">
                          </div>
                      </div>
                           </div>
    				 </div>
                      <div class="branco" style="height:255px; width:15px; background-color:#0F1818;"></div>
</div>
                </div>
                  </div>
  				</div>
<!-----------------------------------iniciorodape----------------------------------------------------->                
                <div class="linhastotal">
                    <div class="linhascentro" style="height:135px;">
                        <div class="patrocinadoresgrande"></div>
                        <div class="patrocinadoresgrande"><img src="imagens/patrocinador/parceiro2.jpg" width="143" height="94" /></div>
                        <div class="patrocinadoresgrande"><img src="imagens/patrocinador/parceiro1.jpg" width="143" height="94" /></div>
                        <div class="patrocinadoresgrande"><img src="imagens/patrocinador/parceiro3.jpg" width="143" height="94" /></div>
                        <div class="patrocinadoresgrande"></div>
                    </div>
                    <div class="linhastotal" style="background-image:url(imagens/fundorodape.jpg); background-repeat:repeat-x; height:29px;">
                        <div class="linhascentro" style="height:29px;"></div>
                    </div>
                    <div class="linhastotal" style="height:97px; background-color:#000000;">
                        <div class="linhascentro" style="height:97px;">
                        	<div class="patrocinadoresmenor"></div>
                         	<div class="patrocinadoresmenor"></div>
                            <div class="patrocinadoresmenor"></div>
                            <div class="patrocinadoresmenor"></div>
                            <div class="patrocinadoresmenor"></div>
                            <div class="patrocinadoresmenor"></div>
                            <div class="patrocinadoresmenor"></div>
                        </div>
                    </div>
                    <div class="linhastotal" style="background-color:#1D282E; height:45px;">
                        <div class="linhascentro" style="height:45px;">
                            <div class="branco" style="width:675px; height:45px;"></div>
                            <div class="branco" style="width:195px; height:45px; font-size:10px; color:#FFF; line-height:45px; font-family:"myriadpro";">© Copyright 2011 Mídia Mix - Mídia interativa</div>
                                <div class="branco" style="width:37px; height:45px;">
                                  <object id="FlashID" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="37" height="45">
                                    <param name="movie" value="flash/flashmix.swf" />
                                    <param name="quality" value="high" />
                                    <param name="wmode" value="opaque" />
                                    <param name="swfversion" value="6.0.65.0" />
                                    <!-- This param tag prompts users with Flash Player 6.0 r65 and higher to download the latest version of Flash Player. Delete it if you don’t want users to see the prompt. -->
                                    <param name="expressinstall" value="Scripts/expressInstall.swf" />
                                    <!-- Next object tag is for non-IE browsers. So hide it from IE using IECC. -->
                                    <!--[if !IE]>-->
                                    <object type="application/x-shockwave-flash" data="flash/flashmix.swf" width="37" height="45">
                                      <!--<![endif]-->
                                      <param name="quality" value="high" />
                                      <param name="wmode" value="opaque" />
                                      <param name="swfversion" value="6.0.65.0" />
                                      <param name="expressinstall" value="Scripts/expressInstall.swf" />
                                      <!-- The browser displays the following alternative content for users with Flash Player 6.0 and older. -->
                                      <div>
                                        <h4>Content on this page requires a newer version of Adobe Flash Player.</h4>
                                        <p><a href="http://www.adobe.com/go/getflashplayer"><img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash player" width="112" height="33" /></a></p>
                                      </div>
                                      <!--[if !IE]>-->
                                    </object>
                                    <!--<![endif]-->
                                  </object>
                                </div>
                        </div>
                    </div>
                </div>
			</	div>
<script type="text/javascript">
swfobject.registerObject("FlashID");
swfobject.registerObject("FlashID2");
</script>
</body>
</html>
