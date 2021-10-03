<%@page import="java.io.File"%>
<%@page import="POJO.agendaPOJO"%>
<%@page import="java.util.Vector"%>
<%@page import="DAO.agendaDAO"%>
<%@page import="BEAN.agendaBEAN"%>
<%@page import="Until.functions"%>
<%@page import="BD.Conexao"%>
<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<link rel="stylesheet" type="text/css" href="estilosadm.css">
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro Agenda</title>
        <link rel="shortcut icon" href="./midia_mix.png" type="image/x-icon" />
        <script type="text/javascript" src="js/jquery-1.2.6.pack.js"></script>
        <script type="text/javascript" src="js/jquery.maskedinput-1.1.4.pack.js"/></script>
        <script type="text/javascript">
            $(document).ready(function(){
                    $("#DataEvento").mask("99/99/9999");
            });
            function chormeErroFoto(){
                var formulario=document.getElementById('formulario');
                var temFoto=false;
                elementos=fomulario.elements;
                for(var i=0;i<elementos.length;i++){
                    var tag=elementos[i].getTagName();
                    var valor="";
                    var tipo="";
                    if (tag.toUpperCase()=="INPUT"){
                        valor=elementos[i].value;
                        tipo=elementos[i].type;
                    }
                    if(type.toUpperCase()=="FILE"){
                        if((valor!=null)&&(valor.length!=0)){
                            temFoto=true;
                        }
                    }                    
                }
                if(!temFoto)
                    document.getElementById('formulario').enctype='';
            }
        </script>
    </head>
    <body onload="relogio();">
        <center>
            <%
                Conexao minhaConexao=new Conexao(functions.CreateDataConection()); 
                agendaBEAN agenda_BEAN=new agendaBEAN();
                agendaDAO agenda_DAO=new agendaDAO(minhaConexao);
                Vector registros_agenda=new Vector();
                Vector registros_agendaD=new Vector();
                Until.functions request2=new Until.functions();
                request2.setRequest(request);
                
                String ID=request2.getParameter("id");
                String Titulo=request2.getParameter("Titulo");
                String Evento=request2.getParameter("Evento");
                String descricao=request2.getParameter("descricao");
                String DataEvento=request2.getParameter("DataEvento");
                String Src="";
                String acao=request2.getParameter("acao");
                
                
                
                String mensagem_ID="";
                String mensagem_Titulo="";
                String mensagem_Evento="";
                String mensagem_descricao="";
                String mensagem_DataEvento="";
                String mensagemSrc="";
                String mensagem_acao="";
                
                try{agenda_BEAN.setID(ID);}catch(Exception erro){mensagem_ID="O agenda selecionado não existe";};
                if((acao!=null)&&(acao.length()>0)){  
                    try{agenda_BEAN.setTitulo(Titulo);}catch(Exception erro){mensagem_Titulo=erro.getMessage();};
                    try{agenda_BEAN.setEvento(Evento);}catch(Exception erro){mensagem_Evento=erro.getMessage();};
                    try{agenda_BEAN.setdescricao(descricao);}catch(Exception erro){mensagem_descricao=erro.getMessage();};
                    try{agenda_BEAN.setDataEvento(DataEvento);}catch(Exception erro){mensagem_DataEvento=erro.getMessage();};
                    if(acao.equals("Buscar")){
                        agendaPOJO agenda_POJO= new agendaPOJO();
                        agenda_POJO.setTitulo(Titulo);
                        agenda_BEAN= new agendaBEAN(agenda_POJO);
                        try{registros_agenda=agenda_DAO.buscar(agenda_BEAN);}catch(Exception erro){mensagem_acao=erro.getMessage();};                            
                        mensagem_ID="";
                        mensagem_Titulo="";
                        mensagem_Evento="";
                        mensagem_descricao="";
                        mensagem_DataEvento="";
                        mensagemSrc="";
                        mensagem_acao="";                        
                    }
                    else if(acao.equals("Salvar")){
                        if(
                            (mensagem_Titulo.length()==0)
                            &&
                            (mensagem_Evento.length()==0)
                            &&
                            (mensagem_descricao.length()==0)
                            &&
                            (mensagem_DataEvento.length()==0)
                        )
                        {
                            try{
                                String acc="/";
                                if (functions.path_upload.indexOf(acc)==-1)
                                    acc="\\";
                                String path =functions.path_upload+acc+"agenda"+acc;
                                Src=request2.upload("Src",path);
                                if (Src!=null){
                                    functions.redimensionarImagem(path+Src,path+"g_"+Src,294,410,true);
                                    functions.redimensionarImagem(path+Src,path+"m_"+Src,292,222,true);
                                    File file=new File(path+Src);
                                    file.delete();
                                    try{
                                        agenda_BEAN.setSrc(Src);
                                    }
                                    catch(Exception erro)
                                    {
                                        mensagemSrc=erro.getMessage();
                                    }
                                }
                                agenda_BEAN=agenda_DAO.salvar(agenda_BEAN,functions.path_upload);
                                registros_agenda.add(agenda_BEAN);
                                if (
                                        (agenda_BEAN!=null)
                                        &&
                                        (agenda_BEAN.getIDStr()!=null)
                                ){
                                    mensagem_acao="agenda salva com sucesso";
                                }
                                else
                                    mensagem_acao="agenda não pode ser salva";
                            }
                            catch(Exception erro){
                                mensagem_acao=erro.getMessage();
                            }
                        }
                        else
                            agenda_BEAN.Clear();
                        mensagem_ID="";                        
                    }
                    else if(acao.equals("Excluir")){
                        if (
                            (mensagem_ID.length()==0)                                                                                  
                        )
                        {
                            try{
                                agenda_BEAN=agenda_DAO.excluir(agenda_BEAN,functions.path_upload);
                                if((agenda_BEAN!=null)&&(agenda_BEAN.getIDStr()!=null)){
                                    mensagem_acao="agenda excluído com sucesso";
                                }
                                else{
                                    mensagem_acao="agenda não encontrado";
                                }
                            }
                            catch(Exception erro){
                                mensagem_acao=erro.getMessage();
                            }
                        }
                        mensagem_ID="";
                        mensagem_DataEvento="";
                        mensagem_Evento="";
                        mensagem_descricao="";
                        mensagem_Titulo="";
                        agenda_BEAN.Clear();
                    }                                          
                }
                else {
                    agenda_BEAN=((agendaBEAN)agenda_DAO.buscarID(agenda_BEAN));
                    if((agenda_BEAN!=null)&&(agenda_BEAN.getIDStr()!=null)){
                        registros_agenda.add(agenda_BEAN);
                    }
                    else{
                        mensagem_ID="";
                        registros_agenda=agenda_DAO.buscarTodos();
                        agenda_BEAN.Clear();
                    }
                                       }
                    
                    
            %>
            <%
            
 
                    agenda_BEAN=((agendaBEAN)agenda_DAO.buscarID(agenda_BEAN));
                    if((agenda_BEAN!=null)&&(agenda_BEAN.getIDStr()!=null)){
                        registros_agenda.add(agenda_BEAN);
                    }
                    else{
                        mensagem_ID="";
                        registros_agendaD=agenda_DAO.buscarTodos();
                        agenda_BEAN.Clear();
                    }
                
%>
        <a class="texto1">Cadastro Agenda</a><br><br>
            <form method="post" enctype="multipart/form-data" id="formulario"> 
                <input type="hidden" name="id" value="<%=functions.removenull(agenda_BEAN.getIDStr())%>" id="id">
               <div style="clear:both;color:red;"><%=mensagem_ID%></div>
             <a class="texto3">Titulo: </a><input type="text" name="Titulo" value="<%=functions.removenull(agenda_BEAN.getTitulo())%>"><br>
               <div style="clear:both;color:red;"><%=mensagem_Titulo%></div>               
               <a class="texto3">Evento:</a> <input type="text" name="Evento" value="<%=functions.removenull(agenda_BEAN.getEvento())%>"><br>
               <div style="clear:both;color:red;"><%=mensagem_Evento%></div>
               <a class="texto3">DataEvento:</a> <input type="text" id="DataEvento" name="DataEvento" value="<%=functions.removenull(agenda_BEAN.getDataEventoStr("dd/MM/yyyy"))%>"><br>
               <div style="clear:both;color:red;"><%=mensagem_DataEvento%></div>
             <a class="texto3">Fotos:</a> <input type="file" name="Src"><br>
              Descricao:<br>
				<span id="areadetexto">
					<label for="areadetexto"></label>
					<textarea name="descricao" id="textarea1" cols="45" rows="4" style="width:400px;text-align:left;"></textarea>
					<span class="textareaRequiredMsg"></span>
				</span>           
               <br></br>
             
               <div style="clear:both;color:red;"></div>
               <input type="submit" value="Novo"   onclick="document.getElementById('id').value=''">
               <input type="submit" value="Salvar" name="acao" onclick="chormeErroFoto()">
               <input type="submit" value="Buscar" name="acao" onclick="chormeErroFoto()">
               <br>
               <div style="clear:both;color:red;"><%=mensagem_acao%></div>
            </form>
                <table cellpadding= "4" cellspacing = "0" border= "1" width= "400px">
                    <tr bgcolor="#CCCCCC">                           
                      <td><a class="texto3"> Titulo </a></td>
                      <td><a class="texto3"> Evento </a></td>
                      <td><a class="texto3"> DataEvento </a></td>
                        <td><a class="texto3"> acao </a></td>
		    </tr>
                  <%
                            if((registros_agenda!=null)&&(registros_agenda.size()>0)){
                                for(int i=0;i<registros_agenda.size();i++){
                                    agenda_BEAN=((agendaBEAN)registros_agenda.get(i));
                                    
                                  
                        %>
                        
                       
                                                                                          
                       
                        <tr>
                            <td><%=functions.removenull(agenda_BEAN.getTitulo())%></td>
                            <td><%=functions.removenull(agenda_BEAN.getEvento())%></td>
                            <td><%=functions.removenull(agenda_BEAN.getDataEventoStr("dd/MM/yyyy"))%></td>
                            <td>
                                <form method="post">
                                    <input name="id" type="hidden" value="<%=agenda_BEAN.getIDStr()%>">
                                    <input type="submit" value="Editar">
                                    <input name="acao" type="submit" value="Excluir">
                                </form>
                            </td>
                        </tr>
                        <tr>
                            <td COLSpan="4" align="center">
                                <a target="_black" src="./arquivo.jsp?nome=g_<%=agenda_BEAN.getSrc()%>&pasta=agenda" >
                                    <img align="center" src="./arquivo.jsp?nome=m_<%=agenda_BEAN.getSrc()%>&pasta=agenda" width="294" height="410" >
                                </a>
                            </td>
                        </tr>
                        <tr>
                            <td COLSpan="5" align="center">
                                <%=Until.functions.removenull(agenda_BEAN.getdescricao())%>
                            </td>
                            
                        </tr>
                      
                        <%
                                }
                            }
                                                                      
                            try{minhaConexao.Fechar();}catch(Exception erro2){}                            
                        %>
                </table>
        </center>
    </body>
</html>

