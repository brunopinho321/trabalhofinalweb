package com.ufc.br.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Carrinho {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long id_pessoa;
	
	private Long id_instrumento;
	
	private String nome_cliente;
	private String nome_instrumento;
	private String tipo;
	private int quant;
	private float preco;
	public Long getId_pessoa() {
		return id_pessoa;
	}
	public void setId_pessoa(Long id_pessoa) {
		this.id_pessoa = id_pessoa;
	}
	public Long getId_instrumento() {
		return id_instrumento;
	}
	public void setId_instrumento(Long id_instrumento) {
		this.id_instrumento = id_instrumento;
	}
	public String getNome_cliente() {
		return nome_cliente;
	}
	public void setNome_cliente(String nome_cliente) {
		this.nome_cliente = nome_cliente;
	}
	public String getNome_instrumento() {
		return nome_instrumento;
	}
	public void setNome_instrumento(String nome_instrumento) {
		this.nome_instrumento = nome_instrumento;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public int getQuant() {
		return quant;
	}
	public void setQuant(int quant) {
		this.quant = quant;
	}
	public float getPreco() {
		return preco;
	}
	public void setPreco(float preco) {
		this.preco = preco;
	}
	}
