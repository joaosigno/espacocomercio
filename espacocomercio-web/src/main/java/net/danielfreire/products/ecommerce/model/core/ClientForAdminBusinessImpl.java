package net.danielfreire.products.ecommerce.model.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.ecommerce.model.domain.ClientEcommerce;
import net.danielfreire.products.ecommerce.model.domain.Site;
import net.danielfreire.products.ecommerce.model.repository.ClientEcommerceRepository;
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

@Component("clientForAdminBusiness")
public class ClientForAdminBusinessImpl implements ClientForAdminBusiness {
	
	private static final String LBL_NAME = "name";
	private static final String LBL_USER = "user";
	private static final String LBL_PASSWORD = "password";
	private static final String LBL_STREET = "addressStreet";
	private static final String LBL_ADD_NUMBER = "addressNumber";
	private static final String LBL_ZIPCODE = "addressZipcode";
	private static final String LBL_CITY = "addressCity";
	private static final String LBL_ERRORS = "errors";
	private static final String LBL_CLIENT = "client";
	private static final String LBL_TEXT = "text";
	
	@Autowired
	private transient ClientEcommerceRepository repository;
	@Autowired
	private transient ClientEcommerceRepository clientRepository;

	@SuppressWarnings("unchecked")
	@Override
	public GenericResponse updateAdmin(final HttpServletRequest request)
			throws java.lang.Exception {
		GenericResponse resp = new GenericResponse();
		
		final Map<String, Object> map = getClient(request);
		
		if (map.get(LBL_ERRORS)==null) {
			repository.save((ClientEcommerce) map.get(LBL_CLIENT));
		} else {
			resp = PortalTools.getInstance().getRespError((HashMap<String, String>) map.get(LBL_ERRORS));
		}
		
		return resp;
	}
	
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
		
		if (addressZipcode!=null) {
			addressZipcode = addressZipcode.replace("-", "");
		}
		
		site = EcommerceUtil.getInstance().getSessionAdmin(request).getSite();
		active = request.getParameter("active");
		
		final HashMap<String, String> errors = new HashMap<String, String>();
		errors.putAll(ValidateTools.getInstancia().validateGeneric("name.invalid", ValidateTools.getInstancia().isNullEmpty(name)));
		errors.putAll(ValidateTools.getInstancia().validateGeneric("email.invalid", !ValidateTools.getInstancia().isEmail(user)));
		errors.putAll(ValidateTools.getInstancia().validateGeneric("password.invalid", !ValidateTools.getInstancia().isPassword(password)));
		errors.putAll(ValidateTools.getInstancia().validateGeneric("addressStreet.invalid", ValidateTools.getInstancia().isNullEmpty(addressStreet)));
		errors.putAll(ValidateTools.getInstancia().validateGeneric("addressNumber.invalid", ValidateTools.getInstancia().isNullEmpty(addressNumber)));
		errors.putAll(ValidateTools.getInstancia().validateGeneric("addressZipcode.invalid", !ValidateTools.getInstancia().isCep(addressZipcode)));
		errors.putAll(ValidateTools.getInstancia().validateGeneric("addressCity.invalid", ValidateTools.getInstancia().isNullEmpty(addressCity)));
		errors.putAll(ValidateTools.getInstancia().validateGeneric("email.exists", ValidateTools.getInstancia().isNumber(idClient) && repository.findBySiteAndUser(site, user)!=null && repository.findBySiteAndUser(site, user).getId()!=Integer.parseInt(idClient)));
		
		if (ValidateTools.getInstancia().isNullEmpty(newsletter)) {
			newsletter = "false";
		}
		if (ValidateTools.getInstancia().isNullEmpty(idClient) || ValidateTools.getInstancia().isNullEmpty(active)) {
			active = "false";
		} 
		
		final HashMap<String, Object> resp = new HashMap<String, Object>();
		if (errors.isEmpty()) {
			final ClientEcommerce client = new ClientEcommerce(Integer.parseInt(idClient));
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

	@Override
	public Long countClients(final HttpServletRequest request) {
		return repository.countBySite(EcommerceUtil.getInstance().getSessionAdmin(request).getSite());
	}
	
	@Override
	public GridResponse consult(final HttpServletRequest request) throws java.lang.Exception {
		final String page = request.getParameter("page");
		
		int pagination = 0;
		if (ValidateTools.getInstancia().isNumber(page)) {
			pagination = Integer.parseInt(page)-1;
		} 
		
		final ArrayList<GridTitleResponse> titles = new ArrayList<GridTitleResponse>();
		titles.add(PortalTools.getInstance().getRowGrid(LBL_NAME, "Nome", LBL_TEXT));
		titles.add(PortalTools.getInstance().getRowGrid(LBL_USER, "E-mail", LBL_TEXT));
		titles.add(PortalTools.getInstance().getRowGrid(LBL_PASSWORD, "Senha", LBL_TEXT));
		titles.add(PortalTools.getInstance().getRowGrid("newsletter", "Notícias", LBL_TEXT));
		titles.add(PortalTools.getInstance().getRowGrid(LBL_STREET, "Rua", LBL_TEXT));
		titles.add(PortalTools.getInstance().getRowGrid(LBL_ADD_NUMBER, "Número", LBL_TEXT));
		titles.add(PortalTools.getInstance().getRowGrid(LBL_ZIPCODE, "CEP", LBL_TEXT));
		titles.add(PortalTools.getInstance().getRowGrid(LBL_CITY, "Cidade", LBL_TEXT));
		titles.add(PortalTools.getInstance().getRowGrid("addressComplement", "Complemento", LBL_TEXT));
		titles.add(PortalTools.getInstance().getRowGrid("active", "Ativo", LBL_TEXT));
		
		final Page<ClientEcommerce> pageable = repository.findBySite(EcommerceUtil.getInstance().getSessionAdmin(request).getSite(), new PageRequest(pagination, 10));
		
		return PortalTools.getInstance().getGrid(pageable.getContent(), titles, pageable.getNumber()+1, pageable.getTotalPages());
	}

	@Override
	public GenericResponse list(final HttpServletRequest request) throws java.lang.Exception {
		final GenericResponse resp = new GenericResponse();
		resp.setGenericList(repository.findBySite(EcommerceUtil.getInstance().getSessionAdmin(request).getSite()));
		
		return resp;
	}

	@Override
	public Integer[] getListClientByLastMonths(final HttpServletRequest request) {
		final Calendar dtInit = Calendar.getInstance();
		dtInit.add(Calendar.MONTH, -6);
		final Calendar dtNow = Calendar.getInstance();
		
		final List<ClientEcommerce> clients = clientRepository.findBySiteAndCreationDateGreaterThanAndCreationDateLessThan(EcommerceUtil.getInstance().getSessionAdmin(request).getSite(), dtInit, dtNow);
		int mes1 = 0, mes2 = 0, mes3 = 0, mes4 = 0, mes5 = 0, mes6 = 0, mes7 = 0;
		for (ClientEcommerce c : clients) {
			final Calendar now = Calendar.getInstance();
			if (now.get(Calendar.MONTH) == c.getCreationDate().get(Calendar.MONTH)) {
				mes7++;
			}
			now.add(Calendar.MONTH, -1);
			if (now.get(Calendar.MONTH) == c.getCreationDate().get(Calendar.MONTH)) {
				mes6++;
			}
			now.add(Calendar.MONTH, -1);
			if (now.get(Calendar.MONTH) == c.getCreationDate().get(Calendar.MONTH)) {
				mes5++;
			}
			now.add(Calendar.MONTH, -1);
			if (now.get(Calendar.MONTH) == c.getCreationDate().get(Calendar.MONTH)) {
				mes4++;
			}
			now.add(Calendar.MONTH, -1);
			if (now.get(Calendar.MONTH) == c.getCreationDate().get(Calendar.MONTH)) {
				mes3++;
			}
			now.add(Calendar.MONTH, -1);
			if (now.get(Calendar.MONTH) == c.getCreationDate().get(Calendar.MONTH)) {
				mes2++;
			}
			now.add(Calendar.MONTH, -1);
			if (now.get(Calendar.MONTH) == c.getCreationDate().get(Calendar.MONTH)) {
				mes1++;
			}
		}
		return new Integer[]{mes1, mes2, mes3, mes4, mes5, mes6, mes7};
	}

	@Override
	public Long countLastClientsRegistry(final HttpServletRequest request) {
		final Calendar dtInit = Calendar.getInstance();
		dtInit.add(Calendar.DAY_OF_MONTH, -2);
		
		return clientRepository.countBySiteAndCreationDateGreaterThan(EcommerceUtil.getInstance().getSessionAdmin(request).getSite(), dtInit);
	}
}
