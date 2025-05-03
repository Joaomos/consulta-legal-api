# 📘 Consulta Legal - API de Consulta de CNPJ

## 📌 Objetivo da API

Esta API foi desenvolvida exclusivamente para a **consulta de dados cadastrais de CNPJs** de empresas brasileiras. Ela é uma parte do projeto **Consulta Legal**, uma plataforma web que oferece funcionalidades para facilitar o acesso a dados públicos empresariais.

A função principal desta API é buscar as informações de um CNPJ no banco de dados local. Caso o CNPJ ainda não tenha sido consultado, a API faz uma requisição à [CNPJá API](https://publica.cnpj.ws), armazena os dados localmente em um banco de dados PostgreSQL e retorna a resposta para o usuário.

---

## 🌐 Projeto Consulta Legal

O **Consulta Legal** é um sistema web com as seguintes funcionalidades:

- Consulta de CNPJ (funcionalidade implementada por esta API)
- Consulta de DANFE e XML por código de acesso *(em desenvolvimento)*
- Consulta de DANFE a partir de um XML *(em desenvolvimento)*

Esta API representa **100% da implementação da funcionalidade de consulta de CNPJ** do projeto.

---

## 🚀 Tecnologias Utilizadas

- Java 17  
- Spring Boot 3  
- Spring Web  
- Spring Data JPA  
- Hibernate  
- PostgreSQL  
- Jackson (ObjectMapper)

---

## 📂 Estrutura do Projeto
```
com.consultalegal.cnpjapi
├── config # Configuração CORS 
├── controller # Endpoints REST para CNPJ
├── model # Entidade CNPJ
├── repository # Acesso ao banco de dados
├── service # Regras de negócio e integração com API externa
└── ConsultalegalApiApplication.java # Classe principal

```

---

## 🔗 Endpoints da API

### 🔍 Buscar CNPJ

- **GET** `/api/cnpj/{cnpj}`
- **Descrição:**  
  Verifica se o CNPJ está armazenado localmente.  
  Se não estiver, consulta a API externa do CNPJá, salva no banco de dados e retorna os dados.
- **Resposta:**
```json
{
  "cnpj": "12345678000195",
  "razaoSocial": "Empresa Exemplo Ltda",
  "nomeFantasia": "Exemplo",
  "situacaoCadastral": "Ativa",
  "dataAbertura": "2000-01-01",
  "naturezaJuridica": "206-2 - Sociedade Empresária Limitada",
  "capitalSocial": "R$ 100000.00",
  "email": "contato@empresa.com",
  "telefone": "1133334444",
  "logradouro": "Rua Exemplo",
  "numero": "123",
  "complemento": "Sala 1",
  "bairro": "Centro",
  "municipio": "São Paulo",
  "uf": "SP",
  "cep": "01000000",
  "cnaePrincipal": "Comércio varejista",
  "cnaeSecundario1": "Serviços de escritório",
  "cnaeSecundario2": "Consultoria empresarial"
}
```

---

## 📄 Listar todos os CNPJs
- GET `/api/cnpj`
- Retorna todos os CNPJs armazenados localmente.

---

## 📃 Paginar resultados
- GET `/api/cnpj/page/{numeroPagina}/{quantidadePorPagina}`
- Exemplo: `/api/cnpj/page/0/10`
- Retorna uma página com registros paginados.

---

## ➕ Criar CNPJ manualmente
- POST `/api/cnpj`
- Insere manualmente um CNPJ no banco.
- Corpo da Requisição:
```json
{
  "cnpj": "12345678000195",
  "razaoSocial": "Empresa Exemplo Ltda",
  ...
}
```

---

## ✏️ Atualizar CNPJ
- PUT `/api/cnpj`
- Atualiza os dados de um CNPJ já existente com base no campo cnpj.

---

## 🧠 Lógica da Consulta
- O usuário realiza uma requisição GET `/api/cnpj/{cnpj}`.
- A API verifica no banco de dados local se o CNPJ está armazenado.
- Caso não esteja:
- Consulta a API CNPJá.
- Mapeia os dados recebidos.
- Armazena as informações no banco.
- Retorna a resposta ao cliente.

---

## 🧱 Banco de Dados
A API utiliza um banco PostgreSQL com a seguinte estrutura (gerada automaticamente pelo Hibernate):
| Campo             | Tipo   | Descrição                        |
| ----------------- | ------ | -------------------------------- |
| cnpj              | String | CNPJ da empresa (chave primária) |
| razaoSocial       | String | Razão social                     |
| nomeFantasia      | String | Nome fantasia                    |
| situacaoCadastral | String | Situação cadastral               |
| dataAbertura      | String | Data de abertura                 |
| naturezaJuridica  | String | Natureza jurídica                |
| capitalSocial     | String | Capital social                   |
| email             | String | E-mail de contato                |
| telefone          | String | Telefone de contato              |
| logradouro        | String | Rua/avenida                      |
| numero            | String | Número                           |
| complemento       | String | Complemento                      |
| bairro            | String | Bairro                           |
| municipio         | String | Município                        |
| uf                | String | Unidade federativa               |
| cep               | String | CEP                              |
| cnaePrincipal     | String | Atividade econômica principal    |
| cnaeSecundario1   | String | Atividade secundária 1           |
| cnaeSecundario2   | String | Atividade secundária 2           |

---

## 🔒 CORS
CORS configurado para aceitar todas as origens e métodos:

---

## ⚙️ Requisitos
- JDK 17+
- PostgreSQL em execução
- Spring Boot
- Maven ou Gradle
- Configuração de banco no `application.properties`:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/consultalegal
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```
--- 

## 📞 Contato

Se tiver dúvidas ou sugestões, entre em contato:

- **Nome:** João Marcos
- **E-mail:** joaomarcos2827@gmail.com
- **GitHub:** [Joaomos](https://github.com/Joaomos)
- **LinkedIn:** [João Marcos](https://www.linkedin.com/in/ojoaomarcosilva/)
