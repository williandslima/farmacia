package com.williandsl.farmacia.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.williandsl.farmacia.model.Produto;


@Repository
public interface ProdutoRepository extends JpaRepository <Produto, Long> {
	
	public List <Produto> findAllByDescricaoContainingIgnoreCase(@Param("descricao") String descricao);
	public List <Produto> findByPrecoGreaterThan (@Param("preco") BigDecimal preco);	//buscar preco acima. 
	public List <Produto> findByPrecoLessThan (@Param("preco") BigDecimal preco); //busca preco abaixo

}
