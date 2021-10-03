<%@page import="BEAN.categoriaFotosBEAN"%>
<%@page import="DAO.categoriaFotosDAO"%>
<%@page import="java.util.Vector"%>
<%@page import="DAO.fotosDAO"%>
<%@page import="BEAN.fotosBEAN"%>
<%@page import="BD.Conexao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!----------------------------------------------------inicio do fotos ------------------------------>
<div class="linhascentro"> 
	<div class="linhascentro" style="height:auto;overflow: auto; background-color:#10191A;">
                    <%
                    try{
                        Conexao minhaConexao=new Conexao(Until.functions.CreateDataConection()); 
                        categoriaFotosBEAN categoriaFotos_BEAN=new categoriaFotosBEAN();
                        categoriaFotosDAO categoriaFotos_DAO=new categoriaFotosDAO(minhaConexao);
                        Vector registros_categoriaFotos = new Vector();
                        String id=request.getParameter("id");
                        if(id==null){
                            registros_categoriaFotos = categoriaFotos_DAO.buscarUltimasAtualizadas();
                        }
                        else{
                            try{categoriaFotos_BEAN.setID(id);}catch(Exception erro1){}
                            registros_categoriaFotos.clear();
                            registros_categoriaFotos.add(categoriaFotos_DAO.buscarID(categoriaFotos_BEAN));
                        }
                        int x=0;
                        while(x<registros_categoriaFotos.size()){
                            categoriaFotos_BEAN=((categoriaFotosBEAN)registros_categoriaFotos.get(x));
                            fotosDAO fotos_DAO=new fotosDAO(minhaConexao);
                            Vector registros_fotos=new Vector();
                            if(id==null){
                                registros_fotos=fotos_DAO.buscarPorCategoria(categoriaFotos_BEAN,10);
                            }
                            else{
                                registros_fotos=fotos_DAO.buscarPorCategoria(categoriaFotos_BEAN);
                            }
                            fotosBEAN fotos_BEAN=new fotosBEAN();
                            int y=0;
                        %>
			<div id="containergaleriafotos">
				<div id="topogaleriafotos">
					<div class="branco" style="width:9px; height:auto;min-height:35px;"></div>
					<div class="branco" style="background-color:#1D282E;">
						<div class="branco" style="height:auto;min-height:37px; width:40px; background-color:#0187DC;"></div>
                                                <div class="branco" style="height:auto;min-height:37px; width:790px;">
							<%=categoriaFotos_BEAN.getTitulo()%>
						</div>
					</div>
				</div>
				<div id="containerminiaturasfotos">
					<div class="branco" style="width:20px; height:auto;min-height:71px;"></div>
                                        <%
                                            while(y<registros_fotos.size()){
                                                fotos_BEAN.Clear();
                                                fotos_BEAN=((fotosBEAN)registros_fotos.get(y));
                                                
                                        %>
                                        <div class="miniaturagaleriafotos">
                                                <%if(fotos_BEAN.getSrc()!=null){%>
                                                    <img src="./arquivo.jsp?nome=m_<%=fotos_BEAN.getSrc()%>" width="auto" height="70px" border="0">
                                                <%}%>
                                        </div>
                                        <div class="branco" style="width:20px; height:auto;min-height:70px;"></div>                                        
                                        <%
                                                if(y%5==4){ 
                                        %>
                                </div>				
                                <div id="containerminiaturasfotos">
					<div class="branco" style="width:20px; height:auto;min-height:71px;"></div>                                        
                                        <%
                                                }
                                                y++;
                                           }
                                           registros_fotos.clear();
                                        %>
				</div>
                                <div  style="background-color:#10191A;line-height:12px;height:20px;clear:both">
                                    <div style="float:right;width:auto">
                                        <div style="float:left;">
                                            <a href="?pg=fotos&id=<%=categoriaFotos_BEAN.getIDStr()%>" style="color:red;text-decoration:none">Veja mais >></a>
                                        </div>
                                        <div style="float:left;width:50px"></div>
                                    </div>
                                </div>
			</div>
		<%
                            x++;
                        }
                    }
                    catch(Exception erro){
                    }
		%>
		<div class="divisorlinhas" style="background-color:#10191A;"></div>
	</div>
</div>
		  
		  

			


<!----------------------------------------------------fim do fotos ------------------------------>