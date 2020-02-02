package net.metrosystems.mylibrary23.data.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "bookInLibrary")
@XmlAccessorType(XmlAccessType.FIELD)
public class BookInLibraryDto {
//    private BookDto book;
    @XmlElement
    private LibraryDto library;
    @XmlElement
    private BigDecimal priceInEur;
    @XmlElement
    private int noPieces;


    public BookInLibraryDto() {};

//    public BookInLibraryDto(BookDto book, LibraryDto library, int priceInEur, int noPieces) {
//        this.book = book;
    public BookInLibraryDto(LibraryDto library, BigDecimal price, int noPieces) {
        this.library = library;
        this.priceInEur = price;
        this.noPieces = noPieces;
    }

//    public BookDto getBook() {
//        return book;
//    }

//    public void setBook(BookDto book) {
//        this.book = book;
//    }


    public LibraryDto getLibrary() {
        return library;
    }

    public void setLibrary(LibraryDto library) {
        this.library = library;
    }

    public BigDecimal getPriceInEur() {
        return priceInEur;
    }

    public void setPriceInEur(BigDecimal priceInEur) {
        this.priceInEur = priceInEur;
    }

    public int getNoPieces() {
        return noPieces;
    }

    public void setNoPieces(int noPieces) {
        this.noPieces = noPieces;
    }

    @Override
    public String toString() {
        return "BookInLibraryDto{" +
                "library=" + library +
                ", priceInDollar=" + priceInEur +
                ", noPieces=" + noPieces +
                '}';
    }
}