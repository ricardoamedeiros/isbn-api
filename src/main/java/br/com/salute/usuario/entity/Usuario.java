package br.com.salute.usuario.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Usuario {
    
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

    private String nome;

    private Integer idade;
}