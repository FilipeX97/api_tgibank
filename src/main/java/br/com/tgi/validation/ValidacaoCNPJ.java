package br.com.tgi.validation;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.InvalidStateException;

public class ValidacaoCNPJ {

	public static boolean validar(String cnpj) {
		CNPJValidator cnpjValidator = new CNPJValidator();
		try {
			cnpjValidator.assertValid(cnpj);
            return true;
        } catch (InvalidStateException e) {
            return false;
        }
	}
}
