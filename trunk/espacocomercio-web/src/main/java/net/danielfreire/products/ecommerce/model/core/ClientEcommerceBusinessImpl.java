package net.danielfreire.products.ecommerce.model.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.danielfreire.products.ecommerce.model.domain.ClientEcommerce;
import net.danielfreire.products.ecommerce.model.domain.Product;
import net.danielfreire.products.ecommerce.model.domain.Site;
import net.danielfreire.products.ecommerce.model.repository.ClientEcommerceRepository;
import net.danielfreire.products.ecommerce.model.repository.SiteRepository;
import net.danielfreire.products.ecommerce.util.GoogleUtil;
import net.danielfreire.util.ConvertTools;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.MailUtil;
import net.danielfreire.util.PortalTools;
import net.danielfreire.util.ValidateTools;
import nl.captcha.Captcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("clientEcommerceBusiness")
public class ClientEcommerceBusinessImpl implements ClientEcommerceBusiness {
	
	private static final String LBL_NAME = "name";
	private static final String LBL_USER = "user";
	private static final String LBL_PASSWORD = "password";
	private static final String LBL_STREET = "addressStreet";
	private static final String LBL_ADD_NUMBER = "addressNumber";
	private static final String LBL_ZIPCODE = "addressZipcode";
	private static final String LBL_CITY = "addressCity";
	private static final String LBL_ERRORS = "errors";
	private static final String LBL_CLIENT = "client";
	private static final String LBL_SID = "sid";
	
	@Autowired
	private transient ClientEcommerceRepository repository;
	@Autowired
	private transient SiteRepository siteRepository;
	@Autowired
	private transient MessageBusiness messageBusiness;
	
	private Map<String, Object> getClient(final HttpServletRequest request) {
		final String name 				= request.getParameter(LBL_NAME);
		final String user 				= request.getParameter(LBL_USER);
		final String password 			= request.getParameter(LBL_PASSWORD);
		final String addressStreet 		= request.getParameter(LBL_STREET);
		final String addressNumber 		= request.getParameter(LBL_ADD_NUMBER);
		final String addressCity 		= request.getParameter(LBL_CITY);
		final String addressComplement 	= request.getParameter("addressComplement");
		final String idClient 			= request.getParameter("id");
		String addressZipcode 			= request.getParameter(LBL_ZIPCODE);
		String newsletter 				= request.getParameter("newsletter");
		
		Site site = null;
		String active = "false";
		boolean captchaIsValid = true;
		
		if (addressZipcode!=null) {
			addressZipcode = addressZipcode.replace("-", "");
		}
		
		site = siteRepository.findOne(Integer.parseInt(PortalTools.getInstance().decode(request.getParameter(LBL_SID))));
		final Captcha captcha = (Captcha) request.getSession().getAttribute(Captcha.NAME);
		if (!captcha.isCorrect(request.getParameter("captcha"))) {
			captchaIsValid = false;
		}
		
		final HashMap<String, String> errors = new HashMap<String, String>();
		errors.putAll(ValidateTools.getInstancia().validateGeneric("name.invalid", ValidateTools.getInstancia().isNullEmpty(name)));
		errors.putAll(ValidateTools.getInstancia().validateGeneric("email.invalid", !ValidateTools.getInstancia().isEmail(user)));
		errors.putAll(ValidateTools.getInstancia().validateGeneric("email.exists", !ValidateTools.getInstancia().isNumber(idClient) && repository.findBySiteAndUser(site, user)!=null));
		errors.putAll(ValidateTools.getInstancia().validateGeneric("password.invalid", !ValidateTools.getInstancia().isPassword(password)));
		errors.putAll(ValidateTools.getInstancia().validateGeneric("addressStreet.invalid", ValidateTools.getInstancia().isNullEmpty(addressStreet)));
		errors.putAll(ValidateTools.getInstancia().validateGeneric("addressNumber.invalid", ValidateTools.getInstancia().isNullEmpty(addressNumber)));
		errors.putAll(ValidateTools.getInstancia().validateGeneric("addressZipcode.invalid", !ValidateTools.getInstancia().isCep(addressZipcode)));
		errors.putAll(ValidateTools.getInstancia().validateGeneric("addressCity.invalid", ValidateTools.getInstancia().isNullEmpty(addressCity)));
		errors.putAll(ValidateTools.getInstancia().validateGeneric("captcha.invalid", !captchaIsValid));
		
		if (ValidateTools.getInstancia().isNullEmpty(newsletter)) {
			newsletter = "false";
		}
		if (ValidateTools.getInstancia().isNullEmpty(idClient) || ValidateTools.getInstancia().isNullEmpty(active)) {
			active = "false";
		} 
		
		final HashMap<String, Object> resp = new HashMap<String, Object>();
		if (errors.isEmpty()) {
			ClientEcommerce client = new ClientEcommerce();
			if (ValidateTools.getInstancia().isNumber(idClient)) {
				client = new ClientEcommerce(Integer.parseInt(idClient));
			}
			client.setName(name);
			client.setSite(site);
			client.setActive(ConvertTools.getInstance().convertBoolean(active));
			client.setAddressCity(addressCity);
			client.setAddressComplement(addressComplement);
			client.setAddressNumber(addressNumber);
			client.setAddressStreet(addressStreet);
			client.setAddressZipcode(addressZipcode);
			client.setNewsletter(ConvertTools.getInstance().convertBoolean(newsletter));
			client.setPassword(password);
			client.setUser(user);
			
			resp.put(LBL_CLIENT, client);
		} else {
			resp.put(LBL_ERRORS, errors);
		}
		
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GenericResponse insert(final HttpServletRequest request) throws java.lang.Exception {
		GenericResponse resp = new GenericResponse();
		
		final String contextid = request.getParameter("contextid");
		final String sid = request.getParameter(LBL_SID);
		
		final Map<String, Object> map = getClient(request);
		
		if (map.get(LBL_ERRORS)==null) {
			final ClientEcommerce client = (ClientEcommerce) map.get(LBL_CLIENT);
			client.setId(null);
			repository.save(client);
			
			new MailUtil().newUser(client.getUser(), client.getName(), client.getPassword(), sid, contextid, client.getSite().getLogo(), client.getSite().getName());
			if (client.getId()==null) {
				new GoogleUtil().createContact(client.getSite(), client);
				messageBusiness.createMessage(client.getName(), "Novo cliente", client.getUser(), "Um novo cliente ("+client.getName()+" - "+client.getUser()+") acaba de se registrar na sua loja virtual.", "system", client.getSite().getId());
			}
		} else {
			resp = PortalTools.getInstance().getRespError((HashMap<String, String>) map.get(LBL_ERRORS));
		}
		
		return resp;
	}

	@Override
	public GenericResponse login(final HttpServletRequest request) throws java.lang.Exception {
		GenericResponse resp = new GenericResponse();
		
		final String user = request.getParameter(LBL_USER);
		final String password = request.getParameter(LBL_PASSWORD);
		final Integer siteId = Integer.parseInt(PortalTools.getInstance().decode(request.getParameter(LBL_SID)));
		final String contextid = request.getParameter("contextid");
		
		if (!ValidateTools.getInstancia().isEmail(user) || !ValidateTools.getInstancia().isPassword(password)) {
			resp = PortalTools.getInstance().getRespError("login.invalid");
		}
		
		if (resp.getStatus()) {
			final ClientEcommerce client = repository.findBySiteAndUserAndPassword(new Site(siteId), user, password);
			if (client==null) {
				resp = PortalTools.getInstance().getRespError("login.invalid");
			} else {
				if (client.getActive()) {
					client.setPassword(null);
					request.getSession().removeAttribute(PortalTools.ID_SESSION);
					request.getSession().setAttribute(PortalTools.ID_SESSION, client);
				} else {
					resp = PortalTools.getInstance().getRespError("login.non.active");
					new MailUtil().activationClient(client.getUser(), client.getName(), contextid, siteId, client.getSite().getLogo(), client.getSite().getName());
				}
			}
		}
		
		return resp;
	}

	@Override
	public GenericResponse forgotPassword(final HttpServletRequest request)
			throws java.lang.Exception {
		GenericResponse resp = new GenericResponse();
		
		final String user = request.getParameter(LBL_USER);
		final Integer siteId = Integer.parseInt(PortalTools.getInstance().decode(request.getParameter(LBL_SID)));
		final String contextid = request.getParameter("contextid");
		
		if (!ValidateTools.getInstancia().isEmail(user)) {
			resp = PortalTools.getInstance().getRespError("user.invalid");
		}
		
		if (resp.getStatus()) {
			final ClientEcommerce client = repository.findBySiteAndUser(new Site(siteId), user);
			if (client!=null) {
				if (client.getActive()) {
					
					String newPass = UUID.randomUUID().toString();
					if (newPass.length()>5) {
						newPass = newPass.substring(0,5)+"0a";
					} else {
						newPass = "abc123";
					}
					client.setPassword(newPass);
					repository.save(client);
					
					//Enviar e-mail com a nova senha
					new MailUtil().newPassword(client.getUser(), client.getName(), client.getPassword(), contextid, client.getSite().getLogo(), client.getSite().getName());
				} else {
					//Enviar e-mail para ativação do cadastro
					new MailUtil().activationClient(client.getUser(), client.getName(), contextid, siteId, client.getSite().getLogo(), client.getSite().getName());
				}
			}
		}
		
		return resp;
	}

	@Override
	public GenericResponse updatePassword(final HttpServletRequest request)
			throws java.lang.Exception {
		GenericResponse resp = new GenericResponse();
		
		final String senha1 = request.getParameter("senha1");
		final String senha2 = request.getParameter("senha2");
		
		if (!ValidateTools.getInstancia().isPassword(senha1) || !ValidateTools.getInstancia().isPassword(senha2)) {
			resp = PortalTools.getInstance().getRespError("password.invalid");
		}
		
		if (resp.getStatus()) {
			final ClientEcommerce client = repository.findOne(((ClientEcommerce) request.getSession().getAttribute(PortalTools.ID_SESSION)).getId());
			if (client.getPassword().equals(senha1)) {
				client.setPassword(senha2);
				repository.save(client);
			} else {
				resp = PortalTools.getInstance().getRespError("password.atual");
			}
		}
		
		return resp;
	}

	@Override
	public GenericResponse updateAddress(final HttpServletRequest request)
			throws java.lang.Exception {
		GenericResponse resp = new GenericResponse();
		
		final String addressStreet = request.getParameter(LBL_STREET);
		final String addressComplement = request.getParameter("addressComplement");
		final String addressNumber = request.getParameter(LBL_ADD_NUMBER);
		final String addressZipcode = request.getParameter(LBL_ZIPCODE);
		final String addressCity = request.getParameter(LBL_CITY);
		
		if (ValidateTools.getInstancia().isNullEmpty(addressStreet) || ValidateTools.getInstancia().isNullEmpty(addressCity) || ValidateTools.getInstancia().isNullEmpty(addressNumber) || !ValidateTools.getInstancia().isCep(addressZipcode)) {
			resp = PortalTools.getInstance().getRespError("address.invalid");
		}
		
		if (resp.getStatus()) {
			final ClientEcommerce client = repository.findOne(((ClientEcommerce) request.getSession().getAttribute(PortalTools.ID_SESSION)).getId());
			
			client.setAddressCity(addressCity);
			client.setAddressComplement(addressComplement);
			client.setAddressNumber(addressNumber);
			client.setAddressStreet(addressStreet);
			client.setAddressZipcode(addressZipcode);
			
			repository.save(client);
			
			client.setPassword(null);
			request.getSession().setAttribute(PortalTools.ID_SESSION, client);
		}
		
		return resp;
	}

	@Override
	public GenericResponse updateData(final HttpServletRequest request)
			throws java.lang.Exception {
		
		GenericResponse resp = new GenericResponse();
		
		final String name = request.getParameter(LBL_NAME);
		final String user = request.getParameter(LBL_USER);
		final String newsletter = request.getParameter("newsletterText");
		
		if (ValidateTools.getInstancia().isNullEmpty(name) || !ValidateTools.getInstancia().isEmail(user)) {
			resp = PortalTools.getInstance().getRespError("data.invalid");
		}
		
		if (resp.getStatus()) {
			ClientEcommerce client = (ClientEcommerce) request.getSession().getAttribute(PortalTools.ID_SESSION);
			
			if (!user.equals(client.getUser()) && repository.findBySiteAndUser(client.getSite(), user)!=null) {
				resp = PortalTools.getInstance().getRespError("email.exists");
			}
			
			if (resp.getStatus()) {
				client = repository.findOne(client.getId());
				
				client.setName(name);
				client.setUser(user);
				client.setNewsletter(ConvertTools.getInstance().convertBoolean(newsletter));
				
				repository.save(client);
				
				client.setPassword(null);
				request.getSession().setAttribute(PortalTools.ID_SESSION, client);
			}
		}
		
		return resp;
	}

	@Override
	public Map<String, Object> portalSession(final HttpServletRequest request, final String sid) throws java.lang.Exception {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		
		final ClientEcommerce client = (ClientEcommerce) request.getSession().getAttribute(PortalTools.ID_SESSION);
		if (client!=null && client.getSite().getId()==Integer.parseInt(PortalTools.getInstance().decode(sid))) {
			map.put(LBL_CLIENT, client);
		} else {
			map.put(LBL_CLIENT, null);
		}
		
		@SuppressWarnings("unchecked")
		final ArrayList<Product> list = (ArrayList<Product>) request.getSession().getAttribute(PortalTools.ID_CART_SESSION);
		if (list!=null && list.size()>0) {
			final ArrayList<Product> listSession = new ArrayList<Product>();
			for (Product p : list) {
				if (p.getSite().getId() == Integer.parseInt(PortalTools.getInstance().decode(sid))) {
					listSession.add(p);
				}
			}
			
			map.put("cart", listSession);
		}
		
		return map;
	}

	@Override
	public void clearSession(final HttpServletRequest request) throws java.lang.Exception {
		request.getSession().removeAttribute(PortalTools.ID_CART_SESSION);
		request.getSession().removeAttribute(PortalTools.ID_SESSION);
	}

	@Override
	public void active(final HttpServletRequest request, final HttpServletResponse response) throws java.lang.Exception {
		final String key = request.getParameter("key");
		final String[] keys = PortalTools.getInstance().decode(key).replace("[SEP]", "#").split("#");
		
		final ClientEcommerce client = repository.findBySiteAndUser(new Site(Integer.parseInt(keys[1])), keys[0]);
		client.setActive(true);
		
		repository.save(client);
		
		response.getWriter().print("<html><head><title>Cadastro ativado com sucesso</title></head><body><script>alert('Seu cadastro foi concluído com sucesso.'); location.href='/ecommerce/"+ConvertTools.getInstance().normalizeString(client.getSite().getName())+"';</script></body></html>");
	}

}


