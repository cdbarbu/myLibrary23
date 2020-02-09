package net.metrosystems.mylibrary23;

import net.metrosystems.mylibrary23.data.model.entity.Book;
import net.metrosystems.mylibrary23.data.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class BookControllerTest  {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    @Transactional
    public void cleanAllDataInTestDb() {
        //before each test all data is deleted so we will work with clean data and current test is not impacted by previous tests of the current execution
        bookRepository.deleteAll();
    }

    @Test
    @Transactional
    public void basicAddAndGet() throws Exception {
        //application is started in test mode and we can now call our controllers
        //we will use MockMvc to perform our Rest calls and check for results
        this.mockMvc.perform(post("/books/title/FirstTitle/authorName/FirstAuthor/year_of_publication/2001")).andDo(print()).andExpect(status().isCreated());
        this.mockMvc.perform(post("/books/title/SecondTitle/authorName/SecondAuthor/year_of_publication/2002")).andDo(print()).andExpect(status().isCreated());
        this.mockMvc.perform(get("/books")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void detailedAddAndGet() throws Exception {
        this.mockMvc.perform(post("/books/title/FirstTitle/authorName/FirstAuthor/year_of_publication/2001")).andDo(print()).andExpect(status().isCreated());
        this.mockMvc.perform(post("/books/title/SecondTitle/authorName/SecondAuthor/year_of_publication/2002")).andDo(print()).andExpect(status().isCreated());
        this.mockMvc.perform(get("/books")).andDo(print()).andExpect(status().isOk());

        //after we call the controller, we check the DB to see if the data is actually there
        //we should have 2 books
        long noBooks = bookRepository.count();
        //check that 2 (long) = noBooks - assertEquals verifica cei 2 parametri daca sunt egali
        assertEquals(2L, noBooks);

        //check if we can find the first book by author and title
        Book bookSingleItem = bookRepository.findByTitleAndAuthorName("FirstTitle", "FirstAuthor");
        assertNotNull(bookSingleItem);
        assertEquals(2001, bookSingleItem.getYearOfPublication());

        //check if we can find the second book by author and title
        bookSingleItem = bookRepository.findByTitleAndAuthorName("SecondTitle", "SecondAuthor");
        assertNotNull(bookSingleItem);
        assertEquals(2002, bookSingleItem.getYearOfPublication());
    }

    @Test
    @Transactional
    public void testMultipleAddsOnSameBookError() throws Exception {
        this.mockMvc.perform(post("/books/title/FirstTitle/authorName/FirstAuthor/year_of_publication/2001")).andDo(print()).andExpect(status().isCreated());
        this.mockMvc.perform(post("/books/title/FirstTitle/authorName/FirstAuthor/year_of_publication/2001")).andDo(print()).andExpect(status().isConflict());
    }
}
