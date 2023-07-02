package com.Integration.NTI.Controllers;

import com.Integration.NTI.Models.Book;
import com.Integration.NTI.Repositries.UserRepo;
import com.Integration.NTI.Requests.CartRequest;
import com.Integration.NTI.Services.BookService;
import com.paypal.api.payments.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/books")
@RestController
public class BookController {

    private BookService bookService;
    private UserRepo userRepo;


    @Autowired
    public BookController(BookService bookService, UserRepo userRepo) {
        this.bookService = bookService;
        this.userRepo = userRepo;
    }
   @PostMapping("/create")
   @PreAuthorize("hasRole('ADMIN')")
   public ResponseEntity<String> createNewBook(@RequestBody Book book){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       boolean isAdmin = authentication.getAuthorities().stream()
               .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

       // Print the user's role in the console
       if (isAdmin) {
           bookService.addBook(book);
           return new ResponseEntity<>("BOOK IS ADDED SUCCESSFULLY...", HttpStatus.CREATED);

       } else {
           return new ResponseEntity<>("ONLY ADMINS CAN ADD NEW BOOK...", HttpStatus.UNAUTHORIZED);
       }
   }

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
    @GetMapping("/delete/{id}")

    public ResponseEntity<String> deleteById(@RequestBody CartRequest request){
        bookService.deleteById(request.getBookId(), request.getQuantity());
        return new ResponseEntity<>("BOOK IS SUCCESSFULLY DELETED",HttpStatus.NO_CONTENT);
    }
}
