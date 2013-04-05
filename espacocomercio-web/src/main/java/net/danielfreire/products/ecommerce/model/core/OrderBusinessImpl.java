package net.danielfreire.products.ecommerce.model.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.ecommerce.model.domain.ClientAdmin;
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
import net.danielfreire.products.ecommerce.util.EcommerceUtil;
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

@Component("orderBusiness")
public class OrderBusinessImpl implements OrderBusiness {
	
	private static final String LBL_TEXT = "text";
	private static final String LBL_DATE = "dateCreate";
	@Autowired
	private transient OrderRepository repository;
	@Autowired
	private transient ClientEcommerceRepository clientRepository;
	@Autowired
	private transient ProductHasOrderRepository pHasOrderRepo;
	@Autowired
	private transient PaymentRepository paymentRepository;
	@Autowired
	private transient ProductRepository productRepository;

	@Override
	public GenericResponse update(final HttpServletRequest request) {
		final Integer idOrder = Integer.parseInt(request.getParameter("id"));
		final Double discount = Double.parseDouble(request.getParameter("discount").replace(",", "."));
		final Double sendCust = Double.parseDouble(request.getParameter("sendCust").replace(",", "."));
		final Integer statusOrder = Integer.parseInt(request.getParameter("statusOrder"));
		final Integer payment = Integer.parseInt(request.getParameter("payment"));
		
		final Order order = repository.findOne(idOrder);
		
		if (statusOrder==3 && order.getStatusOrder()<3) {
			order.setDatePayment(Calendar.getInstance());
		}
		
		order.setDiscount(discount);
		order.setSendCust(sendCust);
		order.setStatusOrder(statusOrder);
		order.setPayment(new Payment(payment));
		
		Double totalValue = 0.0;
		for (ProductHasOrder prodHasOrder : pHasOrderRepo.findByOrderId(idOrder)) {
			totalValue = totalValue + (prodHasOrder.getUnityvalue() * prodHasOrder.getQuantity());
		}
		totalValue = ((totalValue + sendCust) - discount);
		order.setTotalValue(totalValue);
		
		repository.save(order);
		
		return new GenericResponse();
	}

	@Override
	public GridResponse consult(final HttpServletRequest request) throws java.lang.Exception {
		GridResponse resp = new GridResponse();
		final String page = request.getParameter("page");
		final String clientId = request.getParameter("cid");
		
		final ClientEcommerce client = clientRepository.findOne(Integer.parseInt(clientId));
		if (client.getSite().getId()==EcommerceUtil.getInstance().getSessionAdmin(request).getSite().getId()) {
			int pagination = 0;
			if (ValidateTools.getInstancia().isNumber(page)) {
				pagination = Integer.parseInt(page)-1;
			} 
			
			final ArrayList<GridTitleResponse> titles = new ArrayList<GridTitleResponse>();
			titles.add(PortalTools.getInstance().getRowGrid("payment", "Forma de pgto", LBL_TEXT));
			titles.add(PortalTools.getInstance().getRowGrid(LBL_DATE, "Data de criação", LBL_TEXT));
			titles.add(PortalTools.getInstance().getRowGrid("datePayment", "Data de pgto", LBL_TEXT));
			titles.add(PortalTools.getInstance().getRowGrid("statusOrder", "Status", LBL_TEXT));
			titles.add(PortalTools.getInstance().getRowGrid("sendCust", "Custo de envio", LBL_TEXT));
			titles.add(PortalTools.getInstance().getRowGrid("discount", "Desconto", LBL_TEXT));
			titles.add(PortalTools.getInstance().getRowGrid("totalValue", "Valor total", LBL_TEXT));
			titles.add(PortalTools.getInstance().getRowGrid("cesta", "Produtos", LBL_TEXT));
			
			final Page<Order> pageable = repository.findByClient(client, new PageRequest(pagination, 10));
			
			resp = PortalTools.getInstance().getGrid(pageable.getContent(), titles, pageable.getNumber()+1, pageable.getTotalPages());
		}
		
		return resp;
	}

	@Override
	public GenericResponse listByOpt(final HttpServletRequest request) throws java.lang.Exception {
		final GenericResponse resp = new GenericResponse();
		
		final String opt = request.getParameter("opt");
		final ClientEcommerce client = (ClientEcommerce) request.getSession().getAttribute(PortalTools.ID_SESSION);
		
		List<Order> list = new ArrayList<Order>();
		
		if ("order1".equals(opt)) {
			list = repository.findByClientAndStatusOrderLessThan(client, 4, new PageRequest(0, 10, Direction.DESC, LBL_DATE)).getContent();
		} else if ("order2".equals(opt)) {
			list = repository.findByClientAndStatusOrderGreaterThan(client, 3, new PageRequest(0, 10, Direction.DESC, LBL_DATE)).getContent();
		} else if ("order3".equals(opt)) {
			list = repository.findByClient(client, new PageRequest(0, 10, Direction.DESC, LBL_DATE)).getContent();
		} 
		
		resp.setGenericList(list);
		
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GenericResponse insert(final HttpServletRequest request, final Map<String, Object> map) throws java.lang.Exception {
		GenericResponse resp = new GenericResponse();
		
		final ClientEcommerce client = (ClientEcommerce) map.get("client");
		final ArrayList<Product> products = (ArrayList<Product>) map.get("cart");
		final Double send = Double.parseDouble(request.getParameter("send").replace(",", "."));
		final Double total = Double.parseDouble(request.getParameter("total").replace(",", "."));
		
		if (client==null || products==null) {
			resp = PortalTools.getInstance().getRespError("session.invalid");
		}
		
		if (resp.getStatus()) {
			Order order = new Order();
			order.setClient(client);
			order.setDateCreate(Calendar.getInstance());
			order.setDiscount(0.0);
			order.setPayment(paymentRepository.findByNameAndSite("PagSeguro", client.getSite()));
			order.setSendCust(send);
			order.setStatusOrder(1);
			order.setTotalValue(total);
			
			order = repository.saveAndFlush(order);
			
			final ArrayList<Product> prods = new ArrayList<Product>();
			
			for (Product p : products) {
				final Product prod = productRepository.findOne(p.getId());
				final ProductHasOrder prodorder =  generateProductOrder(order.getId(), prod, Integer.parseInt(request.getParameter("qtdSelect"+p.getId())), prod.getUnityvalue());
				
				pHasOrderRepo.save(prodorder);
				
				prod.setQuantity(prod.getQuantity()-prodorder.getQuantity());
				productRepository.save(prod);
				prods.add(prod);
				
				EcommerceUtil.getInstance().generateProductCache(prod, prod.getSite());
			}
			
			request.getSession().removeAttribute(PortalTools.ID_CART_SESSION);
			request.getSession().removeAttribute(PortalTools.ID_SESSION);
			
			final HashMap<String, Object> ret = new HashMap<String, Object>();
			ret.put("client", client);
			ret.put("cart", prods);
			ret.put("order", order.getId());
			ret.put("payment", order.getPayment());
			
			resp.setGeneric(ret);
		}
		
		return resp;
	}

	private ProductHasOrder generateProductOrder(final Integer idPOrder, final Product prod, final int quant,
			final Double unityvalue) {
		final ProductHasOrder prodorder = new ProductHasOrder();
		prodorder.setOrderId(idPOrder);
		prodorder.setProduct(prod);
		prodorder.setQuantity(quant);
		prodorder.setUnityvalue(unityvalue);
		
		return prodorder;
	}

	@Override
	public Long countOrders(final HttpServletRequest request) {
		return repository.countBySite(EcommerceUtil.getInstance().getSessionAdmin(request).getSite());
	}

	@Override
	public Integer[] getListOrdersByLastMonths(final HttpServletRequest request) {
		final Calendar dtInit = Calendar.getInstance();
		dtInit.add(Calendar.MONTH, -6);
		final Calendar dtNow = Calendar.getInstance();
		
		final List<Order> listOrders = repository.findBySite(EcommerceUtil.getInstance().getSessionAdmin(request).getSite(), dtInit, dtNow);
		int oder1 = 0, order2 = 0, order3 = 0, order4 = 0, order5 = 0, order6 = 0, order7 = 0;
		for (Order o : listOrders) {
			final Calendar now2 = Calendar.getInstance();
			if (now2.get(Calendar.MONTH) == o.getDateCreate().get(Calendar.MONTH)) {
				order7++;
			}
			now2.add(Calendar.MONTH, -1);
			if (now2.get(Calendar.MONTH) == o.getDateCreate().get(Calendar.MONTH)) {
				order6++;
			}
			now2.add(Calendar.MONTH, -1);
			if (now2.get(Calendar.MONTH) == o.getDateCreate().get(Calendar.MONTH)) {
				order5++;
			}
			now2.add(Calendar.MONTH, -1);
			if (now2.get(Calendar.MONTH) == o.getDateCreate().get(Calendar.MONTH)) {
				order4++;
			}
			now2.add(Calendar.MONTH, -1);
			if (now2.get(Calendar.MONTH) == o.getDateCreate().get(Calendar.MONTH)) {
				order3++;
			}
			now2.add(Calendar.MONTH, -1);
			if (now2.get(Calendar.MONTH) == o.getDateCreate().get(Calendar.MONTH)) {
				order2++;
			}
			now2.add(Calendar.MONTH, -1);
			if (now2.get(Calendar.MONTH) == o.getDateCreate().get(Calendar.MONTH)) {
				oder1++;
			}
		}
		
		return new Integer[]{oder1, order2, order3, order4, order5, order6, order7};
	}

	@Override
	public Long countLastOrders(final HttpServletRequest request) {
		final Calendar dtInit = Calendar.getInstance();
		dtInit.add(Calendar.DAY_OF_MONTH, -2);
		
		return repository.countBySiteAndDateCreateGreaterThan(EcommerceUtil.getInstance().getSessionAdmin(request).getSite(), dtInit);
	}

	@Override
	public Long countLastOrdersPayment(final HttpServletRequest request) {
		final Calendar dtInit = Calendar.getInstance();
		dtInit.add(Calendar.DAY_OF_MONTH, -2);
		
		return repository.countBySiteAndStatusOrder(EcommerceUtil.getInstance().getSessionAdmin(request).getSite(), 2);
	}

	@Override
	public List<Order> listLastOrders(final HttpServletRequest request) {
		final ClientAdmin user = EcommerceUtil.getInstance().getSessionAdmin(request);
		List<Order> orders;
		if (user.getPermission()<4) {
			orders = repository.findLastOrdersBySite(user.getSite(), new PageRequest(0, 10));
		} else {
			orders = new ArrayList<Order>();
		}
		return orders;
	}
	
}

