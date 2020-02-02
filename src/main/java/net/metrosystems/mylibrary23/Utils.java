package net.metrosystems.mylibrary23;

import net.metrosystems.mylibrary23.data.model.entity.Book;
import net.metrosystems.mylibrary23.data.model.entity.Library;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<Book> createBooks() {
        List<Book> books = new ArrayList<>();

        //String title, String authorName, int yearOfPublication
        Book book1 = new Book("Title1", "Author1", 2011);
        Book book2 = new Book("Title2", "Author2", 2012);
        Book book3 = new Book("Title3", "Author3", 2013);

        //String name, String address, String phoneNumber
        Library library1 = new Library("LibraryName1", "Address1", "012345");
        Library library2 = new Library("LibraryName2", "Address2", "987654");

        //BookInLibrary list
        book1.addLibrary(library1, new BigDecimal(11), 105);
        book1.addLibrary(library2, new BigDecimal(12), 200);
        book2.addLibrary(library1, new BigDecimal(20), 25);
        book3.addLibrary(library2, new BigDecimal(30), 15);

        books.add(book1);
        books.add(book2);
        books.add(book3);

        return books;
    }
}
