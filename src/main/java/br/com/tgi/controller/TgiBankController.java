package br.com.tgi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.tgi.exception.CNPJInvalidoException;
import br.com.tgi.exception.CPFInvalidoException;
import br.com.tgi.exception.EmailInvalidoException;
import br.com.tgi.exception.EnvioEmailException;
import br.com.tgi.exception.SaldoInsuficienteException;
import br.com.tgi.model.Cliente;
import br.com.tgi.model.Empresa;
import br.com.tgi.model.RespostaDeErro;
import br.com.tgi.model.Transacao;
import br.com.tgi.service.ClienteService;
import br.com.tgi.service.EmpresaService;
import br.com.tgi.service.TransacaoService;

@RestController
@RequestMapping("/tgibank")
public class TgiBankController {

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private TransacaoService transacaoService;

	@PostMapping("/empresa")
	@ResponseBody
	public ResponseEntity<?> criarEmpresa(@RequestBody String empresaJson) {
		try {
			Empresa empresa = empresaService.salvarEmpresa(empresaJson);
			return ResponseEntity.ok("Empresa " + empresa.getNome() + " criada com sucesso");
		} catch (CNPJInvalidoException e) {
			return handleValidacaoException(e);
		} catch (IOException e) {
			return ResponseEntity.badRequest().body("Erro ao processar o JSON.");
		}
	}

	@PostMapping("/cliente")
	@ResponseBody
	public ResponseEntity<?> criarCliente(@RequestBody String clienteJson) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(clienteJson);

			Cliente cliente = clienteService.salvarCliente(clienteJson,
					empresaService.pegarEmpresaPorId(jsonNode.get("empresa").asLong()));
			return ResponseEntity.ok("Cliente " + cliente.getNome() + " da empresa " + cliente.getEmpresa().getNome()
					+ " criado com sucesso");
		} catch (CPFInvalidoException | EmailInvalidoException e) {
			return handleValidacaoException(e);
		} catch (IOException e) {
			return ResponseEntity.badRequest().body("Erro ao processar o JSON.");
		}
	}

	@PostMapping("/saque")
	@ResponseBody
	public ResponseEntity<?> realizarSaque(@RequestBody String infoJson) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(infoJson);

			Cliente cliente = clienteService.pegarClientePorId(jsonNode.get("idCliente").asLong());
			Transacao transacao = new Transacao(cliente, "Saque", 0);
			transacaoService.salvarTransacao(transacao);

			try {
				empresaService.enviarCallbackParaEmpresa(transacao);
				Cliente clienteAlterado = clienteService.realizarSaque(cliente, jsonNode.get("valor").asDouble());
				empresaService.salvarEmpresa(clienteAlterado.getEmpresa());
				transacao.setStatus(1);
				transacaoService.salvarTransacao(transacao);

				return ResponseEntity.ok("Saque realizado com sucesso.");
			} catch (SaldoInsuficienteException e) {
				transacao.setTipoTransacao("Saque - Cancelado");
				transacao.setStatus(-1);
				transacaoService.salvarTransacao(transacao);
				return handleSaldoException(e);
			}
		} catch (IOException e) {
			return ResponseEntity.badRequest().body("Erro ao processar o JSON.");
		}
	}

	@PostMapping("/deposito")
	@ResponseBody
	public ResponseEntity<?> realizarDeposito(@RequestBody String infoJson) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(infoJson);

			Cliente cliente = clienteService.pegarClientePorId(jsonNode.get("idCliente").asLong());
			Transacao transacao = new Transacao(cliente, "Deposito", 0);
			transacaoService.salvarTransacao(transacao);

			try {
				empresaService.enviarCallbackParaEmpresa(transacao);
				Cliente clienteAlterado = clienteService.realizarDeposito(cliente, jsonNode.get("valor").asDouble());
				empresaService.salvarEmpresa(clienteAlterado.getEmpresa());
				transacao.setStatus(1);
				transacaoService.salvarTransacao(transacao);

				return ResponseEntity.ok("Depósito realizado com sucesso.");
			} catch (Exception e) {
				transacao.setTipoTransacao("Depósito - Cancelado");
				transacao.setStatus(-1);
				transacaoService.salvarTransacao(transacao);
				return ResponseEntity.badRequest().body("Erro ao processar o JSON.");
			}
		} catch (IOException e) {
			return ResponseEntity.badRequest().body("Erro ao processar o JSON.");
		}

	}

	@ExceptionHandler(value = { CNPJInvalidoException.class, CPFInvalidoException.class, EmailInvalidoException.class })
	public ResponseEntity<?> handleValidacaoException(Exception ex) {
		RespostaDeErro respostaDeErro = new RespostaDeErro(HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respostaDeErro);
	}

	@ExceptionHandler(SaldoInsuficienteException.class)
	public ResponseEntity<?> handleSaldoException(Exception ex) {
		RespostaDeErro respostaDeErro = new RespostaDeErro(HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respostaDeErro);
	}
	
	@ExceptionHandler(EnvioEmailException.class)
	public ResponseEntity<?> handleEnvioEmailException(Exception ex) {
		RespostaDeErro respostaDeErro = new RespostaDeErro(HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respostaDeErro);
	}

}
