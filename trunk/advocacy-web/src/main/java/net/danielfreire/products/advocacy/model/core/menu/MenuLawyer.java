package net.danielfreire.products.advocacy.model.core.menu;

import java.util.HashMap;
import java.util.Map;

import net.danielfreire.products.advocacy.model.domain.AdvocacyUser;

public class MenuLawyer extends Menu {

	@Override
	public Map<String, Object> get(int position, AdvocacyUser user) {
		Map<String, Object> menu = new HashMap<String, Object>();
		
		if (user.getViewLawyer()) {
			Map<String, Object> options = new HashMap<String, Object>();
			
			if (user.getManageLawyer()) options.put("Novo", "/advocacy/admin/lawyer/new");
			options.put("Consultar", "/advocacy/admin/lawyer/consult");
			
			menu.put("Advogados", generateSubmenu(position, "dropdown", "#", options));
		}
		
		return menu;
	}

}
