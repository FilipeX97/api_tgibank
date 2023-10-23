package br.com.tgi.model;

import java.time.LocalDateTime;
import java.time.ZoneId;

import br.com.tgi.exception.CPFInvalidoException;
import br.com.tgi.exception.EmailInvalidoException;
import br.com.tgi.validation.ValidacaoCPF;
import br.com.tgi.validation.ValidacaoEmail;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idCliente;
	@Column(unique = true, nullable = false, length = 14)
	private String cpf;
	@Column(nullable = false, length = 255)
	private String nome;
	@ManyToOne
	@JoinColumn(name = "id_empresa", nullable = false)
	private Empresa empresa;
	@Column(nullable = false, length = 255)
	private String email;
	@Column(length = 20)
	private String telefone;
	@Column(nullable = false)
	private LocalDateTime criadoEm;
	@Column(nullable = false)
	private LocalDateTime atualizadoEm;
	
	public Cliente() {}

	public Cliente(String cpf, String nome, Empresa empresa, String email, String telefone) {
		if (!ValidacaoCPF.validar(cpf)) {
            throw new CPFInvalidoException();
        }
		
		if(!ValidacaoEmail.validar(email)) {
        	throw new EmailInvalidoException();
        }
		
		this.cpf = cpf;
		this.nome = nome;
		this.empresa = empresa;
		this.email = email;
		this.telefone = telefone;
		this.criadoEm = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
		this.atualizadoEm = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
	}

}
