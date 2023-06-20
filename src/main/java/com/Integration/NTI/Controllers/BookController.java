package com.Integration.NTI.Controllers;

import com.Integration.NTI.Models.Book;
import com.Integration.NTI.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RequestMapping("/api/books")
@RestController
public class BookController {

    private static BookService bookService;


    @Autowired
    public BookController(BookService bookService) {
        BookController.bookService = bookService;
    }
    @PreAuthorize("hasRole('USER')")
   @PostMapping("/create")
   public ResponseEntity<Book> createNewBook(@RequestBody Book book){
        Book result = bookService.addBook(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
   }
    @PreAuthorize("hasRole('USER')")

    @GetMapping({"/all", "/"})
    public ResponseEntity<List<Book>> listBooks(){
        List<Book> list =  bookService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id){
        Book book = bookService.getById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
    @GetMapping("/delete")
    public ResponseEntity<Book> deleteById(@PathVariable Long id){
        bookService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
