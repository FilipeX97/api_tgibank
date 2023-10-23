package br.com.tgi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tgi.model.Transacao;
import br.com.tgi.repository.TransacaoRepository;

@Service
public class TransacaoService {
	
	@Autowired
	private TransacaoRepository transacaoRepository;
	
	public void salvarTransacao(Transacao transacao) {
		transacaoRepository.save(transacao);
	}
	
	

}
