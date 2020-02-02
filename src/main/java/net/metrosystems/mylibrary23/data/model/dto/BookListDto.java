package net.metrosystems.mylibrary23.data.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "books")
@XmlAccessorType(XmlAccessType.FIELD)
public class BookListDto {
    @XmlElement(name = "book")
    private List<BookDto> books;

    public BookListDto() {};

    public BookListDto(List<BookDto> allBooks) {
        this.books = allBooks;
    }

    public List<BookDto> getBooks() {
        return books;
    }

    public void setBooks(List<BookDto> books) {
        this.books = books;
    }
}
