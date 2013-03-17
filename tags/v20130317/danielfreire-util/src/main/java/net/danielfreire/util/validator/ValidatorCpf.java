package net.danielfreire.util.validator;

import net.danielfreire.util.ValidateTools;

public class ValidatorCpf extends Validator {

	@Override
	protected boolean validatorRules(Object value) {
		return !ValidateTools.getInstancia().isCPF(value.toString());
	}

	@Override
	protected String validatorError() {
		return "cpf.invalid";
	}

}
