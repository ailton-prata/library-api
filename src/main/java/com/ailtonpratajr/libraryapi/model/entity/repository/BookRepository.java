package com.ailtonpratajr.libraryapi.model.entity.repository;

import com.ailtonpratajr.libraryapi.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByIsbn(String isbn);
}
