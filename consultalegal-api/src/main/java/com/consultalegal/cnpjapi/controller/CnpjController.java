package com.consultalegal.cnpjapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.consultalegal.cnpjapi.model.Cnpj;
import com.consultalegal.cnpjapi.repository.CnpjRepository;
import com.consultalegal.cnpjapi.repository.CnpjRepository2;
import com.consultalegal.cnpjapi.service.CnpjService;

import jakarta.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/cnpj")
public class CnpjController {

    @Autowired
    private CnpjRepository cnpjRepository;

    @Autowired
    private CnpjRepository2 cnpjRepository2;
    
    @Autowired
    private CnpjService cnpjService;

    @PostMapping
    public ResponseEntity<Cnpj> newCnpj(@Valid @RequestBody Cnpj cnpj) {
        Cnpj savedCnpj = cnpjRepository.save(cnpj);
        return new ResponseEntity<>(savedCnpj, HttpStatus.CREATED);  // Status 201 Created
    }

    @PutMapping
    public ResponseEntity<Cnpj> updateCnpj(@Valid @RequestBody Cnpj cnpj) {
        Optional<Cnpj> existingCnpj = cnpjRepository.findById(cnpj.getCnpj());
        if (existingCnpj.isPresent()) {
            Cnpj updatedCnpj = cnpjRepository.save(cnpj);
            return new ResponseEntity<>(updatedCnpj, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Caso o CNPJ não exista
    }

    @GetMapping
    public Iterable<Cnpj> getCnpj() {
        return cnpjRepository.findAll();
    }

    @GetMapping(path = "/{cnpj}")
    public ResponseEntity<Cnpj> getCnpjByNumber(@PathVariable String cnpj) {
        Optional<Cnpj> cnpjData = cnpjRepository.findById(cnpj);

        if (cnpjData.isPresent()) {
            return new ResponseEntity<>(cnpjData.get(), HttpStatus.OK);
        }

        try {
            Cnpj cnpjConsultado = cnpjService.consultarCnpj(cnpj);  // Chama o serviço para consultar a API do CNPJá
            return new ResponseEntity<>(cnpjConsultado, HttpStatus.CREATED);  // Retorna o CNPJ que foi salvo no banco
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // Caso haja erro na consulta
        }
    }

    @GetMapping(path = "/page/{numberPage}/{amountPages}")
    public Iterable<Cnpj> getCnpjByPage(@PathVariable int numberPage, @PathVariable int amountPages) {
        if (amountPages >= 5) amountPages = 5;
        Pageable page = PageRequest.of(numberPage, amountPages);
        return cnpjRepository2.findAll(page);
    }
}
