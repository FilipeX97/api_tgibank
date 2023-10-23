package br.com.tgi.exception;

public class EnvioEmailException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public EnvioEmailException() {
        super("Falha ao enviar e-mail");
    }

}
