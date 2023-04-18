package com.trianaSalesianos.tofuApp.model.dto.type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeRequest {
//TODO UNIQUE Type name
    private String name;

}
