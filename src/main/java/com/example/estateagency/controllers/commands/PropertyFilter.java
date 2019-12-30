package com.example.estateagency.controllers.commands;

import org.springframework.util.StringUtils;

import javax.validation.constraints.PositiveOrZero;

public class PropertyFilter {

    private String phrase;

    @PositiveOrZero
    private Float minPrice;
    @PositiveOrZero
    private Float maxPrice;


    public boolean isEmpty(){
        return StringUtils.isEmpty(phrase) && minPrice == null && minPrice == null;
    }

    public void clear(){
        this.phrase = "";
        this.minPrice = null;
        this.maxPrice = null;
    }

    public String getPhraseLIKE(){
        if(StringUtils.isEmpty(phrase)) {
            return null;
        }else{
            return "%"+phrase+"%";
        }
    }



    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public Float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Float minPrice) {
        this.minPrice = minPrice;
    }

    public Float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Float maxPrice) {
        this.maxPrice = maxPrice;
    }
}
