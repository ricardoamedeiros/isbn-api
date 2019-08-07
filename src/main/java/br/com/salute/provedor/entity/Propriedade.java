package br.com.salute.provedor.entity;

import br.com.salute.provedor.rest.ProvedorRest;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Propriedade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String valor;
    @ManyToOne
    @JoinColumn(name = "id_provedor" )
    private Provedor provedor;


}
