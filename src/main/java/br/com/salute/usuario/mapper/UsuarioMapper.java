package br.com.salute.usuario.mapper;

import br.com.salute.interfaces.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import br.com.salute.usuario.entity.Usuario;
import br.com.salute.usuario.rest.v1.dto.UsuarioDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsuarioMapper extends BaseMapper<Usuario, UsuarioDTO> {


}
