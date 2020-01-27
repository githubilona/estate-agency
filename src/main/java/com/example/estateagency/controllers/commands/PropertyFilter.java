package com.example.estateagency.controllers.commands;

import com.example.estateagency.models.Address;
import com.example.estateagency.models.OfferType;
import com.example.estateagency.models.PropertyType;
import com.example.estateagency.models.Province;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.validation.constraints.PositiveOrZero;

@Setter
@Getter
@NoArgsConstructor
public class PropertyFilter {

    private String phrase;
    private String offerType;
    private String propertyType;
    private String province;
    private String city;

    @PositiveOrZero
    private Float minPrice;
    @PositiveOrZero
    private Float maxPrice;


    public boolean isEmpty(){
        return StringUtils.isEmpty(phrase) && StringUtils.isEmpty(offerType) && StringUtils.isEmpty(propertyType) &&
                StringUtils.isEmpty(province) && StringUtils.isEmpty(city) &&
                minPrice == null && minPrice == null;
    }

    public void clear(){
        this.phrase = "";
        this.offerType = "";
        this.propertyType = "";
        this.province = "";
        this.city = "";
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

}
