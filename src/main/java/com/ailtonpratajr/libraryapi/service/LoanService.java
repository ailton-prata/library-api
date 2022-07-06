package com.ailtonpratajr.libraryapi.service;

import com.ailtonpratajr.libraryapi.api.resource.BookController;
import com.ailtonpratajr.libraryapi.model.entity.Loan;

import java.util.Optional;

public interface LoanService {
    Loan save(Loan loan);

    Optional<Loan> getById(Long id);

    Loan update(Loan loan);
}
