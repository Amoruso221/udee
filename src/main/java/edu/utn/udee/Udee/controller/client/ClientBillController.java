package edu.utn.udee.Udee.controller.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/client/bills")
public class ClientBillController {

    @GetMapping
    public String hello(){
        return "Hola Fede";
    }
}
