package com.ailtonpratajr.libraryapi.service;


import com.ailtonpratajr.libraryapi.model.entity.Book;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.Optional;

public interface BookService {

    Book save(Book any);

    Optional<Book> getId(Long id);

    void delete(Book book);

    Book update(Book book);

    Page<Book> find(Book filter, Pageable pageRequest);
}
