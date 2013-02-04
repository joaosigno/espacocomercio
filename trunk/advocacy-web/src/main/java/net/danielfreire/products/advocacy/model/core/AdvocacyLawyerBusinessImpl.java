package net.danielfreire.products.advocacy.model.core;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.advocacy.model.domain.AdvocacyLawyer;
import net.danielfreire.products.advocacy.model.repository.AdvocacyLawyerRepository;
import net.danielfreire.products.advocacy.util.AdvocacyUtil;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;
import net.danielfreire.util.GridTitleResponse;
import net.danielfreire.util.PortalTools;
import net.danielfreire.util.ValidateTools;
import net.danielfreire.util.validator.Validator;
import net.danielfreire.util.validator.ValidatorCpf;
import net.danielfreire.util.validator.ValidatorGeneric;
import net.danielfreire.util.validator.ValidatorMail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component("advocacyLawyerBusiness")
public class AdvocacyLawyerBusinessImpl implements AdvocacyLawyerBusiness {
	
	@Autowired
	private AdvocacyLawyerRepository repository;

	@Override
	public GenericResponse save(HttpServletRequest request) throws Exception {
		final String id = request.getParameter("id");
		final String name = request.getParameter("name");
		final String email = request.getParameter("email");
		final String cpf = request.getParameter("cpf");
		final String cellphone = request.getParameter("cellphone");
		final String naturalidade = request.getParameter("naturalidade");
		final String estadocivil = request.getParameter("estadocivil");
		final String profissao = request.getParameter("profissao");
		
		HashMap<String, String> error = new HashMap<String, String>();
		
		Validator validator = new ValidatorGeneric();
		error.putAll(validator.isValid("name", name));
		error.putAll(new ValidatorMail().isValid("email", email));
		error.putAll(new ValidatorCpf().isValid("cpf", cpf));
		error.putAll(validator.isValid("cellphone", cellphone));
		error.putAll(validator.isValid("naturalidade", naturalidade));
		error.putAll(validator.isValid("estadocivil", estadocivil));
		error.putAll(validator.isValid("profissao", profissao));
		
		if (error.size()>0) {
			return PortalTools.getInstance().getRespError(error);
		} else {
			AdvocacyLawyer lawyer = new AdvocacyLawyer();
			
			if (ValidateTools.getInstancia().isNumber(id)) {
				lawyer.setId(Integer.parseInt(id));
			}
			
			lawyer.setCellphone(cellphone);
			lawyer.setCpf(cpf);
			lawyer.setEmail(email);
			lawyer.setName(name);
			lawyer.setEstadocivil(estadocivil);
			lawyer.setNaturalidade(naturalidade);
			lawyer.setProfissao(profissao);
			lawyer.setAdvocacyOffice(AdvocacyUtil.getInstancia().getSessionUser(request).getAdvocacyOffice());
			
			repository.save(lawyer);
			
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
		titles.add(PortalTools.getInstance().getRowGrid("cpf", "CPF", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("cellphone", "Celular", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("naturalidade", "naturalidadeidade", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("estadocivil", "Estado Civil", "text"));
		titles.add(PortalTools.getInstance().getRowGrid("profissao", "Profissão", "text"));
		
		Page<AdvocacyLawyer> pageable = repository.findByAdvocacyOffice(AdvocacyUtil.getInstancia().getSessionUser(request).getAdvocacyOffice(), new PageRequest(pagination, 10));
		
		return PortalTools.getInstance().getGrid(pageable.getContent(), titles, pageable.getNumber()+1, pageable.getTotalPages());
	}

	@Override
	public GenericResponse load(HttpServletRequest request) throws Exception {
		Integer id = Integer.parseInt(request.getParameter("id"));
		
		AdvocacyLawyer client = repository.findOne(id);
		
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
		
		AdvocacyLawyer client = repository.findOne(id);
		
		GenericResponse resp = new GenericResponse();
		if (AdvocacyUtil.getInstancia().getSessionUser(request).getAdvocacyOffice().getId()==client.getAdvocacyOffice().getId()) {
			repository.delete(client);
		} else {
			resp = PortalTools.getInstance().getRespError("permission.invalid");
		}
		
		return resp;
	}

}
