package net.danielfreire.util.validator;

import net.danielfreire.util.ValidateTools;

public class ValidatorMail extends Validator {

	@Override
	protected boolean validatorRules(Object value) {
		return !ValidateTools.getInstancia().isEmail(value.toString());
	}

	@Override
	protected String validatorError() {
		return "email.invalid";
	}

}
