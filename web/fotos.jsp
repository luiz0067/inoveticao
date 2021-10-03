<%@page import="BEAN.categoriaFotosBEAN"%>
<%@page import="DAO.categoriaFotosDAO"%>
<%@page import="java.util.Vector"%>
<%@page import="DAO.fotosDAO"%>
<%@page import="BEAN.fotosBEAN"%>
<%@page import="BD.Conexao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!----------------------------------------------------inicio do fotos ------------------------------>
<link rel="stylesheet" type="text/css" href="estilos.css" />
<div class="linhascentro">
  <div class="linhascentro" style="height:auto;overflow: auto; background-color:#10191A;">
    <%
                    Conexao minhaConexao=null;
                    try{
                        minhaConexao=(Conexao)session.getAttribute("minhaConexao");
                    }
                    catch(Exception erro){
                    }
                    try{
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
                            registros_fotos=fotos_DAO.buscarPorCategoria(categoriaFotos_BEAN);
                            fotosBEAN fotos_BEAN=new fotosBEAN();
                            int y=0;
                            
                        %>
    <div id="containergaleriafotos">
      <div id="topogaleriafotos">
        <div class="branco" style="width:9px; height:auto;min-height:35px;"></div>
        <div class="branco" style="background-color:#1D282E;">
          <div class="branco" style="height:37px; width:40px; background-color:#0187DC;"></div>
          <!-- titulo da pagina de fotos-->
          <div class="branco" style="height:37px; line-height:37px; width:500px;"> <%=categoriaFotos_BEAN.getTitulo()%>
          </div>
          <!-- ir para a categoria de fotos -->
          <div class="branco" style="height:37px; line-height:37px; width:290px; text-align:right;">
          <a href="?pg=fotos&id=<%=categoriaFotos_BEAN.getIDStr()%>" style="font-size:10px;" class="sublinhados">Veja mais >></a> 
          </div>
          
          
          
        </div>
      </div>
      <div id="containerminiaturasfotos">
        <div class="branco" style="width:20px; height:auto;min-height:71px; line-height:71px;"></div>
        <%
                                            int limite=8;
                                            if((registros_fotos.size()<8)||(id!=null))
                                                limite=registros_fotos.size();
                                            
                                            while(y<limite){
                                                fotos_BEAN.Clear();
                                                fotos_BEAN=((fotosBEAN)registros_fotos.get(y));                                                
                                        %>
        <a href="./arquivo.jsp?nome=h_<%=fotos_BEAN.getSrc()%>" rel="lightbox[<%=categoriaFotos_BEAN.getIDStr()%>]" >
        <div class="miniaturagaleriafotos">
          <%if(fotos_BEAN.getSrc()!=null){%>
            <img src="./arquivo.jsp?nome=m_<%=fotos_BEAN.getSrc()%>"  class="fotos"  width="70px" border="0" onmouseover="this.className='fotos_hover'" onmouseout="this.className='fotos'">
          <%}%>
        </div>
        </a>
        <div class="branco" style="width:20px;_width:10px;height:70px; line-height:70px; overflow:hidden;display: block; background-color:#000000;">&nbsp;</div>
        <%
                                                if((y%8==7)&&(y+1!=limite)){ 
                                        %>
      </div>
      <div id="containerminiaturasfotos">
        <div class="branco" style="width:20px; height:auto;min-height:71px; line-height:70px;"></div>
        <%
                                                }
                                                y++;
                                           }
                                        %>
      </div>
      <div  style="background-color:#000000;line-height:12px;height:20px;clear:both">
        <div style="float:right;width:auto">
          <%if((registros_fotos.size()>8)&&(id==null)){%>
          <div style="float:left;"> </div>
          <%}
                                        else if (id!=null){
                                        %>
          <div style="float:left;"> <a href="?pg=fotos" style="color:red;text-decoration:none"><< Voltar Para Fotos</a> </div>
          <%}%>
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
<%
   
%>
