package ru.myst.spring.ExchangePrices.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myst.spring.ExchangePrices.entities.Company;
import ru.myst.spring.ExchangePrices.entities.Price;
import ru.myst.spring.ExchangePrices.parser.CompanyParser;
import ru.myst.spring.ExchangePrices.parser.ExchangePriceParser;
import ru.myst.spring.ExchangePrices.repositories.CompanyRepository;
import ru.myst.spring.ExchangePrices.repositories.PriceRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class ExchangeServiceImp implements ExchangeService {

    private final CompanyRepository companyRepository;
    private final PriceRepository priceRepository;

    @Autowired
    public ExchangeServiceImp(CompanyRepository companyRepository, PriceRepository priceRepository) {
        this.companyRepository = companyRepository;
        this.priceRepository = priceRepository;
    }

    @Override
    @Transactional
    public void addCompanies() {
        Map<String, Map<String, String>> companies = new CompanyParser().parseCompanies();
        for (Map<String, String> data : companies.values()) {
            Company company = new Company();
            company.setTicker(data.get("ticker"));
            company.setName(data.get("name"));
            company.setSector(data.get("sector"));
            company.setIndustry(data.get("industry"));
            company.setLastPrice(Double.valueOf(data.get("lastPrice")));
            companyRepository.save(company);
        }
    }

    @Override
    @Transactional
    public void addPrices() {
        List<Company> companies = companyRepository.findAll();
        for (Company company : companies) {
            Price price = new Price();
            price.setValue(company.getLastPrice());
            price.setCompany(company);
            price.setDateTime(LocalDateTime.now());
            priceRepository.save(price);
        }
    }

    @Override
    public List<Company> getCompanies(Pageable pageable) {
        return companyRepository.findAll(pageable).getContent();
    }

    @Override
    public List<Price> getPrices() {
        return priceRepository.findAll();
    }

    @Override
    public List<Price> getPricesByTicker(String ticker) {
        return priceRepository.findByCompany(companyRepository.findByTicker(ticker));
    }

    @Override
    @Transactional
    public void updateCompanies() {
        ExchangePriceParser priceParser = new ExchangePriceParser();
        List<Company> companies = companyRepository.findAll();
        for (Company company : companies) {
            company.setLastPrice(Double.valueOf(priceParser.parsePrice(company.getTicker())));
        }
        companyRepository.saveAll(companies);
    }
}
