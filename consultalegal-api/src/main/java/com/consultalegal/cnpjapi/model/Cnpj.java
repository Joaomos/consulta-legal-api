package com.consultalegal.cnpjapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cnpjs")
public class Cnpj {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String cnpj;
	
	private String razaoSocial;
	private String nomeFantasia;
	private String situacaoCadastral;
	private String dataAbertura;
	private String naturezaJuridica;
	private String capitalSocial;
	private String email;
	private String telefone;
	private String enderecoCompleto;
	private String cnaePrincipal;
	private String cnaeSecundario1;
	private String cnaeSecundario2;
	
	
	
	
	public Cnpj() {
		
	}
	public Cnpj(Long id, String cnpj, String razaoSocial, String nomeFantasia, String situacaoCadastral,
			String dataAbertura, String naturezaJuridica, String capitalSocial, String email, String telefone,
			String enderecoCompleto, String cnaePrincipal, String cnaeSecundario1, String cnaeSecundario2) {
		super();
		this.id = id;
		this.cnpj = cnpj;
		this.razaoSocial = razaoSocial;
		this.nomeFantasia = nomeFantasia;
		this.situacaoCadastral = situacaoCadastral;
		this.dataAbertura = dataAbertura;
		this.naturezaJuridica = naturezaJuridica;
		this.capitalSocial = capitalSocial;
		this.email = email;
		this.telefone = telefone;
		this.enderecoCompleto = enderecoCompleto;
		this.cnaePrincipal = cnaePrincipal;
		this.cnaeSecundario1 = cnaeSecundario1;
		this.cnaeSecundario2 = cnaeSecundario2;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public String getNomeFantasia() {
		return nomeFantasia;
	}
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	public String getSituacaoCadastral() {
		return situacaoCadastral;
	}
	public void setSituacaoCadastral(String situacaoCadastral) {
		this.situacaoCadastral = situacaoCadastral;
	}
	public String getDataAbertura() {
		return dataAbertura;
	}
	public void setDataAbertura(String dataAbertura) {
		this.dataAbertura = dataAbertura;
	}
	public String getNaturezaJuridica() {
		return naturezaJuridica;
	}
	public void setNaturezaJuridica(String naturezaJuridica) {
		this.naturezaJuridica = naturezaJuridica;
	}
	public String getCapitalSocial() {
		return capitalSocial;
	}
	public void setCapitalSocial(String capitalSocial) {
		this.capitalSocial = capitalSocial;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEnderecoCompleto() {
		return enderecoCompleto;
	}
	public void setEnderecoCompleto(String enderecoCompleto) {
		this.enderecoCompleto = enderecoCompleto;
	}
	public String getCnaePrincipal() {
		return cnaePrincipal;
	}
	public void setCnaePrincipal(String cnaePrincipal) {
		this.cnaePrincipal = cnaePrincipal;
	}
	public String getCnaeSecundario1() {
		return cnaeSecundario1;
	}
	public void setCnaeSecundario1(String cnaeSecundario1) {
		this.cnaeSecundario1 = cnaeSecundario1;
	}
	public String getCnaeSecundario2() {
		return cnaeSecundario2;
	}
	public void setCnaeSecundario2(String cnaeSecundario2) {
		this.cnaeSecundario2 = cnaeSecundario2;
	}
	
	
}
