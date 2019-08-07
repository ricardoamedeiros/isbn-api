package br.com.salute.usuario.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.salute.usuario.entity.Usuario;
import br.com.salute.usuario.repository.UsuarioRepository;

@Service
@Transactional
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public Usuario adicionarUsuario(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	

}
