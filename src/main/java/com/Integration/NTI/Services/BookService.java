package com.Integration.NTI.Services;

import com.Integration.NTI.Models.Book;
import com.Integration.NTI.Repositries.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookService {
    private BookRepo bookRepo;

    @Autowired
    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public Book addBook(Book book){

        Book newBook = bookRepo.findByTitle(book.getTitle());
        if(newBook!=null){
            newBook.setQuantity(newBook.getQuantity()+1);
            return bookRepo.save(newBook);
        }
        return this.bookRepo.save(book);

    }
    public List<Book> findAll(){
        List<Book> all = bookRepo.findAll();
        return all;
    }
    public Book getById(Long id){
        try {
            return bookRepo.findById(id).get();
        } catch (NoSuchElementException ex){
            throw new NoSuchElementException("THIS BOOK IS NO LONGER EXIST...");
        }
    }
    public void deleteById(Long id, Integer quantity){
        Book book = getById(id);
        int updatedQuantity = book.getQuantity() - quantity;
        if(updatedQuantity>0){
            book.setQuantity(updatedQuantity);
            bookRepo.save(book);
        }
        else{
            bookRepo.delete(book);
        }

    }


}
