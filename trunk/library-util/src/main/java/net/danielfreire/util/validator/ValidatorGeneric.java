package net.danielfreire.util.validator;

import net.danielfreire.util.ValidateTools;

public class ValidatorGeneric extends Validator {

	@Override
	protected boolean validatorRules(Object value) {
		return ValidateTools.getInstancia().isNullEmpty(value.toString());
	}

	@Override
	protected String validatorError() {
		return "generic.invalid";
	}

	
}
