package net.metrosystems.mylibrary23.data.repository;

import net.metrosystems.mylibrary23.data.model.entity.Book;
import net.metrosystems.mylibrary23.data.model.entity.BookInLibrary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookInLibraryRepository extends CrudRepository<BookInLibrary, Long> {
    List<BookInLibrary> findByBookId(Long bookId);

    Optional<BookInLibrary> findByBookIdAndLibraryId(Long bookId, Long libraryId);

}
