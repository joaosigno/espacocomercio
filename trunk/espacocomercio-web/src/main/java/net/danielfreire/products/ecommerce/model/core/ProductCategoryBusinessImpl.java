package net.danielfreire.products.ecommerce.model.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	private static final String LBL_NAME = "name";
	@Autowired
	private transient ProductCategoryRepository categoryRepo;
	
	@Override
	public GenericResponse insert(final HttpServletRequest request) throws java.lang.Exception {
		GenericResponse resp = new GenericResponse();
		
		final String name = request.getParameter(LBL_NAME);
		final String description = request.getParameter("description");
		
		final ProductCategory category = new ProductCategory();
		category.setDescription(description);
		category.setName(name);
		category.setSite(EcommerceUtil.getInstance().getSessionAdmin(request).getSite());
		category.setKeyUrl(ConvertTools.getInstance().normalizeString(name));
		
		if (isCategory(category).size()>0) {
			resp = PortalTools.getInstance().getRespError(isCategory(category));
		} else {
			categoryRepo.save(category);
			generateMenuPortal(request);
		}
		
		return resp;
	}

	@Override
	public GenericResponse update(final HttpServletRequest request) throws java.lang.Exception {
		GenericResponse resp = new GenericResponse();
		
		final String name = request.getParameter(LBL_NAME);
		final String description = request.getParameter("description");
		final String idCategory = request.getParameter("id");
		
		final ProductCategory category = new ProductCategory(Integer.parseInt(idCategory));
		category.setDescription(description);
		category.setName(name);
		category.setSite(EcommerceUtil.getInstance().getSessionAdmin(request).getSite());
		category.setKeyUrl(ConvertTools.getInstance().normalizeString(name));
		
		if (isCategory(category).size()>0) {
			resp = PortalTools.getInstance().getRespError(isCategory(category));
		} else {
			categoryRepo.save(category);
			generateMenuPortal(request);
		}
		
		return resp;
	}

	@Override
	public GenericResponse list(final HttpServletRequest request) {
		final GenericResponse resp = new GenericResponse();
		resp.setGeneric(categoryRepo.findBySite(EcommerceUtil.getInstance().getSessionAdmin(request).getSite()));
		return resp;
	}
	
	private Map<String, String> isCategory(final ProductCategory category) {
		final HashMap<String, String> errors = new HashMap<String, String>();
		
		if (ValidateTools.getInstancia().isNullEmpty(category.getName())) {
			errors.put(LBL_NAME, PortalTools.getInstance().getMessage("name.invalid"));
		}
		if (category.getId()==null && categoryRepo.findByNameAndSite(category.getName(), category.getSite())!=null) {
			errors.put(LBL_NAME, PortalTools.getInstance().getMessage("category.existent"));
		}
		
		return errors;
	}

	@Override
	public GridResponse consult(final HttpServletRequest request) throws java.lang.Exception {
		final String page = request.getParameter("page");
		
		int pagination = 0;
		if (ValidateTools.getInstancia().isNumber(page)) {
			pagination = Integer.parseInt(page)-1;
		} 
		
		final ArrayList<GridTitleResponse> titles = new ArrayList<GridTitleResponse>();
		titles.add(PortalTools.getInstance().getRowGrid(LBL_NAME, "Nome", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("description", "Descrição", "text"));
		
		final Page<ProductCategory> pageable = categoryRepo.findBySite(EcommerceUtil.getInstance().getSessionAdmin(request).getSite(), new PageRequest(pagination, 10));
		
		return PortalTools.getInstance().getGrid(pageable.getContent(), titles, pageable.getNumber()+1, pageable.getTotalPages());
	}

	@Override
	public GenericResponse remove(final HttpServletRequest request) throws java.lang.Exception {
		GenericResponse resp = new GenericResponse();
		final ProductCategory category = categoryRepo.findOne(Integer.parseInt(request.getParameter("id")));
		
		if (category.getSite().getId() == EcommerceUtil.getInstance().getSessionAdmin(request).getSite().getId()) {
			categoryRepo.delete(category);
			generateMenuPortal(request);
		} else {
			resp = PortalTools.getInstance().getRespError("permission.invalid");
		}
		
		return resp;
	}

	@Override
	public List<ProductCategory> listSite(final String cid) throws java.lang.Exception {
		return categoryRepo.findBySite(new Site(Integer.parseInt(PortalTools.getInstance().decode(cid))));
	}

	private void generateMenuPortal(final HttpServletRequest request) throws java.lang.Exception {
		EcommerceUtil.getInstance().generateMenuPortal(
				EcommerceUtil.getInstance().getSessionAdmin(request).getSite(), 
				categoryRepo.findBySite(EcommerceUtil.getInstance().getSessionAdmin(request).getSite()));
	}

	@Override
	public Long countCategorys(final HttpServletRequest request) {
		return categoryRepo.countBySite(EcommerceUtil.getInstance().getSessionAdmin(request).getSite());
	}
}
