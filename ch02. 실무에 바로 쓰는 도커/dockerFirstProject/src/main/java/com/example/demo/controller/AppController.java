package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class AppController {
    @GetMapping("/hi")
    public String index() {
    		log.info("hihi");
    	return "index";
    }
}