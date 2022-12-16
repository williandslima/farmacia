package com.williandsl.farmacia.model;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "tb_postagens")
public class Postagem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@NotNull (message = "O Atributo id e obrigatorio")
	private Long id; 
	
	
	@NotBlank(message = "O Atributo titulo e obrigatorio e nao pode ser vazio") 
	@Size(min = 5, max = 100, message = "O Atributo titulo deve conter no minimo 5 e no maximo 100")
	private String titulo;
	
	@NotNull(message = "O Atributo titulo e obrigatorio") //not NULL
	@Size(min = 10, max = 1000, message = "O Atributo titulo deve conter no minimo 10 e no maximo 1000")
	private String texto;
	
	@UpdateTimestamp 
	private LocalDateTime data;
		
	
	@ManyToOne
	@NotNull (message = "Colocar o id do TEMA - Não pode ficar sem tema !!!")
	@JsonIgnoreProperties("postagem")
	private Tema tema;
	
	

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}
	
	

}
