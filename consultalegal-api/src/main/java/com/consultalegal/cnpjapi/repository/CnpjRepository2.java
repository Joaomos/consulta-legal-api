package com.consultalegal.cnpjapi.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.consultalegal.cnpjapi.model.Cnpj;

public interface CnpjRepository2 extends 
PagingAndSortingRepository<Cnpj, String> {
	
	public Iterable<Cnpj> findByCnpjContainingIgnoreCase(String cnpj);

}