package com.etiya.ecommercedemo4.business.dtos.response.country;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddCountryResponse {
    private int id;
    private String name;
}
