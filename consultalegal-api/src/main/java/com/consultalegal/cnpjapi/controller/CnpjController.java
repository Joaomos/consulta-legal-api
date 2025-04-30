package com.consultalegal.cnpjapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.consultalegal.cnpjapi.model.Cnpj;
import com.consultalegal.cnpjapi.repository.CnpjRepository;
import com.consultalegal.cnpjapi.repository.CnpjRepository2;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cnpj")
public class CnpjController {
	
	@Autowired
	private CnpjRepository cnpjRepository;
	
	@Autowired
	private CnpjRepository2 cnpjRepository2;
	
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
	public @ResponseBody Cnpj newCnpj(@Valid Cnpj cnpj) {
		cnpjRepository.save(cnpj);
		return cnpj;
	}
	
	@GetMapping
	public Iterable<Cnpj> getCnpj() {
		return cnpjRepository.findAll();
	}
	
	@GetMapping(path = "/{cnpj}")
	public Iterable<Cnpj> getCnpjByNumber(@PathVariable String cnpj) {
		return cnpjRepository2.findByCnpjContainingIgnoreCase(cnpj);
	}
	
	@GetMapping(path = "/page/{numberPage}/{amountPages}")
	public Iterable<Cnpj> getCnpjByPage(@PathVariable int numberPage, @PathVariable int amountPages) {
		if(amountPages >= 5) amountPages = 5;
		Pageable page = PageRequest.of(numberPage, amountPages);
		return cnpjRepository2.findAll(page);
	}
	
}
