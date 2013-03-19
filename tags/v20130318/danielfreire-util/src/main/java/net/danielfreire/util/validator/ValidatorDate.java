package net.danielfreire.util.validator;

import net.danielfreire.util.ValidateTools;

public class ValidatorDate extends Validator {

	@Override
	protected boolean validatorRules(Object value) {
		return !ValidateTools.getInstancia().isStringDate(value.toString());
	}

	@Override
	protected String validatorError() {
		return "date.invalid";
	}

}
