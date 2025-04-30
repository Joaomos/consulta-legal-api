package com.consultalegal.cnpjapi.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.consultalegal.cnpjapi.model.Cnpj;

public interface CnpjRepository2 extends 
PagingAndSortingRepository<Cnpj, Integer> {
	
public Iterable<Cnpj> findByNumberContainingIgnoreCase(String parteNome);


}