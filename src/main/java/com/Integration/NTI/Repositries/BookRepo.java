package com.Integration.NTI.Repositries;

import com.Integration.NTI.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface BookRepo extends JpaRepository<Book, Long> {

    Book findByTitle(String title);
}