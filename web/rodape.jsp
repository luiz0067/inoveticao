<%@page import="java.util.Vector"%>
<%@page import="DAO.patrocinadoresDAO"%>
<%@page import="BD.Conexao"%>
<%@page import="BEAN.patrocinadoresBEAN"%>
<!-----------------------------------inicio rodape-----------------------------------------------------> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>               
			<div class="linhastotal">
				<div class="linhascentro" style="height:135px;">
                                        <%
                                            Conexao minhaConexao=null;
                                            try{
                                            minhaConexao=(Conexao)session.getAttribute("minhaConexao");
                                            }
                                            catch(Exception erro){
                                            }
                                            patrocinadoresBEAN patrocinadores_bean= new patrocinadoresBEAN();
                                            patrocinadoresDAO patrocinadores_dao = new patrocinadoresDAO(minhaConexao);
                                            Vector registros_patrocinadores=patrocinadores_dao.buscarTodos();
                                        %>
                                        <div class="linhascentro" style="width:<%=175*registros_patrocinadores.size()%>px;">
                                        <%    
                                            for(int i=0;i<registros_patrocinadores.size();i++)
                                            {
                                                patrocinadores_bean=(patrocinadoresBEAN)registros_patrocinadores.get(i);
                                        %>
                                            <div class="patrocinadoresgrande"><img src="./arquivo.jsp?nome=<%=patrocinadores_bean.getSrc()%>&pasta=patrocinadores" width="143" height="94" /></div>
                                        <%
                                            }
                                        %>
                                        </div>
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
						<div class="branco" style="width:195px; height:45px; font-size:8px; color:#FFF; line-height:45px; font-family:Verdana, Geneva, sans-serif;">© Copyright 2011 Mídia Mix - Mídia interativa</div>
							<div  class="branco" style="width:37px; height:45px;">
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
				<script type="text/javascript">
					swfobject.registerObject("FlashID");
					swfobject.registerObject("FlashID2");
				</script>
			</div>
<!-----------------------------------fim rodape----------------------------------------------------->                
<%
  
%>