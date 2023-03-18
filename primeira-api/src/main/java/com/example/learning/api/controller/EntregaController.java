package com.example.learning.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.learning.api.mapper.EntregaMapper;
import com.example.learning.api.model.EntregaModel;
import com.example.learning.api.model.input.EntregaInput;
import com.example.learning.domain.model.Entrega;
import com.example.learning.domain.repository.EntregaRepository;
import com.example.learning.domain.service.FinalizacaoEntregaService;
import com.example.learning.domain.service.SolicitaEntregaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas")
public class EntregaController {

	/*
	 * Mediação com o banco de dados de forma mais segura
	 */
	private SolicitaEntregaService solicitaEntregaService;
	/*
	 * Ajuda a fazer buscas no banco de dados
	 */
	private EntregaRepository entregaRepository;
	/*
	 * ModelMapper ajuda a converter as classes no dominio
	 * em classes modelo da api pra dispor melhor as informações
	 */
	private EntregaMapper entregaMapper;
	/*
	 * 
	 */
	private FinalizacaoEntregaService finalizacaoEntregaService;
	/*
	 * Método Solicita uma entrega para restaurante hipotético
	 * @param entrega
	 * @return entrega
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EntregaModel solicitar(@Valid @RequestBody EntregaInput entregaInput) {
		Entrega novaEntrega = entregaMapper.toEntity(entregaInput);
		Entrega entregaSolicitada = solicitaEntregaService.solicitar(novaEntrega);
		return entregaMapper.toModel(entregaSolicitada);
	}
	/*
	 * Lista todas as Entregas
	 * @param null
	 * @return List<EntregaModel>
	 */
	@GetMapping
	public List<EntregaModel> listar(){
		return entregaMapper.toCollectionModel(entregaRepository.findAll());
	}
	
	/*
	 * Lista Entregas por Id
	 * @Param EntregaId
	 * @Return ResponseEntity
	 */
	@GetMapping("/{entregaId}")
	public ResponseEntity<EntregaModel> buscar(@PathVariable Long entregaId){
		return entregaRepository.findById(entregaId)
				//resumido usando modelmapper
				.map(entrega -> ResponseEntity.ok(entregaMapper.toModel(entrega)))
				.orElse(ResponseEntity.notFound().build());
		
		/* Antes = Muito código
		 * entregaModel.setId(entrega.getId());
		 * entregaModel.setNomeCliente(entrega.getCliente().getNome());
		 * entregaModel.setDestinatario(new DestinatarioModel());
		 * entregaModel.getDestinatario().setNome(entrega.getDestinatario().getNome());
		 * entregaModel.getDestinatario().setLogradouro(entrega.getDestinatario().
		 * getLogradouro());
		 * entregaModel.getDestinatario().setNumero(entrega.getDestinatario().getNumero(
		 * )); entregaModel.getDestinatario().setComplemento(entrega.getDestinatario().
		 * getComplemento());
		 * entregaModel.getDestinatario().setBairro(entrega.getDestinatario().getBairro(
		 * )); entregaModel.setTaxa(entrega.getTaxa());
		 * entregaModel.setStatus(entrega.getStatus());
		 * entregaModel.setDataPedido(entrega.getDataPedido());
		 * entregaModel.setDataFinalizacao(entrega.getDataFinalizacao());
		 */
	}
	
	@PutMapping("/{entregaId}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long entregaId) {
		finalizacaoEntregaService.finalizar(entregaId);
	}
}
