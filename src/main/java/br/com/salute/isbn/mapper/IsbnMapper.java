package br.com.salute.isbn.mapper;

import br.com.salute.dto.IsbnDTO;
import br.com.salute.interfaces.mapper.BaseMapper;
import br.com.salute.isbn.entity.Isbn;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IsbnMapper extends BaseMapper<Isbn, IsbnDTO> {
}
