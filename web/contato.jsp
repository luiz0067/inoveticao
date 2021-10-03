<%@ page import="java.util.Properties"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.io.*"%>
<%@ page import="javax.mail.*"%>
<%@ page import="javax.activation.*"%>
<%@ page import="javax.mail.internet.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="estilos.css" />
<%
	String nome= request.getParameter("nome");
	String fone= request.getParameter("fone");
	String e_mail= request.getParameter("e-mail");
	String assunto= request.getParameter("assunto");
	String menssagem= request.getParameter("messagem");
	String acao= request.getParameter("acao");
        if((acao!=null)&&(acao.equals("Enviar"))){
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
                if((acao!=null)&&(acao.equals("Enviar"))){
                    String acc="nome: "+nome+"<br>\n";
                    acc+="Email: "+e_mail+"<br>\n";
                    acc+="Telefone: "+fone+"<br>\n";
                    acc+="<br>\n";
                    acc+=menssagem;
                    menssagem=acc;
                    try{
                        String smtphost = "smtp-web.kinghost.net";
                        InternetAddress remetente    = new InternetAddress("contato@inoveticao.com.br"); 
                        InternetAddress destinatario = new InternetAddress("contato@inoveticao.com.br");
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
            }
            else{
                menssagem="Email inválido";
            }
        }
        menssagem=(menssagem==null)?"":menssagem;
%>  
<div class="linhascentro" style="height:500px;">
	<div class="linhascentro" style="height:500px; background-color:#000000;">
		<div class="branco" style="width:10px; height:500px;"></div>
		<div class="branco" style="width:490px; height:500px; background-color:#000000; padding-left:10px; color:#FFF; font-size:14px; font-family:Verdana, Geneva, sans-serif;">
			<form method="post" style="text-align:left; color:#FFF; font-family:Verdana, Geneva, sans-serif; padding-top:10px; height:480px; background-color:#000000;">
				Nome:<br><input class="camposcontato" name="nome" type="text" style="width:400px;text-align:left;" /><br><br> 
				Telefone:<br><input class="camposcontato" name="fone" type="text" style="width:400px;text-align:left;" /><br><br>
				E-mail:<br><input class="camposcontato" name="e-mail" type="text" style="width:400px;text-align:left;"  /><br><br>
				Assunto:<br><input class="camposcontato" name="assunto" type="text" style="width:400px;text-align:left;"  /><br><br>
				Mensagem:<br>
				<span id="areadetexto">
					<label for="areadetexto"></label>
					<textarea name="messagem" id="textarea1" cols="45" rows="4" style="width:400px;text-align:left;"></textarea>
					<span class="textareaRequiredMsg"></span>
				</span><br><br>
				<input name="acao" type="submit" id="Enviar" value="Enviar" />
				<input name="Enviar" type="reset" id="Limpar" value="Limpar" /><br>
                                <br>
                                <%=menssagem%>
			</form>
		</div>
		<div class="branco" style="height:490px; width:377px; padding-left:20px; padding-top:10px; color:#FFF; font-family:Verdana, Geneva, sans-serif; font-size:14px;">
			<h1> Inove Eventos</h1><br>
			Rua Alberto Dalcanale, 951 - Jd. Porto Alegre <br>
			Cep: 85906-402<br>
			Toledo | PR<br>
            Fone: 45 | 3252-3768<br>
			E-mail: inove@inoveticao.com.br<br><br><br><br><br>
			<h4> Bar do Ticão</h4><br>
			Rua Guarani, 3485 - Vila Becker <br>
			Cep: 85902-030<br>
			Toledo | PR<br>
			Fone: 45 | 3277-2101<br>
			E-mail: ticao@inoveticao.com.br<br>
		</div>
	</div>
</div>