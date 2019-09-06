package com.company.gamestore.controller;

import com.company.gamestore.service.InvoiceService;
import com.company.gamestore.viewmodel.InvoiceViewModel;
import com.company.gamestore.viewmodel.PurchaseViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/store")
public class InvoiceController {

    @Autowired
    private InvoiceService service;

    @PostMapping("/purchases")
    @ResponseStatus(value = HttpStatus.CREATED)
    public InvoiceViewModel purchase(@RequestBody @Valid PurchaseViewModel purchaseViewModel){
        return service.purchase(purchaseViewModel);
    }

    @RequestMapping(value = "/loggedin", method = RequestMethod.GET)
    public String loggedIn(Principal principal) {
        return "Hello " + principal.getName() + "! Looks like you're logged in!";
    }

}
