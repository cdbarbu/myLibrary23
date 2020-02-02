package net.metrosystems.mylibrary23.data.model.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BookInLibraryId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "book_id")
    private Long bookId;
    @Column(name = "library_id")
    private Long libraryId;

    public BookInLibraryId() {
    }

    public BookInLibraryId(Long bookId, Long libraryId) {
        super();
        this.bookId = bookId;
        this.libraryId = libraryId;
    }

    public Long getBookId() {
        return bookId;
    }

     public Long getLibraryId() {
        return libraryId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        BookInLibraryId  that = (BookInLibraryId) obj;
        return Objects.equals(this.bookId, that.bookId) && Objects.equals(this.libraryId, that.libraryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, libraryId);
    }

}
