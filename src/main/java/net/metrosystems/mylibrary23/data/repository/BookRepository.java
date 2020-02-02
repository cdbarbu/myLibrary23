package net.metrosystems.mylibrary23.data.repository;

import net.metrosystems.mylibrary23.data.model.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findByAuthorName(String authorName);

    Book findByTitleAndAuthorName(String title, String authorName);

}
