package net.metrosystems.mylibrary23.data.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "books", uniqueConstraints = {@UniqueConstraint(name = "uk_books", columnNames = {"title", "author_name"})})
@SequenceGenerator(name = "gen_books_seq", sequenceName = "books_seq")
public class Book {
    @Id
    @GeneratedValue(generator = "gen_books_seq", strategy = GenerationType.SEQUENCE)
    //@Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author_name", nullable = false)
    private String authorName;

    @Column(name = "year_of_publication", nullable = false)
    private int yearOfPublication;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<BookInLibrary> librariesOfBookList = new ArrayList<>();

    public Book() {
    }

    public Book(String title, String authorName, int yearOfPublication) {
        this.title = title;
        this.authorName = authorName;
        this.yearOfPublication = yearOfPublication;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public List<BookInLibrary> getLibrariesOfBookList() {
        return librariesOfBookList;
    }

    public void setLibrariesOfBookList(List<BookInLibrary> librariesOfBookList) {
        this.librariesOfBookList = librariesOfBookList;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Book that = (Book) obj;
        return Objects.equals(this.title, that.getTitle()) && Objects.equals(this.authorName, that.getAuthorName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.title, this.authorName);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorName='" + authorName + '\'' +
                ", yearOfPublication=" + yearOfPublication +
                ", librariesOfBookList=" + librariesOfBookList +
                '}';
    }

    public void addLibrary(Library library, BigDecimal priceInEur, int noPieces) {
        BookInLibrary bil = new BookInLibrary(this, library);
        bil.setPriceInEur(priceInEur);
        bil.setNoPieces(noPieces);
        librariesOfBookList.add(bil);
        library.getBooksOfLibraryList().add(bil);
    }

    public void removeLibrary(Library library) {
        for (Iterator<BookInLibrary> iterator = librariesOfBookList.iterator(); iterator.hasNext(); ) {
            BookInLibrary bil = iterator.next();

            if (bil.getLibrary().equals(library)) {
                iterator.remove();
                bil.getLibrary().getBooksOfLibraryList().remove(bil);
                bil.setBook(null);
                bil.setLibrary(null);
            }
        }
    }

    public void updateBook(Library library, int noPieces, BigDecimal priceInEur) {
        for (BookInLibrary bil : librariesOfBookList) {
            if (bil.getLibrary().equals(library)) {
                bil.setNoPieces(noPieces);
                bil.setPriceInEur(priceInEur);
                break;
            }
        }
    }

}

