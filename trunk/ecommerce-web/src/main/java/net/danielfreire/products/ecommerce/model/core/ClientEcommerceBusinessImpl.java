package net.danielfreire.products.ecommerce.model.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.ecommerce.model.domain.ClientEcommerce;
import net.danielfreire.products.ecommerce.model.repository.ClientEcommerceRepository;
import net.danielfreire.util.ConvertTools;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;
import net.danielfreire.util.GridTitleResponse;
import net.danielfreire.util.MailUtil;
import net.danielfreire.util.PortalTools;
import net.danielfreire.util.ValidateTools;
import nl.captcha.Captcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component("clientEcommerceBusiness")
public class ClientEcommerceBusinessImpl implements ClientEcommerceBusiness {
	
	@Autowired
	private ClientEcommerceRepository repository;

	@Override
	public GridResponse consult(HttpServletRequest request) throws Exception {
		String siteId = (String) request.getSession().getAttribute(PortalTools.getInstance().idAdminSession);
		String page = request.getParameter("page");
		
		int pagination = 0;
		
		if (ValidateTools.getInstancia().isNumber(page)) {
			pagination = Integer.parseInt(page)-1;
		} 
		
		ArrayList<GridTitleResponse> titles = new ArrayList<GridTitleResponse>();
		
		GridTitleResponse title = new GridTitleResponse();
		title.setId("name");
		title.setTitle("Nome");
		title.setType("text");
		titles.add(title);
		
		title = new GridTitleResponse();
		title.setId("user");
		title.setTitle("E-mail");
		title.setType("text");
		titles.add(title);
		
		title = new GridTitleResponse();
		title.setId("password");
		title.setTitle("Senha");
		title.setType("text");
		titles.add(title);
		
		title = new GridTitleResponse();
		title.setId("newsletter");
		title.setTitle("Notícias");
		title.setType("text");
		titles.add(title);
		
		title = new GridTitleResponse();
		title.setId("addressStreet");
		title.setTitle("Rua");
		title.setType("text");
		titles.add(title);
		
		title = new GridTitleResponse();
		title.setId("addressNumber");
		title.setTitle("Número");
		title.setType("text");
		titles.add(title);
		
		title = new GridTitleResponse();
		title.setId("addressZipcode");
		title.setTitle("CEP");
		title.setType("text");
		titles.add(title);
		
		title = new GridTitleResponse();
		title.setId("addressCity");
		title.setTitle("Cidade");
		title.setType("text");
		titles.add(title);
		
		title = new GridTitleResponse();
		title.setId("addressComplement");
		title.setTitle("Complemento");
		title.setType("text");
		titles.add(title);
		
		title = new GridTitleResponse();
		title.setId("active");
		title.setTitle("Ativo");
		title.setType("text");
		titles.add(title);
		
		Page<ClientEcommerce> pageable = repository.findBySiteId(Integer.parseInt(siteId), new PageRequest(pagination, 10));
		
		GridResponse grid = new GridResponse();
		grid.setRows(pageable.getContent());
		grid.setTitles(titles);
		grid.setPage(pageable.getNumber()+1);
		grid.setTotalPages(pageable.getTotalPages());
		
		return grid;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GenericResponse updateAdmin(HttpServletRequest request)
			throws Exception {
		GenericResponse resp = new GenericResponse();
		
		HashMap<String, Object> map = getClient(request, true);
		
		if (map.get("errors")!=null) {
			resp = PortalTools.getInstance().getRespError((HashMap<String, String>) map.get("errors"));
		} else {
			repository.save((ClientEcommerce) map.get("client"));
		}
		
		return resp;
	}
	
	private HashMap<String, Object> getClient(HttpServletRequest request, boolean admin) {
		Integer siteId = null;
		final String name = request.getParameter("name");
		final String user = request.getParameter("user");
		final String password = request.getParameter("password");
		String newsletter = request.getParameter("newsletter");
		final String addressStreet = request.getParameter("addressStreet");
		final String addressNumber = request.getParameter("addressNumber");
		String addressZipcode = request.getParameter("addressZipcode");
		if (addressZipcode!=null) {
			addressZipcode = addressZipcode.replace("-", "");
		}
		final String addressCity = request.getParameter("addressCity");
		final String addressComplement = request.getParameter("addressComplement");
		String active = "false";
		final String id = request.getParameter("id");
		boolean captchaIsValid = true;
		
		if (admin) {
			siteId = Integer.parseInt(request.getSession().getAttribute(PortalTools.getInstance().idAdminSession).toString());
			active = request.getParameter("active");
		} else {
			siteId = Integer.parseInt(PortalTools.getInstance().Decode(request.getParameter("sid")));
			final Captcha captcha = (Captcha) request.getSession().getAttribute(Captcha.NAME);
			if (!captcha.isCorrect(request.getParameter("captcha"))) {
				captchaIsValid = false;
			}
		}
		
		HashMap<String, String> errors = new HashMap<String, String>();
		
		if (ValidateTools.getInstancia().isNullEmpty(name)) {
			errors.put("name", PortalTools.getInstance().getMessage("name.invalid"));
		}
		if (!ValidateTools.getInstancia().isEmail(user)) {
			errors.put("user", PortalTools.getInstance().getMessage("email.invalid"));
		}
		
		if (!admin && !ValidateTools.getInstancia().isNumber(id)) {
			if (repository.findBySiteIdAndUser(siteId, user)!=null) {
				errors.put("user", PortalTools.getInstance().getMessage("email.exists"));
			}
		}
		if (admin && ValidateTools.getInstancia().isNumber(id)) {
			ClientEcommerce oth = repository.findBySiteIdAndUser(siteId, user);
			if (oth!=null && oth.getId()!=Integer.parseInt(id)) {
				errors.put("user", PortalTools.getInstance().getMessage("email.exists"));
			}
		}
		
		if (!ValidateTools.getInstancia().isPassword(password)) {
			errors.put("password", PortalTools.getInstance().getMessage("password.invalid"));
		}
		if (ValidateTools.getInstancia().isNullEmpty(newsletter)) {
			newsletter = "false";
		}
		if (ValidateTools.getInstancia().isNullEmpty(addressStreet)) {
			errors.put("addressStreet", PortalTools.getInstance().getMessage("addressStreet.invalid"));
		}
		if (ValidateTools.getInstancia().isNullEmpty(addressNumber)) {
			errors.put("addressNumber", PortalTools.getInstance().getMessage("addressNumber.invalid"));
		}
		if (!ValidateTools.getInstancia().isCep(addressZipcode)) {
			errors.put("addressZipcode", PortalTools.getInstance().getMessage("addressZipcode.invalid"));
		}
		if (ValidateTools.getInstancia().isNullEmpty(addressCity)) {
			errors.put("addressCity", PortalTools.getInstance().getMessage("addressCity.invalid"));
		}
		if (ValidateTools.getInstancia().isNullEmpty(id) || ValidateTools.getInstancia().isNullEmpty(active)) {
			active = "false";
		} 
		if (!captchaIsValid) {
			errors.put("captcha", PortalTools.getInstance().getMessage("captcha.invalid"));
		}
		
		HashMap<String, Object> resp = new HashMap<String, Object>();
		if (errors.size()>0) {
			resp.put("errors", errors);
			resp.put("client", null);
		} else {
			ClientEcommerce client = new ClientEcommerce();
			if (ValidateTools.getInstancia().isNumber(id)) {
				client = new ClientEcommerce(Integer.parseInt(id));
			}
			client.setName(name);
			client.setSiteId(siteId);
			client.setActive(ConvertTools.getInstance().convertBoolean(active));
			client.setAddressCity(addressCity);
			client.setAddressComplement(addressComplement);
			client.setAddressNumber(addressNumber);
			client.setAddressStreet(addressStreet);
			client.setAddressZipcode(addressZipcode);
			client.setNewsletter(ConvertTools.getInstance().convertBoolean(newsletter));
			client.setPassword(password);
			client.setUser(user);
			
			resp.put("errors", null);
			resp.put("client", client);
		}
		
		return resp;
	}

	@Override
	public GenericResponse list(HttpServletRequest request) throws Exception {
		GenericResponse resp = new GenericResponse();
		
		resp.setGenericList(repository.findBySiteId(Integer.parseInt(request.getSession().getAttribute(PortalTools.getInstance().idAdminSession).toString())));
		
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GenericResponse insert(HttpServletRequest request) throws Exception {
		GenericResponse resp = new GenericResponse();
		
		final String contextid = request.getParameter("contextid");
		
		HashMap<String, Object> map = getClient(request, false);
		
		if (map.get("errors")!=null) {
			resp = PortalTools.getInstance().getRespError((HashMap<String, String>) map.get("errors"));
		} else {
			ClientEcommerce client = (ClientEcommerce) map.get("client");
			client.setId(null);
			repository.save(client);
			
			//Enviar e-mail para ativar cadastro
			new MailUtil().newUser(client.getUser(), client.getName(), client.getPassword(), contextid);
		}
		
		return resp;
	}

	@Override
	public GenericResponse login(HttpServletRequest request) throws Exception {
		GenericResponse resp = new GenericResponse();
		
		final String user = request.getParameter("user");
		final String password = request.getParameter("password");
		final Integer siteId = Integer.parseInt(PortalTools.getInstance().Decode(request.getParameter("sid")));
		final String contextid = request.getParameter("contextid");
		
		if (!ValidateTools.getInstancia().isEmail(user) || !ValidateTools.getInstancia().isPassword(password)) {
			resp = PortalTools.getInstance().getRespError("login.invalid");
		}
		
		if (resp.getStatus()) {
			ClientEcommerce client = repository.findBySiteIdAndUserAndPassword(siteId, user, password);
			if (client!=null) {
				
				if (client.getActive()) {
					client.setPassword(null);
					request.getSession().setAttribute(PortalTools.getInstance().idSession, client);
				} else {
					resp = PortalTools.getInstance().getRespError("login.non.active");
					new MailUtil().activationClient(client.getUser(), client.getName(), contextid);
				}
				
			} else {
				resp = PortalTools.getInstance().getRespError("login.invalid");
			}
		}
		
		return resp;
	}

	@Override
	public GenericResponse forgotPassword(HttpServletRequest request)
			throws Exception {
		GenericResponse resp = new GenericResponse();
		
		final String user = request.getParameter("user");
		final Integer siteId = Integer.parseInt(PortalTools.getInstance().Decode(request.getParameter("sid")));
		final String contextid = request.getParameter("contextid");
		
		if (!ValidateTools.getInstancia().isEmail(user)) {
			resp = PortalTools.getInstance().getRespError("user.invalid");
		}
		
		if (resp.getStatus()) {
			ClientEcommerce client = repository.findBySiteIdAndUser(siteId, user);
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
					new MailUtil().newPassword(client.getUser(), client.getName(), client.getPassword(), contextid);
				} else {
					//Enviar e-mail para ativação do cadastro
					new MailUtil().activationClient(client.getUser(), client.getName(), contextid);
				}
			}
		}
		
		return resp;
	}

	@Override
	public GenericResponse updatePassword(HttpServletRequest request)
			throws Exception {
		GenericResponse resp = new GenericResponse();
		
		final String senha1 = request.getParameter("senha1");
		final String senha2 = request.getParameter("senha2");
		
		if (!ValidateTools.getInstancia().isPassword(senha1) || !ValidateTools.getInstancia().isPassword(senha2)) {
			resp = PortalTools.getInstance().getRespError("password.invalid");
		}
		
		if (resp.getStatus()) {
			ClientEcommerce client = repository.findOne(((ClientEcommerce) request.getSession().getAttribute(PortalTools.getInstance().idSession)).getId());
			if (!client.getPassword().equals(senha1)) {
				resp = PortalTools.getInstance().getRespError("password.atual");
			} else {
				client.setPassword(senha2);
				repository.save(client);
			}
		}
		
		return resp;
	}

	@Override
	public GenericResponse updateAddress(HttpServletRequest request)
			throws Exception {
		GenericResponse resp = new GenericResponse();
		
		final String addressStreet = request.getParameter("addressStreet");
		final String addressComplement = request.getParameter("addressComplement");
		final String addressNumber = request.getParameter("addressNumber");
		final String addressZipcode = request.getParameter("addressZipcode");
		final String addressCity = request.getParameter("addressCity");
		
		if (ValidateTools.getInstancia().isNullEmpty(addressStreet) || ValidateTools.getInstancia().isNullEmpty(addressCity) || ValidateTools.getInstancia().isNullEmpty(addressNumber) || !ValidateTools.getInstancia().isCep(addressZipcode)) {
			resp = PortalTools.getInstance().getRespError("address.invalid");
		}
		
		if (resp.getStatus()) {
			ClientEcommerce client = repository.findOne(((ClientEcommerce) request.getSession().getAttribute(PortalTools.getInstance().idSession)).getId());
			
			client.setAddressCity(addressCity);
			client.setAddressComplement(addressComplement);
			client.setAddressNumber(addressNumber);
			client.setAddressStreet(addressStreet);
			client.setAddressZipcode(addressZipcode);
			
			repository.save(client);
			
			client.setPassword(null);
			request.getSession().setAttribute(PortalTools.getInstance().idSession, client);
		}
		
		return resp;
	}

	@Override
	public GenericResponse updateData(HttpServletRequest request)
			throws Exception {
		
		GenericResponse resp = new GenericResponse();
		
		final String name = request.getParameter("name");
		final String user = request.getParameter("user");
		final String newsletter = request.getParameter("newsletterText");
		
		if (ValidateTools.getInstancia().isNullEmpty(name) || !ValidateTools.getInstancia().isEmail(user)) {
			resp = PortalTools.getInstance().getRespError("data.invalid");
		}
		
		if (resp.getStatus()) {
			ClientEcommerce client = (ClientEcommerce) request.getSession().getAttribute(PortalTools.getInstance().idSession);
			
			if (!user.equals(client.getUser())) {
				if (repository.findBySiteIdAndUser(client.getSiteId(), user)!=null) {
					resp = PortalTools.getInstance().getRespError("email.exists");
				}
			}
			
			if (resp.getStatus()) {
				client = repository.findOne(client.getId());
				
				client.setName(name);
				client.setUser(user);
				client.setNewsletter(ConvertTools.getInstance().convertBoolean(newsletter));
				
				repository.save(client);
				
				client.setPassword(null);
				request.getSession().setAttribute(PortalTools.getInstance().idSession, client);
			}
		}
		
		return resp;
	}

	@Override
	public HashMap<String, Object> portalSession(HttpServletRequest request, String sid) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		ClientEcommerce client = (ClientEcommerce) request.getSession().getAttribute(PortalTools.getInstance().idSession);
		if (client!=null && client.getSiteId()==Integer.parseInt(PortalTools.getInstance().Decode(sid))) {
			map.put("client", client);
		} else {
			map.put("client", null);
		}
		
		map.put("cart", request.getSession().getAttribute(PortalTools.getInstance().idCartSession));
		return map;
	}

	@Override
	public void clearSession(HttpServletRequest request) throws Exception {
		request.getSession().removeAttribute(PortalTools.getInstance().idCartSession);
		request.getSession().removeAttribute(PortalTools.getInstance().idSession);
	}

}

