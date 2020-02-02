package net.metrosystems.mylibrary23.rest.controllers;

import net.metrosystems.mylibrary23.data.model.dto.BookDto;
import net.metrosystems.mylibrary23.data.model.dto.BookInLibraryDto;
import net.metrosystems.mylibrary23.data.model.dto.LibraryDto;
import net.metrosystems.mylibrary23.data.model.entity.Book;
import net.metrosystems.mylibrary23.data.model.entity.BookInLibrary;
import net.metrosystems.mylibrary23.data.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    private BookRepository repository;

    @Autowired
    BookController(BookRepository repository) {
        this.repository = repository;
    }

//    @Autowired
//    private BookRepository repository;

    // get all books
    @GetMapping("/books") // produces = json este DEFAULT
    public ResponseEntity<List<BookDto>> all() {
        //find the books
        Iterable<Book> books = repository.findAll();

        //convert them to DTO objects
        List<BookDto> results = new ArrayList<> ();
        books.forEach(currentBook -> results.add(convertToBookDto(currentBook)));

        if (results.isEmpty()) {
            //if there is no books in DB, the suggested returned code should be 201 (NO_CONTENT) - easily to be interpreted by other app
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else
            //if we have results, the correct returned code is 200 (OK), along with the objects
            return new ResponseEntity<>(results, HttpStatus.OK);
    }

    //insert new book
    @PostMapping("/books/title/{title}/authorName/{authorName}/year_of_publication/{yearOfPublication}")
    public ResponseEntity<String> newBook(@PathVariable String title, @PathVariable String authorName, @PathVariable Integer yearOfPublication) {
        //check parameters - if fail -->> error 412 - PRECONDITION_FAILED status
        if (yearOfPublication<1900)
            return new ResponseEntity<>("This book is too old for us. Please, provide a year > 1900", HttpStatus.PRECONDITION_FAILED);
        if (yearOfPublication> LocalDate.now().getYear())
            return new ResponseEntity<>("This book is not published yet. Please, provide a year <= current year", HttpStatus.PRECONDITION_FAILED);
        //check if book already exists -> if yes -->> error 409 - CONFLICT status
        if (repository.findByTitleAndAuthorName(title, authorName) != null)
            return new ResponseEntity<>("This book is already in database!", HttpStatus.CONFLICT);

        Book newBook = new Book(title, authorName, yearOfPublication);
        Book savedBook = repository.save(newBook);
        return new ResponseEntity<>("The book is inserted with id " + savedBook.getId(), HttpStatus.CREATED);
    }

    // cautare dupa id
    @GetMapping("/books/id/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        Optional<Book> isBook = repository.findById(id);

        if (isBook.isPresent()) {
            BookDto requestedBook = convertToBookDto(isBook.get());
            return new ResponseEntity<>(requestedBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // update total book
    @PutMapping(path="/books/id/{id}/title/{title}/authorName/{authorName}/year_of_publication/{yearOfPublication}")
    public ResponseEntity<String> replaceBook(@PathVariable Long id, @PathVariable String title,
                                              @PathVariable String authorName, @PathVariable int yearOfPublication) {
        Optional<Book> isBook = repository.findById(id);

        if (!isBook.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Book book = isBook.get();
        book.setAuthorName(authorName);
        book.setTitle(title);
        book.setYearOfPublication(yearOfPublication);

        Book updateBook = repository.save(book);
        return new ResponseEntity<>("The book is updated", HttpStatus.OK);
    }

    @DeleteMapping("/books/id/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        Optional<Book> isBook = repository.findById(id);

        if (!isBook.isPresent())
            return new ResponseEntity<>("The book doesn't exist",HttpStatus.NOT_FOUND);

        repository.deleteById(id);

//        Book book = isBook.get();
//        repository.delete(book);
        return new ResponseEntity<>("The book is deleted", HttpStatus.OK);
    }

    private BookDto convertToBookDto(Book book) {
        BookDto bookDto = new BookDto(book.getAuthorName(), book.getTitle(), book.getYearOfPublication());

        //convert also the libraries detail
        List<BookInLibrary> bookInLibrary = book.getLibrariesOfBookList();

        List<BookInLibraryDto> bookInLibraryDtos = new ArrayList<>();

        for (BookInLibrary currentBil : bookInLibrary) {
            LibraryDto libraryDto = new LibraryDto(currentBil.getLibrary().getName(),
                                                   currentBil.getLibrary().getAddress(),
                                                   currentBil.getLibrary().getPhoneNumber());

            BookInLibraryDto currentBilDto = new BookInLibraryDto(libraryDto, currentBil.getPriceInEur(), currentBil.getNoPieces());

            bookInLibraryDtos.add(currentBilDto);
        }

        bookDto.setBookInLibraryStock(bookInLibraryDtos);

        return bookDto;
    }
}

