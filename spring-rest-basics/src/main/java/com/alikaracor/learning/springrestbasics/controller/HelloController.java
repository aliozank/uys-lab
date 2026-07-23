package com.alikaracor.learning.springrestbasics.controller;

import org.springframework.web.bind.annotation.*;

@RequestMapping("/hello")
@RestController
public class HelloController {


    @GetMapping
    public String sayHello() {
        return "Spring laba hoş geldin.";
    }

    @GetMapping("/{name}")
    public String sayHelloToName(@PathVariable String name) {   //URLDEN GELEN DEĞERİ NAME DEĞİŞKENİNE ATAR @PATHVARİABLE

        return "Merhaba " + name + " Tanıştığımıza memnum oldum";

    }

    @GetMapping("/{search}")
    public String sayHelloWithQueryParameter(@RequestParam(defaultValue = "Misafir") String name) {   //NAME GELMEZSE DEFAULT VALUE DÖNER

        return "Merhaba " + name;
    }
}
