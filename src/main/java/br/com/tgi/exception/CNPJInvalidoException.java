package br.com.tgi.exception;

public class CNPJInvalidoException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public CNPJInvalidoException() {
        super("CNPJ inválido");
    }

}
