package net.danielfreire.products.ecommerce.model.core;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.ecommerce.model.domain.FreteParameter;
import net.danielfreire.products.ecommerce.model.domain.Site;
import net.danielfreire.products.ecommerce.model.repository.FreteParameterRepository;
import net.danielfreire.products.ecommerce.model.repository.ProductRepository;
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

@Component("freteParameterBusiness")
public class FreteParameterBusinessImpl implements FreteParameterBusiness {
	
	@Autowired
	private FreteParameterRepository repository;
	@Autowired
	private ProductRepository productRepository;

	@SuppressWarnings("unchecked")
	@Override
	public GenericResponse insertUpdate(HttpServletRequest request)
			throws Exception {
		GenericResponse resp = new GenericResponse();
		
		HashMap<String, Object> map = getFreteParameter(request);
		
		if (map.get("errors")!=null) {
			resp = PortalTools.getInstance().getRespError((HashMap<String, String>) map.get("errors"));
		} else {
			repository.save((FreteParameter) map.get("freteParameter"));
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
		titles.add(PortalTools.getInstance().getRowGrid("state", "Estado", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("value", "Valor", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("quantityDay", "Prazo de entrega", "text"));
		
		Page<FreteParameter> pageable = repository.findBySite(EcommerceUtil.getInstance().getSessionAdmin(request).getSite(), new PageRequest(pagination, 10));
		
		return PortalTools.getInstance().getGrid(pageable.getContent(), titles, pageable.getNumber()+1, pageable.getTotalPages());
	}
	
	private HashMap<String, Object> getFreteParameter(HttpServletRequest request) {
		final String state = request.getParameter("state");
		final String value = request.getParameter("value").replace(",", ".");
		final String id = request.getParameter("id");
		final String quantityDay = request.getParameter("quantityDay");
		
		HashMap<String, String> errors = new HashMap<String, String>();
		
		if (ValidateTools.getInstancia().isNullEmpty(state)) {
			errors.put("state", PortalTools.getInstance().getMessage("state.invalid"));
		}
		if (!ValidateTools.getInstancia().isDouble(value)) {
			errors.put("value", PortalTools.getInstance().getMessage("value.invalid"));
		}
		if (!ValidateTools.getInstancia().isNumber(quantityDay)) {
			errors.put("quantityDay", PortalTools.getInstance().getMessage("quantityDay.invalid"));
		}
		
		HashMap<String, Object> resp = new HashMap<String, Object>();
		if (errors.size()>0) {
			resp.put("errors", errors);
			resp.put("client", null);
		} else {
			FreteParameter frete = new FreteParameter();
			if (ValidateTools.getInstancia().isNumber(id)) {
				frete = new FreteParameter(Integer.parseInt(id));
			}
			frete.setSite(EcommerceUtil.getInstance().getSessionAdmin(request).getSite());
			frete.setState(state);
			frete.setValue(Double.parseDouble(value));
			frete.setQuantityDay(Integer.parseInt(quantityDay));
			
			resp.put("errors", null);
			resp.put("freteParameter", frete);
		}
		
		return resp;
	}

	@Override
	public GenericResponse getFrete(HttpServletRequest request)
			throws Exception {
		GenericResponse resp = new GenericResponse();
		
		final String state = request.getParameter("uf");
		final String[] params = request.getParameterValues("param");
		final Integer siteId = Integer.parseInt(PortalTools.getInstance().decode(request.getParameter("sid")));
		
		FreteParameter frete = repository.findBySiteAndState(new Site(siteId), state);
		
		if (frete!=null) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			if (params!=null) {
				Double percenteFrete = 0.0;
				
				for (String param : params) {
					
					String[] p = param.split("-");
					
					final Integer productId = Integer.parseInt(p[0]);
					final Integer qtd = Integer.parseInt(p[1]);
					
					percenteFrete = percenteFrete + ((qtd*100) / productRepository.findOne(productId).getQuantityFrete());
				}
				
				Double qFrete = percenteFrete/100;
				Double tFrete = 1.0;
				if (qFrete>1) {
					tFrete = ConvertTools.getInstance().round(qFrete, 0);
				}
				
				map.put("frete", (tFrete * frete.getValue()));
			}
			
			map.put("prazo", frete.getQuantityDay());
			
			resp.setGeneric(map);
		} else {
			resp = PortalTools.getInstance().getRespError("frete.notavaible");
		}
		
		return resp;
	}

	@Override
	public GenericResponse delivery(HttpServletRequest request)
			throws Exception {
		
		GenericResponse resp = new GenericResponse();
		resp.setGeneric(repository.findBySite(new Site(Integer.parseInt(PortalTools.getInstance().decode(request.getParameter("sid"))))));
		
		return resp;
	}

}
