package net.metrosystems.mylibrary23.service;

import net.metrosystems.mylibrary23.Utils;
import net.metrosystems.mylibrary23.data.model.dto.BookDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBException;
import java.util.List;

@Service
@PropertySource({"classpath:application.properties"})
public class BusinessService {
    private static Logger logger = LogManager.getLogger(BusinessService.class);

    @Value("${file.location}")
    private String fileLocation;
    @Value("${file.name}")
    private String fileName;

    @Autowired
    private DataService dataService;

    @Autowired
    private XmlFileService xmlService;

    public void takeFromDbAndWriteToXml() {
       logger.info("Read the data from DB");
       List<BookDto> allBooks = dataService.getAllBooks();
       try {
           logger.info("Write data to XML");
           xmlService.printBooksInFile(fileLocation, fileName, allBooks);
       } catch (JAXBException e) {
            logger.error("Error - writing xml", e);
        }
    }

    @Transactional
    public void recreateData () {
        dataService.clearAll();
        dataService.addBooksToDb(Utils.createBooks());
    }
}
