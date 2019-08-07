package br.com.salute.isbn.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Calendar;

@Entity
@Data
public class Isbn {

    @Id
    private String codigoIsbn;
    private String titulo;
    private Integer ano;
    private Integer paginas;
    private String tiragem;
    private Boolean reedicao;
    private Boolean reempressao;
    private String meioDivulgacao;
    private String url;
    private String idioma;
    private String pais;
    private String naturezaObra;
    private String naturezaConteudo;
    private String tipoContribuicao;
    private Integer paginasContribuicaoObra;
    private String tipoEditora;
    private String nomeEditora;
    private String financiamento;
    private String nomeFinanciador;
    private String conselhoEditorial;
    private String distribuicaoAcesso;
    private Boolean informacaoAutores;
    private Boolean revisaoPorPartes;
    private String indiceRemessivo;
    private String premiacao;
    private String instituicaoPremiacao;
    private Calendar dataPremiacao;
    private String indicacaoObraReferencia;
    private String nomeInstituicao;
    private Calendar dataIndicacao;
    private String observacao;
    private String cidadeEditora;
}
