package ru.myst.spring.ExchangePrices.endpoint;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.myst.spring.ExchangePrices.dto.CompanyDTO;
import ru.myst.spring.ExchangePrices.dto.PriceDTO;
import ru.myst.spring.ExchangePrices.entities.Company;
import ru.myst.spring.ExchangePrices.entities.Price;
import ru.myst.spring.ExchangePrices.services.ExchangeServiceImp;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final ExchangeServiceImp exchangeServiceImp;
    private final ModelMapper modelMapper;

    @Autowired
    public CompanyController(ExchangeServiceImp exchangeServiceImp, ModelMapper modelMapper) {
        this.exchangeServiceImp = exchangeServiceImp;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public ResponseEntity<List<CompanyDTO>> getCompanies(Pageable pageable) {
        exchangeServiceImp.updateCompanies();
        List<CompanyDTO> companies = exchangeServiceImp.getCompanies(pageable)
                .stream()
                .map(this::convertToCompanyDTO)
                .collect(Collectors.toList());
        if (companies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @GetMapping("/{ticker}/prices")
    public ResponseEntity<List<PriceDTO>> companyPrices(@PathVariable("ticker") String ticker) {
        List<PriceDTO> prices = exchangeServiceImp.getPricesByTicker(ticker)
                .stream()
                .map(this::convertToPriceDTO)
                .collect(Collectors.toList());
        if (prices.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(prices, HttpStatus.OK);
    }

    @GetMapping("/prices")
    public ResponseEntity<List<PriceDTO>> getPrices() {
        List<PriceDTO> prices = exchangeServiceImp.getPrices()
                .stream()
                .map(this::convertToPriceDTO)
                .collect(Collectors.toList());
        if (prices.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(prices, HttpStatus.OK);
    }

    private CompanyDTO convertToCompanyDTO(Company company) {
        return modelMapper.map(company, CompanyDTO.class);
    }

    private PriceDTO convertToPriceDTO(Price price) {
        return modelMapper.map(price, PriceDTO.class);
    }
}
