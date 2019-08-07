package br.com.salute.provedor.rest;

import br.com.salute.provedor.dto.ProvedorDTO;
import br.com.salute.provedor.entity.Provedor;
import br.com.salute.provedor.mapper.ProvedorMapper;
import br.com.salute.provedor.repository.ProvedorRepository;
import br.com.salute.provedor.service.ProvedorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/provedores")
@Api(value = "provedores", description = "Operações sobre Provedores", tags = "PROVEDOR")
public class ProvedorRest {

    @Autowired
    private ProvedorMapper provedorMapper;

    @Autowired
    private ProvedorService provedorService;

    @Autowired
    private ProvedorRepository provedorRepository;


    @PostMapping
    @ApiOperation(value = "Adiciona o provedor")
    public ResponseEntity<Void> adicionarProvedor(
            @ApiParam(name = "provedor", value = "Representação do provedor", required = true)
            @Valid @RequestBody ProvedorDTO dto) {
        return getVoidResponseEntity(dto);
    }

    private ResponseEntity<Void> getVoidResponseEntity(ProvedorDTO dto) {
        Provedor novo = provedorMapper.toEntity(dto);
        novo = provedorService.salvar(novo);
        ProvedorDTO provedorDTO = provedorMapper.toDto(novo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(provedorDTO.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Recuperar provedor")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET,  produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ProvedorDTO get(@PathVariable("id") Long id) {
        return provedorService.get(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Alterar provedor")
    public void update(@PathVariable("id") Long id, @RequestBody ProvedorDTO provedorDTO) {
        provedorService.update(id, provedorDTO);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Excluir provedor")
    public void delete(@PathVariable("id") Long id) {
        provedorService.delete(id);
    }

    @GetMapping
    @ApiOperation(value = "Listar provedores cadastrados")
    public List<ProvedorDTO> get(){
        return provedorService.get();
    }

}
