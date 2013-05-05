package net.danielfreire.products.ecommerce.model.core;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.ecommerce.model.domain.Product;
import net.danielfreire.products.ecommerce.model.domain.Site;
import net.danielfreire.products.ecommerce.model.repository.CategoryHasProductRepository;
import net.danielfreire.products.ecommerce.model.repository.ProductCategoryRepository;
import net.danielfreire.products.ecommerce.model.repository.ProductRepository;
import net.danielfreire.products.ecommerce.util.EcommerceUtil;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;
import net.danielfreire.util.PortalTools;
import net.danielfreire.util.ValidateTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;


@Component("productBusiness")
public class ProductBusinessImpl implements ProductBusiness {
	
	private static final String LBL_UNCHEKED = "unchecked";
	
	@Autowired
	private transient ProductRepository repository;
	@Autowired
	private transient CategoryHasProductRepository chpRepository;
	@Autowired
	private transient ProductCategoryRepository categoryRepo;

	

	@Override
	public GenericResponse load(final HttpServletRequest request) {
		GenericResponse resp = new GenericResponse();
		final Product product = repository.findOne(Integer.parseInt(request.getParameter("id")));
		if (product.getSite().getId() == EcommerceUtil.getInstance().getSessionAdmin(request).getSite().getId()) {
			resp.setGeneric(repository.findOne(Integer.parseInt(request.getParameter("id"))));
			resp.setGenericList(chpRepository.findByProductId(Integer.parseInt(request.getParameter("id"))));
		} else {
			resp = PortalTools.getInstance().getRespError("permission.invalid");
		}
		
		return resp;
	}

	@Override
	public GridResponse listToPortal(final String sid, final String categoryId,
			final String search, final String page) throws java.lang.Exception {
		final String siteId = PortalTools.getInstance().decode(sid);
		
		int pagination = 0;
		if (ValidateTools.getInstancia().isNumber(page)) {
			pagination = Integer.parseInt(page)-1;
		} 
		
		Page<?> pageable = null;
		if (ValidateTools.getInstancia().isNullEmpty(categoryId) && ValidateTools.getInstancia().isNullEmpty(search)) {
			pageable = repository.findBySite(new Site(Integer.parseInt(siteId)), new PageRequest(pagination, 9, Direction.DESC, "datecreate"));
		} else if (ValidateTools.getInstancia().isNotnull(categoryId)) {
			pageable = chpRepository.findByCategory(categoryRepo.findByKeyUrlAndSite(categoryId, new Site(Integer.parseInt(siteId))), new PageRequest(pagination, 9, Direction.DESC, "product.datecreate"));
		} else if (!ValidateTools.getInstancia().isNullEmpty(search)) {
			pageable = repository.findBySiteAndDescriptionLike(new Site(Integer.parseInt(siteId)), "%"+search+"%", new PageRequest(pagination, 9, Direction.DESC, "datecreate"));
		}
		
		final GridResponse grid = new GridResponse();
		grid.setRows(pageable.getContent());
		grid.setPage(pageable.getNumber()+1);
		grid.setTotalPages(pageable.getTotalPages());
		
		return grid;
	}

	@Override
	public GenericResponse load(final String siteId, final String productId)
			throws java.lang.Exception {
		final GenericResponse resp = new GenericResponse();
		
		if (ValidateTools.getInstancia().isNullEmpty(productId) || ValidateTools.getInstancia().isNullEmpty(siteId) || !ValidateTools.getInstancia().isNumber(PortalTools.getInstance().decode(siteId))) {
			resp.setStatus(false);
		} else {
			resp.setGeneric(repository.findByKeyUrlAndSite(productId, new Site(Integer.parseInt(PortalTools.getInstance().decode(siteId)))));
		}
		
		return resp;
	}

	@SuppressWarnings(LBL_UNCHEKED)
	@Override
	public GenericResponse addCart(final HttpServletRequest request) throws java.lang.Exception {
		final Integer productId = Integer.parseInt(request.getParameter("pid"));
		
		ArrayList<Product> list = (ArrayList<Product>) request.getSession().getAttribute(PortalTools.ID_CART_SESSION);
		
		if (list==null || list.isEmpty()) {
			list = new ArrayList<Product>();
			list.add(new Product(productId, Integer.parseInt(PortalTools.getInstance().decode(request.getParameter("sid")))));
		} else {
			boolean exist = false;
			for (Product p : list) {
				if (p.getId()==productId) {
					exist = true;
				}
			}
			
			if (!exist) {
				list.add(new Product(productId, Integer.parseInt(PortalTools.getInstance().decode(request.getParameter("sid")))));
			}
		}
		
		request.getSession().setAttribute(PortalTools.ID_CART_SESSION, list);
		
		return new GenericResponse();
	}

	@SuppressWarnings(LBL_UNCHEKED)
	@Override
	public GenericResponse myCart(final HttpServletRequest request) throws java.lang.Exception {
		final GenericResponse resp = new GenericResponse();
		
		final ArrayList<Product> list = new ArrayList<Product>();
		final ArrayList<Product> listSession = (ArrayList<Product>) request.getSession().getAttribute(PortalTools.ID_CART_SESSION);
		for (Product p : listSession) {
			if (p.getSite().getId()==Integer.parseInt(PortalTools.getInstance().decode(request.getParameter("sid")))) {
				list.add(repository.findOne(p.getId()));
			}
		}
		
		final HashMap<String, Object> ret = new HashMap<String, Object>();
		ret.put("cart", list);
		
		resp.setGeneric(ret);
		
		return resp;
	}
	
	@SuppressWarnings(LBL_UNCHEKED)
	@Override
	public GenericResponse removeItemCart(final HttpServletRequest request) throws java.lang.Exception {
		final Integer idProduct = Integer.parseInt(request.getParameter("id"));
		
		final ArrayList<Product> newlist = new ArrayList<Product>();
		final ArrayList<Product> list = (ArrayList<Product>) request.getSession().getAttribute(PortalTools.ID_CART_SESSION);
		for (Product p : list) {
			if (p.getId()!=idProduct) {
				newlist.add(p);
			}
		}
		
		request.getSession().setAttribute(PortalTools.ID_CART_SESSION, newlist);
		
		return new GenericResponse();
	}
	
}
