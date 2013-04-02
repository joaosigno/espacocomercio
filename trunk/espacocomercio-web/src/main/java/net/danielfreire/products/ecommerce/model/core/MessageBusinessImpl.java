package net.danielfreire.products.ecommerce.model.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.ecommerce.model.domain.Message;
import net.danielfreire.products.ecommerce.model.domain.Site;
import net.danielfreire.products.ecommerce.model.repository.MessageRepository;
import net.danielfreire.products.ecommerce.model.repository.SiteRepository;
import net.danielfreire.products.ecommerce.util.EcommerceUtil;
import net.danielfreire.util.ConvertTools;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.MailUtil;
import net.danielfreire.util.PortalTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("messageBusiness")
public class MessageBusinessImpl implements MessageBusiness {
	
	private static final String ORIGIN_SYSTEM = "system";
	@Autowired
	private MessageRepository repository;
	@Autowired
	private SiteRepository siteRepository;

	@Override
	public GenericResponse newMessage(final HttpServletRequest request) throws Exception {
		final String name = request.getParameter("name");
		final String type = request.getParameter("type");
		final String email = request.getParameter("email");
		final String text = request.getParameter("message");
		final String origin = request.getParameter("origin");
		final String sid = request.getParameter("sid");
		
		createMessage(name, type, email, text, origin, Integer.parseInt(sid));
		
		return new GenericResponse();
	}

	@Override
	public void createMessage(final String name, final String type, final String email,
			final String text, final String origin, final Integer sid) throws Exception {
		final Message message = new Message();
		
		final StringBuilder messageText = new StringBuilder()
			.append("<message><name>")
			.append(getValue(name))
			.append("</name><type>")
			.append(getValue(type))
			.append("</type><email>")
			.append(getValue(email))
			.append("</email><text>")
			.append(text)
			.append("</text></message>");
		
		message.setSite(siteRepository.findOne(sid));
		message.setOrigin(origin);
		message.setMessageText(messageText.toString());
		
		repository.save(message);
		
		if (origin.equals(ORIGIN_SYSTEM)) {
			sendMessageMailSystem(type, text, message.getSite());
		} else {
			sendMessageMail(name, type, email, text, message.getSite());
		}
		
	}

	private String getValue(final String value) {
		if (value!=null) {
			return value;
		} else { 
			return "";
		}
	}

	private void sendMessageMail(final String name, final String type, final String email,
			final String text, final Site site) throws Exception {
		
		FileReader fileReader = new FileReader(PortalTools.getInstance().getEcommerceProperties("location.email.template") + "/generic_message.html");
		BufferedReader reader = new BufferedReader(fileReader);
		String html = "";
		String line = "";  
		while((line = reader.readLine()) != null) {  
			html += line;  
		}
		fileReader.close();
		reader.close();
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("[logo.location]", PortalTools.getInstance().getEcommerceProperties("url.ecommerce")+"/"+ConvertTools.getInstance().normalizeString(site.getName())+"/logo.png");
		params.put("[msg.name]", name);
		params.put("[msg.type]", type);
		params.put("[msg.email]", email);
		params.put("[msg.text]", text);
		params.put("[site.url]", PortalTools.getInstance().getEcommerceProperties("url.ecommerce")+"/"+ConvertTools.getInstance().normalizeString(site.getName()));
		
		Set<String> keys = params.keySet();  
        for (String chave : keys)  
        {  
            if(chave != null)  
                html = html.replace(chave, params.get(chave));
        }
        
        html = html.replaceAll("  ", "");
        html = ConvertTools.getInstance().normalizeEspecialChar(html);
		
		new MailUtil().sendMail(type, ConvertTools.getInstance().normalizeString(site.getName()), html, "e-commerce");
	}

	private void sendMessageMailSystem(final String type, final String text, final Site site) throws Exception {
		
		FileReader fileReader = new FileReader(PortalTools.getInstance().getEcommerceProperties("location.email.template") + "/system_message.html");
		BufferedReader reader = new BufferedReader(fileReader);
		String html = "";
		String line = "";  
		while((line = reader.readLine()) != null) {  
			html += line;  
		}
		fileReader.close();
		reader.close();
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("[logo.location]", PortalTools.getInstance().getEcommerceProperties("url.ecommerce")+"/"+ConvertTools.getInstance().normalizeString(site.getName())+"/logo.png");
		params.put("[msg.text]", text);
		params.put("[site.url]", PortalTools.getInstance().getEcommerceProperties("url.ecommerce")+"/"+ConvertTools.getInstance().normalizeString(site.getName()));
		
		Set<String> keys = params.keySet();  
        for (String chave : keys)  
        {  
            if(chave != null)  
                html = html.replace(chave, params.get(chave));
        }
        
        html = html.replaceAll("  ", "");
        html = ConvertTools.getInstance().normalizeEspecialChar(html);
        
		new MailUtil().sendMail(type, ConvertTools.getInstance().normalizeString(site.getName()), html, "e-commerce");
	}

	@Override
	public GenericResponse countLastMessage(final HttpServletRequest request) {
		final Calendar init = Calendar.getInstance();
		init.add(Calendar.DAY_OF_MONTH, -1);
		
		final GenericResponse resp = new GenericResponse();
		resp.setGeneric(repository.countBySiteAndCreationDateGreaterThan(EcommerceUtil.getInstance().getSessionAdmin(request).getSite(), init));
		
		return resp;
	}

}
