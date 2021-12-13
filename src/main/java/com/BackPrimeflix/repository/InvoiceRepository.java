package com.BackPrimeflix.repository;

import com.BackPrimeflix.model.ActorEntity;
import com.BackPrimeflix.model.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {
}
