package br.com.tgi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tgi.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
