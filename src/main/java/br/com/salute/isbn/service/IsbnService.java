package br.com.salute.isbn.service;

import br.com.salute.dto.IsbnDTO;
import br.com.salute.isbn.entity.Isbn;
import br.com.salute.isbn.mapper.IsbnMapper;
import br.com.salute.isbn.repository.IsbnRepository;
import br.com.salute.provedor.entity.Provedor;
import br.com.salute.provedor.repository.ProvedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

@Service
@Transactional
public class IsbnService {
    @Autowired
    private IsbnRepository isbnRepository;
    @Autowired
    private IsbnMapper isbnMapper;

    private IsbnDTO isbnDTO;

    @Autowired
    private ProvedorRepository provedorRepository;

    public IsbnDTO getIsbn(String codigoIsbn) {
        Isbn isbn = isbnRepository.findOne(codigoIsbn);
        if(isbn!=null){
            return isbnMapper.toDto(isbn);
        }else{
            isbnDTO = new IsbnDTO();
           return getIsbnByProvedores(codigoIsbn);
        }
    }

    private IsbnDTO getIsbnByProvedores(String codigoIsbn){
        List<Provedor> provedores = provedorRepository.getAtivos();

        provedores.forEach(p -> {
            try{
                System.out.println(p.getLocal());
                URLClassLoader child = new URLClassLoader(new URL[]{new URL("file://"+p.getLocal())}, this.getClass().getClassLoader());
                Class classToLoad = Class.forName (p.getClasse(), true, child);
                Method method = classToLoad.getDeclaredMethod ("recuperarIsbn", new Class[] {String.class} );
                Object instance = classToLoad.newInstance ();
                IsbnDTO isbnDTOTemp = (IsbnDTO) method.invoke(instance, codigoIsbn);
                atualizarIsbnDTO(isbnDTOTemp);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        if(isbnDTO.getCodigoIsbn()!=null)
        isbnRepository.save(isbnMapper.toEntity(isbnDTO));
        return isbnDTO;
    }

    private void atualizarIsbnDTO(IsbnDTO isbnDTOTemp) {
        isbnDTO.setCodigoIsbn(isbnDTO.getCodigoIsbn()==null?isbnDTOTemp.getCodigoIsbn():isbnDTO.getCodigoIsbn());
        isbnDTO.setTitulo(isbnDTO.getTitulo()==null?isbnDTOTemp.getTitulo():isbnDTO.getTitulo());
        isbnDTO.setAno(isbnDTO.getAno()==null?isbnDTOTemp.getAno():isbnDTO.getAno());
        isbnDTO.setPaginas(isbnDTO.getPaginas()==null?isbnDTOTemp.getPaginas():isbnDTO.getPaginas());
        isbnDTO.setTiragem(isbnDTO.getTiragem()==null?isbnDTOTemp.getTiragem():isbnDTO.getTiragem());
        isbnDTO.setReedicao(isbnDTO.getReedicao()==null?isbnDTOTemp.getReedicao():isbnDTO.getReedicao());
        isbnDTO.setReempressao(isbnDTO.getReempressao()==null?isbnDTOTemp.getReempressao():isbnDTO.getReempressao());
        isbnDTO.setMeioDivulgacao(isbnDTO.getMeioDivulgacao()==null?isbnDTOTemp.getMeioDivulgacao():isbnDTO.getMeioDivulgacao());
        isbnDTO.setUrl(isbnDTO.getUrl()==null?isbnDTOTemp.getUrl():isbnDTO.getUrl());
        isbnDTO.setPais(isbnDTO.getPais()==null?isbnDTOTemp.getPais():isbnDTO.getPais());
        isbnDTO.setNaturezaObra(isbnDTO.getNaturezaObra()==null?isbnDTOTemp.getNaturezaObra():isbnDTO.getNaturezaObra());
        isbnDTO.setNaturezaConteudo(isbnDTO.getNaturezaConteudo()==null?isbnDTOTemp.getNaturezaConteudo():isbnDTO.getNaturezaConteudo());
        isbnDTO.setTipoContribuicao(isbnDTO.getTipoContribuicao()==null?isbnDTOTemp.getTipoContribuicao():isbnDTO.getTipoContribuicao());
        isbnDTO.setPaginasContribuicaoObra(isbnDTO.getPaginasContribuicaoObra()==null?isbnDTOTemp.getPaginasContribuicaoObra():isbnDTO.getPaginasContribuicaoObra());
        isbnDTO.setTipoEditora(isbnDTO.getTipoEditora()==null?isbnDTOTemp.getTipoEditora():isbnDTO.getTipoEditora());
        isbnDTO.setNomeEditora(isbnDTO.getNomeEditora()==null?isbnDTOTemp.getNomeEditora():isbnDTO.getNomeEditora());
        isbnDTO.setFinanciamento(isbnDTO.getFinanciamento()==null?isbnDTOTemp.getFinanciamento():isbnDTO.getFinanciamento());
        isbnDTO.setNomeFinanciador(isbnDTO.getNomeFinanciador()==null?isbnDTOTemp.getNomeFinanciador():isbnDTO.getNomeFinanciador());
        isbnDTO.setConselhoEditorial(isbnDTO.getConselhoEditorial()==null?isbnDTOTemp.getConselhoEditorial():isbnDTO.getConselhoEditorial());
        isbnDTO.setDistribuicaoAcesso(isbnDTO.getDistribuicaoAcesso()==null?isbnDTOTemp.getDistribuicaoAcesso():isbnDTO.getDistribuicaoAcesso());
        isbnDTO.setInformacaoAutores(isbnDTO.getInformacaoAutores()==null?isbnDTOTemp.getInformacaoAutores():isbnDTO.getInformacaoAutores());
        isbnDTO.setRevisaoPorPartes(isbnDTO.getRevisaoPorPartes()==null?isbnDTOTemp.getRevisaoPorPartes():isbnDTO.getRevisaoPorPartes());
        isbnDTO.setIndiceRemessivo(isbnDTO.getIndiceRemessivo()==null?isbnDTOTemp.getIndiceRemessivo():isbnDTO.getIndiceRemessivo());
        isbnDTO.setPremiacao(isbnDTO.getPremiacao()==null?isbnDTOTemp.getPremiacao():isbnDTO.getPremiacao());
        isbnDTO.setIndiceRemessivo(isbnDTO.getIndiceRemessivo()==null?isbnDTOTemp.getIndiceRemessivo():isbnDTO.getIndiceRemessivo());
        isbnDTO.setInstituicaoPremiacao(isbnDTO.getInstituicaoPremiacao()==null?isbnDTOTemp.getInstituicaoPremiacao():isbnDTO.getInstituicaoPremiacao());
        isbnDTO.setDataPremiacao(isbnDTO.getDataPremiacao()==null?isbnDTOTemp.getDataPremiacao():isbnDTO.getDataPremiacao());
        isbnDTO.setIndicacaoObraReferencia(isbnDTO.getIndicacaoObraReferencia()==null?isbnDTOTemp.getIndicacaoObraReferencia():isbnDTO.getIndicacaoObraReferencia());
        isbnDTO.setNomeInstituicao(isbnDTO.getNomeInstituicao()==null?isbnDTOTemp.getNomeInstituicao():isbnDTO.getNomeInstituicao());
        isbnDTO.setDataIndicacao(isbnDTO.getDataIndicacao()==null?isbnDTOTemp.getDataIndicacao():isbnDTO.getDataIndicacao());
        isbnDTO.setObservacao(isbnDTO.getObservacao()==null?isbnDTOTemp.getObservacao():isbnDTO.getObservacao());
        isbnDTO.setNomeInstituicao(isbnDTO.getNomeInstituicao()==null?isbnDTOTemp.getNomeInstituicao():isbnDTO.getNomeInstituicao());
        isbnDTO.setCidadeEditora(isbnDTO.getCidadeEditora()==null?isbnDTOTemp.getCidadeEditora():isbnDTO.getCidadeEditora());
    }

    public void save(IsbnDTO isbnDTO) {
        Isbn isbn = isbnMapper.toEntity(isbnDTO);
        isbnRepository.save(isbn);
    }

    public void update(String isbnCodigo, IsbnDTO isbnDTO) {
        if(isbnRepository.exists(isbnCodigo)){
            Isbn isbn = isbnMapper.toEntity(isbnDTO);
            isbnRepository.save(isbn);
        }
    }

    public void delete(String isbn) {
        if(isbnRepository.exists(isbn)){
            isbnRepository.delete(isbn);
        }
    }

    public List<IsbnDTO> get() {
        return isbnMapper.toDto(isbnRepository.recuperarTodos());
    }
}
