package com.ufc.br.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import com.ufc.br.model.Pessoa;

import com.ufc.br.service.PessoaService;

@Controller
@RequestMapping("/pessoa")
public class PessoaController {
	
	@Autowired
	private PessoaService pessoaService;
	
	
	
	
	@RequestMapping("/formulario")
	public ModelAndView formularioPessoa() {
		ModelAndView mv = new ModelAndView("formulario");
		mv.addObject("pessoa", new Pessoa());
		return mv;
	}
	
	@PostMapping("/salvar")
	public ModelAndView salvarPessoa(Pessoa pessoa){
		pessoaService.adicionarPessoa(pessoa);
		ModelAndView mv = new ModelAndView("formulario");
		mv.addObject("mensagem", "Usuário cadastrado com sucesso");
		return mv;
	}
	
	
	@RequestMapping("/logar")
	public ModelAndView logar() {
		ModelAndView mv = new ModelAndView("login");
		return mv;
	}
	@RequestMapping(value="/logout")
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    
	    return "redirect:/sonic/listar/instrumento2";
	
	}
	
	
	@RequestMapping("/pegar")
	public ModelAndView pegarUsuarioLogado() {
		ModelAndView mv = new ModelAndView("pagina");
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		Pessoa pessoa = pessoaService.buscarPorLogin(user.getUsername());
		mv.addObject("pessoa", pessoa);
		return mv;
	}
	
	
	public Pessoa retornaPessoa() {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		Pessoa pessoa = pessoaService.buscarPorLogin(user.getUsername());
		return pessoa;
	}

}
