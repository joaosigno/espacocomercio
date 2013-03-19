package net.danielfreire.products.advocacy.model.core;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.advocacy.model.domain.FinanceCategory;
import net.danielfreire.products.advocacy.model.repository.FinanceCategoryRepository;
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

@Component("financeCategoryBusiness")
public class FinanceCategoryBusinessImpl implements FinanceCategoryBusiness {
	
	@Autowired
	private FinanceCategoryRepository repository;

	@Override
	public GridResponse consult(HttpServletRequest request) {
		String page = request.getParameter("page");
		
		int pagination = 0;
		
		if (ValidateTools.getInstancia().isNumber(page)) {
			pagination = Integer.parseInt(page)-1;
		} 
		
		ArrayList<GridTitleResponse> titles = new ArrayList<GridTitleResponse>();
		titles.add(PortalTools.getInstance().getRowGrid("title", "Categoria", "text"));
		
		Page<FinanceCategory> pageable = null;
		pageable = repository.findByAdvocacyOffice(AdvocacyUtil.getInstancia().getSessionUser(request).getAdvocacyOffice().getId(), new PageRequest(pagination, 10));
		
		return PortalTools.getInstance().getGrid(pageable.getContent(), titles, pageable.getNumber()+1, pageable.getTotalPages());
	}

	@Override
	public GenericResponse manage(HttpServletRequest request) throws Exception {
		GenericResponse resp = new GenericResponse();
		
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		
		if (ValidateTools.getInstancia().isNullEmpty(title)) {
			resp = PortalTools.getInstance().getRespError("name.invalid");
		}
		
		if (resp.getStatus()) {
			FinanceCategory category = new FinanceCategory();
			if (ValidateTools.getInstancia().isNumber(id)) {
				category.setId(Integer.parseInt(id));
			}
			category.setTitle(title);
			category.setAdvocacyOffice(AdvocacyUtil.getInstancia().getSessionUser(request).getId());
			
			repository.save(category);
		}
		
		return resp;
	}

	@Override
	public GenericResponse remove(HttpServletRequest request) throws Exception {
		GenericResponse resp = new GenericResponse();
		
		final String id = request.getParameter("id");
		if (!ValidateTools.getInstancia().isNumber(id)) {
			resp = PortalTools.getInstance().getRespError("data.invalid");
		}
		
		if (resp.getStatus()) {
			FinanceCategory category = new FinanceCategory();
			category.setId(Integer.parseInt(id));
			
			repository.delete(category);
		}
		
		return resp;
	}
	
	@Override
	public GenericResponse list(HttpServletRequest request) throws Exception {
		GenericResponse resp = new GenericResponse();
		resp.setGeneric(repository.findByAdvocacyOffice(AdvocacyUtil.getInstancia().getSessionUser(request).getAdvocacyOffice().getId()));
		return resp;
	}
}
