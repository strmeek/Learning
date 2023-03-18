package com.example.learning.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import com.example.learning.domain.ValidationGroups;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
/*
 * Implementa Equals() e HashCode() métodos
 * mas somente para aqueles que você incluir
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter //produz os getters
@Setter //produz os setters
@Entity //Define a Classe como uma Entidade(facilita na conexão com o banco de dados)
public class Cliente {
	
	//@NotNull(groups = ValidationGroups.ClienteId.class) não necessário pois Mapper/Input/Model
	@EqualsAndHashCode.Include // inclui para gerar os métodos
	@Id //define como primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) //define como auto Increment
	private Long id;
	
	@NotBlank // não pode ser vazio nem em branco
	@Size(max = 60) // define o máximo de caracteres, não deixando chegar no banco de dados
	private String nome;
	
	@NotBlank
	@Email // ativa validação se realmente o usuário está passando um email
	@Size(max = 255)
	private String email;
	
	@NotBlank
	@Size(max = 20)
	@Column(name= "telefone") // define a coluna referente a variavel no banco de dados
	private String telefone;
	
}
