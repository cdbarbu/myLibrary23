package net.metrosystems.mylibrary23.service;

import net.metrosystems.mylibrary23.data.model.dto.BookDto;
import net.metrosystems.mylibrary23.data.model.dto.BookListDto;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;


@Service
public class XmlFileService {
    public void printBooksInFile (String fileLocation, String fileName, List<BookDto> allBooks) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(BookListDto.class);
        Marshaller  jaxbMarshaller = jaxbContext.createMarshaller();
        //jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(new BookListDto(allBooks), new File(fileLocation + "/" + fileName));
    }

    public List<BookDto> readBooksFromFile (String fileLocation, String fileName) throws FileNotFoundException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(BookListDto.class);
        BookListDto results = (BookListDto) context.createUnmarshaller().unmarshal(new FileReader(fileLocation + "/" + fileName));
        return results.getBooks();
    }
}
