package com.consultalegal.cnpjapi.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.consultalegal.cnpjapi.model.Cnpj;
import com.consultalegal.cnpjapi.repository.CnpjRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CnpjService {

    @Autowired
    private CnpjRepository cnpjRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Cnpj consultarCnpj(String cnpj) {
        try {
            String urlString = "https://publica.cnpj.ws/cnpj/" + cnpj;
            URL url = URI.create(urlString).toURL();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int statusCode = conn.getResponseCode();

            if (statusCode != 200) {
                throw new RuntimeException("Erro na consulta HTTP: Código " + statusCode);
            }

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
            );

            StringBuilder responseBuilder = new StringBuilder();
            String linha;

            while ((linha = reader.readLine()) != null) {
                responseBuilder.append(linha);
            }

            reader.close();
            conn.disconnect();

            String response = responseBuilder.toString();
            System.out.println("Resposta da API: " + response);

            JsonNode root = objectMapper.readTree(response);

            if (root.has("message")) {
                throw new RuntimeException("CNPJ não encontrado: " + cnpj);
            }

            JsonNode estabelecimento = root.path("estabelecimento");
            JsonNode pais = estabelecimento.path("pais");

            Cnpj empresa = new Cnpj();
            empresa.setCnpj(cnpj);
            empresa.setRazaoSocial(estabelecimento.get("razao_social").asText());
            empresa.setNomeFantasia(estabelecimento.get("nome_fantasia").asText());
            empresa.setSituacaoCadastral(estabelecimento.get("situacao").asText());
            empresa.setDataAbertura(estabelecimento.get("data_inicio_atividade").asText());
            empresa.setNaturezaJuridica(estabelecimento.get("natureza_juridica").get("descricao").asText());
            empresa.setCapitalSocial(estabelecimento.get("capital_social").asText());
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
            
            return cnpjRepository.save(empresa);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar e salvar CNPJ: " + e.getMessage(), e);
        }
    }
}
