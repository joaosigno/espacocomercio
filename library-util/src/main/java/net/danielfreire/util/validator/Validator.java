package net.danielfreire.util.validator;

import java.util.HashMap;
import java.util.Map;

import net.danielfreire.util.PortalTools;

public abstract class Validator {

	public Map<String, String> isValid(String name, Object value) {
		Map<String, String> map = new HashMap<String, String>();
		
		if (validatorRules(value)) {
			map.put(name, PortalTools.getInstance().getMessage(validatorError()));
		}
		
		return map;
	}

	protected abstract boolean validatorRules(Object value);

	protected abstract String validatorError();
	
}
