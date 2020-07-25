package com.Spring.Web.Model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Paciente implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private Integer idade;
	private Integer quarto;
	
	public Paciente() {	
	}

	public Paciente(Long id, String nome, Integer idade, Integer quarto) {
		this.id = id;
		this.nome = nome;
		this.idade = idade;
		this.quarto = quarto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public Integer getQuarto() {
		return quarto;
	}

	public void setQuarto(Integer quarto) {
		this.quarto = quarto;
	}
	
}
