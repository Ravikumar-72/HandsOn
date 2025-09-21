package com.project.springcloud.book.controller;


import com.project.springcloud.book.entity.Book;
import com.project.springcloud.book.service.BookService;
import com.project.springcloud.book.utils.BookDto;
import com.project.springcloud.book.utils.StockUpdate;
import com.project.springcloud.book.utils.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/")
    public ResponseEntity<SuccessResponse> createNewBook(@RequestBody BookDto bookDto){
        bookService.createBook(bookDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse("201","Book added successfully!"));
    }

    @GetMapping("/")
    public ResponseEntity<List<Book>> showAllBooks(){
        List<Book> books = bookService.showAllBooks();
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id){
        Book book = bookService.getBook(id);
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody BookDto bookDto){
        Book updatedBook = bookService.updateBook(id, bookDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedBook);
    }

    @PutMapping("/stock/{id}")
    public ResponseEntity<BookDto> updateBookQuantity(@PathVariable Long id, @RequestBody StockUpdate stockUpdate){
        BookDto updatedBookStock = bookService.updateBookQuantity(id, stockUpdate);
        return ResponseEntity.status(HttpStatus.OK).body(updatedBookStock);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("200", "Book deleted successfully!"));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam(required = false) String author,
                                                  @RequestParam(required = false) String title,
                                                  @RequestParam(required = false) String genre){
        List<Book> searchResults = bookService.searchBooks(author, title, genre);
        return ResponseEntity.status(HttpStatus.OK).body(searchResults);
    }

}
