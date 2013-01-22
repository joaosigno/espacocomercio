package net.danielfreire.products.sso.model.core;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import net.danielfreire.products.sso.model.domain.Client;
import net.danielfreire.products.sso.model.domain.Site;
import net.danielfreire.products.sso.model.repository.SiteRepository;
import net.danielfreire.util.GenericResponse;
import net.danielfreire.util.GridResponse;
import net.danielfreire.util.GridTitleResponse;
import net.danielfreire.util.PortalTools;
import net.danielfreire.util.ValidateTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component("siteBusiness")
public class SiteBusinessImpl implements SiteBusiness {
	
	@Autowired
	private SiteRepository siteDAO;

	@Override
	public GenericResponse update(String nome, String desc,
			HttpServletRequest request) {
		GenericResponse resp = new GenericResponse();
		
		if (ValidateTools.getInstancia().isNullEmpty(nome))
			resp = PortalTools.getInstance().getRespError("data.invalid");
		
		if (resp.getStatus()) {
			try {
				Client clientSession = (Client) request.getSession().getAttribute(PortalTools.getInstance().idSession); 
				
				Site site = (Site) siteDAO.findOne(clientSession.getSite().getId());
				site.setDescription(desc);
				site.setName(nome);
				siteDAO.save(site);
			} catch (Exception e) {
				resp = PortalTools.getInstance().getRespError(e);
			}
		}
		
		return resp;
	}

	@Override
	public GenericResponse insert(HttpServletRequest request) {
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String urladm = request.getParameter("urladm");
		String urlsite = request.getParameter("urlsite");
		
		Site site = new Site();
		site.setDescription(description);
		site.setName(name);
		site.setUrladm(urladm);
		site.setUrlsite(urlsite);
		
		GenericResponse resp = siteIsValid(site);
		
		if (resp.getStatus()) {
			siteDAO.save(site);
		}
		
		return resp;
	}
	
	private GenericResponse siteIsValid(Site site) {
		HashMap<String, String> errors = new HashMap<String, String>();
		
		if (ValidateTools.getInstancia().isNullEmpty(site.getName())) {
			errors.put("name", "Preencha o campo \"nome\" corretamente.");
		}
		if (!ValidateTools.getInstancia().isUrl(site.getUrladm())) {
			errors.put("urladm", "Preencha o campo \"URL Administração\" com um site válido.");
		}
		if (!ValidateTools.getInstancia().isUrl(site.getUrlsite())) {
			errors.put("urlsite", "Preencha o campo \"URL Site\" com um site válido.");
		}
		
		GenericResponse resp = new GenericResponse();
		if (errors.size()>0) {
			resp.setStatus(false);
			resp.setMessageError(errors);
		} 
		
		return resp;
	}

	@Override
	public GridResponse consult(HttpServletRequest request) {
		String page = request.getParameter("page");
		int pagination = 0;
		
		if (ValidateTools.getInstancia().isNumber(page)) {
			pagination = Integer.parseInt(page)-1;
		} 
		
		ArrayList<GridTitleResponse> titles = new ArrayList<GridTitleResponse>();
		
		GridTitleResponse title = new GridTitleResponse();
		title.setId("name");
		title.setTitle("Nome");
		title.setType("text");
		titles.add(title);
		
		title = new GridTitleResponse();
		title.setId("description");
		title.setTitle("Descrição");
		title.setType("text");
		titles.add(title);
		
		title = new GridTitleResponse();
		title.setId("urladm");
		title.setTitle("URL Administração");
		title.setType("text");
		titles.add(title);
		
		title = new GridTitleResponse();
		title.setId("urlsite");
		title.setTitle("URL Site");
		title.setType("text");
		titles.add(title);
		
		Page<Site> pageable = siteDAO.findAll(new PageRequest(pagination, 10));
		
		GridResponse grid = new GridResponse();
		grid.setRows(pageable.getContent());
		grid.setTitles(titles);
		grid.setPage(pageable.getNumber()+1);
		grid.setTotalPages(pageable.getTotalPages());
		
		return grid;
	}

	@Override
	public GenericResponse update(HttpServletRequest request) {
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String urladm = request.getParameter("urladm");
		String urlsite = request.getParameter("urlsite");
		Integer id = Integer.parseInt(request.getParameter("id"));
		
		Site site = new Site();
		site.setDescription(description);
		site.setName(name);
		site.setUrladm(urladm);
		site.setUrlsite(urlsite);
		site.setId(id);
		
		GenericResponse resp = siteIsValid(site);
		
		if (resp.getStatus()) {
			siteDAO.save(site);
		}
		
		return resp;
	}

	@Override
	public GenericResponse remove(HttpServletRequest request) {
		GenericResponse resp = new GenericResponse();
		
		Integer id = Integer.parseInt(request.getParameter("id"));
		
		try {
			siteDAO.delete(id);
		} catch (Exception e) {
			resp = PortalTools.getInstance().getRespError(e);
		}
		
		return resp;
	}

}
