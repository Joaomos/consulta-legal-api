package com.consultalegal.cnpjapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.consultalegal.cnpjapi.model.Cnpj;
import com.consultalegal.cnpjapi.repository.CnpjRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CnpjService {

    private final CnpjRepository cnpjRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public CnpjService(CnpjRepository cnpjRepository, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.cnpjRepository = cnpjRepository;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public Cnpj consultarCnpj(String cnpj) throws Exception {
        
        return cnpjRepository.findById(cnpj).orElseGet(() -> {
            try {

                String url = "https://publica.cnpj.ws/cnpj/" + cnpj;
                String response = restTemplate.getForObject(url, String.class);

              
                JsonNode jsonNode = objectMapper.readTree(response);
                JsonNode estabelecimento = jsonNode.get("estabelecimento");
                JsonNode atividadesSecundarias = estabelecimento.get("atividades_secundarias");

             
                Cnpj empresa = new Cnpj();
                empresa.setRazaoSocial(jsonNode.get("razao_social").asText());
                empresa.setNomeFantasia(estabelecimento.get("nome_fantasia").asText());
                empresa.setSituacaoCadastral(estabelecimento.get("situacao").asText());
                empresa.setDataAbertura(estabelecimento.get("data_inicio_atividade").asText());
                empresa.setNaturezaJuridica(jsonNode.get("natureza_juridica").get("descricao").asText());
                empresa.setCapitalSocial(jsonNode.get("capital_social").asText());
                empresa.setEmail(estabelecimento.get("email").asText());
                empresa.setTelefone(estabelecimento.get("ddd1").asText() + estabelecimento.get("telefone1").asText());
                empresa.setLogradouro(estabelecimento.get("logradouro").asText());
                empresa.setNumero(estabelecimento.get("numero").asText());
                empresa.setComplemento(estabelecimento.get("complemento").asText());
                empresa.setBairro(estabelecimento.get("bairro").asText());
                empresa.setMunicipio(estabelecimento.get("cidade").get("nome").asText());
                empresa.setUf(estabelecimento.get("estado").get("sigla").asText());
                empresa.setCep(estabelecimento.get("cep").asText());
                empresa.setCnaePrincipal(estabelecimento.get("atividade_principal").get("descricao").asText());

               
                if (atividadesSecundarias != null && atividadesSecundarias.size() > 0) {
                    empresa.setCnaeSecundario1(atividadesSecundarias.get(0).get("descricao").asText());
                }
                if (atividadesSecundarias != null && atividadesSecundarias.size() > 1) {
                    empresa.setCnaeSecundario2(atividadesSecundarias.get(1).get("descricao").asText());
                }

                
                return cnpjRepository.save(empresa);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao consultar e salvar CNPJ: " + e.getMessage(), e);
            }
        });
    }
}
