package net.metrosystems.mylibrary23;

import net.metrosystems.mylibrary23.data.model.entity.Book;
import net.metrosystems.mylibrary23.data.model.entity.BookInLibrary;
import net.metrosystems.mylibrary23.data.model.entity.Library;
import net.metrosystems.mylibrary23.service.BusinessService;
import net.metrosystems.mylibrary23.service.DataService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@PropertySource({"classpath:application.properties"})
//public class Application implements CommandLineRunner {
public class Application {
    private static Logger logger = LogManager.getLogger(Application.class);

    @Autowired
    private DataService dataService;
    @Autowired
    private BusinessService businessService;

    public static void main(String[] args) {
        logger.info("STARTING THE APPLICATION");
        SpringApplication.run(Application.class, args);
        logger.info("APPLICATION FINISHED");
    }

//   !!!! metoda run este necesara pentru CommandRunner (cand clasa Application implementeaza interfata CommandRunner !!!!
//    @Override
//    public void run(String...args) throws Exception {
//        dataService.clearAll();
//        dataService.addBooksToDb(createBooks());
//

//        acelasi apel utilizat in BusinessService!!!!!!!
//        List<BookDto> bookDtoList = dataService.getAllBooks();
//        System.out.println("getAllBooks:");
//        for (BookDto b : bookDtoList) {
//            System.out.println(b);
//        }
//

//        List<Book> bookList = dataService.findByAuthorName("Author1");
//        System.out.println("getAuthor1:");
//        for (Book b : bookList) {
//            System.out.println(b);
//        }

//        BookDto bookToRemove = new BookDto("Author1", "Title1", 0);
//        LibraryDto libraryToRemove = new LibraryDto("LibraryName1", "Address1", null);
//        dataService.removeLibraryOfBook(bookToRemove, libraryToRemove);

//        BookDto bookUpdated = new BookDto("Author1", "Title1", 0);
//        LibraryDto libraryOfBookUpdated = new LibraryDto("LibraryName1", "Address1", null);
//        dataService.updateBookStockAndPrice(bookUpdated, libraryOfBookUpdated, 30, new BigDecimal(100));

//        citire date din DB si scriere in XML
//        businessService.takeFromDbAndWriteToXml();
//   }
// end pentru metoda run !!!!

    // in relatie cu metoda run de mai sus (mutata in clasa Utils ca sa poata fi folosita si in web)
//    private List<Book> createBooks() {
//        List<Book> books = new ArrayList<>();
//
//        //String title, String authorName, int yearOfPublication
//        Book book1 = new Book("Title1", "Author1", 2011);
//        Book book2 = new Book("Title2", "Author2", 2012);
//        Book book3 = new Book("Title3", "Author3", 2013);
//
//        //String name, String address, String phoneNumber
//        Library library1 = new Library("LibraryName1", "Address1", "012345");
//        Library library2 = new Library("LibraryName2", "Address2", "987654");
//
//        //BookInLibrary list
//        book1.addLibrary(library1, new BigDecimal(11), 105);
//        book1.addLibrary(library2, new BigDecimal(12), 200);
//        book2.addLibrary(library1, new BigDecimal(20), 25);
//        book3.addLibrary(library2, new BigDecimal(30), 15);
//
//        books.add(book1);
//        books.add(book2);
//        books.add(book3);
//
//        return books;
//    }
}
