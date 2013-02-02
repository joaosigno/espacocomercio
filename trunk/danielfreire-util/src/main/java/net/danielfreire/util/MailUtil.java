package net.danielfreire.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

public class MailUtil {

	private void sendMail(final String subject, final String to, final String text, final String contextId) throws Exception {
		final String username = PortalTools.getInstance().getEcommerceProperties("mail.smtp.user");
		final String password = PortalTools.getInstance().getEcommerceProperties("mail.smtp.password");
		final String from = contextId + PortalTools.getInstance().getEcommerceProperties("mail.smtp.from");
		
		Properties propriedades = System.getProperties();
		propriedades.put("mail.smtp.auth", PortalTools.getInstance().getEcommerceProperties("mail.smtp.auth"));
		propriedades.put("mail.smtp.starttls.enable", PortalTools.getInstance().getEcommerceProperties("mail.smtp.starttls.enable"));
		propriedades.put("mail.smtp.host", PortalTools.getInstance().getEcommerceProperties("mail.smtp.host"));
		propriedades.put("mail.smtp.port", PortalTools.getInstance().getEcommerceProperties("mail.smtp.port"));
		
		Session session = Session.getInstance(propriedades,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
		});
		
        MimeMessage msgEmail = new MimeMessage(session);

        msgEmail.setFrom(new InternetAddress(from));
        msgEmail.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

        msgEmail.setSentDate(new Date());
        msgEmail.setSubject(subject);
        msgEmail.setContent(text,"text/html");

        Transport.send(msgEmail);
	}
	
	public void newUser(String to, String name, String pass, String sidd, String contextId, String logo, String siteName) {
		try {
			final Integer siteId = Integer.parseInt(PortalTools.getInstance().Decode(sidd));
			final String key = PortalTools.getInstance().Encode(to + "[SEP]" + siteId);
			final String urlActivation = PortalTools.getInstance().getEcommerceProperties("url.activation.client") + "?key=" + key; 
			
			FileReader fileReader = new FileReader(PortalTools.getInstance().getEcommerceProperties("location.email.template") + "/new_client.html");
			BufferedReader reader = new BufferedReader(fileReader);
			String html = "";
			String line = "";  
			while((line = reader.readLine()) != null) {  
				html += line;  
			}
			fileReader.close();
			reader.close();
			
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("[logo.location]", PortalTools.getInstance().getEcommerceProperties("url.domain") + logo);
			params.put("[client.name]", name);
			params.put("[client.user]", to);
			params.put("[client.pass]", pass);
			params.put("[site.name]", siteName);
			params.put("[site.url]", PortalTools.getInstance().getEcommerceProperties("url.ecommerce")+"/"+contextId);
			params.put("[activation.location]", urlActivation);
			
			Set<String> keys = params.keySet();  
	        for (String chave : keys)  
	        {  
	            if(chave != null)  
	                html = html.replace(chave, params.get(chave));
	        }
	        
	        html = html.replaceAll("  ", "");
	        html = ConvertTools.getInstance().normalizeEspecialChar(html);
			
			sendMail(siteName+" - Cadastro", to, html, contextId);
		} catch (Exception e) {
			Logger.getLogger(getClass()).error(e);
		}
	}
	
	public void newPassword(String to, String name, String pass, String contextId, String logo, String siteName) {
		try {
			FileReader fileReader = new FileReader(PortalTools.getInstance().getEcommerceProperties("location.email.template") + "/new_password.html");
			BufferedReader reader = new BufferedReader(fileReader);
			String html = "";
			String line = "";  
			while((line = reader.readLine()) != null) {  
				html += line;  
			}
			fileReader.close();
			reader.close();
			
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("[logo.location]", PortalTools.getInstance().getEcommerceProperties("url.domain") + logo);
			params.put("[client.name]", name);
			params.put("[client.user]", to);
			params.put("[client.pass]", pass);
			params.put("[site.name]", siteName);
			params.put("[site.url]", PortalTools.getInstance().getEcommerceProperties("url.ecommerce")+"/"+contextId);
			
			Set<String> keys = params.keySet();  
	        for (String chave : keys)  
	        {  
	            if(chave != null)  
	                html = html.replace(chave, params.get(chave));
	        }
	        
	        html = html.replaceAll("  ", "");
	        html = ConvertTools.getInstance().normalizeEspecialChar(html);
			
			sendMail(siteName+" - Nova senha", to, html, contextId);
		} catch (Exception e) {
			Logger.getLogger(getClass()).error(e);
		}
	}
	
	public void activationClient(String to, String name, String contextId, Integer sid, String logo, String siteName) {
		try {
			final String key = PortalTools.getInstance().Encode(to + "[SEP]" + sid);
			final String urlActivation = PortalTools.getInstance().getEcommerceProperties("url.activation.client") + "?key=" + key; 
			
			FileReader fileReader = new FileReader(PortalTools.getInstance().getEcommerceProperties("location.email.template") + "/activation_client.html");
			BufferedReader reader = new BufferedReader(fileReader);
			String html = "";
			String line = "";  
			while((line = reader.readLine()) != null) {  
				html += line;  
			}
			fileReader.close();
			reader.close();
			
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("[logo.location]", PortalTools.getInstance().getEcommerceProperties("url.domain") + logo);
			params.put("[client.name]", name);
			params.put("[client.user]", to);
			params.put("[site.name]", siteName);
			params.put("[site.url]", PortalTools.getInstance().getEcommerceProperties("url.ecommerce")+"/"+contextId);
			params.put("[activation.location]", urlActivation);
			
			Set<String> keys = params.keySet();  
	        for (String chave : keys)  
	        {  
	            if(chave != null)  
	                html = html.replace(chave, params.get(chave));
	        }
	        
	        html = html.replaceAll("  ", "");
	        html = ConvertTools.getInstance().normalizeEspecialChar(html);
			
			sendMail(siteName+" - Ativar cadastro", to, html, contextId);
		} catch (Exception e) {
			Logger.getLogger(getClass()).error(e);
		}
	}
}
