package com.Integration.NTI.Controllers;

import com.Integration.NTI.Exception.CustomException;

import com.Integration.NTI.Models.Requests.BookRequest;
import com.Integration.NTI.Models.Requests.CartRequest;
import com.Integration.NTI.Models.Response.BookRespnse;
import com.Integration.NTI.Services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/books")
@RestController
public class BookController {

    private final BookService bookService;
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
   @PostMapping("/create")
   public ResponseEntity<String> createNewBook(@RequestBody BookRequest book){
        try {
            bookService.addNewBook(book);
            return new ResponseEntity<>("BOOK ADDED SUCCESSFULLY... ", HttpStatus.CREATED);
        }catch (CustomException ex){
            return new ResponseEntity<>(ex.getDescription(), ex.getStatus());
        }
   }
    @GetMapping({"/all", "/"})
    public ResponseEntity<List<BookRespnse>> listBooks(){
        try {
            List<BookRespnse> list =  bookService.findAll();
            return new ResponseEntity<>(list, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("get/{id}")
    public ResponseEntity<BookRespnse> getById(@PathVariable Long id){
        try {
            BookRespnse book = bookService.getById(id);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (CustomException ex){
            return new ResponseEntity<>(ex.getStatus());
        }
    }
    @GetMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteById(@RequestBody CartRequest request){
        try {
            bookService.deleteById(request.getBookId(), request.getQuantity());
            return new ResponseEntity<>("BOOK IS SUCCESSFULLY DELETED",HttpStatus.NO_CONTENT);
        }catch (CustomException ex){
            return new ResponseEntity<>(ex.getDescription(),ex.getStatus());
        }

    }
}
