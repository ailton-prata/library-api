package com.ailtonpratajr.libraryapi.service;

import com.ailtonpratajr.libraryapi.api.dto.LoanFilterDTO;
import com.ailtonpratajr.libraryapi.api.resource.BookController;
import com.ailtonpratajr.libraryapi.model.entity.Book;
import com.ailtonpratajr.libraryapi.model.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LoanService {
    Loan save(Loan loan);

    Optional<Loan> getById(Long id);

    Loan update(Loan loan);

    Page<Loan> find(LoanFilterDTO filterDTO, Pageable pageable);

    Page<Loan> getLoansByBook(Book book, Pageable pageable);
}
