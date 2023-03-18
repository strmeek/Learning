package com.example.learning.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.learning.domain.exception.NegocioException;
import com.example.learning.domain.model.Cliente;
import com.example.learning.domain.repository.ClienteRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service //define a classe como serviço, faz mediação com banco de dados
public class CrudClienteService {
	
	private ClienteRepository clienteRepository;
	/*
	 * Método faz salva o cliente no banco de dados definitivamente
	 * trata exceção de EmailDuplicado
	 * @param cliente
	 * @return cliente
	 */
	@Transactional //Define que o método é transacional, ou seja, se der errado não interfere no banco de dados
	public Cliente salvar(Cliente cliente) {
		//Procura se email já existe e devolve true or false
		boolean duplicatedEmail = clienteRepository.findByEmail(cliente.getEmail())
				.stream()
				.anyMatch(clienteExistente -> !clienteExistente.equals(cliente));
		
		if (duplicatedEmail) {
			//lança exceção caso true
			throw new NegocioException("Email já Cadastrado");
		}
		//caso false salva o cliente
		return clienteRepository.save(cliente);
	}
	/*
	 * Método exclui o cliente do banco de dados definitivamente
	 * @param clienteId
	 * @return void
	 */
	@Transactional
	public void excluir(Long clienteId) {
		clienteRepository.deleteById(clienteId);
	}
	
	/*
	 * Método busca cliente pelo ID
	 * trata a exception caso não entrone (404 not found)
	 */
	public Cliente buscar(Long clienteId) {
		return clienteRepository.findById(clienteId)
				.orElseThrow(() -> new NegocioException("Cliente não encontrado"));
	}
}
