package net.danielfreire.products.advocacy.model.core.menu;

import java.util.HashMap;
import java.util.Map;

import net.danielfreire.products.advocacy.model.domain.AdvocacyUser;

public class MenuClient extends Menu {

	@Override
	public Map<String, Object> get(int position, AdvocacyUser user) {
		Map<String, Object> menu = new HashMap<String, Object>();
		
		if (user.getViewClient()) {
			HashMap<String, Object> options = new HashMap<String, Object>();
			
			if (user.getManageClient()) options.put("Novo", "/advocacy/admin/client/new");
			options.put("Consultar", "/advocacy/admin/client/consult");
			
			menu.put("Clientes", generateSubmenu(position, "dropdown", "#", options));
		}
		
		return menu;
	}

}
