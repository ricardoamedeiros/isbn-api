package br.com.salute.provedor.repository;

import br.com.salute.provedor.entity.Provedor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvedorRepository extends PagingAndSortingRepository<Provedor, Long> {

    @Query("select p from Provedor p where ativo = 'true' order by ordem asc ")
    public List<Provedor> getAtivos();

    @Query("SELECT p FROM Provedor p")
    List<Provedor> recuperarTodos();


}
