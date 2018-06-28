package com.ufc.br.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import com.ufc.br.service.CarrinhoService;


@Controller
public class CarrinhoController {
	@Autowired
	CarrinhoService cS;
	//CarrinhoRepository cR;
	@Autowired
	PessoaController pC;
	@Autowired
	InstrumentoController iC;
	
	
	/*@RequestMapping("/salvar/carrinho2")
	public ModelAndView salvarCarrinho2() {
		cS.salvarNoCarrinho(pC.retornaPessoa(), iC.listaInstrumentos());
		
		ModelAndView mv = new ModelAndView("redirect:/sonic/listar/instrumento2");
		return mv;
	}*/
	
}
