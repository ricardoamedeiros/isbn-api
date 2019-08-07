package br.com.salute.usuario.rest.v1.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
	
	private Long id;

    private String nome;

    private Integer idade;
}
