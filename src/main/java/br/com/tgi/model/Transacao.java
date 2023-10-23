package br.com.tgi.model;

import java.time.LocalDateTime;
import java.time.ZoneId;

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
public class Transacao {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idTransacao;
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;
    @Column(nullable = false)
    private String tipoTransacao;
    @Column(nullable = false)
    private int status;
    @Column(nullable = false)
    private LocalDateTime dataTransacao;
    
    public Transacao() {}
    
	public Transacao(Cliente cliente, String tipoTransacao, int status) {
		this.cliente = cliente;
		this.tipoTransacao = tipoTransacao;
		this.status = status;
		this.dataTransacao = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
	}
    
    
	
	
}
