package net.danielfreire.util.validator;

import net.danielfreire.util.ValidateTools;

public class ValidatorCep extends Validator {

	@Override
	protected boolean validatorRules(Object value) {
		return !ValidateTools.getInstancia().isCep(value.toString());
	}

	@Override
	protected String validatorError() {
		return "addressZipcode.invalid";
	}

}
