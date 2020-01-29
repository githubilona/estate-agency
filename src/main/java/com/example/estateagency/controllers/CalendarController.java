package com.example.estateagency.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CalendarController {
    @GetMapping("/calendar")
    public String showCalendar(){
        return "calendar";
    }
}
