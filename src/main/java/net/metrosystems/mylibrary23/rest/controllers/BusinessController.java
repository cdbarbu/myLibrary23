package net.metrosystems.mylibrary23.rest.controllers;

import net.metrosystems.mylibrary23.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("business")
public class BusinessController {
    @Autowired
    BusinessService businessService;

    @GetMapping(path = "/dbtoxml", produces = "application/json")
    public String takeFromDbAndWriteToXml() {
        businessService.takeFromDbAndWriteToXml();

        return "{\"message\":\"Web worked\"}";
    }

    @GetMapping(path="/recreatedata")
    public ResponseEntity<String> recreateData() {
       businessService.recreateData();
       return new ResponseEntity<>("Data created!", HttpStatus.OK);
    }
}
