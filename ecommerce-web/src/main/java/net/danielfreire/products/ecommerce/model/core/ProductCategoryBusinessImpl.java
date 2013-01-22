package net.danielfreire.products.ecommerce.model.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.ecommerce.model.domain.ProductCategory;
import net.danielfreire.products.ecommerce.model.repository.ProductCategoryRepository;
import net.danielfreire.util.ConvertTools;
import net.danielfreire.util.FileUtil;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;
import net.danielfreire.util.GridTitleResponse;
import net.danielfreire.util.PortalTools;
import net.danielfreire.util.ValidateTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component("productCategoryBusiness")
public class ProductCategoryBusinessImpl implements ProductCategoryBusiness {

	@Autowired
	private ProductCategoryRepository categoryRepository;
	
	@Override
	public GenericResponse insert(HttpServletRequest request) throws Exception {
		GenericResponse resp = new GenericResponse();
		
		String siteId = (String) request.getSession().getAttribute(PortalTools.getInstance().idAdminSession);
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		
		ProductCategory category = new ProductCategory();
		category.setDescription(description);
		category.setName(name);
		category.setSiteId(Integer.parseInt(siteId));
		category.setKeyUrl(ConvertTools.getInstance().normalizeString(name));
		
		if (isCategory(category).size()>0) {
			resp = PortalTools.getInstance().getRespError(isCategory(category));
		} else {
			categoryRepository.save(category);
			FileUtil.getInstance().createFile(PortalTools.getInstance().getEcommerceProperties("location.json"), "menu"+PortalTools.getInstance().Encode(siteId)+".json", new Gson().toJson(categoryRepository.findBySiteId(Integer.parseInt(siteId))));
		}
		
		return resp;
	}

	@Override
	public GenericResponse update(HttpServletRequest request) throws Exception {
		GenericResponse resp = new GenericResponse();
		
		String siteId = (String) request.getSession().getAttribute(PortalTools.getInstance().idAdminSession);
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String id = request.getParameter("id");
		
		ProductCategory category = new ProductCategory(Integer.parseInt(id));
		category.setDescription(description);
		category.setName(name);
		category.setSiteId(Integer.parseInt(siteId));
		category.setKeyUrl(ConvertTools.getInstance().normalizeString(name));
		
		if (isCategory(category).size()>0) {
			resp = PortalTools.getInstance().getRespError(isCategory(category));
		} else {
			categoryRepository.save(category);
			FileUtil.getInstance().createFile(PortalTools.getInstance().getEcommerceProperties("location.json"), "menu"+PortalTools.getInstance().Encode(siteId)+".json", new Gson().toJson(categoryRepository.findBySiteId(Integer.parseInt(siteId))));
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
		resp.setGeneric(categoryRepository.findBySiteId(Integer.parseInt(request.getSession().getAttribute(PortalTools.getInstance().idAdminSession).toString())));
		return resp;
	}
	
	private HashMap<String, String> isCategory(ProductCategory category) {
		HashMap<String, String> errors = new HashMap<String, String>();
		
		if (ValidateTools.getInstancia().isNullEmpty(category.getName())) {
			errors.put("name", PortalTools.getInstance().getMessage("name.invalid"));
		}
		if (category.getId()==null && categoryRepository.findByNameAndSiteId(category.getName(), category.getSiteId())!=null) {
			errors.put("name", PortalTools.getInstance().getMessage("category.existent"));
		}
		
		return errors;
	}

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
		title.setId("description");
		title.setTitle("Descrição");
		title.setType("text");
		titles.add(title);
		
		Page<ProductCategory> pageable = categoryRepository.findBySiteId(Integer.parseInt(siteId), new PageRequest(pagination, 10));
		
		GridResponse grid = new GridResponse();
		grid.setRows(pageable.getContent());
		grid.setTitles(titles);
		grid.setPage(pageable.getNumber()+1);
		grid.setTotalPages(pageable.getTotalPages());
		
		return grid;
	}

	@Override
	public GenericResponse remove(HttpServletRequest request) throws Exception {
		final String siteId = request.getParameter("id");
		
		categoryRepository.delete(Integer.parseInt(siteId));
		
		FileUtil.getInstance().createFile(PortalTools.getInstance().getEcommerceProperties("location.json"), "menu"+PortalTools.getInstance().Encode(siteId)+".json", new Gson().toJson(categoryRepository.findBySiteId(Integer.parseInt(siteId))));
		
		return new GenericResponse();
	}

	@Override
	public List<ProductCategory> listSite(String cid) throws Exception {
		return categoryRepository.findBySiteId(Integer.parseInt(PortalTools.getInstance().Decode(cid)));
	}

}
