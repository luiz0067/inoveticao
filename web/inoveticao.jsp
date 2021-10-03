<%@page import="DAO.conteudoDAO"%>
<%@page import="BEAN.conteudoBEAN"%>
<%@page import="DAO.paginasDAO"%>
<%@page import="BEAN.paginasBEAN"%>
<%@page import="java.util.Vector"%>
<%@page import="Until.functions"%>
<%@page import="BD.Conexao"%>
<!----------------------------------------------------inicio do inove ticao ------------------------------>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="linhascentro" style="background-color:#000000;">
	<div class="linhascentro" style="height:auto;min-height:1150px; background-color:#000000;overflow:auto;">
		<div class="branco" style="width:7px; height:auto;min-height:500px;"></div>
		<div id="textoinoveticao">
                        
                        <%
                            Conexao minhaConexao=null;
                            try{
                                minhaConexao=(Conexao)session.getAttribute("minhaConexao");
                            }
                            catch(Exception erro){
                            }
                            paginasBEAN paginas_BEAN=new paginasBEAN();
                            Vector registros_paginas=new Vector();
                            paginasDAO paginas_DAO = new paginasDAO(minhaConexao); 
                            try{paginas_BEAN=(paginasBEAN)paginas_DAO.buscarTitulo("Inove").get(0);}catch(Exception erro){};
                            conteudoBEAN conteudo_BEAN=new conteudoBEAN();
                            try{conteudo_BEAN.setId_paginas(paginas_BEAN.getIDStr());}catch(Exception erro){}
                            conteudoDAO conteudo_dao=new conteudoDAO(minhaConexao);
                            Vector registros_conteudo=new Vector();
                            try{registros_conteudo=conteudo_dao.buscarUltimasPaginas(conteudo_BEAN);}catch(Exception erro){}
                            if (registros_conteudo.size()>=1){
                                try{ conteudo_BEAN=(conteudoBEAN)registros_conteudo.get(0);}catch(Exception erro){}
                            }
                        %>
			<h1><%=conteudo_BEAN.getTitulo()%></h1><br>
			<div>
                            <%=conteudo_BEAN.getConteudo()%>
			</div>
		</div>
		<div class="branco" style="width:415px;height:auto;min-height:500px;">
			<div id="containerfoto">
				<div class="branco">
					<div id="fotogrande"><img src="./arquivo.jsp?nome=<%=paginas_BEAN.getSrc()%>&pasta=conteudo" width="404" height="303" /></div>
					<div class="branco" style="width:415px; height:auto;min-height:83px;">
						<div id="fotopequena"></div>
						<div id="fotopequena"></div>
						<div id="fotopequena"></div>
					</div>
					<div class="branco" style="width:404px; height:auto;min-height:9px;"></div>
					<div class="branco" style="width:404px; height:auto;min-height:21px;">
						<div class="branco" style="width:202px; height:auto;min-height:21px; color:#FFF; font-size:13px; font-family:'myriadpro'; font-style:italic; line-height:auto;min-height:21px;"></div>
						<div class="branco" style="width:202px; height:auto;min-height:21px; color:#FFF; font-size:13px; font-family:'myriadpro'; text-align:right; font-style:italic; line-height:auto;min-height:21px;"></div>
					</div>
				</div>
			</div>
		</div>
		<div class="branco" style="width:8px;height:auto;min-height:500px;"></div>
	</div>		
</div>


<!----------------------------------------------------fim do inove ticao ------------------------------>
<%
   
%>