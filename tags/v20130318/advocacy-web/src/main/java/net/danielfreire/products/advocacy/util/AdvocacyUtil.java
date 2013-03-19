package net.danielfreire.products.advocacy.util;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.advocacy.model.domain.AdvocacyOffice;
import net.danielfreire.products.advocacy.model.domain.AdvocacyUser;
import net.danielfreire.util.PortalTools;

public class AdvocacyUtil {
	
	private static AdvocacyUtil util = new AdvocacyUtil();
	
	public static synchronized AdvocacyUtil getInstancia() {
        return util;
    }
	
	public void sessionUser(HttpServletRequest request, AdvocacyUser advocacyUser) {
		advocacyUser.setUserpassword("***");
		request.getSession().setAttribute(PortalTools.getInstance().idAdminSession, advocacyUser);
	}
	
	public AdvocacyUser getSessionUser(HttpServletRequest request) {
		return (AdvocacyUser) request.getSession().getAttribute(PortalTools.getInstance().idAdminSession);
	}
	
	public AdvocacyUser getUserMaster() {
		AdvocacyOffice advocacyOffice = new AdvocacyOffice();
		advocacyOffice.setName("Administração");
		
		AdvocacyUser advocacyUser = new AdvocacyUser();
		advocacyUser.setAdvocacyOffice(advocacyOffice);
		advocacyUser.setId(0);
		advocacyUser.setUsername("daniel@danielfreire.net");
		advocacyUser.setManageClient(true);
		advocacyUser.setManageContract(true);
		advocacyUser.setManageFinance(true);
		advocacyUser.setManageOffice(true);
		advocacyUser.setManageUser(true);
		advocacyUser.setViewClient(true);
		advocacyUser.setViewContract(true);
		advocacyUser.setViewLawyer(true);
		advocacyUser.setManageLawyer(true);
		
		return advocacyUser;
	}
	
}
