package net.danielfreire.products.advocacy.model.core.menu;

import java.util.HashMap;
import java.util.Map;

import net.danielfreire.products.advocacy.model.domain.AdvocacyUser;

public class MenuOut extends Menu {

	@Override
	public Map<String, Object> get(int position, AdvocacyUser user) {
		Map<String, Object> menu = new HashMap<String, Object>();
		
		menu.put("Sair", generateSubmenu(position, "link", "/advocacy-web/logout", null));
		
		return menu;
	}

}
