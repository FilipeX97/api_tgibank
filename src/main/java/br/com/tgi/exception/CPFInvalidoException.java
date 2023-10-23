package br.com.tgi.exception;

public class CPFInvalidoException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public CPFInvalidoException() {
        super("CPF inválido");
    }

}
