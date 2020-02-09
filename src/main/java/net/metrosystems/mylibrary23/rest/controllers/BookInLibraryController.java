package net.metrosystems.mylibrary23.rest.controllers;

import net.metrosystems.mylibrary23.data.model.dto.BookDto;
import net.metrosystems.mylibrary23.data.model.dto.BookInLibraryDto;
import net.metrosystems.mylibrary23.data.model.dto.LibraryDto;
import net.metrosystems.mylibrary23.data.model.entity.Book;
import net.metrosystems.mylibrary23.data.model.entity.BookInLibrary;
import net.metrosystems.mylibrary23.data.model.entity.Library;
import net.metrosystems.mylibrary23.data.repository.BookInLibraryRepository;
import net.metrosystems.mylibrary23.data.repository.BookRepository;
import net.metrosystems.mylibrary23.data.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class BookInLibraryController {
    @Autowired
    private BookInLibraryRepository bilRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    BookInLibraryController(BookInLibraryRepository bilRepository) {
        this.bilRepository = bilRepository;
    }

    // get all BILs
    @GetMapping("/bookInLibs") // produces = json este DEFAULT
    public ResponseEntity<List<BookInLibraryDto>> all() {
        //find the BILs
        Iterable<BookInLibrary> bookInLibrary = bilRepository.findAll();

        //convert them to DTO objects
        List<BookInLibraryDto> results = new ArrayList<> ();
        bookInLibrary.forEach(currentBIL -> results.add(convertToBookInLibraryDto(currentBIL)));

        if (results.isEmpty()) {
            //if there is no BIL in DB, the suggested returned code should be 201 (NO_CONTENT) - easily to be interpreted by other app
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else
            //if we have results, the correct returned code is 200 (OK), along with the objects
            return new ResponseEntity<>(results, HttpStatus.OK);
    }

    // update stock
    @PutMapping("/bookInLibs/bookId/{bookId}/libraryId/{libraryId}/stock/{noPieces}/price/{price}")
    public ResponseEntity<String> updateStock(@PathVariable Long bookId, @PathVariable Long libraryId,
                                              @PathVariable Integer noPieces, @PathVariable BigDecimal price) {
        Optional<Book> isBook = bookRepository.findById(bookId);
        if (!isBook.isPresent()) {
            return new ResponseEntity<>("No such book", HttpStatus.NOT_FOUND);
        }

        Optional<Library> isLibrary = libraryRepository.findById(libraryId);
        if (!isLibrary.isPresent()) {
            return new ResponseEntity<>("No such library", HttpStatus.NOT_FOUND);
        }

        Optional<BookInLibrary> isBil = bilRepository.findByBookIdAndLibraryId(bookId, libraryId);
        BookInLibrary bookInLibrary;

        if (!isBil.isPresent()) {
            bookInLibrary = new BookInLibrary(isBook.get(), isLibrary.get());
       } else {
             bookInLibrary = isBil.get();
        }

        bookInLibrary.setNoPieces(noPieces);
        bookInLibrary.setPriceInEur(price);

        BookInLibrary updateBIL = bilRepository.save(bookInLibrary);

        return new ResponseEntity<>("The stock is updated", HttpStatus.CREATED);
    }

    // delete stock
    @DeleteMapping("/bookInLibs/bookId/{bookId}/libraryId/{libraryId}")
    public ResponseEntity<String> deleteStock(@PathVariable Long bookId, @PathVariable Long libraryId) {
        Optional<Book> isBook = bookRepository.findById(bookId);
        if (!isBook.isPresent()) {
            return new ResponseEntity<>("No such book", HttpStatus.NOT_FOUND);
        }

        Optional<Library> isLibrary = libraryRepository.findById(libraryId);
        if (!isLibrary.isPresent()) {
            return new ResponseEntity<>("No such library", HttpStatus.NOT_FOUND);
        }

        Optional<BookInLibrary> bil = bilRepository.findByBookIdAndLibraryId(bookId,libraryId);

        if (bil.isPresent()) {
            bilRepository.delete(bil.get());

            return new ResponseEntity<>("The book is removed from stock's library", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No book to delete from this library", HttpStatus.NOT_FOUND);
        }
    }


    private BookInLibraryDto convertToBookInLibraryDto(BookInLibrary bookInLibrary) {
        BookInLibraryDto bilDto = new BookInLibraryDto();

        bilDto.setNoPieces(bookInLibrary.getNoPieces());
        bilDto.setPriceInEur(bookInLibrary.getPriceInEur());

        LibraryDto libDto = new LibraryDto();
        libDto.setName(bookInLibrary.getLibrary().getName());
        libDto.setAddress(bookInLibrary.getLibrary().getAddress());
        libDto.setPhoneNumber(bookInLibrary.getLibrary().getPhoneNumber());

        bilDto.setLibrary(libDto);

        return bilDto;
    }

}

