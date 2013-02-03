package net.danielfreire.products.advocacy.model.core.menu;

import java.util.HashMap;
import java.util.Map;

import net.danielfreire.products.advocacy.model.domain.AdvocacyUser;

public class MenuFinance extends Menu {

	@Override
	public Map<String, Object> get(int position, AdvocacyUser user) {
		Map<String, Object> menu = new HashMap<String, Object>();
		
		if (user.getManageFinance()) {
			HashMap<String, Object> options = new HashMap<String, Object>();
			options.put("Categorias", "/advocacy/admin/finance/category");
			options.put("Contas a pagar", "/advocacy/admin/finance/expenses");
			options.put("Contas a receber", "/advocacy/admin/finance/revenue");
			
			menu.put("Finanças", generateSubmenu(position, "dropdown", "#", options));
		}
		
		return menu;
	}

}
