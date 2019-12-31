package com.example.estateagency.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such advertisement")
public class PropertyNotFoundException extends RuntimeException{

    public PropertyNotFoundException(){
        super(String.format("Ogłoszenie nie istnieje"));
    }

    public PropertyNotFoundException(Long id){
        super(String.format("Ogłoszenie o id %d nie istnieje", id));
    }
}
