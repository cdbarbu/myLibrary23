package net.metrosystems.mylibrary23.data.repository;

import net.metrosystems.mylibrary23.data.model.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findByAuthorName(String authorName);

    Book findByTitleAndAuthorName(String title, String authorName);

}
