package net.danielfreire.products.ecommerce.model.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.ecommerce.model.domain.ClientEcommerce;
import net.danielfreire.products.ecommerce.model.domain.Order;
import net.danielfreire.products.ecommerce.model.domain.Payment;
import net.danielfreire.products.ecommerce.model.domain.Product;
import net.danielfreire.products.ecommerce.model.domain.ProductHasOrder;
import net.danielfreire.products.ecommerce.model.repository.ClientEcommerceRepository;
import net.danielfreire.products.ecommerce.model.repository.OrderRepository;
import net.danielfreire.products.ecommerce.model.repository.PaymentRepository;
import net.danielfreire.products.ecommerce.model.repository.ProductHasOrderRepository;
import net.danielfreire.products.ecommerce.model.repository.ProductRepository;
import net.danielfreire.util.FileUtil;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;
import net.danielfreire.util.GridTitleResponse;
import net.danielfreire.util.PortalTools;
import net.danielfreire.util.ValidateTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component("orderBusiness")
public class OrderBusinessImpl implements OrderBusiness {
	
	@Autowired
	private OrderRepository repository;
	@Autowired
	private ClientEcommerceRepository clientRepository;
	@Autowired
	private ProductHasOrderRepository productHasOrderRepository;
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private ProductRepository productRepository;

	@Override
	public GenericResponse update(HttpServletRequest request) {
		final Integer id = Integer.parseInt(request.getParameter("id"));
		final Double discount = Double.parseDouble(request.getParameter("discount").replace(",", "."));
		final Double sendCust = Double.parseDouble(request.getParameter("sendCust").replace(",", "."));
		final Integer statusOrder = Integer.parseInt(request.getParameter("statusOrder"));
		final Integer payment = Integer.parseInt(request.getParameter("payment"));
		
		Order order = repository.findOne(id);
		
		if (statusOrder==3 && order.getStatusOrder()<3) {
			order.setDatePayment(Calendar.getInstance());
		}
		
		order.setDiscount(discount);
		order.setSendCust(sendCust);
		order.setStatusOrder(statusOrder);
		order.setPayment(new Payment(payment));
		
		Double totalValue = 0.0;
		for (ProductHasOrder prodHasOrder : productHasOrderRepository.findByOrderId(id)) {
			totalValue = totalValue + (prodHasOrder.getUnityvalue() * prodHasOrder.getQuantity());
		}
		totalValue = ((totalValue + sendCust) - discount);
		order.setTotalValue(totalValue);
		
		repository.save(order);
		
		return new GenericResponse();
	}

	@Override
	public GridResponse consult(HttpServletRequest request) throws Exception {
		String siteId = (String) request.getSession().getAttribute(PortalTools.getInstance().idAdminSession);
		String page = request.getParameter("page");
		String clientId = request.getParameter("cid");
		
		ClientEcommerce client = clientRepository.findOne(Integer.parseInt(clientId));
		if (client.getSiteId()!=Integer.parseInt(siteId)) {
			return new GridResponse();
		} else {
			int pagination = 0;
			
			if (ValidateTools.getInstancia().isNumber(page)) {
				pagination = Integer.parseInt(page)-1;
			} 
			
			ArrayList<GridTitleResponse> titles = new ArrayList<GridTitleResponse>();
			
			GridTitleResponse title = new GridTitleResponse();
			
			title = new GridTitleResponse();
			title.setId("payment");
			title.setTitle("Forma de pgto");
			title.setType("text");
			titles.add(title);
			
			title = new GridTitleResponse();
			title.setId("dateCreate");
			title.setTitle("Data de criação");
			title.setType("text");
			titles.add(title);
			
			title = new GridTitleResponse();
			title.setId("datePayment");
			title.setTitle("Data de pgto");
			title.setType("text");
			titles.add(title);
			
			title = new GridTitleResponse();
			title.setId("statusOrder");
			title.setTitle("Status");
			title.setType("text");
			titles.add(title);
			
			title = new GridTitleResponse();
			title.setId("sendCust");
			title.setTitle("Custo de envio");
			title.setType("text");
			titles.add(title);
			
			title = new GridTitleResponse();
			title.setId("discount");
			title.setTitle("Desconto");
			title.setType("text");
			titles.add(title);
			
			title = new GridTitleResponse();
			title.setId("totalValue");
			title.setTitle("Valor total");
			title.setType("text");
			titles.add(title);
			
			title = new GridTitleResponse();
			title.setId("cesta");
			title.setTitle("Produtos");
			title.setType("text");
			titles.add(title);
			
			Page<Order> pageable = repository.findByClient(client, new PageRequest(pagination, 10));
			
			GridResponse grid = new GridResponse();
			grid.setRows(pageable.getContent());
			grid.setTitles(titles);
			grid.setPage(pageable.getNumber()+1);
			grid.setTotalPages(pageable.getTotalPages());
			
			return grid;
		}
	}

	@Override
	public GenericResponse listByOpt(HttpServletRequest request) throws Exception {
		GenericResponse resp = new GenericResponse();
		
		final String opt = request.getParameter("opt");
		final ClientEcommerce client = (ClientEcommerce) request.getSession().getAttribute(PortalTools.getInstance().idSession);
		
		List<Order> list = new ArrayList<Order>();
		
		if (opt.equals("order1")) {
			list = repository.findByClientAndStatusOrderLessThan(client, 4, new PageRequest(0, 10, Direction.DESC, "dateCreate")).getContent();
		} else if (opt.equals("order2")) {
			list = repository.findByClientAndStatusOrderGreaterThan(client, 3, new PageRequest(0, 10, Direction.DESC, "dateCreate")).getContent();
		} else if (opt.equals("order3")) {
			list = repository.findByClient(client, new PageRequest(0, 10, Direction.DESC, "dateCreate")).getContent();
		} 
		
		resp.setGenericList(list);
		
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GenericResponse insert(HttpServletRequest request, HashMap<String, Object> map) throws Exception {
		GenericResponse resp = new GenericResponse();
		
		ClientEcommerce client = (ClientEcommerce) map.get("client");
		ArrayList<Product> products = (ArrayList<Product>) map.get("cart");
		Double send = Double.parseDouble(request.getParameter("send").replace(",", "."));
		Double total = Double.parseDouble(request.getParameter("total").replace(",", "."));
		
		if (client==null || products==null) {
			resp = PortalTools.getInstance().getRespError("session.invalid");
		}
		
		if (resp.getStatus()) {
			Order order = new Order();
			order.setClient(client);
			order.setDateCreate(Calendar.getInstance());
			order.setDiscount(0.0);
			order.setPayment(paymentRepository.findByName("PagSeguro"));
			order.setSendCust(send);
			order.setStatusOrder(2);
			order.setTotalValue(total);
			
			order = repository.saveAndFlush(order);
			
			ArrayList<Product> prods = new ArrayList<Product>();
			
			for (Product p : products) {
				Product prod = productRepository.findOne(p.getId());
				ProductHasOrder prodorder = new ProductHasOrder();
				prodorder.setOrderId(order.getId());
				prodorder.setProduct(prod);
				prodorder.setQuantity(Integer.parseInt(request.getParameter("qtdSelect"+p.getId())));
				prodorder.setUnityvalue(prod.getUnityvalue());
				
				productHasOrderRepository.save(prodorder);
				
				prod.setQuantity(prod.getQuantity()-prodorder.getQuantity());
				productRepository.save(prod);
				prods.add(prod);
				
				FileUtil.getInstance().createFile(PortalTools.getInstance().getEcommerceProperties("location.json"), "product"+PortalTools.getInstance().Encode(prod.getSiteId().toString())+prod.getKeyUrl()+".json", new Gson().toJson(prod));
			}
			
			request.getSession().removeAttribute(PortalTools.getInstance().idCartSession);
			request.getSession().removeAttribute(PortalTools.getInstance().idSession);
			
			HashMap<String, Object> ret = new HashMap<String, Object>();
			ret.put("client", client);
			ret.put("cart", prods);
			ret.put("order", order.getId());
			
			resp.setGeneric(ret);
		}
		
		return resp;
	}
	
}

