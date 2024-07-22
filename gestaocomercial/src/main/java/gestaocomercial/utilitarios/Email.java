package gestaocomercial.utilitarios;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import gestaocomercial.dominio.Produto;

public class Email {
	 public static void enviarEmail(String emailPara, String assunto,String mensagem) {
	        String seuEmail = "medicopestedoidao@gmail.com"; 
	        String suaSenha = "vike qmhn hcqr glte"; 
	        String hostName = "smtp.gmail.com"; 
	        
	        
	        SimpleEmail email = new SimpleEmail();
	        email.setHostName(hostName);
	        email.setSmtpPort(465); 
	        email.setAuthenticator(new DefaultAuthenticator(seuEmail, suaSenha));
	        email.setSSLOnConnect(true);

	        try {
	            email.setFrom(seuEmail);
	            email.addTo(emailPara);
	            email.setSubject(assunto);
	            email.setMsg(mensagem);

	            email.send();
	        } catch (EmailException e) {
	            e.printStackTrace();
	        }
	    }//enviarEmail()
	 
	 public static String montarMensagemEmail(Produto produto) {
		 var sb = new StringBuilder();
		 
		 return sb.append("\n\nO Produto: ").append(produto.getNomeProduto()).
		 append(" está com: ").append(produto.getQuantidadeEstoque()).
		 append(" quantidade em estoque").toString();
		 
	 }//montarMensagemEmail();
}//Email()
