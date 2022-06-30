package com.ailtonpratajr.libraryapi.model.entity.repository;

import com.ailtonpratajr.libraryapi.model.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
