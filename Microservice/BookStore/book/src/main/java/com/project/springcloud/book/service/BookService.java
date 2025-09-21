package com.project.springcloud.book.service;


import com.project.springcloud.book.entity.Book;
import com.project.springcloud.book.exception.BookNotFoundException;
import com.project.springcloud.book.exception.OutOfStockException;
import com.project.springcloud.book.repository.BookRepository;
import com.project.springcloud.book.utils.BookDto;
import com.project.springcloud.book.utils.StockUpdate;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public void createBook(BookDto bookDto) {
        Book newBook = new Book();
        newBook.setTitle(bookDto.getTitle());
        newBook.setPrice(bookDto.getPrice());
        newBook.setAuthor(bookDto.getAuthor());
        newBook.setDescription(bookDto.getDescription());
        newBook.setGenre(bookDto.getGenre());
        newBook.setStockQuantity(bookDto.getStockQuantity());

        bookRepository.save(newBook);
    }

    public Book getBook(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isPresent()){
            return book.get();
        }else{
            throw new BookNotFoundException("Book not found for the ID: "+id);
        }
    }

    @Transactional
    public Book updateBook(Long id, BookDto bookDto) {
        Book book = bookRepository.findById(id).orElseThrow(
                ()-> new BookNotFoundException("Book not found for the ID: "+id)
        );

        book.setTitle(bookDto.getTitle());
        book.setPrice(bookDto.getPrice());
        book.setAuthor(bookDto.getAuthor());
        book.setDescription(bookDto.getDescription());
        book.setGenre(bookDto.getGenre());
        book.setStockQuantity(bookDto.getStockQuantity());

        return bookRepository.save(book);

    }

    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                ()-> new BookNotFoundException("Book not found for the ID: "+id)
        );
        bookRepository.deleteById(id);
    }

    public List<Book> showAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books;
    }

    public List<Book> searchBooks(String author, String title, String genre) {
        if(author == null && title == null && genre == null){
            return bookRepository.findAll();
        }
        return bookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndGenreContainingIgnoreCase(
                title != null ? title : "",
                author != null ? author : "",
                genre != null ? genre : ""
        );
    }

    public BookDto updateBookQuantity(Long id, StockUpdate stockUpdate) {
        Book book = bookRepository.findById(id).orElseThrow(
                ()-> new BookNotFoundException("Book not found for the ID: "+id)
        );
        if(book.getStockQuantity() >= stockUpdate.getStockQuantity()){
            book.setStockQuantity(book.getStockQuantity() - stockUpdate.getStockQuantity());
            bookRepository.save(book);
        }else{
            throw new OutOfStockException("Requested book currently unavailable - out of stock");
        }

        return new BookDto(book);
    }
}
