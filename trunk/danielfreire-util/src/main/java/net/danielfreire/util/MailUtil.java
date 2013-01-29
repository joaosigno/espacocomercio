package net.danielfreire.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

public class MailUtil {

	private void sendMail(final String subject, final String to, final String text, final String contextId) throws Exception {
		final String username = PortalTools.getInstance().getEcommerceProperties(contextId+".mail.smtp.user");
		final String password = PortalTools.getInstance().getEcommerceProperties(contextId+".mail.smtp.password");
		final String smtp = PortalTools.getInstance().getEcommerceProperties(contextId+".mail.smtp.host");
		final String from = PortalTools.getInstance().getEcommerceProperties(contextId+".mail.smtp.from");
		
		Properties propriedades = System.getProperties();
        Session session = Session.getDefaultInstance(propriedades, null);
        MimeMessage msgEmail = new MimeMessage(session);

        msgEmail.setFrom(new InternetAddress(from));
        msgEmail.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

        msgEmail.setSentDate(new Date());
        msgEmail.setSubject(subject);
        msgEmail.setContent(text,"text/html");

        Transport tr = session.getTransport("smtp");
        tr.connect(smtp, username, password);
        msgEmail.saveChanges();
        tr.sendMessage(msgEmail, msgEmail.getAllRecipients());
        tr.close();
	}
	
	public void newUser(String to, String name, String pass, String sidd, String contextId) {
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
			params.put("[logo.location]", PortalTools.getInstance().getEcommerceProperties(contextId+".logo.url"));
			params.put("[client.name]", name);
			params.put("[client.user]", to);
			params.put("[client.pass]", pass);
			params.put("[site.name]", PortalTools.getInstance().getEcommerceProperties(contextId+".name"));
			params.put("[site.url]", PortalTools.getInstance().getEcommerceProperties(contextId+".url"));
			params.put("[activation.location]", urlActivation);
			
			Set<String> keys = params.keySet();  
	        for (String chave : keys)  
	        {  
	            if(chave != null)  
	                html = html.replace(chave, params.get(chave));
	        }
	        
	        html = html.replaceAll("  ", "");
	        html = ConvertTools.getInstance().normalizeEspecialChar(html);
			
			sendMail(PortalTools.getInstance().getEcommerceProperties(contextId+".name")+" - Cadastro", to, html, contextId);
		} catch (Exception e) {
			Logger.getLogger(getClass()).error(e);
		}
	}
	
	public void newPassword(String to, String name, String pass, String contextId) {
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
			params.put("[logo.location]", PortalTools.getInstance().getEcommerceProperties(contextId+".logo.url"));
			params.put("[client.name]", name);
			params.put("[client.user]", to);
			params.put("[client.pass]", pass);
			params.put("[site.name]", PortalTools.getInstance().getEcommerceProperties(contextId+".name"));
			params.put("[site.url]", PortalTools.getInstance().getEcommerceProperties(contextId+".url"));
			
			Set<String> keys = params.keySet();  
	        for (String chave : keys)  
	        {  
	            if(chave != null)  
	                html = html.replace(chave, params.get(chave));
	        }
	        
	        html = html.replaceAll("  ", "");
	        html = ConvertTools.getInstance().normalizeEspecialChar(html);
			
			sendMail(PortalTools.getInstance().getEcommerceProperties(contextId+".name")+" - Nova senha", to, html, contextId);
		} catch (Exception e) {
			Logger.getLogger(getClass()).error(e);
		}
	}
	
	public void activationClient(String to, String name, String contextId) {
		try {
			final Integer sid = Integer.parseInt(PortalTools.getInstance().Decode(PortalTools.getInstance().getEcommerceProperties(contextId+".site.id")));
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
			params.put("[logo.location]", PortalTools.getInstance().getEcommerceProperties(contextId+".logo.url"));
			params.put("[client.name]", name);
			params.put("[client.user]", to);
			params.put("[site.name]", PortalTools.getInstance().getEcommerceProperties(contextId+".name"));
			params.put("[site.url]", PortalTools.getInstance().getEcommerceProperties(contextId+".url"));
			params.put("[activation.location]", urlActivation);
			
			Set<String> keys = params.keySet();  
	        for (String chave : keys)  
	        {  
	            if(chave != null)  
	                html = html.replace(chave, params.get(chave));
	        }
	        
	        html = html.replaceAll("  ", "");
	        html = ConvertTools.getInstance().normalizeEspecialChar(html);
			
			sendMail(PortalTools.getInstance().getEcommerceProperties(contextId+".name")+" - Ativar cadastro", to, html, contextId);
		} catch (Exception e) {
			Logger.getLogger(getClass()).error(e);
		}
	}
}
