package br.com.salute.provedor.dto;


import lombok.Data;

@Data
public class ProvedorDTO {
    private Long id;
    private String nome;
    private Boolean ativo;
    private String local;
    private Integer ordem;
    private String classe;
}