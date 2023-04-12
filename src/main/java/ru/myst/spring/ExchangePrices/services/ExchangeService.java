package ru.myst.spring.ExchangePrices.services;

import org.springframework.data.domain.Pageable;
import ru.myst.spring.ExchangePrices.entities.Company;
import ru.myst.spring.ExchangePrices.entities.Price;

import java.util.List;

public interface ExchangeService {

    void addCompanies();

    void addPrices();

    List<Company> getCompanies(Pageable pageable);

    List<Price> getPrices();

    List<Price> getPricesByTicker(String ticker);

    void updateCompanies();
}
