package com.ailtonpratajr.libraryapi.model.repository;

import com.ailtonpratajr.libraryapi.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
