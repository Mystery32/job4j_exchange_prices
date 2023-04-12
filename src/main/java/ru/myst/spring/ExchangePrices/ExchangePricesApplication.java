package ru.myst.spring.ExchangePrices;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.myst.spring.ExchangePrices.services.ExchangeServiceImp;

@SpringBootApplication
@EnableScheduling
public class ExchangePricesApplication {

    private final ExchangeServiceImp exchangeServiceImp;
    private static final Logger LOG = LoggerFactory.getLogger(ExchangePricesApplication.class);

    public ExchangePricesApplication(ExchangeServiceImp exchangeServiceImp) {
        this.exchangeServiceImp = exchangeServiceImp;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            exchangeServiceImp.addCompanies();
            exchangeServiceImp.addPrices();
            LOG.info("Repositories is loaded");
        };
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void addAndUpdatePrices() throws JsonProcessingException {
        exchangeServiceImp.addPrices();
        exchangeServiceImp.updateCompanies();
        LOG.info("Data updated");
    }

    public static void main(String[] args) {
        SpringApplication.run(ExchangePricesApplication.class, args);
    }
}
