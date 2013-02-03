package net.danielfreire.products.advocacy.model.core;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.advocacy.model.domain.AdvocacyClient;
import net.danielfreire.products.advocacy.model.repository.AdvocacyClientRepository;
import net.danielfreire.products.advocacy.util.AdvocacyUtil;
import net.danielfreire.util.ConvertTools;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.PortalTools;
import net.danielfreire.util.validator.Validator;
import net.danielfreire.util.validator.ValidatorCep;
import net.danielfreire.util.validator.ValidatorCpf;
import net.danielfreire.util.validator.ValidatorDate;
import net.danielfreire.util.validator.ValidatorGeneric;
import net.danielfreire.util.validator.ValidatorMail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("advocacyClientBusiness")
public class AdvocacyClientBusinessImpl implements AdvocacyClientBusiness {
	
	@Autowired
	private AdvocacyClientRepository repository;

	@Override
	public GenericResponse save(HttpServletRequest request) throws Exception {
		final String name = request.getParameter("name");
		final String email = request.getParameter("email");
		final String rg = request.getParameter("rg");
		final String cpf = request.getParameter("cpf");
		final String born = request.getParameter("born");
		final String phone = request.getParameter("phone");
		final String cellphone = request.getParameter("cellphone");
		final String addressStreet = request.getParameter("addressStreet");
		final String addressCity = request.getParameter("addressCity");
		final String addressZipcode = request.getParameter("addressZipcode");
		final String addressNumber = request.getParameter("addressNumber");
		final String addressComplement = request.getParameter("addressComplement");
		
		HashMap<String, String> error = new HashMap<String, String>();
		
		Validator validator = new ValidatorGeneric();
		error.putAll(validator.isValid("name", name));
		error.putAll(new ValidatorMail().isValid("email", email));
		error.putAll(new ValidatorCpf().isValid("cpf", cpf));
		error.putAll(new ValidatorDate().isValid("born", born));
		error.putAll(validator.isValid("cellphone", cellphone));
		error.putAll(validator.isValid("addressStreet", addressStreet));
		error.putAll(validator.isValid("addressCity", addressCity));
		error.putAll(validator.isValid("addressNumber", addressNumber));
		error.putAll(new ValidatorCep().isValid("addressZipcode", addressZipcode));
		
		if (error.size()>0) {
			return PortalTools.getInstance().getRespError(error);
		} else {
			AdvocacyClient client = new AdvocacyClient();
			client.setAddressCity(addressCity);
			client.setAddressComplement(addressComplement);
			client.setAddressNumber(addressNumber);
			client.setAddressStreet(addressStreet);
			client.setAddressZipcode(addressZipcode);
			client.setBorn(ConvertTools.getInstance().convertDate(born, "dd/MM/yyyy"));
			client.setCellphone(cellphone);
			client.setCpf(cpf);
			client.setEmail(email);
			client.setName(name);
			client.setPhone(phone);
			client.setRg(rg);
			client.setAdvocacyOffice(AdvocacyUtil.getInstancia().getSessionUser(request).getAdvocacyOffice());
			
			repository.save(client);
			
			return new GenericResponse();
		}
	}

}
