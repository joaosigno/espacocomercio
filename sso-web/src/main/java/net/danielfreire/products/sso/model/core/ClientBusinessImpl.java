package net.danielfreire.products.sso.model.core;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.danielfreire.products.sso.model.domain.Client;
import net.danielfreire.products.sso.model.domain.Site;
import net.danielfreire.products.sso.model.repository.ClientRepository;
import net.danielfreire.products.sso.model.repository.SiteRepository;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;
import net.danielfreire.util.GridTitleResponse;
import net.danielfreire.util.PortalTools;
import net.danielfreire.util.ValidateTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component("clientBusiness")
public class ClientBusinessImpl implements ClientBusiness {
	
	@Autowired
	private ClientRepository clientDAO;
	@Autowired
	private SiteRepository siteDAO;

	@Override
	public GenericResponse insert(HttpServletRequest request) {
		GenericResponse resp = new GenericResponse();
		
		Client client = null;
		try {
			client = getClient(request);
		} catch (Exception e) {
			resp = PortalTools.getInstance().getRespError("data.invalid");
		}
		
		if (resp.getStatus()) {
			resp = insert(client);
		}
		
		return resp;
	}
	
	@Override
	public GenericResponse insert(Client client) {
		GenericResponse resp = new GenericResponse();
		
		try {
			HashMap<String, String> errors = isClient(client);
			if (errors.size()>0) {
				resp = PortalTools.getInstance().getRespError(errors);
			} else {
				clientDAO.save(client);
				resp.setGeneric(clientDAO.findByUserAndPassword(client.getUser(), client.getPassword()).getId());
			}
		} catch (Exception e) {
			resp = PortalTools.getInstance().getRespError(e);
		}
		
		return resp;
	}

	@Override
	public GenericResponse update(HttpServletRequest request) {
		GenericResponse resp = new GenericResponse();
		
		Client client = null;
		try {
			client = getClient(request);
		} catch (Exception e) {
			resp = PortalTools.getInstance().getRespError("data.invalid");
		}
		
		if (resp.getStatus()) {
			try {
				HashMap<String, String> errors = isClient(client);
				if (errors.size()>0) {
					resp = PortalTools.getInstance().getRespError(errors);
				} else {
					
					Client clientSession = (Client) request.getSession().getAttribute(PortalTools.getInstance().idSession); 
					if (clientSession.getId().equals(client.getId())) {
						clientDAO.save(client);
						if (clientSession.getId().equals(client.getId())) {
							request.getSession().setAttribute(PortalTools.getInstance().idSession, client);
						}
					} else {
						resp = PortalTools.getInstance().getRespError("permission.invalid");
					}
					
				}
			} catch (Exception e) {
				resp = PortalTools.getInstance().getRespError(e);
			}
		}
		
		return resp;
	}

	@Override
	public GenericResponse remove(HttpServletRequest request) {
		GenericResponse resp = new GenericResponse();
		
		try {
			Client clientSession = (Client) request.getSession().getAttribute(PortalTools.getInstance().idSession);
			if (!clientSession.getId().toString().equals(request.getParameter("id"))) {
				clientDAO.delete(Integer.parseInt(request.getParameter("id")));
			} else {
				resp = PortalTools.getInstance().getRespError("permission.invalid");
			}
		} catch (Exception e) {
			resp = PortalTools.getInstance().getRespError(e);
		}
		
		return resp;
	}

	@Override
	public GenericResponse list(HttpServletRequest request) {
		GenericResponse resp = new GenericResponse();
		
		String site = request.getParameter("site");
		
		if (!ValidateTools.getInstancia().isNumber(site)) {
			resp = PortalTools.getInstance().getRespError("system.out");
		}
		
		if (resp.getStatus()) {
			try {
				resp.setGenericList(clientDAO.findBySite(new Site(Integer.parseInt(site))));
			} catch (Exception e) {
				resp = PortalTools.getInstance().getRespError(e);
			}
		}
		
		return resp;
	}

	@Override
	public GenericResponse login(String user, String password,
			HttpServletRequest request) {
		GenericResponse resp = new GenericResponse();
		
		if (!ValidateTools.getInstancia().isEmail(user) || !ValidateTools.getInstancia().isPassword(password)) {
			resp = PortalTools.getInstance().getRespError("data.invalid");
		}
		
		if (resp.getStatus()) {
			if (user.equals("daniel@danielfreire.net")) {
				
				if (password.equals("dabf1985")) {
					request.getSession().setAttribute(PortalTools.getInstance().idAdminMasterSession, user);
					resp.setGeneric("/sso/admin");
				} else {
					resp = PortalTools.getInstance().getRespError("data.invalid");
				}
				
			} else {
				Client client = null;
				try {
					client = clientDAO.findByUserAndPassword(user, password);
				} catch (Exception e) {
					resp = PortalTools.getInstance().getRespError(e);
				}
						
				if (client == null) {
					resp = PortalTools.getInstance().getRespError("data.invalid");
				} else {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("to", client.getSite().getUrladm());
					map.put("tk", PortalTools.getInstance().Encode(PortalTools.getInstance().getToken()+"---"+client.getSite().getId()));
					
					resp.setGeneric(map);
				}
			}
		}
		
		return resp;
	}

	@Override
	public GenericResponse load(HttpServletRequest request) {
		GenericResponse resp = new GenericResponse();
		
		final String id = request.getParameter("id");
		
		if (!ValidateTools.getInstancia().isNumber(id)) {
			resp = PortalTools.getInstance().getRespError("system.out");
		}
		
		if (resp.getStatus()) {
			try {
				Client clientSession = (Client) request.getSession().getAttribute(PortalTools.getInstance().idSession);
				if (clientSession.getId().toString().equals(id)) {
					resp.setGeneric(clientDAO.findOne(Integer.valueOf(id)));
				} else {
					resp = PortalTools.getInstance().getRespError("permission.invalid");
				}
			} catch (Exception e) {
				resp = PortalTools.getInstance().getRespError(e);
			}
		}
		
		return resp;
	}
	
	private Client getClient(HttpServletRequest request) throws Exception {
		final String site = request.getParameter("site");
		final String usermail = request.getParameter("usermail");
		final String userpassword = request.getParameter("userpassword");
		final String id = request.getParameter("id");
		
		Client client = null;
		if (ValidateTools.getInstancia().isNumber(id)) {
			client = (Client) clientDAO.findOne(Integer.parseInt(id));
		} else {
			client = new Client();
			client.setSite(new Site(Integer.parseInt(site)));
		}
		
		if (ValidateTools.getInstancia().isNotnull(usermail))
			client.setUser(usermail);
		if (ValidateTools.getInstancia().isNotnull(userpassword))
			client.setPassword(userpassword);
		
		return client;
	}
	
	private HashMap<String, String> isClient(Client client) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		
		if (!ValidateTools.getInstancia().isEmail(client.getUser())) {
			map.put("user", PortalTools.getInstance().getMessage("email.invalid"));
		}
		if (!ValidateTools.getInstancia().isPassword(client.getPassword())) {
			map.put("password", PortalTools.getInstance().getMessage("password.invalid"));
		}
		if (!ValidateTools.getInstancia().isNumber(client.getId().toString())) {
			if (clientDAO.findByUserAndSite(client.getUser(), client.getSite())!=null) {
				map.put("user", PortalTools.getInstance().getMessage("client.exists"));
			}
		} else {
			Client client2 = (Client) clientDAO.findOne(client.getId());
			if (client2==null) {
				map.put("name", PortalTools.getInstance().getMessage("client.non.exists"));
			} else if (!client2.getUser().equals(client.getUser()) && clientDAO.findByUserAndSite(client.getUser(), client.getSite())!=null) {
				map.put("user", PortalTools.getInstance().getMessage("client.exists"));
			}
		}
		
		return map;
	}
	
	@Override
	public GridResponse consult(HttpServletRequest request) {
		String page = request.getParameter("page");
		
		int pagination = 0;
		
		if (ValidateTools.getInstancia().isNumber(page)) {
			pagination = Integer.parseInt(page)-1;
		} 
		
		ArrayList<GridTitleResponse> titles = new ArrayList<GridTitleResponse>();
		
		GridTitleResponse title = new GridTitleResponse();
		title.setId("site");
		title.setTitle("Site");
		title.setType("text");
		titles.add(title);
		
		title = new GridTitleResponse();
		title.setId("user");
		title.setTitle("Usuário");
		title.setType("text");
		titles.add(title);
		
		title = new GridTitleResponse();
		title.setId("password");
		title.setTitle("Senha");
		title.setType("text");
		titles.add(title);
		
		Page<Client> pageable = clientDAO.findAll(new PageRequest(pagination, 10));
		
		GridResponse grid = new GridResponse();
		grid.setRows(pageable.getContent());
		grid.setTitles(titles);
		grid.setPage(pageable.getNumber()+1);
		grid.setTotalPages(pageable.getTotalPages());
		
		return grid;
	}

	@Override
	public GenericResponse updateAdmin(HttpServletRequest request) throws Exception {
		Integer id = Integer.parseInt(request.getParameter("id"));
		String user = request.getParameter("user");
		String password = request.getParameter("password");
		
		Client client = clientDAO.findOne(id);
		
		client.setPassword(password);
		client.setUser(user);
		
		GenericResponse resp = new GenericResponse();
		
		HashMap<String, String> errors = isClient(client);
		
		if (errors.size()>0) {
			resp.setStatus(false);
			resp.setMessageError(errors);
		} else {
			clientDAO.save(client);
		}
		
		return resp;
	}

	@Override
	public void logout(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.getSession().removeAttribute(PortalTools.getInstance().idAdminMasterSession);
		request.getSession().removeAttribute(PortalTools.getInstance().idSession);
		request.getSession().removeAttribute(PortalTools.getInstance().idAdminSession);
		
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();

	    out.println("<HTML><HEAD><script>location.href='/sso';</script></HEAD><BODY></BODY></HTML>");
	}

	@Override
	public GenericResponse session(HttpServletRequest request) throws Exception {
		if (request.getSession().getAttribute(PortalTools.getInstance().idAdminMasterSession)!=null || request.getSession().getAttribute(PortalTools.getInstance().idAdminMasterSession)!=null) {
			
		}
		return null;
	}

}
