package com.example.learning.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.learning.api.model.EntregaModel;
import com.example.learning.api.model.input.EntregaInput;
import com.example.learning.domain.model.Entrega;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EntregaMapper {
	/*
	 * Classe serve para converter do tipo Entrega
	 * para EntregaModel
	 */
	
	private ModelMapper modelMapper;
	
	public EntregaModel toModel(Entrega entrega) {
		return modelMapper.map(entrega, EntregaModel.class);
	}
	
	public List<EntregaModel> toCollectionModel(List<Entrega> entregas){
		return entregas.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
	public Entrega toEntity(EntregaInput entregaInput) {
		return modelMapper.map(entregaInput, Entrega.class);
	}
}
