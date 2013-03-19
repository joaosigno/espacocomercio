package net.danielfreire.products.advocacy.model.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.advocacy.model.domain.AdvocacyOffice;
import net.danielfreire.products.advocacy.model.repository.AdvocacyOfficeRepository;
import net.danielfreire.products.advocacy.util.AdvocacyUtil;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;
import net.danielfreire.util.GridTitleResponse;
import net.danielfreire.util.PortalTools;
import net.danielfreire.util.ValidateTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component("advocacyOfficeBusiness")
public class AdvocacyOfficeBusinessImpl implements AdvocacyOfficeBusiness {
	
	@Autowired
	private AdvocacyOfficeRepository repository;

	@Override
	public GenericResponse manage(HttpServletRequest request) throws Exception {
		AdvocacyOffice office = getOffice(request);
		GenericResponse resp = isOffice(office);
		
		if (resp.getStatus()) {
			repository.save(office);
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
		titles.add(PortalTools.getInstance().getRowGrid("name", "Nome", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("subtitle", "Sub-título", "text"));
		
		Page<AdvocacyOffice> pageable = repository.findAll(new PageRequest(pagination, 10));
		
		return PortalTools.getInstance().getGrid(pageable.getContent(), titles, pageable.getNumber()+1, pageable.getTotalPages());
	}

	private AdvocacyOffice getOffice(HttpServletRequest request) {
		final String name = request.getParameter("name");
		final String subtitle = request.getParameter("subtitle");
		final String id = request.getParameter("id");
		
		AdvocacyOffice office = new AdvocacyOffice();
		if (ValidateTools.getInstancia().isNumber(id)) {
			office = new AdvocacyOffice(Integer.parseInt(id));
		}
		office.setName(name);
		office.setSubtitle(subtitle);
		
		return office;
	}
	
	private GenericResponse isOffice(AdvocacyOffice office) {
		HashMap<String, String> errors = new HashMap<String, String>();
		if (ValidateTools.getInstancia().isNullEmpty(office.getName())) {
			errors.put("name", PortalTools.getInstance().getMessage("name.invalid"));
		}
		
		GenericResponse resp = new GenericResponse();
		if (errors.size()>0) {
			resp.setStatus(false);
			resp.setMessageError(errors);
		}
		
		return resp;
	}

	@Override
	public GenericResponse listBySession(HttpServletRequest request)
			throws Exception {
		GenericResponse resp = new GenericResponse();
		
		List<AdvocacyOffice> list = null;
		if (AdvocacyUtil.getInstancia().getSessionUser(request).getId()==0) {
			list = repository.findAll();
		} else {
			list = new ArrayList<AdvocacyOffice>();
			list.add(AdvocacyUtil.getInstancia().getSessionUser(request).getAdvocacyOffice());
		}
		
		resp.setGeneric(list);
		
		return resp;
	}

}
