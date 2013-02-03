package net.danielfreire.products.advocacy.model.core.menu;

import java.util.HashMap;
import java.util.Map;

import net.danielfreire.products.advocacy.model.domain.AdvocacyUser;

public abstract class Menu {
	
	public abstract Map<String, Object> get(int position, AdvocacyUser user);
	
	protected final Map<String, Object> generateSubmenu(Integer position, String type, String url, Map<String, Object> options) {
		Map<String, Object> submenu = new HashMap<String, Object>();
		submenu.put("position", position.toString());
		submenu.put("type", type);
		submenu.put("url", url);
		submenu.put("options", options);
		
		return submenu;
	}

}
