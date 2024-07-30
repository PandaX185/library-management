package com.panda.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotBlank
    private String title;
    @NotBlank
    @Column(nullable = false)
    private String author;
    @NotBlank
    @Column(nullable = false)
    @Min(1700)
    @Max(2030)
    private int publicationYear;
    @NotBlank
    @Column(nullable = false, unique = true)
    private String isbn;
}
