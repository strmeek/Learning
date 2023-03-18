package com.example.learning.domain.service;

import org.springframework.stereotype.Service;

import com.example.learning.domain.exception.EntidadeNaoEncontradaException;
import com.example.learning.domain.model.Entrega;
import com.example.learning.domain.repository.EntregaRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BuscaEntregaService {

	private EntregaRepository entregaRepository;
	
	public Entrega buscar(Long entregaId) {
		return entregaRepository.findById(entregaId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Entrega Não Encontrada"));
	}
}
