package net.danielfreire.products.ecommerce.util;

import java.net.URL;
import java.text.SimpleDateFormat;

import net.danielfreire.products.ecommerce.model.domain.ClientEcommerce;
import net.danielfreire.products.ecommerce.model.domain.Site;
import net.danielfreire.util.ConvertTools;
import net.danielfreire.util.PortalTools;
import sample.appsforyourdomain.AppsForYourDomainClient;

import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.appsforyourdomain.provisioning.UserEntry;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.extensions.City;
import com.google.gdata.data.extensions.Country;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.FamilyName;
import com.google.gdata.data.extensions.FormattedAddress;
import com.google.gdata.data.extensions.FullName;
import com.google.gdata.data.extensions.GivenName;
import com.google.gdata.data.extensions.Name;
import com.google.gdata.data.extensions.Neighborhood;
import com.google.gdata.data.extensions.PostCode;
import com.google.gdata.data.extensions.Street;
import com.google.gdata.data.extensions.StructuredPostalAddress;

public class GoogleUtil {

	private static final String PASS_AUTH_PATTERN = "oik5683kdm344##";

	public void createGoogleAccount(String name, String siteIdString) throws Exception {
		AppsForYourDomainClient client = new AppsForYourDomainClient(
				PortalTools.getInstance().getEcommerceProperties("account.google.user"), 
				PortalTools.getInstance().getEcommerceProperties("account.google.password"), 
				PortalTools.getInstance().getEcommerceProperties("ecommerce.domain"));
		client.createUser(siteIdString, name, "- Espaço Comércio", PASS_AUTH_PATTERN);
	}
	
    public void createContact(Site site, ClientEcommerce client) throws Exception {
    	AppsForYourDomainClient admin = new AppsForYourDomainClient(
				PortalTools.getInstance().getEcommerceProperties("account.google.user"), 
				PortalTools.getInstance().getEcommerceProperties("account.google.password"), 
				PortalTools.getInstance().getEcommerceProperties("ecommerce.domain"));
    	UserEntry u = admin.retrieveUser(ConvertTools.getInstance().normalizeString(site.getName()) + "@" + PortalTools.getInstance().getEcommerceProperties("ecommerce.domain"));
    	u.getLogin().setPassword(PASS_AUTH_PATTERN);
    	admin.updateUser(ConvertTools.getInstance().normalizeString(site.getName()) + "@" + PortalTools.getInstance().getEcommerceProperties("ecommerce.domain"), u);
    	
    	ContactsService myService = new ContactsService("RupalMindfire-AddressApp");
    	myService.setUserCredentials(
    			ConvertTools.getInstance().normalizeString(site.getName()) + "@" + PortalTools.getInstance().getEcommerceProperties("ecommerce.domain"), 
    			PASS_AUTH_PATTERN);
    	
        ContactEntry contact = new ContactEntry();
        Name name = new Name();
        final String NO_YOMI = null;
        name.setFullName(new FullName(client.getName(), NO_YOMI));
        name.setGivenName(new GivenName(client.getName().split(" ").length>1?client.getName().split(" ")[0]:client.getName(), NO_YOMI));
        name.setFamilyName(new FamilyName(client.getName().split(" ").length>1?client.getName().split(" ")[client.getName().split(" ").length-1]:"", NO_YOMI));
        contact.setName(name);
        contact.setContent(new PlainTextConstruct(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(client.getCreationDate().getTime())));

        Email primaryMail = new Email();
        primaryMail.setAddress(client.getUser());
        primaryMail.setRel("http://schemas.google.com/g/2005#home");
        primaryMail.setPrimary(true);
        contact.addEmailAddress(primaryMail);

        StructuredPostalAddress address = new StructuredPostalAddress();
        address.setCity(new City(client.getAddressCity()));
        address.setCountry(new Country("BR", "BRASIL"));
        address.setFormattedAddress(new FormattedAddress(client.getAddressStreet() + ", " + client.getAddressNumber()));
        address.setNeighborhood(new Neighborhood(""));
        address.setPostcode(new PostCode(client.getAddressZipcode()));
        address.setStreet(new Street(client.getAddressStreet()));
        address.setPrimary(true);
        contact.addStructuredPostalAddress(address);

        URL postUrl = new URL("https://www.google.com/m8/feeds/contacts/default/full");
        myService.insert(postUrl, contact);
    }
	 
	 
}
