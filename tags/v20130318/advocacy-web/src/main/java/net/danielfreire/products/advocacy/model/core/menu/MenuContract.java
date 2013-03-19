package net.danielfreire.products.advocacy.model.core.menu;

import java.util.HashMap;
import java.util.Map;

import net.danielfreire.products.advocacy.model.domain.AdvocacyUser;

public class MenuContract extends Menu {

	@Override
	public Map<String, Object> get(int position, AdvocacyUser user) {
		Map<String, Object> menu = new HashMap<String, Object>();
		
		if (user.getViewContract()) {
			HashMap<String, Object> options = new HashMap<String, Object>();
			
			if (user.getManageContract()) options.put("Novo", "/advocacy/admin/contract/new");
			options.put("Consultar", "/advocacy/admin/contract/consult");
			
			menu.put("Contratos", generateSubmenu(position, "dropdown", "#", options));
		}
		
		return menu;
	}

}
