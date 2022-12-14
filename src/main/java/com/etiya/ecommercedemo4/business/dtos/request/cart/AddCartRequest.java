package com.etiya.ecommercedemo4.business.dtos.request.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@AllArgsConstructor
@Getter
@Setter
public class AddCartRequest {

    @NotNull
    @PositiveOrZero
    private int quantity;
    @NotNull
    @Positive
    private int customerId;

}
