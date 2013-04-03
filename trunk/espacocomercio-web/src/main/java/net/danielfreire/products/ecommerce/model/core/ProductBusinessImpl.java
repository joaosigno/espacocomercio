package net.danielfreire.products.ecommerce.model.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.danielfreire.products.ecommerce.model.domain.CategoryHasProduct;
import net.danielfreire.products.ecommerce.model.domain.Product;
import net.danielfreire.products.ecommerce.model.domain.ProductCategory;
import net.danielfreire.products.ecommerce.model.domain.Site;
import net.danielfreire.products.ecommerce.model.repository.CategoryHasProductRepository;
import net.danielfreire.products.ecommerce.model.repository.ProductCategoryRepository;
import net.danielfreire.products.ecommerce.model.repository.ProductRepository;
import net.danielfreire.products.ecommerce.util.EcommerceUtil;
import net.danielfreire.util.ConvertTools;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;
import net.danielfreire.util.GridTitleResponse;
import net.danielfreire.util.PortalTools;
import net.danielfreire.util.ValidateTools;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;


@Component("productBusiness")
public class ProductBusinessImpl implements ProductBusiness {
	
	@Autowired
	private ProductRepository repository;
	@Autowired
	private CategoryHasProductRepository chpRepository;
	@Autowired
	private ProductCategoryRepository categoryRepository;

	@SuppressWarnings("unchecked")
	@Override
	public GenericResponse insertUpdate(HttpServletRequest request) throws Exception {
		GenericResponse resp = new GenericResponse();
		
		HashMap<String, Object> map = getProduct(request);
		
		if (map.get("errors")!=null) {
			resp = PortalTools.getInstance().getRespError((HashMap<String, String>) map.get("errors"));
		} else {
			Product p = repository.save((Product) map.get("product"));
			
			for (CategoryHasProduct cp : chpRepository.findByProductId(p.getId())) {
				chpRepository.delete(cp);
			}
			
			String[] categorys = request.getParameter("category").toString().replace("[LIN]", "-").split("-");
			for (String category : categorys) {
				CategoryHasProduct cp = new CategoryHasProduct();
				cp.setCategory(new ProductCategory(Integer.parseInt(category)));
				cp.setProduct(p);
				
				chpRepository.save(cp);
			}
			
			generateProductCache(p, request);
		}
		
		return resp;
	}

	@Override
	public GenericResponse load(HttpServletRequest request) {
		GenericResponse resp = new GenericResponse();
		Product p = repository.findOne(Integer.parseInt(request.getParameter("id")));
		if (p.getSite().getId() == EcommerceUtil.getInstance().getSessionAdmin(request).getSite().getId()) {
			resp.setGeneric(repository.findOne(Integer.parseInt(request.getParameter("id"))));
			resp.setGenericList(chpRepository.findByProductId(Integer.parseInt(request.getParameter("id"))));
		} else {
			resp = PortalTools.getInstance().getRespError("permission.invalid");
		}
		
		return resp;
	}

	@Override
	public GenericResponse detele(HttpServletRequest request) {
		Product p = repository.findOne(Integer.parseInt(request.getParameter("id")));
		if (p.getSite().getId()==EcommerceUtil.getInstance().getSessionAdmin(request).getSite().getId()) {
			for (CategoryHasProduct cp : chpRepository.findByProductId(Integer.parseInt(request.getParameter("id")))) {
				chpRepository.delete(cp);
			}
			repository.delete(p);
			return new GenericResponse();
		} else {
		 	return PortalTools.getInstance().getRespError("permission.invalid");
		}
	}

	@Override
	public GenericResponse listByCategory(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void upload(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		if (!isMultipart) {
			response.getWriter().print("$('#alertError', top.document).val('"+PortalTools.getInstance().getMessage("upload.invalid")+"');$('#alertError', top.document).click()");
		} else {
			
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<?> items = upload.parseRequest(request);
			Iterator<?> itr = items.iterator();
			
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				if (!item.isFormField()){
					String itemName = item.getName();
					
					int IndexOf = itemName.indexOf(".");
					String domainName = itemName.substring(IndexOf);
					
					if (!domainName.toLowerCase().equals(".jpg") && !domainName.toLowerCase().equals(".png") && !domainName.toLowerCase().equals(".jpeg") ) {
						response.getWriter().print("$('#alertError', top.document).val('"+PortalTools.getInstance().getMessage("file.invalid")+"');$('#alertError', top.document).click()");
					} else {
						String finalimage = (new Date().getTime())+domainName;
						
						String photoFile = PortalTools.getInstance().getEcommerceProperties("location.generatesite")+
								"/"+ConvertTools.getInstance().normalizeString(EcommerceUtil.getInstance().getSessionAdmin(request).getSite().getName())+
								"/upload/"+finalimage;
						
						File savedFile = new File(photoFile);
						item.write(savedFile);
						
						final String extension = photoFile.substring(photoFile.lastIndexOf(".") + 1);
						final String newPhtoFile = photoFile.replace(domainName, "") + "_original" + domainName;
						BufferedImage img = ConvertTools.getInstance().RedimensionImage(photoFile,1000,900);
					    ImageIO.write(img,extension,new File(newPhtoFile)); 
						
						savedFile.delete();
						img = ConvertTools.getInstance().RedimensionImage(newPhtoFile,190,160);
					    ImageIO.write(img,extension,new File(photoFile)); 
					    
						String locationUrl = "/ecommerce/"+ConvertTools.getInstance().normalizeString(EcommerceUtil.getInstance().getSessionAdmin(request).getSite().getName())+"/upload/"+finalimage;
						
						String reponseText = "uploadSet('"+locationUrl+"');";
						
						response.getWriter().print(reponseText);
					}
				}
			}
			
	  	}
	}
	
	private HashMap<String, Object> getProduct(HttpServletRequest request) {
		final String name = request.getParameter("name");
		final String introduction = request.getParameter("introduction");
		final String images = request.getParameter("images");
		final String description = request.getParameter("description");
		final String quantity = request.getParameter("quantity");
		final String unityvalue = request.getParameter("unityvalue").replace(",", ".");
		final String id = request.getParameter("id");
		final String category =  request.getParameter("category");
		final String quantityFrete = request.getParameter("quantityFrete");
		
		HashMap<String, String> errors = new HashMap<String, String>();
		
		if (ValidateTools.getInstancia().isNullEmpty(name)) {
			errors.put("name", PortalTools.getInstance().getMessage("name.invalid"));
		}
		if (ValidateTools.getInstancia().isNullEmpty(introduction)) {
			errors.put("introduction", PortalTools.getInstance().getMessage("introduction.invalid"));
		}
		if (ValidateTools.getInstancia().isNullEmpty(images)) {
			errors.put("images", PortalTools.getInstance().getMessage("images.invalid"));
		}
		if (!ValidateTools.getInstancia().isNumber(quantity)) {
			errors.put("quantity", PortalTools.getInstance().getMessage("quantity.invalid"));
		}
		if (!ValidateTools.getInstancia().isDouble(unityvalue)) {
			errors.put("unityvalue", PortalTools.getInstance().getMessage("unityvalue.invalid"));
		}
		if (!ValidateTools.getInstancia().isDouble(quantityFrete)) {
			errors.put("quantityFrete", PortalTools.getInstance().getMessage("quantityFrete.invalid"));
		}
		if (ValidateTools.getInstancia().isNullEmpty(category)) {
			errors.put("category", PortalTools.getInstance().getMessage("category.invalid"));
		}
		if ((!ValidateTools.getInstancia().isNumber(id) 
				&& repository.findByKeyUrlAndSite(ConvertTools.getInstance().normalizeString(name), EcommerceUtil.getInstance().getSessionAdmin(request).getSite())!=null) 
				|| (ValidateTools.getInstancia().isNumber(id) 
						&& !repository.findOne(Integer.parseInt(id)).getName().equals(name) 
						&& repository.findByKeyUrlAndSite(ConvertTools.getInstance().normalizeString(name), EcommerceUtil.getInstance().getSessionAdmin(request).getSite())!=null)) {
			errors.put("name", PortalTools.getInstance().getMessage("name.exist"));
		}
		
		HashMap<String, Object> resp = new HashMap<String, Object>();
		if (errors.size()>0) {
			resp.put("errors", errors);
			resp.put("product", null);
		} else {
			Product product = new Product();
			if (ValidateTools.getInstancia().isNumber(id)) {
				product = repository.findOne(Integer.parseInt(id));
			} else {
				product.setDatecreate(Calendar.getInstance());
			}
			product.setDescription(description);
			product.setImages(images);
			product.setIntroduction(introduction);
			product.setName(name);
			product.setQuantity(Double.parseDouble(quantity));
			product.setUnityvalue(Double.parseDouble(unityvalue));
			product.setSite(EcommerceUtil.getInstance().getSessionAdmin(request).getSite());
			product.setKeyUrl(ConvertTools.getInstance().normalizeString(name));
			product.setQuantityFrete(Double.parseDouble(quantityFrete));
			
			resp.put("errors", null);
			resp.put("product", product);
		}
		
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
		titles.add(PortalTools.getInstance().getRowGrid("name", "Nome", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("introduction", "Introdução", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("images", "Imagens", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("quantity", "Quantidade", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("unityvalue", "Valor unitário (R$)", "text"));
		
		Page<Product> pageable = repository.findBySite(EcommerceUtil.getInstance().getSessionAdmin(request).getSite(), new PageRequest(pagination, 10));
		
		return PortalTools.getInstance().getGrid(pageable.getContent(), titles, pageable.getNumber()+1, pageable.getTotalPages());
	}

	@Override
	public GridResponse listToPortal(String sid, String categoryId,
			String search, String page) throws Exception {
		String siteId = PortalTools.getInstance().decode(sid);
		
		int pagination = 0;
		if (ValidateTools.getInstancia().isNumber(page)) {
			pagination = Integer.parseInt(page)-1;
		} 
		
		Page<?> pageable = null;
		if (ValidateTools.getInstancia().isNullEmpty(categoryId) && ValidateTools.getInstancia().isNullEmpty(search)) {
			pageable = repository.findBySite(new Site(Integer.parseInt(siteId)), new PageRequest(pagination, 9, Direction.DESC, "datecreate"));
		} else if (!ValidateTools.getInstancia().isNullEmpty(categoryId)) {
			pageable = chpRepository.findByCategory(categoryRepository.findByKeyUrlAndSite(categoryId, new Site(Integer.parseInt(siteId))), new PageRequest(pagination, 9, Direction.DESC, "product.datecreate"));
		} else if (!ValidateTools.getInstancia().isNullEmpty(search)) {
			pageable = repository.findBySiteAndDescriptionLike(new Site(Integer.parseInt(siteId)), "%"+search+"%", new PageRequest(pagination, 9, Direction.DESC, "datecreate"));
		}
		
		GridResponse grid = new GridResponse();
		grid.setRows(pageable.getContent());
		grid.setPage(pageable.getNumber()+1);
		grid.setTotalPages(pageable.getTotalPages());
		
		return grid;
	}

	@Override
	public GenericResponse load(String siteId, String productId)
			throws Exception {
		GenericResponse resp = new GenericResponse();
		
		if (ValidateTools.getInstancia().isNullEmpty(productId) || ValidateTools.getInstancia().isNullEmpty(siteId) || !ValidateTools.getInstancia().isNumber(PortalTools.getInstance().decode(siteId))) {
			resp.setStatus(false);
		} else {
			resp.setGeneric(repository.findByKeyUrlAndSite(productId, new Site(Integer.parseInt(PortalTools.getInstance().decode(siteId)))));
		}
		
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GenericResponse addCart(HttpServletRequest request) throws Exception {
		final Integer productId = Integer.parseInt(request.getParameter("pid"));
		
		ArrayList<Product> list = (ArrayList<Product>) request.getSession().getAttribute(PortalTools.getInstance().ID_CART_SESSION);
		
		if (list!=null && list.size()>0) {
			boolean exist = false;
			for (Product p : list) {
				if (p.getId()==productId) {
					exist = true;
				}
			}
			
			if (!exist) {
				list.add(new Product(productId, Integer.parseInt(PortalTools.getInstance().decode(request.getParameter("sid")))));
			}
		} else {
			list = new ArrayList<Product>();
			list.add(new Product(productId, Integer.parseInt(PortalTools.getInstance().decode(request.getParameter("sid")))));
		}
		
		request.getSession().setAttribute(PortalTools.getInstance().ID_CART_SESSION, list);
		
		return new GenericResponse();
	}

	@SuppressWarnings("unchecked")
	@Override
	public GenericResponse myCart(HttpServletRequest request) throws Exception {
		GenericResponse resp = new GenericResponse();
		
		ArrayList<Product> list = new ArrayList<Product>();
		final ArrayList<Product> listSession = (ArrayList<Product>) request.getSession().getAttribute(PortalTools.getInstance().ID_CART_SESSION);
		for (Product p : listSession) {
			if (p.getSite().getId()==Integer.parseInt(PortalTools.getInstance().decode(request.getParameter("sid")))) {
				Product product = repository.findOne(p.getId());
				list.add(product);
			}
		}
		
		HashMap<String, Object> ret = new HashMap<String, Object>();
		ret.put("cart", list);
		
		resp.setGeneric(ret);
		
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public GenericResponse removeItemCart(HttpServletRequest request) throws Exception {
		Integer id = Integer.parseInt(request.getParameter("id"));
		
		ArrayList<Product> newlist = new ArrayList<Product>();
		ArrayList<Product> list = (ArrayList<Product>) request.getSession().getAttribute(PortalTools.getInstance().ID_CART_SESSION);
		for (Product p : list) {
			if (p.getId()!=id) {
				newlist.add(p);
			}
		}
		
		request.getSession().setAttribute(PortalTools.getInstance().ID_CART_SESSION, newlist);
		
		return new GenericResponse();
	}
	
	private void generateProductCache(Product p, HttpServletRequest request) throws Exception {
		EcommerceUtil.getInstance().generateProductCache(p, EcommerceUtil.getInstance().getSessionAdmin(request).getSite());
	}

}