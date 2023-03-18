package com.example.learning.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.example.learning.domain.exception.NegocioException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Entrega {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/*
	 * @ManyToOne notation
	 * Define a Relação com CLIENTE como N para 1, ou seja
	 * um cliente pode receber + de uma entrega, mas cada entrega vai
	 *  somente pra 1 cliente
	 * @ConvertGroup - muda o grupo de validação, para função especifica
	 * de validar o id pra solicitar entregas
	 */
	@ManyToOne
	//@NotNull
	//@Valid não mais necessário por conta do Mapper / Input / Model
	//@ConvertGroup(from = Default.class ,to = ValidationGroups.ClienteId.class)
	private Cliente cliente;
	
	//@NotNull
	//@Valid
	@Embedded //inclui as informações na tabela de Entrega
	private Destinatario destinatario;
	
	//@NotNull
	private BigDecimal taxa;
	
	@OneToMany(mappedBy = "entrega", cascade = CascadeType.ALL) // sincronizar ocorrencias
	private List<Ocorrencia> ocorrencias = new ArrayList<>();
	
	//@JsonProperty(access = Access.READ_ONLY) // impede que o usuário passe informações nesse campo
	@Enumerated(EnumType.STRING) // aparece no banco de dados com o nome "PENDENTE" (+organizado)
	private StatusEntrega status;
	
	//@JsonProperty(access = Access.READ_ONLY) //impede que o usuário passe informações nessa variável
	private OffsetDateTime dataPedido;
	
	//@JsonProperty(access = Access.READ_ONLY) não é mais necessário por conta dos Models que já validam
	private OffsetDateTime dataFinalizacao;

	public Ocorrencia adicionarOcorrencia(String descricao) {
		
		Ocorrencia ocorrencia = new Ocorrencia();
		
		ocorrencia.setDescricao(descricao);
		ocorrencia.setDataRegistro(OffsetDateTime.now());
		ocorrencia.setEntrega(this);
		
		this.getOcorrencias().add(ocorrencia);
		
		return ocorrencia;
	}

	public void finalizar() {
		if(!podeSerFinalizada()) {
			throw new NegocioException("Entrega não pode ser finalizada");
		}
		
		setStatus(StatusEntrega.FINALIZADA);
		setDataFinalizacao(OffsetDateTime.now());
	}
	
	public boolean podeSerFinalizada() {
		return StatusEntrega.PENDENTE.equals(getStatus());
	}
}

