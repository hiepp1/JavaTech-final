package com.posweb.website.Model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(unique = true)
    private String barcode;
    private String name;
    private int importPrice;
    private int retailPrice;
    private String category;
    private Date date;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] picture;
}

////barcode / QR Code, product name, import price, retail price, category, creation date.