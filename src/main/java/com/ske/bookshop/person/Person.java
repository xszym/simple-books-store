package com.ske.bookshop.person;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    //In mongodb in id is by default String.
    @Id
    private String id;
    private String firstName;
    private String secondName;
    @Indexed(unique = true)
    private String email;

}
