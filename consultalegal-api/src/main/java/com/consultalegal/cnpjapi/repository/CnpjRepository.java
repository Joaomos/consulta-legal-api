package com.consultalegal.cnpjapi.repository;

import org.springframework.data.repository.CrudRepository;

import com.consultalegal.cnpjapi.model.Cnpj;

public interface CnpjRepository extends CrudRepository<Cnpj, String>{

}