package br.com.salute.provedor.service;

import br.com.salute.provedor.dto.ProvedorDTO;
import br.com.salute.provedor.entity.Provedor;
import br.com.salute.provedor.mapper.ProvedorMapper;
import br.com.salute.provedor.repository.ProvedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProvedorService {

    @Autowired
    private ProvedorRepository provedorRepository;

    @Autowired
    private ProvedorMapper provedorMapper;

    public Provedor salvar(Provedor provedor){
        return provedorRepository.save(provedor);
    }

    public ProvedorDTO get(Long id) {
        return provedorMapper.toDto(provedorRepository.findOne(id));
    }

    public void update(Long id, ProvedorDTO provedorDTO) {
        if(provedorRepository.exists(id)){
            provedorDTO.setId(id);
            Provedor provedor = provedorMapper.toEntity(provedorDTO);
            provedorRepository.save(provedor);
        }
    }

    public void delete(Long id) {
        if(provedorRepository.exists(id)){
            provedorRepository.delete(id);
        }
    }

    public List<ProvedorDTO> get() {
        return provedorMapper.toDto(provedorRepository.recuperarTodos());
    }
}
