package com.consultalegal.cnpjapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.consultalegal.cnpjapi.model.Cnpj;

public interface CnpjRepository extends JpaRepository<Cnpj, Long>{
	Optional<Cnpj> findByCnpj(String cnpj);

}
