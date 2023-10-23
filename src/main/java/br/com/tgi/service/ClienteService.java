package br.com.tgi.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.tgi.exception.SaldoInsuficienteException;
import br.com.tgi.model.Cliente;
import br.com.tgi.model.Empresa;
import br.com.tgi.repository.ClienteRepository;
import br.com.tgi.util.FormatarDoubleParaReal;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private ObjectMapper objectMapper;

	public Cliente pegarClientePorId(Long id) {
		return clienteRepository.findById(id).get();
	}

	public Cliente salvarCliente(String clienteJson, Empresa empresa)
			throws JsonMappingException, JsonProcessingException {
		JsonNode jsonNode = objectMapper.readTree(clienteJson);
		Cliente cliente = new Cliente(jsonNode.get("cpf").asText(), jsonNode.get("nome").asText(), empresa,
				jsonNode.get("email").asText(), jsonNode.get("telefone").asText());
		return clienteRepository.save(cliente);
	}
	
	public Cliente realizarSaque(Cliente cliente, double valor) {
		BigDecimal valorSacado = new BigDecimal(
				valor + cliente.getEmpresa().getTaxaSistema());

		if (cliente.getEmpresa().getSaldo().compareTo(valorSacado) < 0) {
			throw new SaldoInsuficienteException();
		}

		emailService.notificarClientePorEmail(
				cliente.getEmail(), 
				"Realização de Saque", 
				"Olá "+cliente.getNome()+"\n\nVocê realizou um saque de "
				+FormatarDoubleParaReal.formatar(valorSacado.doubleValue())+
				"\nObs.: Valor já inclui a taxa do sistema.");
		cliente.getEmpresa().setSaldo(cliente.getEmpresa().getSaldo().subtract(valorSacado));
		
		return cliente;
	}
	
	public Cliente realizarDeposito(Cliente cliente, double valor) {
		BigDecimal valorDepositado = new BigDecimal(
				valor - cliente.getEmpresa().getTaxaSistema());
		
		emailService.notificarClientePorEmail(cliente.getEmail(), "Realização de Deposito",
				"Olá " + cliente.getNome() + "\n\nVocê realizou um deposito de "
						+ FormatarDoubleParaReal.formatar(valorDepositado.doubleValue())
						+ "\nObs.: Valor já desconta a taxa do sistema.");
		cliente.getEmpresa().setSaldo(cliente.getEmpresa().getSaldo().add(valorDepositado));
		
		return cliente;
	}

}
