package net.danielfreire.products.ecommerce.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.danielfreire.products.ecommerce.model.core.ClientEcommerceBusiness;
import net.danielfreire.util.PortalTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ClientController {
	
	@Autowired
	private transient ClientEcommerceBusiness business;
	
	@RequestMapping(value="/active", method = RequestMethod.GET)
	public void active(final HttpServletRequest request, final HttpServletResponse response) {
		try {
			business.active(request, response);
		} catch (Exception e) {
			PortalTools.getInstance().getRespError(e);
			try {
				response.getWriter().print("<html><head><title>Sistema indisponivel</title></head><body><script>alert('Sistema indisponível no momento, tente em alguns minutos.');</script></body></html>");
			} catch (IOException e1) {
				PortalTools.getInstance().getRespError(e);
			}
		}
	}

}
