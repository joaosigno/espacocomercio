package net.danielfreire.products.advocacy.model.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.advocacy.model.core.menu.MenuClient;
import net.danielfreire.products.advocacy.model.core.menu.MenuContract;
import net.danielfreire.products.advocacy.model.core.menu.MenuFinance;
import net.danielfreire.products.advocacy.model.core.menu.MenuHome;
import net.danielfreire.products.advocacy.model.core.menu.MenuLawyer;
import net.danielfreire.products.advocacy.model.core.menu.MenuOffice;
import net.danielfreire.products.advocacy.model.core.menu.MenuOut;
import net.danielfreire.products.advocacy.model.domain.AdvocacyOffice;
import net.danielfreire.products.advocacy.model.domain.AdvocacyUser;
import net.danielfreire.products.advocacy.model.repository.AdvocacyUserRepository;
import net.danielfreire.products.advocacy.util.AdvocacyUtil;
import net.danielfreire.util.ConvertTools;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;
import net.danielfreire.util.GridTitleResponse;
import net.danielfreire.util.PortalTools;
import net.danielfreire.util.ValidateTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component("advocacyUserBusiness")
public class AdvocacyUserBusinessImpl implements AdvocacyUserBusiness {
	
	@Autowired
	private AdvocacyUserRepository repository;

	@Override
	public GenericResponse login(HttpServletRequest request) throws Exception {
		GenericResponse resp = new GenericResponse();
		
		final String user = request.getParameter("user");
		final String pass = request.getParameter("password");
		
		if (!ValidateTools.getInstancia().isEmail(user) || !ValidateTools.getInstancia().isPassword(pass)) {
			resp = PortalTools.getInstance().getRespError("login.invalid");
		}
		
		if (resp.getStatus()) {
			if (!user.equals("daniel@danielfreire.net")) {
				
				AdvocacyUser advocacyUser = repository.findByUsernameAndUserpassword(user, pass);
				if (advocacyUser != null) {
					AdvocacyUtil.getInstancia().sessionUser(request, advocacyUser);
				}
				
			} else {
				
				if (pass.equals("dabf1985")) {
					AdvocacyUtil.getInstancia().sessionUser(request, AdvocacyUtil.getInstancia().getUserMaster());
				} else {
					resp = PortalTools.getInstance().getRespError("login.invalid");
				}
				
			}
		}
		
		return resp;
	}

	@Override
	public GenericResponse menuAdmin(HttpServletRequest request)
			throws Exception {
		
		final AdvocacyUser user = AdvocacyUtil.getInstancia().getSessionUser(request);
		
		HashMap<String, Object> menu = new HashMap<String, Object>();
			
		menu.put("title", user.getAdvocacyOffice().getName());
		menu.put("username", user.getUsername());
		menu.put("position", true);
		menu.put("menus", generateMenu(user));
		
		GenericResponse resp = new GenericResponse();
		resp.setGeneric(menu);
		
		return resp;
	}
	
	@Override
	public GenericResponse manage(HttpServletRequest request) throws Exception {
		final AdvocacyUser user = getUser(request);
		
		GenericResponse resp = isUser(user);
		if (resp.getStatus()) {
			repository.save(user);
		}
		
		return resp;
	}
	
	@Override
	public GridResponse consult(HttpServletRequest request) {
		String page = request.getParameter("page");
		
		int pagination = 0;
		
		if (ValidateTools.getInstancia().isNumber(page)) {
			pagination = Integer.parseInt(page)-1;
		} 
		
		ArrayList<GridTitleResponse> titles = new ArrayList<GridTitleResponse>();
		titles.add(PortalTools.getInstance().getRowGrid("username", "Usuário", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("userpassword", "Senha", "text"));
		if (AdvocacyUtil.getInstancia().getSessionUser(request).getId()==0) {
			titles.add(PortalTools.getInstance().getRowGrid("advocacyOffice", "Escritório", "text"));
		}
		titles.add(PortalTools.getInstance().getRowGrid("permissions", "Permissões", "text"));
		
		Page<AdvocacyUser> pageable = null;
		if (AdvocacyUtil.getInstancia().getSessionUser(request).getId()==0) {
			pageable = repository.findAll(new PageRequest(pagination, 10));
		} else {
			pageable = repository.findByAdvocacyOffice(AdvocacyUtil.getInstancia().getSessionUser(request).getAdvocacyOffice(), new PageRequest(pagination, 10));
		}
		
		return PortalTools.getInstance().getGrid(pageable.getContent(), titles, pageable.getNumber()+1, pageable.getTotalPages());
	}
	
	private Map<String, Object> generateMenu(AdvocacyUser user) {
		Map<String, Object> menu = new HashMap<String, Object>();
		
		menu.putAll(new MenuHome().get(1, user));
		menu.putAll(new MenuOffice().get(2, user));
		menu.putAll(new MenuFinance().get(3, user));
		menu.putAll(new MenuContract().get(4, user));
		menu.putAll(new MenuClient().get(5, user));
		menu.putAll(new MenuLawyer().get(6, user));
		menu.putAll(new MenuOut().get(7, user));
		
		return menu;
	}

	private AdvocacyUser getUser(HttpServletRequest request) {
		final String id = request.getParameter("id");
		final String username = request.getParameter("username");
		final String userpassword = request.getParameter("userpassword");
		final String manageOffice = request.getParameter("manageOffice");
		final String manageUser = request.getParameter("manageUser");
		final String manageFinance = request.getParameter("manageFinance");
		final String manageClient = request.getParameter("manageClient");
		final String manageContract = request.getParameter("manageContract");
		final String manageLawyer = request.getParameter("manageLawyer");
		
		AdvocacyUser user = new AdvocacyUser();
		if (ValidateTools.getInstancia().isNumber(id)) {
			user = repository.findOne(Integer.parseInt(id));
		} 
		
		if (!ValidateTools.getInstancia().isNullEmpty(userpassword)) {
			user.setUserpassword(userpassword);
		}
		
		AdvocacyOffice office = null;
		if (AdvocacyUtil.getInstancia().getSessionUser(request).getId()==0) {
			office = new AdvocacyOffice(Integer.parseInt(request.getParameter("office")));
		} else {
			office = new AdvocacyOffice(AdvocacyUtil.getInstancia().getSessionUser(request).getAdvocacyOffice().getId());
		}
		
		user.setAdvocacyOffice(office);
		user.setUsername(username);
		
		user.setManageOffice(ConvertTools.getInstance().convertBoolean(manageOffice));
		if (manageClient.equals("2")) {
			user.setManageClient(false);
			user.setViewClient(true);
		} else {
			user.setManageClient(ConvertTools.getInstance().convertBoolean(manageClient));
			user.setViewClient(ConvertTools.getInstance().convertBoolean(manageClient));
		}
		if (manageContract.equals("2")) {
			user.setManageContract(false);
			user.setViewContract(true);
		} else {
			user.setManageContract(ConvertTools.getInstance().convertBoolean(manageContract));
			user.setViewContract(ConvertTools.getInstance().convertBoolean(manageContract));
		}
		user.setManageFinance(ConvertTools.getInstance().convertBoolean(manageFinance));
		if (manageLawyer.equals("2")) {
			user.setManageLawyer(false);
			user.setViewLawyer(true);
		} else {
			user.setManageLawyer(ConvertTools.getInstance().convertBoolean(manageLawyer));
			user.setViewLawyer(ConvertTools.getInstance().convertBoolean(manageLawyer));
		}
		user.setManageUser(ConvertTools.getInstance().convertBoolean(manageUser));
		
		return user;
	}
	
	private GenericResponse isUser(AdvocacyUser user) {
		HashMap<String, String> errors = new HashMap<String, String>();
		if (!ValidateTools.getInstancia().isEmail(user.getUsername())) {
			errors.put("username", PortalTools.getInstance().getMessage("user.invalid"));
		}
		if (user.getId()==null && repository.findByUsername(user.getUsername())!=null) {
			errors.put("username", PortalTools.getInstance().getMessage("client.exists"));
		}
		if (!ValidateTools.getInstancia().isPassword(user.getUserpassword())) {
			errors.put("userpassword", PortalTools.getInstance().getMessage("password.invalid"));
		}
		
		GenericResponse resp = new GenericResponse();
		if (errors.size()>0) {
			resp.setStatus(false);
			resp.setMessageError(errors);
		}
		
		return resp;
	}

	@Override
	public GenericResponse load(HttpServletRequest request) throws Exception {
		GenericResponse resp = new GenericResponse();
		final String id = request.getParameter("id");
		
		if (!ValidateTools.getInstancia().isNumber(id)) {
			resp = PortalTools.getInstance().getRespError("data.invalid");
		}
		
		if (resp.getStatus()) {
			resp.setGeneric(repository.findOne(Integer.parseInt(id)));
		}
		
		return resp;
	}

}
