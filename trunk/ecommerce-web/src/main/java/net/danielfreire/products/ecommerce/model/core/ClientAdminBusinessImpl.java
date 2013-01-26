package net.danielfreire.products.ecommerce.model.core;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.ecommerce.model.domain.ClientAdmin;
import net.danielfreire.products.ecommerce.model.domain.Site;
import net.danielfreire.products.ecommerce.model.repository.ClientAdminRepository;
import net.danielfreire.products.ecommerce.util.EcommerceUtil;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.PortalTools;
import net.danielfreire.util.ValidateTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("clientAdminBusiness")
public class ClientAdminBusinessImpl implements ClientAdminBusiness {
	
	@Autowired
	private ClientAdminRepository repository;

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
							if (client.getId()==Integer.parseInt(site)) {
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
		resp.setGeneric(user.getPermission());
		
		return resp;
	}
	
}
