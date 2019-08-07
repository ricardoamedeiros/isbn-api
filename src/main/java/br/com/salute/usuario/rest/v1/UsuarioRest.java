package br.com.salute.usuario.rest.v1;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import javax.validation.Valid;

import br.com.salute.dto.IsbnDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.salute.usuario.entity.Usuario;
import br.com.salute.usuario.mapper.UsuarioMapper;
import br.com.salute.usuario.rest.v1.dto.UsuarioDTO;
import br.com.salute.usuario.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@RestController
@RequestMapping("/v1/usuarios")
@Api(value = "usuarios", description = "Operações sobre usuários", tags = "Usuário")
public class UsuarioRest {

    private final UsuarioMapper usuarioMapper;
	private final UsuarioService usuarioService;


	public UsuarioRest(UsuarioService usuarioService, UsuarioMapper usuarioMapper) {
		super();
		this.usuarioMapper = usuarioMapper;
		this.usuarioService = usuarioService;
	}

	@PostMapping
	@ApiOperation(value = "Adiciona um usuário")
	public ResponseEntity<Void> adicionarUsuario(
			@ApiParam(name = "usuario", value = "Representação do usuário a ser adicionado", required = true)
			@Valid @RequestBody UsuarioDTO dto) {
		Usuario novoUsuario = usuarioMapper.toEntity(dto);
		novoUsuario = usuarioService.adicionarUsuario(novoUsuario);
		UsuarioDTO usuarioCriado = usuarioMapper.toDto(novoUsuario);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
				"/{id}").buildAndExpand(usuarioCriado.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@GetMapping("/{isbn}")
	@ApiOperation(value = "Recuperar conector")
	public IsbnDTO getConector(@PathVariable("isbn") String isbn) throws ClassNotFoundException, MalformedURLException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
		URLClassLoader child = new URLClassLoader(new URL[]{new URL("file:/Users/ricardo/workspace/biblioteca-nacional-connector/target/biblioteca-nacional-connector-1.0.0.jar")}, this.getClass().getClassLoader());
		Class classToLoad = Class.forName ("br.com.salute.BibliotecaNacionalConnector", true, child);
		Method method = classToLoad.getDeclaredMethod ("recuperarIsbn", new Class[] {String.class} );
		Object instance = classToLoad.newInstance ();
        return (IsbnDTO) method.invoke(instance,isbn);
	}


}
