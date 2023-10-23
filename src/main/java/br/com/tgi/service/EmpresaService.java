package br.com.tgi.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.tgi.model.Empresa;
import br.com.tgi.model.Transacao;
import br.com.tgi.repository.EmpresaRepository;

@Service
public class EmpresaService {

	@Autowired
	private EmpresaRepository empresaRepository;

	@Value("${callbackUrl}")
	private String callbackUrl;

	@Autowired
	private ObjectMapper objectMapper;

	public Empresa pegarEmpresaPorId(Long id) {
		return empresaRepository.findById(id).get();
	}

	public Empresa salvarEmpresa(String empresaJson) throws JsonMappingException, JsonProcessingException {
		JsonNode jsonNode = objectMapper.readTree(empresaJson);
		Empresa empresa = new Empresa(jsonNode.get("cnpj").asText(), jsonNode.get("nome").asText(),
				new BigDecimal(jsonNode.get("saldo").asDouble()), jsonNode.get("taxaSistema").asDouble());
		return empresaRepository.save(empresa);

	}
	
	public void salvarEmpresa(Empresa empresa) {
		empresaRepository.save(empresa);
	}

	public boolean enviarCallbackParaEmpresa(Transacao transacao) {
		try {
			String empresaCallbackUrl = callbackUrl;

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Transacao> requestEntity = new HttpEntity<>(transacao, headers);
			ResponseEntity<String> response = new RestTemplate().postForEntity(empresaCallbackUrl, requestEntity,
					String.class);

			if (response.getStatusCode().is2xxSuccessful()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

}
