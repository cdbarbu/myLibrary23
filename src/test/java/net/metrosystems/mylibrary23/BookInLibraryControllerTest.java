package net.metrosystems.mylibrary23;

import net.metrosystems.mylibrary23.data.repository.BookInLibraryRepository;
import net.metrosystems.mylibrary23.data.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class BookInLibraryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookInLibraryRepository bookInLibraryRepository;

    @BeforeEach
    @Transactional
    public void cleanAllDataInTestDb() {
        //before each test all data is deleted so we will work with clean data and current test is not impacted by previous tests of the current execution
        bookInLibraryRepository.deleteAll();
    }

    @Test
    @Transactional
    public void basicAddAndGet() throws Exception {
        //application is started in test mode and we can now call our controllers
        //we will use MockMvc to perform our Rest calls and check for results

        this.mockMvc.perform(put("/bookInLibs/bookId/253/libraryId/1/stock/5/price/100")).andDo(print()).andExpect(status().isCreated());
//        this.mockMvc.perform(put("/bookInLibs/bookId/254/libraryId/1/stock/10/price/200")).andDo(print()).andExpect(status().isCreated());
//        this.mockMvc.perform(get("/bookInLibs")).andDo(print()).andExpect(status().isOk());
    }
}
