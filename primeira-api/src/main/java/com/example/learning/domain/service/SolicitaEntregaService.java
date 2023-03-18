package com.example.learning.domain.service;

import java.time.OffsetDateTime;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.learning.domain.model.Cliente;
import com.example.learning.domain.model.Entrega;
import com.example.learning.domain.model.StatusEntrega;
import com.example.learning.domain.repository.EntregaRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service //define classe como service que faz intemédio com o controller e o banco de dados
public class SolicitaEntregaService {

	private EntregaRepository entregaRepository;
	private CrudClienteService crudClienteService;
	
	/*
	 * Método faz a solicitação quando um cliente pede uma entrega
	 * e adiciona no banco de dados
	 * @param entrega
	 * @return entrega
	 */
	@Transactional
	public Entrega solicitar(Entrega entrega){
		Cliente cliente = crudClienteService.buscar(entrega.getCliente().getId());
		
		entrega.setCliente(cliente);
		entrega.setStatus(StatusEntrega.PENDENTE);
		entrega.setDataPedido(OffsetDateTime.now());
		
		return entregaRepository.save(entrega);
	}
}
