package net.danielfreire.products.ecommerce.model.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.danielfreire.products.ecommerce.model.domain.CategoryHasProduct;
import net.danielfreire.products.ecommerce.model.domain.Product;
import net.danielfreire.products.ecommerce.model.domain.ProductCategory;
import net.danielfreire.products.ecommerce.model.repository.CategoryHasProductRepository;
import net.danielfreire.products.ecommerce.model.repository.ProductRepository;
import net.danielfreire.products.ecommerce.util.EcommerceUtil;
import net.danielfreire.products.ecommerce.util.builder.CategoryHasProductBuilder;
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

@Component("productAdminBusiness")
public class ProductAdminBusinessImpl implements ProductAdminBusiness {
	
	@Autowired
	private transient ProductRepository repository;
	@Autowired
	private transient CategoryHasProductRepository chpRepository;
	
	private static final String LBL_UNCHEKED = "unchecked";
	private static final String LBL_ERROR = "errors";
	private static final String LBL_NAME = "name";
	private static final String LBL_TEXT = "text";
	
	@SuppressWarnings(LBL_UNCHEKED)
	@Override
	public GenericResponse insertUpdate(final HttpServletRequest request) throws java.lang.Exception {
		GenericResponse resp = new GenericResponse();
		
		final HashMap<String, Object> map = (HashMap<String, Object>) getProduct(request);
		
		if (map.get(LBL_ERROR)==null) {
			final Product product = repository.save((Product) map.get("product"));
			
			for (CategoryHasProduct cp : chpRepository.findByProductId(product.getId())) {
				chpRepository.delete(cp);
			}
			
			final String[] categorys = request.getParameter("category").toString().replace("[LIN]", "-").split("-");
			for (String category : categorys) {
				chpRepository.save(buildCategoryHasProduct(Integer.parseInt(category), product));
			}
			
			generateProductCache(product, request);
		} else {
			resp = PortalTools.getInstance().getRespError((HashMap<String, String>) map.get(LBL_ERROR));
		}
		
		return resp;
	}
	
	@Override
	public GenericResponse delete(final HttpServletRequest request) {
		GenericResponse resp = new GenericResponse();
		final Product product = repository.findOne(Integer.parseInt(request.getParameter("id")));
		if (product.getSite().getId().equals(EcommerceUtil.getInstance().getSessionAdmin(request).getSite().getId())) {
			for (CategoryHasProduct cp : chpRepository.findByProductId(Integer.parseInt(request.getParameter("id")))) {
				chpRepository.delete(cp);
			}
			repository.delete(product);
		} else {
			resp = PortalTools.getInstance().getRespError("permission.invalid");
		}
		
		return resp;
	}
	
	@Override
	public void upload(final HttpServletRequest request,
			final HttpServletResponse response) throws java.lang.Exception {
		
		if (ServletFileUpload.isMultipartContent(request)) {
			final FileItemFactory factory = new DiskFileItemFactory();
			final ServletFileUpload upload = new ServletFileUpload(factory);
			final List<?> items = upload.parseRequest(request);
			final Iterator<?> itr = items.iterator();
			
			while (itr.hasNext()) {
				final FileItem item = (FileItem) itr.next();
				if (!item.isFormField()){
					final String itemName = item.getName();
					final String domainName = itemName.substring(itemName.indexOf('.'));
					
					if (domainName.equalsIgnoreCase(".jpg") || domainName.equalsIgnoreCase(".png") || domainName.equalsIgnoreCase(".jpeg")) {
						response.getWriter().print("uploadSet('"+upload(domainName, item, request)+"');");
					} else {
						response.getWriter().print("$('#alertError', top.document).val('"+PortalTools.getInstance().getMessage("file.invalid")+"');$('#alertError', top.document).click()");
					}
				}
			}
		} else {
			response.getWriter().print("$('#alertError', top.document).val('"+PortalTools.getInstance().getMessage("upload.invalid")+"');$('#alertError', top.document).click()");
	  	}
	}
	
	private CategoryHasProduct buildCategoryHasProduct(final Integer productCategory,
			final Product product) {
		
		return new CategoryHasProductBuilder()
					.withCategory(new ProductCategory(productCategory))
					.withProduct(product)
					.build();
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
		titles.add(PortalTools.getInstance().getRowGrid("introduction", "Introdução", LBL_TEXT));
		titles.add(PortalTools.getInstance().getRowGrid("images", "Imagens", LBL_TEXT));
		titles.add(PortalTools.getInstance().getRowGrid("quantity", "Quantidade", LBL_TEXT));
		titles.add(PortalTools.getInstance().getRowGrid("unityvalue", "Valor unitário (R$)", LBL_TEXT));
		
		final Page<Product> pageable = repository.findBySite(EcommerceUtil.getInstance().getSessionAdmin(request).getSite(), new PageRequest(pagination, 10));
		
		return PortalTools.getInstance().getGrid(pageable.getContent(), titles, pageable.getNumber()+1, pageable.getTotalPages());
	}
	
	@Override
	public Long countProducts(final HttpServletRequest request) {
		return repository.countBySite(EcommerceUtil.getInstance().getSessionAdmin(request).getSite());
	}

	@Override
	public Long countProductsSemEstoque(final HttpServletRequest request) {
		return repository.countBySiteAndQuantity(EcommerceUtil.getInstance().getSessionAdmin(request).getSite(), Double.valueOf(0.0));
	}
	
	private String upload(final String domainName, final FileItem item, final HttpServletRequest request) throws java.lang.Exception {
		final String finalimage = (new Date().getTime())+domainName;
		
		final String photoFile = PortalTools.getInstance().getEcommerceProperties("location.generatesite")+
				"/"+ConvertTools.getInstance().normalizeString(EcommerceUtil.getInstance().getSessionAdmin(request).getSite().getName())+
				"/upload/"+finalimage;
		
		final File savedFile = new File(photoFile);
		item.write(savedFile);
		
		final String extension = photoFile.substring(photoFile.lastIndexOf('.') + 1);
		final String newPhtoFile = photoFile.replace(domainName, "") + "_original" + domainName;
		BufferedImage img = ConvertTools.getInstance().RedimensionImage(photoFile,1000,900);
	    ImageIO.write(img,extension,new File(newPhtoFile)); 
		
		savedFile.delete();
		img = ConvertTools.getInstance().RedimensionImage(newPhtoFile,190,160);
	    ImageIO.write(img,extension,new File(photoFile)); 
	    
		return "/ecommerce/"+ConvertTools.getInstance().normalizeString(EcommerceUtil.getInstance().getSessionAdmin(request).getSite().getName())+"/upload/"+finalimage;
	}

	private Map<String, Object> getProduct(final HttpServletRequest request) {
		final String name = request.getParameter(LBL_NAME);
		final String introduction = request.getParameter("introduction");
		final String images = request.getParameter("images");
		final String description = request.getParameter("description");
		final String quantity = request.getParameter("quantity");
		final String unityvalue = request.getParameter("unityvalue").replace(".", "").replace(",", ".");
		final String idProduct = request.getParameter("id");
		final String category =  request.getParameter("category");
		final String quantityFrete = request.getParameter("quantityFrete");
		
		final Map<String, String> errors = isValid(name, introduction, images, quantity, unityvalue, idProduct, category, quantityFrete, request);
		final HashMap<String, Object> resp = new HashMap<String, Object>();
		
		if (errors.isEmpty()) {
			Product product = new Product();
			if (ValidateTools.getInstancia().isNumber(idProduct)) {
				product = repository.findOne(Integer.parseInt(idProduct));
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
			
			resp.put(LBL_ERROR, null);
			resp.put("product", product);
		} else {
			resp.put(LBL_ERROR, errors);
			resp.put("product", null);
		}
		
		return resp;
	}
	
	private void generateProductCache(final Product product, final HttpServletRequest request) throws java.lang.Exception {
		EcommerceUtil.getInstance().generateProductCache(product, EcommerceUtil.getInstance().getSessionAdmin(request).getSite());
	}
	
	private Map<String, String> isValid(final String name, final String introduction, final String images, final String quantity, final String unityvalue, final String idProduct, final String category, final String quantityFrete, final HttpServletRequest request) {
		final HashMap<String, String> errors = new HashMap<String, String>();
		
		errors.putAll(ValidateTools.getInstancia().validateGeneric("name.invalid", ValidateTools.getInstancia().isNullEmpty(name)));
		errors.putAll(ValidateTools.getInstancia().validateGeneric("introduction.invalid", ValidateTools.getInstancia().isNullEmpty(introduction)));
		errors.putAll(ValidateTools.getInstancia().validateGeneric("images.invalid", ValidateTools.getInstancia().isNullEmpty(images)));
		errors.putAll(ValidateTools.getInstancia().validateGeneric("quantity.invalid", !ValidateTools.getInstancia().isNumber(quantity)));
		errors.putAll(ValidateTools.getInstancia().validateGeneric("unityvalue.invalid", !ValidateTools.getInstancia().isDouble(unityvalue)));
		errors.putAll(ValidateTools.getInstancia().validateGeneric("quantityFrete.invalid", !ValidateTools.getInstancia().isDouble(quantityFrete)));
		errors.putAll(ValidateTools.getInstancia().validateGeneric("category.invalid", ValidateTools.getInstancia().isNullEmpty(category)));
		errors.putAll(ValidateTools.getInstancia().validateGeneric("name.invalid", (!ValidateTools.getInstancia().isNumber(idProduct) 
				&& repository.findByKeyUrlAndSite(ConvertTools.getInstance().normalizeString(name), EcommerceUtil.getInstance().getSessionAdmin(request).getSite())!=null) 
				|| (ValidateTools.getInstancia().isNumber(idProduct) 
						&& !repository.findOne(Integer.parseInt(idProduct)).getName().equals(name) 
						&& repository.findByKeyUrlAndSite(ConvertTools.getInstance().normalizeString(name), EcommerceUtil.getInstance().getSessionAdmin(request).getSite())!=null)));
		
		return errors;
	}

	@Override
	public List<Product> listProductsSemEstoque(final HttpServletRequest request) {
		return repository.findBySiteAndQuantity(EcommerceUtil.getInstance().getSessionAdmin(request).getSite(), Double.valueOf(0.0), new PageRequest(0, 10)).getContent();
	}

	@Override
	public List<Product> listProductsOrderByEstoque(final HttpServletRequest request) {
		return repository.findBySite(EcommerceUtil.getInstance().getSessionAdmin(request).getSite(), new PageRequest(0, 10, Direction.DESC, "quantity")).getContent();
	}

	@Override
	public Double countQuantityAllProducts(final HttpServletRequest request) {
		return repository.countQuantityBySite(EcommerceUtil.getInstance().getSessionAdmin(request).getSite());
	}

}
