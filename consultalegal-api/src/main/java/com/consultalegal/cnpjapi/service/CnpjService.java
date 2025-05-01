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

            // Log para debugar estrutura recebida
            System.out.println("Estabelecimento JSON: " + estabelecimento.toPrettyString());

            Cnpj empresa = new Cnpj();
            empresa.setCnpj(cnpj);
            empresa.setRazaoSocial(estabelecimento.path("razao_social").asText(""));
            empresa.setNomeFantasia(estabelecimento.path("nome_fantasia").asText(""));
            empresa.setSituacaoCadastral(estabelecimento.path("situacao").asText(""));
            empresa.setDataAbertura(estabelecimento.path("data_inicio_atividade").asText(""));

            JsonNode naturezaJuridica = estabelecimento.path("natureza_juridica");
            empresa.setNaturezaJuridica(naturezaJuridica.path("descricao").asText(""));

            empresa.setCapitalSocial(estabelecimento.path("capital_social").asText(""));
            empresa.setEmail(estabelecimento.path("email").asText(""));

            String ddd = estabelecimento.path("ddd1").asText("");
            String telefone = estabelecimento.path("telefone1").asText("");
            empresa.setTelefone(ddd + telefone);

            empresa.setLogradouro(estabelecimento.path("logradouro").asText(""));
            empresa.setNumero(estabelecimento.path("numero").asText(""));
            empresa.setComplemento(estabelecimento.path("complemento").asText(""));
            empresa.setBairro(estabelecimento.path("bairro").asText(""));

            JsonNode cidade = estabelecimento.path("cidade");
            empresa.setMunicipio(cidade.path("nome").asText(""));

            JsonNode estado = estabelecimento.path("estado");
            empresa.setUf(estado.path("sigla").asText(""));

            empresa.setCep(estabelecimento.path("cep").asText(""));

            JsonNode atividadePrincipal = estabelecimento.path("atividade_principal");
            empresa.setCnaePrincipal(atividadePrincipal.path("descricao").asText(""));

            System.out.println("Empresa antes de salvar: " + empresa);
            cnpjRepository.save(empresa);

            return empresa;

        } catch (Exception e) {
            e.printStackTrace(); // exibe erro no console
            throw new RuntimeException("Erro ao consultar e salvar CNPJ: " + e.getMessage(), e);
        }
    }
}
