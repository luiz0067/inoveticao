<%@page import="java.util.Vector"%>
<%@page import="DAO.contatoDAO"%>
<%@page import="BD.Conexao"%>
<%@page import="BEAN.contatoBEAN"%>
<%@ page import="java.util.Properties"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.io.*"%>
<%@ page import="javax.mail.*"%>
<%@ page import="javax.activation.*"%>
<%@ page import="javax.mail.internet.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%     
    
    String acao= request.getParameter("acao");
    if((acao!=null)&&(acao.equals("Enviar"))){
        Conexao minhaConexao=new Conexao(Until.functions.CreateDataConection()); 
        contatoBEAN contato_bean= new contatoBEAN();
        contatoDAO contato_dao = new contatoDAO(minhaConexao);
        Vector contatos=contato_dao.buscarAtivos();
        for(int i=0;i<contatos.size();i++)
        {   
            try{
                contato_bean=(contatoBEAN)contatos.get(i);
            }
            catch(Exception erro){
            }
            String assunto= request.getParameter("assunto");
            String menssagem= request.getParameter("messagem");
            String nome= contato_bean.getNome();
            String e_mail= contato_bean.getEmail();
            
            menssagem+="<br>";
            menssagem+="<br>";
            menssagem+="<br>";
            menssagem+="<a style=\"color:red;\" href=http://www.inoveticao.com.br/desativar_email.jsp?id="+contato_bean.getIDStr()+">";
            menssagem+="Não receber mais noticias deste site";
            menssagem+="</a>";
            
            if(
                (e_mail!=null)
                &&
                (
                    (e_mail.indexOf("@")!=-1)
                    &&
                    (e_mail.indexOf(".")!=-1)
                    &&
                    (e_mail.length()!=-1)                
                )
            ){            
                try{
                    String smtphost = "smtp-web.kinghost.net";
                    InternetAddress remetente    = new InternetAddress("contato@inoveticao.com.br"); 
                    InternetAddress destinatario = new InternetAddress(e_mail);
                    Properties p = new Properties();
                    p.put ("mail.smtp.host", smtphost);
                    Session email = Session.getInstance(p, null);
                    MimeMessage msg = new MimeMessage(email);
                    msg.setFrom(remetente);
                    msg.setRecipient(Message.RecipientType.TO, destinatario);
                    msg.setSubject(assunto);
                    msg.setContent(menssagem,"text/html");
                    msg.saveChanges();
                    Transport transport = email.getTransport("smtp");
                    transport.connect(smtphost,"");
                    transport.sendMessage(msg, msg.getAllRecipients());
                    transport.close();
                    menssagem="Enviado com sucesso!";
                }catch(Exception e){
                    menssagem="Falha no envio.";
                } 
                
            }
            else{
                menssagem="Email inválido";
            }
        }
    }
%>  
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro conteudo</title>
        <link rel="shortcut icon" href="./midia_mix.png" type="image/x-icon" />
    </head>
    <body onload="relogio();">
        <center>
            <jsp:include page="./topo.jsp" />
            <form>
            News Latter<br><br>
            <form method="post" >
               Assunto: <input type="text" name="assunto" <br>
               Conteudo<br>
               <%             
                    net.fckeditor.FCKeditor fckEditor = new net.fckeditor.FCKeditor(request, "messagem");
                    fckEditor.setConfig("UserFilesAbsolutePath", "/home/inoveticao/webapps/ROOT/upload/");
                    fckEditor.setConfig("UserFilesAbsolutePath", "/home/inoveticao/webapps/ROOT/upload/");
                    fckEditor.setConfig("UserFilesPath", "/upload/");
                    fckEditor.setConfig("SkinPath", "skins/office2003/");
                    fckEditor.setConfig("DefaultLanguage", "pt-br/");
                    
                    fckEditor.setValue(Until.functions.removenull(""));
                    fckEditor.setBasePath("/fckeditor");
                    fckEditor.setHeight("400px");
                    fckEditor.createHtml();
                    out.println(fckEditor);
               %>
               <input type="submit" value="Enviar" name="acao">
            </form>
                
        </center>
    </body>
</html>