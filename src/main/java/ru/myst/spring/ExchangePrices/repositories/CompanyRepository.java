package ru.myst.spring.ExchangePrices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myst.spring.ExchangePrices.entities.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company findByTicker(String ticker);
}
