package net.danielfreire.products.advocacy.model.core.menu;

import java.util.HashMap;
import java.util.Map;

import net.danielfreire.products.advocacy.model.domain.AdvocacyUser;

public class MenuOffice extends Menu {

	@Override
	public Map<String, Object> get(int position, AdvocacyUser user) {
		Map<String, Object> menu = new HashMap<String, Object>();
		
		if (user.getManageOffice()) {
		
			Map<String, Object> options = new HashMap<String, Object>();
			if (user.getUsername().equals("daniel@danielfreire.net")) {
				options.put("Novo", "/advocacy/admin/office/new");
				options.put("Consultar", "/advocacy/admin/office/consult");
			} else {
				options.put("Configurações", "/advocacy/admin/office/config");
			}
			
			if (user.getManageUser()) options.put("Usuários", "/advocacy/admin/office/user/manage");
			
			menu.put("Escritório", generateSubmenu(position, "dropdown", "#", options));
		
		}
		
		return menu;
	}

}
