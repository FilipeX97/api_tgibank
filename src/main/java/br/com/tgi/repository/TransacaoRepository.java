package br.com.tgi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tgi.model.Transacao;


public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

}
