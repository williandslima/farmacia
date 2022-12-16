package com.williandsl.farmacia.controller;

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

import com.williandsl.farmacia.model.Postagem;
import com.williandsl.farmacia.repository.PostagemRepository;
import com.williandsl.farmacia.repository.TemaRepository;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*") 
public class PostagemController {

	@Autowired 
	private PostagemRepository postagemRepository;
	
	@Autowired 
	private TemaRepository temaRepository;

	@GetMapping
	public ResponseEntity<List<Postagem>> getAll() { 
		return ResponseEntity.ok(postagemRepository.findAll());

	}

	@GetMapping("/{id}")
	public ResponseEntity<Postagem> getById(@PathVariable Long id) {

		
		return postagemRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());


	}
	
	
	@GetMapping("/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo) { 

		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));

	}

	@PostMapping("/cadastrar")
	public ResponseEntity<Postagem> postPostagem(@Valid @RequestBody Postagem postagem) {
		
		if (temaRepository.existsById(postagem.getTema().getId())) 
			return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

	@PutMapping("/atualizar")
	public ResponseEntity<Postagem> putPostagem(@Valid @RequestBody Postagem postagem) {

		
		if (postagemRepository.existsById(postagem.getId())){
			if (temaRepository.existsById(postagem.getTema().getId()))
				return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem));
	
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

	}

	
	
	@ResponseStatus
	@DeleteMapping("/{id}")
	public void deletePostagem(@PathVariable Long id) {
		
		
		Optional <Postagem> recebeidPostagem = postagemRepository.findById(id);
				
		if (recebeidPostagem.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		postagemRepository.deleteById(id);
		
		
		
	}

}
