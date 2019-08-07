package br.com.salute.isbn.rest;

import br.com.salute.dto.IsbnDTO;
import br.com.salute.isbn.service.IsbnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/isbns")
@Api(value = "isbns", description = "Operações sobre ISBN", tags = "ISBN")
public class IsbnRest {

    @Autowired
    private IsbnService isbnService;

    @ApiOperation(value = "Recuperar ISBN")
    @RequestMapping(path = "/{isbn}", method = RequestMethod.GET,  produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public IsbnDTO get(@PathVariable("isbn") String isbn) {
        return isbnService.getIsbn(isbn);
    }

    @PostMapping
    @ApiOperation(value = "Salvar ISBN")
    public void save(@RequestBody IsbnDTO isbnDTO) {
        isbnService.save(isbnDTO);
    }

    @PutMapping("/{isbn}")
    @ApiOperation(value = "Alterar ISBN")
    public void update(@PathVariable("isbn") String isbn, @RequestBody IsbnDTO isbnDTO) {
        isbnService.update(isbn, isbnDTO);
    }

    @DeleteMapping("/{isbn}")
    @ApiOperation(value = "Excluir ISBN")
    public void delete(@PathVariable("isbn") String isbn) {
        isbnService.delete(isbn);
    }

    @GetMapping
    @ApiOperation(value = "Listar ISBNs cadastrados")
    public List<IsbnDTO> get(){
        return isbnService.get();
    }
}