package com.example.learning.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.learning.domain.model.Entrega;

/*
 * Ajuda a fazer uma busca e conexão no banco de dados na tabela ENTREGA
 */
@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long>{

}
