package com.posweb.website.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductForm {
    private int id;
    private String name;
    private String category;
    private int importPrice;
    private MultipartFile image;

    // Add getters and setters
}
