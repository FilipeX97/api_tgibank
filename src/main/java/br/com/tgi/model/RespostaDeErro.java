package br.com.tgi.model;

import java.time.LocalDateTime;
import java.time.ZoneId;

import lombok.Data;

@Data
public class RespostaDeErro {
	
	private LocalDateTime localDateTime;
    private int status;
    private String error;
    private String cause;
    
    public RespostaDeErro(int status, String error, String cause) {
        this.localDateTime = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
    	this.status = status;
    	this.error = error;
    	this.cause = cause;
    }

}
