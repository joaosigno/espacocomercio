package net.danielfreire.products.ecommerce.model.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.ecommerce.model.domain.ProductCategory;
import net.danielfreire.products.ecommerce.model.domain.Site;
import net.danielfreire.products.ecommerce.model.repository.ProductCategoryRepository;
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

@Component("productCategoryBusiness")
public class ProductCategoryBusinessImpl implements ProductCategoryBusiness {

	@Autowired
	private ProductCategoryRepository categoryRepository;
	
	@Override
	public GenericResponse insert(HttpServletRequest request) throws Exception {
		GenericResponse resp = new GenericResponse();
		
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		
		ProductCategory category = new ProductCategory();
		category.setDescription(description);
		category.setName(name);
		category.setSite(EcommerceUtil.getInstance().getSessionAdmin(request).getSite());
		category.setKeyUrl(ConvertTools.getInstance().normalizeString(name));
		
		if (isCategory(category).size()>0) {
			resp = PortalTools.getInstance().getRespError(isCategory(category));
		} else {
			categoryRepository.save(category);
			generateMenuPortal(request);
		}
		
		return resp;
	}

	@Override
	public GenericResponse update(HttpServletRequest request) throws Exception {
		GenericResponse resp = new GenericResponse();
		
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String id = request.getParameter("id");
		
		ProductCategory category = new ProductCategory(Integer.parseInt(id));
		category.setDescription(description);
		category.setName(name);
		category.setSite(EcommerceUtil.getInstance().getSessionAdmin(request).getSite());
		category.setKeyUrl(ConvertTools.getInstance().normalizeString(name));
		
		if (isCategory(category).size()>0) {
			resp = PortalTools.getInstance().getRespError(isCategory(category));
		} else {
			categoryRepository.save(category);
			generateMenuPortal(request);
		}
		
		return resp;
	}

	@Override
	public GenericResponse load(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenericResponse list(HttpServletRequest request) {
		GenericResponse resp = new GenericResponse();
		resp.setGeneric(categoryRepository.findBySite(EcommerceUtil.getInstance().getSessionAdmin(request).getSite()));
		return resp;
	}
	
	private HashMap<String, String> isCategory(ProductCategory category) {
		HashMap<String, String> errors = new HashMap<String, String>();
		
		if (ValidateTools.getInstancia().isNullEmpty(category.getName())) {
			errors.put("name", PortalTools.getInstance().getMessage("name.invalid"));
		}
		if (category.getId()==null && categoryRepository.findByNameAndSite(category.getName(), category.getSite())!=null) {
			errors.put("name", PortalTools.getInstance().getMessage("category.existent"));
		}
		
		return errors;
	}

	@Override
	public GridResponse consult(HttpServletRequest request) throws Exception {
		String page = request.getParameter("page");
		
		int pagination = 0;
		if (ValidateTools.getInstancia().isNumber(page)) {
			pagination = Integer.parseInt(page)-1;
		} 
		
		ArrayList<GridTitleResponse> titles = new ArrayList<GridTitleResponse>();
		titles.add(PortalTools.getInstance().getRowGrid("name", "Nome", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("description", "Descrição", "text"));
		
		Page<ProductCategory> pageable = categoryRepository.findBySite(EcommerceUtil.getInstance().getSessionAdmin(request).getSite(), new PageRequest(pagination, 10));
		
		return PortalTools.getInstance().getGrid(pageable.getContent(), titles, pageable.getNumber()+1, pageable.getTotalPages());
	}

	@Override
	public GenericResponse remove(HttpServletRequest request) throws Exception {
		ProductCategory category = categoryRepository.findOne(Integer.parseInt(request.getParameter("id")));
		
		if (category.getSite().getId() == EcommerceUtil.getInstance().getSessionAdmin(request).getSite().getId()) {
			categoryRepository.delete(category);
			generateMenuPortal(request);
		} else {
			return PortalTools.getInstance().getRespError("permission.invalid");
		}
		
		return new GenericResponse();
	}

	@Override
	public List<ProductCategory> listSite(String cid) throws Exception {
		return categoryRepository.findBySite(new Site(Integer.parseInt(PortalTools.getInstance().decode(cid))));
	}

	private void generateMenuPortal(HttpServletRequest request) throws Exception {
		EcommerceUtil.getInstance().generateMenuPortal(
				EcommerceUtil.getInstance().getSessionAdmin(request).getSite(), 
				categoryRepository.findBySite(EcommerceUtil.getInstance().getSessionAdmin(request).getSite()));
	}
}
