package com.example.learning.api.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.learning.domain.model.Cliente;
import com.example.learning.domain.repository.ClienteRepository;
import com.example.learning.domain.service.CrudClienteService;

import lombok.AllArgsConstructor;

@AllArgsConstructor //Cria todos os construtores para a classe
@RestController //Define a Classe como Controller
@RequestMapping("/clientes") //Define o local que ela controla
public class ClienteController {
	/*
	 * interface que me ajuda a buscar no banco de dados
	 * @extends JPAREPOSITORY
	 */
	private ClienteRepository clienteRepository;
	/*
	 * Faz a conexão com o banco de dados mais segura
	 * e isola/tira essa função de dentro do controlador
	 */
	private CrudClienteService crudClienteService;
	
	/*
	 * Metodo lista todos os clientes no banco de dados
	 * @return List<Cliente>
	 */
	@GetMapping //Define que isso é GET em HTTP
	public List<Cliente> listar() {
		return clienteRepository.findAll();
	}
	
	/*
	 * Método busca cliente por ID no banco de dados
	 * @param clienteId
	 * @exception 404 not found
	 * @return Cliente
	 */
	@GetMapping("/{clienteId}")
	/*Define um GET em HTTP com uma variável
	 *(@PATHVARIABLE Passa a variável no mapping*/
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
		//Funcional solution
		return clienteRepository.findById(clienteId)
				//.map(cliente -> ResponseEntity.ok(cliente))
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
		
		//Normal
		//Optional<Cliente> cliente = clienteRepository.findById(clienteId);
		/*if (cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}
		
		return ResponseEntity.notFound().build();*/
	}
	
	/*
	 * @PostMapping Diz que esse método é um POST em HTTP
	 * @ResponseStatus Define qual código retorna quando o metodo rodar corretamente
	 * @Valid Cria uma validação baseada em javax.validation
	 * @RequestBody retorna as informações em cliente
	 * 
	 * Método criado para adicionar clientes no Banco de dados
	 * @param Cliente
	 * @return Cliente
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
		//return clienteRepository.save(cliente);
		return crudClienteService.salvar(cliente);
	}
	
	/*
	 * @PutMapping Diz que esse método é um PUT em HTTP
	 * @Valid, @pathvariable, @requestbody já foram definidos em comentarios anteriores
	 * 
	 * Esse método atualiza informações do cliente desejado
	 * @param clienteId, cliente
	 * @return ResponseEntity
	 */
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clienteId,
			@RequestBody Cliente cliente){
		
		if (!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
			}
		cliente.setId(clienteId);
		cliente = clienteRepository.save(cliente);
		
		return ResponseEntity.ok(cliente);
	}
	
	/*
	 * @DeleteMapping diz que o método é um DELETE em HTTP
	 * 
	 * Método Deleta um cliente do banco de dados
	 * @param clienteId
	 * @return ResponseEntity NoContent
	 */
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> deletar(@PathVariable Long clienteId){
		
		if(!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		
		//clienteRepository.deleteById(clienteId);
		crudClienteService.excluir(clienteId);
		
		return ResponseEntity.noContent().build();
	}
}
