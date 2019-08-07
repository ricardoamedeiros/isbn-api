package br.com.salute.provedor.mapper;

import br.com.salute.dto.IsbnDTO;
import br.com.salute.interfaces.mapper.BaseMapper;
import br.com.salute.isbn.entity.Isbn;
import br.com.salute.provedor.dto.ProvedorDTO;
import br.com.salute.provedor.entity.Provedor;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProvedorMapper extends BaseMapper<Provedor, ProvedorDTO> {
}
