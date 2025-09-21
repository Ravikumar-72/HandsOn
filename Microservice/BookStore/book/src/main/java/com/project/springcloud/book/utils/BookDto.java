package com.project.springcloud.book.utils;

import com.project.springcloud.book.entity.Book;
import lombok.Data;

@Data
public class BookDto {

    private String title;
    private String author;
    private String description;
    private String genre;
    private Double price;
    private Integer stockQuantity;

    public BookDto(Book book){
        this.setTitle(book.getTitle());
        this.setAuthor(book.getAuthor());
        this.setDescription(book.getDescription());
        this.setGenre(book.getGenre());
        this.setPrice(book.getPrice());
        this.setStockQuantity(book.getStockQuantity());
    }
}
