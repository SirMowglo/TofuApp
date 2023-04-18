package com.trianaSalesianos.tofuApp.model.dto.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequest {

    //TODO Unique category name
    private String name;
    //TODO HEXADECIMAL color
    private String color;
}
