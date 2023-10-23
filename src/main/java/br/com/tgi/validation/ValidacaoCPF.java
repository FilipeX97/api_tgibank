package br.com.tgi.validation;


import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

public class ValidacaoCPF {
	
	public static boolean validar(String cpf) {
        CPFValidator cpfValidator = new CPFValidator();
        try {
            cpfValidator.assertValid(cpf);
            return true;
        } catch (InvalidStateException e) {
            return false;
        }
    }

}
