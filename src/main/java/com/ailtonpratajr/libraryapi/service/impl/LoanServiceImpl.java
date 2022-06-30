package com.ailtonpratajr.libraryapi.service.impl;

import com.ailtonpratajr.libraryapi.model.entity.Loan;
import com.ailtonpratajr.libraryapi.model.entity.repository.LoanRepository;
import com.ailtonpratajr.libraryapi.service.LoanService;

public class LoanServiceImpl implements LoanService {
    private LoanRepository repository;

    public LoanServiceImpl(LoanRepository repository) {
        this.repository = repository;
    }

    @Override
    public Loan save(Loan loan) {
        return repository.save(loan);
    }
}
