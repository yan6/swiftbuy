package com.ywj.swiftbuy.admin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryBean {
    private int id;
    private String name;
    private String description;
    private String image;

    public CategoryBean(String name, String description, String image) {
        this.name = name;
        this.description = description;
        this.image = image;
        return;
    }
}
