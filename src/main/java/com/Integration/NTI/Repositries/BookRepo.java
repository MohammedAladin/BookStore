package com.Integration.NTI.Repositries;

import com.Integration.NTI.Models.Entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface BookRepo extends JpaRepository<Book, Long> {

    Book findByTitle(String title);
}