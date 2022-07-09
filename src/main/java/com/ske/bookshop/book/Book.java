package com.ske.bookshop.book;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Book {
    @Id
    private String id;
    private String title;
    private String author_id;
    private String publisher_id;
    private double price;


}
