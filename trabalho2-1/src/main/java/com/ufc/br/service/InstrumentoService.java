package com.ufc.br.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.ufc.br.model.Instrumento;
import com.ufc.br.repository.InstrumentoRepository;
import com.ufc.br.util.FileUtil;



@Service
public class InstrumentoService {

	@Autowired
	InstrumentoRepository instrumentoRepository;
	
	public void salvarInstrumeto(Instrumento instrumento, MultipartFile imagem) {
		String caminho = "images/" + instrumento.getNome() + ".png";
		FileUtil.salvarImagem(caminho, imagem);
		instrumentoRepository.save(instrumento);
	}
	
	public List<Instrumento> listarInstrumentos(){
		return instrumentoRepository.findAll();
	}
	
	public List<Instrumento> listarInstrumentoCordas(){
		List<Instrumento> listaCordas = new ArrayList<Instrumento> ();
		for (Instrumento instrumento : listarInstrumentos()) {
			if(instrumento.getTipo().equals("corda")) {
				listaCordas.add(instrumento);
			}
		}
		return listaCordas;
	}
	public List<Instrumento> listarInstrumentoSopro(){
		List<Instrumento> listaSopro = new ArrayList<Instrumento> ();
		for (Instrumento instrumento : listarInstrumentos()) {
			if(instrumento.getTipo().equals("sopro")) {
				listaSopro.add(instrumento);
			}
		}
		return listaSopro;
	}
	
	public List<Instrumento> listarInstrumentoPercussao(){
		List<Instrumento> listaCordas = new ArrayList<Instrumento> ();
		for (Instrumento instrumento : listarInstrumentos()) {
			if(instrumento.getTipo().equals("percussão")) {
				listaCordas.add(instrumento);
			}
		}
		return listaCordas;
	}
	
	public List<Instrumento> listarInstrumentoTeclas(){
		List<Instrumento> listaCordas = new ArrayList<Instrumento> ();
		for (Instrumento instrumento : listarInstrumentos()) {
			if(instrumento.getTipo().equals("teclas")) {
				listaCordas.add(instrumento);
			}
		}
		return listaCordas;
	}
	
	
	public List<Instrumento> comprarInstrumento(Long id, List<Instrumento> instrumentos) {
		Instrumento inst = instrumentoRepository.getOne(id);
		if(inst.getQuant() == 0) {
			return instrumentos;
		}
		else {
			for (Instrumento instrumento : instrumentos) {
				if(instrumento.getId() == id) {
					instrumento.setQuant(instrumento.getQuant()+1);
					inst.setQuant(inst.getQuant()-1);
					instrumentoRepository.save(inst);
					return instrumentos;
					
				}
					
			}
		}
		inst.setQuant(inst.getQuant()-1);
		instrumentoRepository.save(inst);
		inst.setQuant(1);
		instrumentos.add(inst);
		
		return instrumentos;
	}
	public List<Instrumento> removerInstrumento(Long id, List<Instrumento> instrumentos) {
		Instrumento inst = instrumentoRepository.getOne(id);
		for (Instrumento instrumento : instrumentos) {
			if(instrumento.getId() == id) {
				if(instrumento.getQuant() > 0) {
					instrumento.setQuant(instrumento.getQuant()-1);
					inst.setQuant(inst.getQuant()+1);
					instrumentoRepository.save(inst);
				}
				if(instrumento.getQuant() == 0){
					instrumentos.remove(instrumento);
					return instrumentos;
				}
			}
		}
		
		return instrumentos;
	}
	
	public List<Instrumento> cancelarCompra(Long id, List<Instrumento> instrumentos){
		Instrumento inst = instrumentoRepository.getOne(id);
		for (Instrumento instrumento : instrumentos) {
			if(instrumento.getId() == inst.getId()) {
				inst.setQuant(inst.getQuant()+instrumento.getQuant());
				instrumento.setQuant(0);
				instrumentos.remove(instrumento);
				instrumentoRepository.save(inst);
				return instrumentos;
			}
		}
		return instrumentos;
	}
	
	public List<Instrumento> cancelarTodasAsCompras(List<Instrumento> inst, List<Instrumento> instrumentos){
		for (Instrumento instrumento : inst) {
			instrumentos = cancelarCompra(instrumento.getId(), instrumentos);
		}
		return instrumentos;
	}
	
	public float ValorDaCompra(List<Instrumento> instrumentos) {
		float valor = 0;
		float valor2 = 0;
		for (Instrumento instrumento : instrumentos) {
			valor2 = instrumento.getQuant() * instrumento.getPreco();
			valor = valor + valor2;
		}
		return valor;
	}
	
	
	
	
	
	public Instrumento buscarPorId(Long id) {
		return instrumentoRepository.getOne(id);
	}
	
	public void ExcluirPorId(Long id) {
		instrumentoRepository.deleteById(id);
	}
	
}
