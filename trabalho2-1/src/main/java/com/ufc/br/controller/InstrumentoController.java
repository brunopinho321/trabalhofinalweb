package com.ufc.br.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import com.ufc.br.model.Instrumento;
import com.ufc.br.service.CarrinhoService;
import com.ufc.br.service.InstrumentoService;

@Controller
@RequestMapping("/sonic")
public class InstrumentoController {
	
	@Autowired
	InstrumentoService instService;
	
	@Autowired
	PessoaController pC;
	
	@Autowired
	CarrinhoService cS;
	
	List<Instrumento> instrumentos = new ArrayList<Instrumento>();
	
	@RequestMapping("/adicionar/instrumento")
	public ModelAndView adicionarInstrumento() {
		ModelAndView mv = new ModelAndView("adicionarInstrumento");
		mv.addObject("instrumento", new Instrumento());
		return mv;
	}
	
	@PostMapping("/salvar/instrumeto")
	public ModelAndView salvarInstrumento(Instrumento instrumento, @RequestParam(value="imagem") MultipartFile imagem) {
		instService.salvarInstrumeto(instrumento, imagem);
		ModelAndView mv = new ModelAndView("redirect:/sonic/listar/instrumento");
		return mv;
	}
	
	@GetMapping("/listar/instrumento")
	public ModelAndView listarInstrumento() {
		ModelAndView mv = new ModelAndView("listarInstrumentos");
		List<Instrumento> instrumentos = instService.listarInstrumentos();
		mv.addObject("todosInstrumentos", instrumentos);
		return mv;
	}
	
	@GetMapping("/listar/instrumento2") 
	public ModelAndView listarInstrumento2() {
		ModelAndView mv = new ModelAndView("index");
		List<Instrumento> cordas = instService.listarInstrumentoCordas();
		List<Instrumento> sopro = instService.listarInstrumentoSopro();
		List<Instrumento> percussao = instService.listarInstrumentoPercussao();
		List<Instrumento> teclas = instService.listarInstrumentoTeclas();
		mv.addObject("tcordas", cordas);
		mv.addObject("tsopro", sopro);
		mv.addObject("tpercussao", percussao);
		mv.addObject("tteclas", teclas);
		return mv;
	}
	
	
	@RequestMapping("/atualizar/instrumento/{id}")
	public ModelAndView atualizarInstrumentoPorId(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("adicionarInstrumento");
		Instrumento instrumento = instService.buscarPorId(id);
		mv.addObject("instrumento", instrumento);
		return mv;
	}
	@RequestMapping("/excluir/instrumento/{id}")
	public ModelAndView excluirInstrumentoPorId(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("redirect:/sonic/listar/instrumento");
		instService.ExcluirPorId(id);
		return mv;
	}
	
	@RequestMapping("/exibir/instrumento/{id}")
	public ModelAndView exibirInstrumento(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("instrumento");
		Instrumento instrumento = instService.buscarPorId(id);
		mv.addObject("instrumento",instrumento);
		return mv;
	}
	
	@RequestMapping("/carrinho")
	public ModelAndView carrinho() {
		ModelAndView mv = new ModelAndView("carrinho");
		float valor = instService.ValorDaCompra(instrumentos);
		mv.addObject("valor",valor);
		mv.addObject("instrumentos", instrumentos);
		mv.addObject("comprasRealizadas", cS.listarCarrinhoCliente(pC.retornaPessoa().getId()));
		return mv;
	}
	
	@RequestMapping("comprar/instrumento/{id}")
	public ModelAndView comprarInstrumento(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("carrinho");
		instrumentos = instService.comprarInstrumento(id, instrumentos);
		float valor = instService.ValorDaCompra(instrumentos);
		mv.addObject("instrumentos", instrumentos);
		mv.addObject("valor",valor);
		mv.addObject("comprasRealizadas", cS.listarCarrinhoCliente(pC.retornaPessoa().getId()));
		return mv;
	}
	
	@RequestMapping("remover/instrumento/{id}")
	public ModelAndView removerInstrumento(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("carrinho");
		instrumentos = instService.removerInstrumento(id, instrumentos);
		mv.addObject("instrumentos", instrumentos);
		float valor = instService.ValorDaCompra(instrumentos);
		mv.addObject("valor",valor);
		mv.addObject("comprasRealizadas", cS.listarCarrinhoCliente(pC.retornaPessoa().getId()));
		return mv;
	}
	
	@RequestMapping("cancelar")
	public ModelAndView cancelarCompra() {
		ModelAndView mv = new ModelAndView("carrinho");
		instrumentos = instService.cancelarTodasAsCompras(instService.listarInstrumentos(), instrumentos);
		mv.addObject("instrumentos",instrumentos);
		float valor = instService.ValorDaCompra(instrumentos);
		mv.addObject("valor",valor);
		mv.addObject("comprasRealizadas", cS.listarCarrinhoCliente(pC.retornaPessoa().getId()));
		return mv;
	}
	
	
	@RequestMapping("/salvar/carrinho2")
	public ModelAndView salvarCarrinho2() {
		cS.salvarNoCarrinho(pC.retornaPessoa(), listaInstrumentos());
		ModelAndView mv = new ModelAndView("redirect:/sonic/listar/instrumento2");
		instrumentos.clear();
		return mv;
	}
	
	
	public List<Instrumento> listaInstrumentos(){
		return instrumentos;
	}
	
	
	
}
