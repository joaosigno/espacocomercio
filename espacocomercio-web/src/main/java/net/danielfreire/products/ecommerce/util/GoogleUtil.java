package net.danielfreire.products.ecommerce.util;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;

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

	private static final String KEY_ECO_DOMAIN = "google.domain";
	private static final String KEY_GOOGLE_USER = "account.google.user";
	private static final String KEY_GOOGLE_PASS = "account.google.password";

	public void createGoogleAccount(final String name, final String siteIdString) throws java.lang.Exception {
		final AppsForYourDomainClient client = new AppsForYourDomainClient(
				PortalTools.getInstance().getEcommerceProperties(KEY_GOOGLE_USER), 
				PortalTools.getInstance().getEcommerceProperties(KEY_GOOGLE_PASS), 
				PortalTools.getInstance().getEcommerceProperties(KEY_ECO_DOMAIN));
		client.createUser(siteIdString, name, "- Espaço Comércio", PortalTools.PASS_AUTH_PATTERN);
	}
	
	public void removeGoogleAccount(final String siteIdString) throws java.lang.Exception {
		final AppsForYourDomainClient client = new AppsForYourDomainClient(
				PortalTools.getInstance().getEcommerceProperties(KEY_GOOGLE_USER), 
				PortalTools.getInstance().getEcommerceProperties(KEY_GOOGLE_PASS), 
				PortalTools.getInstance().getEcommerceProperties(KEY_ECO_DOMAIN));
		client.deleteUser(siteIdString);
	}
	
    public void createContact(final Site site, final ClientEcommerce client) throws java.lang.Exception {
    	updatePassword(site);
    	
    	final ContactsService myService = new ContactsService("RupalMindfire-AddressApp");
    	myService.setUserCredentials(
    			ConvertTools.getInstance().normalizeString(site.getName()) + "@" + PortalTools.getInstance().getEcommerceProperties(KEY_ECO_DOMAIN), 
    			site.getGmailPass());
    	
        final ContactEntry contact = new ContactEntry();
        final Name name = new Name();
        name.setFullName(new FullName(client.getName(), null));
        name.setGivenName(new GivenName(client.getName().split(" ").length>1?client.getName().split(" ")[0]:client.getName(), null));
        name.setFamilyName(new FamilyName(client.getName().split(" ").length>1?client.getName().split(" ")[client.getName().split(" ").length-1]:"", null));
        contact.setName(name);
        contact.setContent(new PlainTextConstruct(new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(client.getCreationDate().getTime())));

        final Email primaryMail = new Email();
        primaryMail.setAddress(client.getUser());
        primaryMail.setRel("http://schemas.google.com/g/2005#home");
        primaryMail.setPrimary(true);
        contact.addEmailAddress(primaryMail);

        final StructuredPostalAddress address = new StructuredPostalAddress();
        address.setCity(new City(client.getAddressCity()));
        address.setCountry(new Country("BR", "BRASIL"));
        address.setFormattedAddress(new FormattedAddress(client.getAddressStreet() + ", " + client.getAddressNumber()));
        address.setNeighborhood(new Neighborhood(""));
        address.setPostcode(new PostCode(client.getAddressZipcode()));
        address.setStreet(new Street(client.getAddressStreet()));
        address.setPrimary(true);
        contact.addStructuredPostalAddress(address);

        final URL postUrl = new URL("https://www.google.com/m8/feeds/contacts/default/full");
        myService.insert(postUrl, contact);
    }

	public void updatePassword(Site site) throws java.lang.Exception {
		final AppsForYourDomainClient admin = new AppsForYourDomainClient(
				PortalTools.getInstance().getEcommerceProperties(KEY_GOOGLE_USER), 
				PortalTools.getInstance().getEcommerceProperties(KEY_GOOGLE_PASS), 
				PortalTools.getInstance().getEcommerceProperties(KEY_ECO_DOMAIN));
    	final UserEntry userEntry = admin.retrieveUser(ConvertTools.getInstance().normalizeString(site.getName()) + "@" + PortalTools.getInstance().getEcommerceProperties(KEY_ECO_DOMAIN));
    	userEntry.getLogin().setPassword(site.getGmailPass());
    	admin.updateUser(ConvertTools.getInstance().normalizeString(site.getName()) + "@" + PortalTools.getInstance().getEcommerceProperties(KEY_ECO_DOMAIN), userEntry);		
	}
	 
	 
}
