package com.ufc.br.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufc.br.model.Carrinho;
import com.ufc.br.model.Instrumento;
import com.ufc.br.model.Pessoa;
import com.ufc.br.repository.CarrinhoRepository;

@Service
public class CarrinhoService {
	
	@Autowired
	CarrinhoRepository cR;
	
	public void criarCarrinho(Carrinho carrinho) {
		cR.save(carrinho);
	}
	
	public void salvarNoCarrinho(Pessoa pessoa, List<Instrumento> instrumentos) {
		for (Instrumento instrumento : instrumentos) {
			Carrinho carrinho = new Carrinho();
			carrinho.setId_pessoa(pessoa.getId());
			carrinho.setId_instrumento(instrumento.getId());
			carrinho.setNome_cliente(pessoa.getNome());
			carrinho.setNome_instrumento(instrumento.getNome());
			carrinho.setPreco(instrumento.getPreco());
			carrinho.setQuant(instrumento.getQuant());
			carrinho.setTipo(instrumento.getTipo());
			criarCarrinho(carrinho);
		}
		
	}
	public List<Carrinho> listarCarrinho(){
		return cR.findAll();
	}
	public List<Carrinho> listarCarrinhoCliente(Long id){
		List<Carrinho> carrinhos = cR.findAll();
		List<Carrinho> carrinhoCliente = new ArrayList<Carrinho>();
		for (Carrinho carrinho : carrinhos) {
			if(carrinho.getId_pessoa() == id) {
				carrinhoCliente.add(carrinho);
			}
		}
		return carrinhoCliente;		
	}
	
}
