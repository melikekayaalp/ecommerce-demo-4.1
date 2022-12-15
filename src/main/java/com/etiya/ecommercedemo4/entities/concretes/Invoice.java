package com.etiya.ecommercedemo4.entities.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Size(min = 7,max = 7)
    @Column(name = "invoice_number")
    private String invoiceNumber;

    @OneToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;
}
