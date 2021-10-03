<%@page import="BEAN.sorteioBEAN"%>
<%@page import="java.util.Date"%>
<%@page import="DAO.contagemDAO"%>
<%@page import="BEAN.contagemBEAN"%>
<%@page import="Until.functions"%>
<%@page import="BEAN.bannersBEAN"%>
<%@page import="BD.Conexao"%>
<%@page import="java.util.Vector"%>
<%@page import="DAO.bannersDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    try{
        Conexao minhaConexao=null;
        minhaConexao=new Conexao(functions.CreateDataConection());
        contagemBEAN contagem =new contagemBEAN();
        contagem.setDataCadastro(new Date());
        contagem.setIP(request.getRemoteHost());
        contagemDAO contagem_dao=new contagemDAO(minhaConexao);
        contagem_dao.salvar(contagem);
        session.setAttribute("minhaConexao",minhaConexao);
    
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Inove Ticão</title>
		<link rel="stylesheet" href="estilos.css" type="text/css" />
		<link rel="stylesheet" href="css/lightbox.css" type="text/css" media="screen" />
		<style type="text/css">
			body {
			width:100%;
			height:100%;
			margin-bottom:0px;
			margin-left:0px;
			margin-right:0px;
			margin-top:0px;
			background-color:#1D282E;
			}

			@font-face {		
				font-family:'myriadpro';				
				_src: url('./Fontes/myriadpro.ttf'),src: url('./Fontes/myriadpro.eot');
				src: url('./Fontes/myriadpro.otf');
				font-weight: normal; 
				font-style: normal; 				
			}
		</style>
		<script src="js/prototype.js" type="text/javascript"></script>
		<script src="js/scriptaculous.js?load=effects,builder" type="text/javascript"></script>
		<script src="js/lightbox.js" type="text/javascript"></script>		
		<script src="Scripts/index.js" type="text/javascript"></script>
		<script src="Scripts/swfobject_modified.js" type="text/javascript"></script>
		
	</head>
        <body onload="carregar()">
		<div id="fb-root"></div>
		<script>
			(function(d, s, id) {
			  var js, fjs = d.getElementsByTagName(s)[0];
			  if (d.getElementById(id)) {return;}
			  js = d.createElement(s); js.id = id;
			  js.src = "//connect.facebook.net/pt_BR/all.js#xfbml=1";
			  fjs.parentNode.insertBefore(js, fjs);
			}(document, 'script', 'facebook-jssdk'));
			function carregar(){
				try{
					iniciar();
				}
				catch(erro){
					
				}
				try{
					 iniciar_banner();
				}
				catch(erro){
					
				}
				 try{
					 iniciar_banner2();
				}
				catch(erro){
					
				}
			}
		</script>
		<div id="tudo">
                    <jsp:include page="popup.jsp" />
                    <jsp:include page="topo.jsp" />
                    <jsp:include page="titulo.jsp" />
                    <jsp:include page="conteudo.jsp" />
                    <jsp:include page="banner.jsp" />
                    <jsp:include page="rodape.jsp" />
		</div>
	</body>
</html>
<%  
        try{
            minhaConexao=(Conexao)session.getAttribute("minhaConexao");
        }
        catch(Exception erro){
        }
        session.setAttribute("minhaConexao",null);
        session.removeAttribute("minhaConexao");
        //session.invalidate();
        minhaConexao.Fechar();
        //minhaConexao=null;
    }catch(Exception erro_geral){}
%>