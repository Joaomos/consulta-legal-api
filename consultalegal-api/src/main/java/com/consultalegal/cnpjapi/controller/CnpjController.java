package com.consultalegal.cnpjapi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consultalegal.cnpjapi.model.Cnpj;
import com.consultalegal.cnpjapi.service.CnpjService;

@RestController
@RequestMapping("/api/cnpj")
public class CnpjController {

	@Autowired
	private CnpjService service;
	
	public CnpjController(CnpjService service) {
		this.service = service;
	}
	
	@PostMapping(path = "/{cnpj}")
	public ResponseEntity<Cnpj> salvar(@PathVariable Cnpj cnpj) {
		Cnpj salvo = service.salvar(cnpj);
		return ResponseEntity.ok(salvo);
	}
	
	@GetMapping("/{cnpj}")
	public ResponseEntity<Cnpj> buscar(@PathVariable String cnpj) {
		Optional<Cnpj> encontrado = service.buscarPorCnpj(cnpj);
		return encontrado.map(ResponseEntity::ok)
							.orElse(ResponseEntity.notFound().build());
	}
}
