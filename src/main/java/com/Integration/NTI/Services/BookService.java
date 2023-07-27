package com.Integration.NTI.Services;

import com.Integration.NTI.Exception.CustomException;
import com.Integration.NTI.Models.Book;
import com.Integration.NTI.Models.User;
import com.Integration.NTI.Repositries.BookRepo;
import com.Integration.NTI.Requests.BookRequest;
import com.Integration.NTI.Response.BookRespnse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private BookRepo bookRepo;
    private UserService userService;

@Autowired
    public BookService(BookRepo bookRepo, UserService userService) {
        this.bookRepo = bookRepo;
        this.userService = userService;
    }

    /**
     * @param book
     * @return book
     * @throws CustomException
     */
    public Book addNewBook(BookRequest book) throws CustomException {
        User currentUser = userService.returnUserAuth();
        System.out.println("current: "+ currentUser.isAdmin());
        if(currentUser.isAdmin()) {
            Book newBook;
            try {
               newBook = bookRepo.findByTitle(book.getTitle());
               newBook.setQuantity(newBook.getQuantity()+1);
            }catch (NullPointerException ex){
                newBook = new Book(book.getTitle(),book.getAuthor(),book.getPrice(), 1);
            }
            return this.bookRepo.save(newBook);
        }
        else {
            throw new CustomException("USER IS NOT ADMIN", HttpStatus.UNAUTHORIZED);
        }
    }
    public List<BookRespnse> findAll() throws CustomException{
        List<Book> books = bookRepo.findAll();
        List<BookRespnse> responses = new ArrayList<>();
        for(Book book : books){
            BookRespnse bookRespnse = BookRespnse.convertTOResponse(book);
            responses.add(bookRespnse);
        }
        return responses;
    }
    public BookRespnse getById(Long id) throws CustomException {
        Optional<Book> book = bookRepo.findById(id);
        if(book.isPresent()) {
            return BookRespnse.convertTOResponse(book.get());
        }
        else{
            throw new CustomException("THIS BOOK IS NO LONGER EXIST...", HttpStatus.NOT_FOUND);
        }
    }
    public Book getByIdLocal(Long id) throws CustomException {
        Optional<Book> book = bookRepo.findById(id);
        if(book.isPresent()) {
            return book.get();
        }
        else{
            throw new CustomException("THIS BOOK IS NO LONGER EXIST...", HttpStatus.NOT_FOUND);
        }
    }
    public void deleteById(Long id, Integer quantity) throws CustomException {
        User currentUser = userService.returnUserAuth();
        if(currentUser.isAdmin()) {
            Book book;
            try {
                book = getByIdLocal(id);
            } catch (CustomException ex) {
                throw new CustomException("THIS BOOK IS NO LONGER EXIST...", HttpStatus.NOT_FOUND);
            }
            int updatedQuantity = book.getQuantity() - quantity;
            if (updatedQuantity > 0) {
                book.setQuantity(updatedQuantity);
                bookRepo.save(book);
            } else {
                bookRepo.delete(book);
            }
        }
        else{
            throw new CustomException("THE USER IS NOT ADMIN", HttpStatus.UNAUTHORIZED);
        }

    }
}
