package com.company.gamestore.controller;

import com.company.gamestore.service.InvoiceService;
import com.company.gamestore.viewmodel.TshirtVeiwModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/store")
public class TshirtController {

    @Autowired
    private InvoiceService service;

    @PostMapping("/tshirts")
    @ResponseStatus(value = HttpStatus.CREATED)
    public TshirtVeiwModel addTshirt(@RequestBody @Valid TshirtVeiwModel tshirtVeiwModel){
        return service.addTshirt(tshirtVeiwModel);
    }

    @GetMapping("/tshirts/{tshirtId}")
    @ResponseStatus(value = HttpStatus.OK)
    public TshirtVeiwModel getTshirt(@PathVariable int tshirtId){
        return service.getTshirt(tshirtId);
    }

    @GetMapping("/tshirts")
    @ResponseStatus(value = HttpStatus.OK)
    public List<TshirtVeiwModel> getAllTshirts() {
        return service.getAllTshirts();
    }


    @PutMapping("/tshirts")
    @ResponseStatus(value = HttpStatus.OK)
    public TshirtVeiwModel updateTsirt(@RequestBody @Valid TshirtVeiwModel tshirtVeiwModel){
        return service.updateTshirt(tshirtVeiwModel);
    }

    @DeleteMapping("/tshirts/{tshirtId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteTshirt(@PathVariable int tshirtId){
        service.deleteTshirt(tshirtId);
    }

    @GetMapping("/tshirts/color/{color}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<TshirtVeiwModel> findTshirtByColor(@PathVariable String color) {
        return service.findTshirtByColor(color);
    }

    @GetMapping("/tshirts/size/{size}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<TshirtVeiwModel> findTshirtBySize(@PathVariable String size) {
        return service.findTshirtBySize(size);
    }
}
