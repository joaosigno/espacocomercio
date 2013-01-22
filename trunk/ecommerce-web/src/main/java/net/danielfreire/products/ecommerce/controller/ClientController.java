package net.danielfreire.products.ecommerce.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.danielfreire.products.ecommerce.model.core.ClientEcommerceBusiness;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;
import net.danielfreire.util.PortalTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ClientController {
	
	@Autowired
	ClientEcommerceBusiness clientBusiness;
	
	@RequestMapping(value="/admin", method = RequestMethod.GET)
	public @ResponseBody void categoryinsert(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			String tk = PortalTools.getInstance().Decode(request.getParameter("tk"));
			String token = tk.split("---")[0];
			
			response.setContentType("text/html");
		    PrintWriter out = response.getWriter();
			
			if (PortalTools.getInstance().validToken(token)) {
				String siteId = tk.split("---")[1];
				
				request.getSession().setAttribute(PortalTools.getInstance().idAdminSession, siteId);
				
				out.println("<HTML><HEAD><script>location.href='/ecommerce/admin';</script></HEAD><BODY></BODY></HTML>");
			} else {
			    out.println("<HTML><HEAD><script>location.href='/sso';</script></HEAD><BODY></BODY></HTML>");
			}
			
		} catch (Exception e) {
			PortalTools.getInstance().getRespError(e);
			try {
				response.setContentType("text/html");
			    PrintWriter out = response.getWriter();
	
			    out.println("<HTML><HEAD><script>location.href='/sso';</script></HEAD><BODY></BODY></HTML>");
			} catch (Exception ex) {
				PortalTools.getInstance().getRespError(ex);
			}
		}
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public @ResponseBody void logout(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			request.getSession().removeAttribute(PortalTools.getInstance().idAdminMasterSession);
			request.getSession().removeAttribute(PortalTools.getInstance().idSession);
			request.getSession().removeAttribute(PortalTools.getInstance().idAdminSession);
			
			response.setContentType("text/html");
		    PrintWriter out = response.getWriter();

		    out.println("<HTML><HEAD><script>location.href='/sso';</script></HEAD><BODY></BODY></HTML>");
			
		} catch (Exception e) {
			PortalTools.getInstance().getRespError(e);
			try {
				response.setContentType("text/html");
			    PrintWriter out = response.getWriter();
	
			    out.println("<HTML><HEAD><script>location.href='/sso';</script></HEAD><BODY></BODY></HTML>");
			} catch (Exception ex) {
				PortalTools.getInstance().getRespError(ex);
			}
		}
	}
	
	@RequestMapping(value="/client/admin/consult", method = RequestMethod.GET)
	public @ResponseBody GridResponse categoryconsult(HttpServletRequest request) {
		try {
			return clientBusiness.consult(request);
		} catch (Exception e) {
			PortalTools.getInstance().getRespError(e);
			return new GridResponse();
		}
	}
	
	@RequestMapping(value="/client/admin/update", method = RequestMethod.POST)
	public @ResponseBody GenericResponse clientupdate(HttpServletRequest request) {
		try {
			return clientBusiness.updateAdmin(request);
		} catch (Exception e) {
			PortalTools.getInstance().getRespError(e);
			return new GenericResponse();
		}
	}
	
	@RequestMapping(value="/admin/client/list", method = RequestMethod.GET)
	public @ResponseBody GenericResponse list(HttpServletRequest request) {
		try {
			return clientBusiness.list(request);
		} catch (Exception e) {
			return PortalTools.getInstance().getRespError(e);
		}
	}

}
