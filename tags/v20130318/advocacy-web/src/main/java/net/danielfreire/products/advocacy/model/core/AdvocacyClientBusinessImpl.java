package net.danielfreire.products.advocacy.model.core;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.advocacy.model.domain.AdvocacyClient;
import net.danielfreire.products.advocacy.model.repository.AdvocacyClientRepository;
import net.danielfreire.products.advocacy.util.AdvocacyUtil;
import net.danielfreire.util.ConvertTools;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;
import net.danielfreire.util.GridTitleResponse;
import net.danielfreire.util.PortalTools;
import net.danielfreire.util.ValidateTools;
import net.danielfreire.util.validator.Validator;
import net.danielfreire.util.validator.ValidatorCep;
import net.danielfreire.util.validator.ValidatorCpf;
import net.danielfreire.util.validator.ValidatorDate;
import net.danielfreire.util.validator.ValidatorGeneric;
import net.danielfreire.util.validator.ValidatorMail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component("advocacyClientBusiness")
public class AdvocacyClientBusinessImpl implements AdvocacyClientBusiness {
	
	@Autowired
	private AdvocacyClientRepository repository;

	@Override
	public GenericResponse save(HttpServletRequest request) throws Exception {
		final String id = request.getParameter("id");
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
			
			if (ValidateTools.getInstancia().isNumber(id)) {
				client.setId(Integer.parseInt(id));
			}
			
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

	@Override
	public GridResponse consult(HttpServletRequest request) throws Exception {
		String page = request.getParameter("page");
		
		int pagination = 0;
		
		if (ValidateTools.getInstancia().isNumber(page)) {
			pagination = Integer.parseInt(page)-1;
		} 
		
		ArrayList<GridTitleResponse> titles = new ArrayList<GridTitleResponse>();
		titles.add(PortalTools.getInstance().getRowGrid("name", "Nome", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("email", "E-mail", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("rg", "RG", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("cpf", "CPF", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("born", "Data de Nasc.", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("phone", "Telefone", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("cellphone", "Celular", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("address", "Endereço", "text"));
		
		Page<AdvocacyClient> pageable = repository.findByAdvocacyOffice(AdvocacyUtil.getInstancia().getSessionUser(request).getAdvocacyOffice(), new PageRequest(pagination, 10));
		
		return PortalTools.getInstance().getGrid(pageable.getContent(), titles, pageable.getNumber()+1, pageable.getTotalPages());
	}

	@Override
	public GenericResponse load(HttpServletRequest request) throws Exception {
		Integer id = Integer.parseInt(request.getParameter("id"));
		
		AdvocacyClient client = repository.findOne(id);
		
		GenericResponse resp = new GenericResponse();
		if (AdvocacyUtil.getInstancia().getSessionUser(request).getAdvocacyOffice().getId()==client.getAdvocacyOffice().getId()) {
			resp.setGeneric(client);
		} else {
			resp = PortalTools.getInstance().getRespError("permission.invalid");
		}
		
		return resp;
	}

	@Override
	public GenericResponse remove(HttpServletRequest request) throws Exception {
		Integer id = Integer.parseInt(request.getParameter("id"));
		
		AdvocacyClient client = repository.findOne(id);
		
		GenericResponse resp = new GenericResponse();
		if (AdvocacyUtil.getInstancia().getSessionUser(request).getAdvocacyOffice().getId()==client.getAdvocacyOffice().getId()) {
			repository.delete(client);
		} else {
			resp = PortalTools.getInstance().getRespError("permission.invalid");
		}
		
		return resp;
	}

}
