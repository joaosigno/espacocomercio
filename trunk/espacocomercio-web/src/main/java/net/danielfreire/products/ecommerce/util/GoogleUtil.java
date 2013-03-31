package net.danielfreire.products.ecommerce.util;

import net.danielfreire.util.PortalTools;
import sample.appsforyourdomain.AppsForYourDomainClient;

public class GoogleUtil {

	public void createGoogleAccount(String name, String siteIdString) throws Exception {
		AppsForYourDomainClient client = new AppsForYourDomainClient(
				PortalTools.getInstance().getEcommerceProperties("account.google.user"), 
				PortalTools.getInstance().getEcommerceProperties("account.google.password"), 
				PortalTools.getInstance().getEcommerceProperties("ecommerce.domain"));
		client.createUser(siteIdString, name, "- Espaço Comércio", "abc1234");
	}

}
