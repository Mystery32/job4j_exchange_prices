package ru.myst.spring.ExchangePrices.parser;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

public class ExchangePriceParser {

    private static final String SOURCE_LINK = "https://query1.finance.yahoo.com/v8/finance/chart/";

    public String parsePrice(String ticker) {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(SOURCE_LINK + ticker, String.class);
        JSONObject object = new JSONObject(response).getJSONObject("chart");
        JSONArray array = object.getJSONArray("result");
        return array.getJSONObject(0).getJSONObject("meta").get("regularMarketPrice").toString();
    }
}
