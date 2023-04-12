package ru.myst.spring.ExchangePrices.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CompanyParser {

    private static final String SOURCE_LINK = "https://finviz.com/screener.ashx?v=111&f=idx_sp500&o=-marketcap";
    private static final Logger LOG = LoggerFactory.getLogger(CompanyParser.class);

    public Map<String, Map<String, String>> parseCompanies() {
        Map<String, Map<String, String>> companies = new HashMap<>();
        try {
            Document document = Jsoup.connect(SOURCE_LINK).get();
            Elements rows = document.select(".table-light tr");
            for (int i = 1; i < rows.size(); i++) {
                Element row = rows.get(i);
                String ticker = row.select("td").get(1).text();
                String name = row.select("td").get(2).text();
                String sector = row.select("td").get(3).text();
                String industry = row.select("td").get(4).text();
                String lastPrice = new ExchangePriceParser().parsePrice(ticker);
                companies.put(ticker, Map.of(
                        "ticker", ticker,
                        "name", name,
                        "sector", sector,
                        "industry", industry,
                        "lastPrice", lastPrice));
            }
        } catch (IOException e) {
            LOG.info("CompanyParser.class IOException in parseCompanies() method. " + e.getMessage());
        }
        return companies;
    }
}
