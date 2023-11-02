package br.com.tgi.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

import br.com.tgi.exception.CNPJInvalidoException;
import br.com.tgi.validation.ValidacaoCNPJ;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Empresa {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idEmpresa;
	@Column(unique = true, nullable = false, length = 18)
	private String cnpj;
	@Column(nullable = false, length = 255)
	private String nome;
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal saldo;
	@Column(nullable = false)
	private double taxaSistema;
	@Column(nullable = true)
	private LocalDateTime criadoEm;
	@Column(nullable = true)
	private LocalDateTime atualizadoEm;
	
	public Empresa() {}

	public Empresa(String cnpj, String nome, BigDecimal saldo, double taxaSistema) {
		if (!ValidacaoCNPJ.validar(cnpj)) {
	        throw new CNPJInvalidoException();
	    }
		
		this.cnpj = cnpj;
		this.nome = nome;
		this.saldo = saldo;
		this.taxaSistema = taxaSistema;
		this.criadoEm = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
		this.atualizadoEm = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
	}

}
