package com.posweb.website.Model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int id;
    @Column(name = "product_name")
    private String name;
    private int importPrice;
    private int retailPrice;
    private String category;
    private Date date;
    private byte img;
}

////barcode / QR Code, product name, import price, retail price, category, creation date.