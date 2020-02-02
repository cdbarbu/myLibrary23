package net.metrosystems.mylibrary23.service;

import net.metrosystems.mylibrary23.data.model.dto.BookDto;
import net.metrosystems.mylibrary23.data.model.dto.BookInLibraryDto;
import net.metrosystems.mylibrary23.data.model.dto.LibraryDto;
import net.metrosystems.mylibrary23.data.model.entity.Book;
import net.metrosystems.mylibrary23.data.model.entity.BookInLibrary;
import net.metrosystems.mylibrary23.data.model.entity.Library;
import net.metrosystems.mylibrary23.data.repository.BookRepository;
import net.metrosystems.mylibrary23.data.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LibraryRepository libraryRepository;

    @Transactional
    public void clearAll() {
        bookRepository.deleteAll();
        libraryRepository.deleteAll();
    }

    @Transactional
    public void addBooksToDb(List<Book> books) {
        bookRepository.saveAll(books);
    }

    @Transactional
    public List<BookDto> getAllBooks() {
        Iterable<Book> books = bookRepository.findAll();
        List<BookDto> allBooks = new ArrayList<>();

        for (Book b : books) {
            BookDto bookDto = new BookDto();
            bookDto.setAuthorName(b.getAuthorName());
            bookDto.setTitle(b.getTitle());
            bookDto.setYearOfPublication(b.getYearOfPublication());

            List<BookInLibraryDto> bookInLibraryDtos = new ArrayList<>();

            for (BookInLibrary bil : b.getLibrariesOfBookList()) {
                BookInLibraryDto bilDto = new BookInLibraryDto();

                LibraryDto libraryDto = new LibraryDto();
                libraryDto.setAddress(bil.getLibrary().getAddress());
                libraryDto.setName(bil.getLibrary().getName());
                libraryDto.setPhoneNumber(bil.getLibrary().getPhoneNumber());

                bilDto.setLibrary(libraryDto);
                bilDto.setNoPieces(bil.getNoPieces());
                bilDto.setPriceInEur(bil.getPriceInEur());

                bookInLibraryDtos.add(bilDto);
            }

            bookDto.setBookInLibraryStock(bookInLibraryDtos);

            allBooks.add(bookDto);
        }

        return allBooks;
    }

    @Transactional
    public List<Book> findByAuthorName (String authorName) {
        List<Book> booksByAuthor = bookRepository.findByAuthorName(authorName);
//        ca sa mearga fetch = FetchType.LAZY:
//               @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//               private List<BookInLibrary> librariesOfBookList = new ArrayList<>();
//        din clasa Book, adaugate for-urile de mai jos!!! alternativa: folosire EAGER in loc de LAZY!!!!
        for (Book b : booksByAuthor) {
            List<BookInLibrary> librariesOfBookList = b.getLibrariesOfBookList();
            for (BookInLibrary bil : librariesOfBookList) {
                System.out.println(bil);
            }
        }
        return booksByAuthor;
    }

    @Transactional
    public void removeLibraryOfBook (BookDto bookDto, LibraryDto libraryDto) {
        Book book = bookRepository.findByTitleAndAuthorName(bookDto.getTitle(), bookDto.getAuthorName());
        Library library = libraryRepository.findByNameAndAddress(libraryDto.getName(), libraryDto.getAddress());
        book.removeLibrary(library);
        bookRepository.save(book);
    }

    @Transactional
    public void updateBookStockAndPrice (BookDto bookDto, LibraryDto libraryDto, int noPieces, BigDecimal priceInEur) {
        Book book = bookRepository.findByTitleAndAuthorName(bookDto.getTitle(), bookDto.getAuthorName());
        Library library = libraryRepository.findByNameAndAddress(libraryDto.getName(), libraryDto.getAddress());
        book.updateBook(library, noPieces, priceInEur);
    }

}


