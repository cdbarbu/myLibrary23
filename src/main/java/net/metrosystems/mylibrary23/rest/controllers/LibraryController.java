package net.metrosystems.mylibrary23.rest.controllers;

import net.metrosystems.mylibrary23.data.model.dto.LibraryDto;
import net.metrosystems.mylibrary23.data.model.entity.Library;
import net.metrosystems.mylibrary23.data.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class LibraryController {
    @Autowired
    private LibraryRepository libraryRepository;

    //get all libraries
    @GetMapping("/libraries")
    public ResponseEntity<List<LibraryDto>> all() {
        //find the books
        Iterable<Library> libraries = libraryRepository.findAll();

        //convert them to DTO objects
        List<LibraryDto> results = new ArrayList<>();
        libraries.forEach(currentLibrary -> results.add(convertToLibraryDto(currentLibrary)));

        if (results.isEmpty()) {
            //if there is no books in DB, the suggested returned code should be 201 (NO_CONTENT) - easily to be interpreted by other app
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else
            //if we have results, the correct returned code is 200 (OK), along with the objects
            return new ResponseEntity<>(results, HttpStatus.OK);
    }

    //insert new library
    @PostMapping("/libraries/name/{name}/address/{address}/phoneNumber/{phoneNumber}")
    public ResponseEntity<String> newBook(@PathVariable String name, @PathVariable String address, @PathVariable String phoneNumber) {
        //check if library already exists -> if yes -->> error 409 - CONFLICT status
        if (libraryRepository.findByNameAndAddress(name, address) != null)
            return new ResponseEntity<>("This library is already in database!", HttpStatus.CONFLICT);

        Library newLibrary = new Library(name, address, phoneNumber);
        Library savedLibrary = libraryRepository.save(newLibrary);
        return new ResponseEntity<>("The library inserted has id " + savedLibrary.getId(), HttpStatus.CREATED);
    }

    // cautare dupa id
    @GetMapping("/libraries/id/{id}")
    public ResponseEntity<LibraryDto> getLibraryById(@PathVariable Long id) {
        Optional<Library> isLibrary = libraryRepository.findById(id);

        if (isLibrary.isPresent()) {
            LibraryDto requestedLibrary = convertToLibraryDto(isLibrary.get());
            return new ResponseEntity<>(requestedLibrary, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // update total library
    @PutMapping(path="/libraries/id/{id}/name/{name}/address/{address}/phoneNumber/{phoneNumber}")
    public ResponseEntity<String> replaceLibrary(@PathVariable Long id, @PathVariable String name,
                                              @PathVariable String address, @PathVariable String phoneNumber) {
        Optional<Library> isLibrary = libraryRepository.findById(id);

        if (!isLibrary.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Library library = isLibrary.get();
        library.setName(name);
        library.setAddress(address);
        library.setPhoneNumber(phoneNumber);

        Library updateLibrary = libraryRepository.save(library);
        return new ResponseEntity<>("The library is updated", HttpStatus.OK);
    }

    @DeleteMapping("/libraries/id/{id}")
    public ResponseEntity<String> deleteLibrary(@PathVariable Long id) {
        Optional<Library> isLibrary = libraryRepository.findById(id);

        if (!isLibrary.isPresent())
            return new ResponseEntity<>("This library doesn't exist",HttpStatus.NOT_FOUND);

        libraryRepository.deleteById(id);

        return new ResponseEntity<>("The library is deleted", HttpStatus.OK);
    }

    private LibraryDto convertToLibraryDto(Library library) {
        LibraryDto libraryDto = new LibraryDto(library.getName(), library.getAddress(), library.getPhoneNumber());

        return libraryDto;
    }

}
