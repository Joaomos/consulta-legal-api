package com.consultalegal.cnpjapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.consultalegal.cnpjapi.model.Cnpj;
import com.consultalegal.cnpjapi.repository.CnpjRepository;

@Service
public class CnpjService {

	@Autowired
	private CnpjRepository repository;
	
	public CnpjService(CnpjRepository repository) {
		this.repository = repository;
	}
	
	public Cnpj salvar(Cnpj cnpj) {
        Optional<Cnpj> existente = repository.findByCnpj(cnpj.getCnpj());
        return existente.orElseGet(() -> repository.save(cnpj));
    }

    public Optional<Cnpj> buscarPorCnpj(String cnpj) {
        return repository.findByCnpj(cnpj);
    }
}
