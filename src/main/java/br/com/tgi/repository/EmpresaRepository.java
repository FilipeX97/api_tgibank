package br.com.tgi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tgi.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

}
