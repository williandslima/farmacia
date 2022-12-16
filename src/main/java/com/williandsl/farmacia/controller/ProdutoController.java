package com.williandsl.farmacia.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.williandsl.farmacia.model.Produto;

import com.williandsl.farmacia.repository.CategoriaRepository;
import com.williandsl.farmacia.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public ResponseEntity<List<Produto>> getAll() {

		return ResponseEntity.ok(produtoRepository.findAll());

	}

	@GetMapping("/{id}")
	public ResponseEntity<Produto> getById(@PathVariable Long id) {

		return produtoRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/precoacimade/{preco}")
	public ResponseEntity<List<Produto>> getByPreco(@PathVariable BigDecimal preco) {

		return ResponseEntity.ok(produtoRepository.findByPrecoGreaterThan(preco));

	}

	@GetMapping("/precoabaixode/{preco}")
	public ResponseEntity<List<Produto>> getByPrecoAbaixo(@PathVariable BigDecimal preco) {

		return ResponseEntity.ok(produtoRepository.findByPrecoLessThan(preco));

	}

	@GetMapping("/produto/{descricao}")
	public ResponseEntity<List<Produto>> getByDescricao(@PathVariable String descricao) { // resposta HTTP

		return ResponseEntity.ok(produtoRepository.findAllByDescricaoContainingIgnoreCase(descricao));

	}

	// postar
	@PostMapping
	public ResponseEntity<Produto> postProtudo(@Valid @RequestBody Produto descricao) {

		if (categoriaRepository.existsById(descricao.getCategoria().getId()))
			return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(descricao));

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	// atualizar
	@PutMapping
	public ResponseEntity<Produto> putProduto(@Valid @RequestBody Produto descricao) {

		// return
		// ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem));

		if (produtoRepository.existsById(descricao.getId())) {
			if (categoriaRepository.existsById(descricao.getCategoria().getId()))
				return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(descricao));

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

	}

	// deletar
	@ResponseStatus
	@DeleteMapping("/{id}")
	public void deleteProduto(@PathVariable Long id) {

		Optional<Produto> recebeidProduto = produtoRepository.findById(id);

		if (recebeidProduto.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		produtoRepository.deleteById(id);

	}

}