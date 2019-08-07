package br.com.salute.isbn.repository;

import br.com.salute.isbn.entity.Isbn;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IsbnRepository extends PagingAndSortingRepository<Isbn, String> {

    @Query("SELECT i FROM Isbn i")
    List<Isbn> recuperarTodos();

}
