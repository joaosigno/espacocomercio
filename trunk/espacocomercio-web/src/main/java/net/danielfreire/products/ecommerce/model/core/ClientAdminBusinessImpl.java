package net.danielfreire.products.ecommerce.model.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.ecommerce.model.domain.ClientAdmin;
import net.danielfreire.products.ecommerce.model.domain.ClientEcommerce;
import net.danielfreire.products.ecommerce.model.domain.Order;
import net.danielfreire.products.ecommerce.model.domain.Site;
import net.danielfreire.products.ecommerce.model.repository.ClientAdminRepository;
import net.danielfreire.products.ecommerce.model.repository.ClientEcommerceRepository;
import net.danielfreire.products.ecommerce.model.repository.OrderRepository;
import net.danielfreire.products.ecommerce.util.EcommerceUtil;
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

@Component("clientAdminBusiness")
public class ClientAdminBusinessImpl implements ClientAdminBusiness {
	
	@Autowired
	private ClientAdminRepository repository;
	@Autowired
	private ClientEcommerceRepository clientRepository;
	@Autowired
	private OrderRepository orderRepository;

	@Override
	public GenericResponse login(HttpServletRequest request) throws Exception {
		GenericResponse resp = new GenericResponse();
		
		final String user = request.getParameter("u");
		final String password = request.getParameter("p");
		final String site = request.getParameter("s");
		
		if (!ValidateTools.getInstancia().isEmail(user) || !ValidateTools.getInstancia().isPassword(password)) {
			resp = PortalTools.getInstance().getRespError("login.invalid");
		}
		
		if (resp.getStatus()) {
			List<ClientAdmin> listClient = repository.findByUserAndPassword(user, password);
			if (listClient!=null && listClient.size()>0) {
				if (listClient.size()>1) {
					if (ValidateTools.getInstancia().isNumber(site)) {
						for (ClientAdmin client : listClient) {
							if (client.getSite().getId()==Integer.parseInt(site)) {
								EcommerceUtil.getInstance().sessionAdminUser(request, client);
							}
						}
					} else {
						List<Site> sites = new ArrayList<Site>();
						for (ClientAdmin client : listClient) {
							sites.add(client.getSite());
						}
						resp.setGeneric(sites);
					}
				} else {
					EcommerceUtil.getInstance().sessionAdminUser(request, listClient.get(0));
				}
			} else {
				resp = PortalTools.getInstance().getRespError("login.invalid");
			}
		}
		
		return resp;
	}

	@Override
	public GenericResponse menu(HttpServletRequest request) throws Exception {
		final ClientAdmin user = EcommerceUtil.getInstance().getSessionAdmin(request);
		
		GenericResponse resp = new GenericResponse();
		resp.setGeneric( new String[] { user.getPermission().toString(), user.getSite().getName(), user.getUser(), ConvertTools.getInstance().normalizeString(user.getSite().getName()) });
		
		return resp;
	}

	@Override
	public GridResponse consult(HttpServletRequest request) throws Exception {
		String page = request.getParameter("page");
		
		int pagination = 0;
		if (ValidateTools.getInstancia().isNumber(page)) {
			pagination = Integer.parseInt(page)-1;
		} 
		
		ArrayList<GridTitleResponse> titles = new ArrayList<GridTitleResponse>();
		titles.add(PortalTools.getInstance().getRowGrid("user", "Usuário", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("password", "Senha", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("permission", "Permissão", "text"));
		
		Page<ClientAdmin> pageable = null;
		if (EcommerceUtil.getInstance().getSessionAdmin(request).getPermission()==2) {
			pageable = repository.findBySiteAndPermissionNot(EcommerceUtil.getInstance().getSessionAdmin(request).getSite(), 1, new PageRequest(pagination, 10));
		} else if (EcommerceUtil.getInstance().getSessionAdmin(request).getPermission()==1) {
			pageable = repository.findBySite(EcommerceUtil.getInstance().getSessionAdmin(request).getSite(), new PageRequest(pagination, 10));
		}
		
		return PortalTools.getInstance().getGrid(pageable.getContent(), titles, pageable.getNumber()+1, pageable.getTotalPages());
	}

	@SuppressWarnings("unchecked")
	@Override
	public GenericResponse save(HttpServletRequest request) throws Exception {
		Map<String, Object> map = getUser(request);
		
		GenericResponse resp = new GenericResponse();
		if (map.get("errors")!=null) {
			resp = PortalTools.getInstance().getRespError((HashMap<String, String>) map.get("errors"));
		} else {
			repository.save((ClientAdmin) map.get("user"));
		}
		
		return resp;
	}
	
	private Map<String, Object> getUser(HttpServletRequest request) {
		String user = request.getParameter("user");
		String password = request.getParameter("password");
		String permission = request.getParameter("permission");
		String id = request.getParameter("id");
		
		Map <String, String> errors = new HashMap<String, String>();
		if (!ValidateTools.getInstancia().isEmail(user)) {
			errors.put("user", PortalTools.getInstance().getMessage("user.invalid"));
		}
		if ((ValidateTools.getInstancia().isNumber(id) && !ValidateTools.getInstancia().isNullEmpty(password)) || !ValidateTools.getInstancia().isNumber(id)) {
			if (!ValidateTools.getInstancia().isPassword(password)) {
				errors.put("password", PortalTools.getInstance().getMessage("password.invalid"));
			}
		}
		if (!ValidateTools.getInstancia().isNumber(permission)) {
			errors.put("permission", PortalTools.getInstance().getMessage("data.invalid"));
		}
		if (errors.size()==0) {
			ClientAdmin userExist = repository.findBySiteAndUser(EcommerceUtil.getInstance().getSessionAdmin(request).getSite(), user);
			if (userExist!=null && !ValidateTools.getInstancia().isNumber(id)) {
				errors.put("user", PortalTools.getInstance().getMessage("client.exists"));
			} else if (ValidateTools.getInstancia().isNumber(id) && userExist!=null && userExist.getId()!=Integer.parseInt(id)) {
				errors.put("user", PortalTools.getInstance().getMessage("client.exists"));
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (errors.size()==0) {
			ClientAdmin u = new ClientAdmin();
			if (ValidateTools.getInstancia().isNumber(id)) {
				u = repository.findOne(Integer.parseInt(id));
			}
			if (ValidateTools.getInstancia().isNotnull(password)) {
				u.setPassword(password);
			}
			u.setPermission(Integer.parseInt(permission));
			u.setSite(EcommerceUtil.getInstance().getSessionAdmin(request).getSite());
			u.setUser(user);
			
			map.put("user", u);
		} else {
			map.put("errors", errors);
		}
		
		return map;
	}

	@Override
	public GenericResponse load(HttpServletRequest request) throws Exception {
		GenericResponse resp = new GenericResponse();
	
		ClientAdmin client = repository.findOne(Integer.parseInt(request.getParameter("id")));
		if (client.getSite().getId()==EcommerceUtil.getInstance().getSessionAdmin(request).getSite().getId()) {
			client.setPassword("");
			resp.setGeneric(client);
		} else {
			resp = PortalTools.getInstance().getRespError("permission.invalid");
		}
		
		return resp;
	}

	@Override
	public GenericResponse remove(HttpServletRequest request) throws Exception {
		GenericResponse resp = new GenericResponse();
		
		ClientAdmin client = repository.findOne(Integer.parseInt(request.getParameter("id")));
		if (client.getSite().getId() == EcommerceUtil.getInstance().getSessionAdmin(request).getSite().getId() && client.getId() != EcommerceUtil.getInstance().getSessionAdmin(request).getId()) {
			repository.delete(client);
		} else {
			resp = PortalTools.getInstance().getRespError("permission.invalid");
		}
		
		return resp;
	}

	@Override
	public GenericResponse home(HttpServletRequest request) throws Exception {
		final ClientAdmin user = EcommerceUtil.getInstance().getSessionAdmin(request);
		final List<ClientEcommerce> clients = clientRepository.findBySite(user.getSite());
		
		int qtdOrder = 0;
		int mes1 = 0, mes2 = 0, mes3 = 0, mes4 = 0, mes5 = 0, mes6 = 0, mes7 = 0;
		int o1 = 0, o2 = 0, o3 = 0, o4 = 0, o5 = 0, o6 = 0, o7 = 0;
		for (ClientEcommerce c : clients) {
			List<Order> orders = orderRepository.findByClient(c);
			qtdOrder += orders.size();
			
			Calendar now = Calendar.getInstance();
			if (now.get(Calendar.MONTH) == c.getCreationDate().get(Calendar.MONTH))
				mes7++;
			now.add(Calendar.MONTH, -1);
			if (now.get(Calendar.MONTH) == c.getCreationDate().get(Calendar.MONTH))
				mes6++;
			now.add(Calendar.MONTH, -1);
			if (now.get(Calendar.MONTH) == c.getCreationDate().get(Calendar.MONTH))
				mes5++;
			now.add(Calendar.MONTH, -1);
			if (now.get(Calendar.MONTH) == c.getCreationDate().get(Calendar.MONTH))
				mes4++;
			now.add(Calendar.MONTH, -1);
			if (now.get(Calendar.MONTH) == c.getCreationDate().get(Calendar.MONTH))
				mes3++;
			now.add(Calendar.MONTH, -1);
			if (now.get(Calendar.MONTH) == c.getCreationDate().get(Calendar.MONTH))
				mes2++;
			now.add(Calendar.MONTH, -1);
			if (now.get(Calendar.MONTH) == c.getCreationDate().get(Calendar.MONTH))
				mes1++;
			
			for (Order o : orders) {
				Calendar now2 = Calendar.getInstance();
				if (now2.get(Calendar.MONTH) == o.getDateCreate().get(Calendar.MONTH))
					o7++;
				now2.add(Calendar.MONTH, -1);
				if (now2.get(Calendar.MONTH) == o.getDateCreate().get(Calendar.MONTH))
					o6++;
				now2.add(Calendar.MONTH, -1);
				if (now2.get(Calendar.MONTH) == o.getDateCreate().get(Calendar.MONTH))
					o5++;
				now2.add(Calendar.MONTH, -1);
				if (now2.get(Calendar.MONTH) == o.getDateCreate().get(Calendar.MONTH))
					o4++;
				now2.add(Calendar.MONTH, -1);
				if (now2.get(Calendar.MONTH) == o.getDateCreate().get(Calendar.MONTH))
					o3++;
				now2.add(Calendar.MONTH, -1);
				if (now2.get(Calendar.MONTH) == o.getDateCreate().get(Calendar.MONTH))
					o2++;
				now2.add(Calendar.MONTH, -1);
				if (now2.get(Calendar.MONTH) == o.getDateCreate().get(Calendar.MONTH))
					o1++;
			}
		}
		
		final Integer[] arrayClients = {mes1, mes2, mes3, mes4, mes5, mes6, mes7};
		final Integer[] arrayOrders = {o1, o2, o3, o4, o5, o6, o7};
		
		GenericResponse resp = new GenericResponse();
		resp.setGeneric(new Object[] {clients.size(), qtdOrder, ConvertTools.getInstance().normalizeString(user.getSite().getName()), arrayClients, arrayOrders});
		
		return resp;
	}
	
}
