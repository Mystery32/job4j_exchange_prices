package ru.myst.spring.ExchangePrices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myst.spring.ExchangePrices.entities.Company;
import ru.myst.spring.ExchangePrices.entities.Price;

import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    List<Price> findByCompany(Company model);
}
