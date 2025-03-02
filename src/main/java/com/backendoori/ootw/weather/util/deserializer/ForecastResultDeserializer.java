package com.backendoori.ootw.weather.util.deserializer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import com.backendoori.ootw.weather.domain.ForecastCategory;
import com.backendoori.ootw.weather.dto.forecast.ForecastResult;
import com.backendoori.ootw.weather.dto.forecast.ForecastResultItem;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ForecastResultDeserializer extends JsonDeserializer<ForecastResult> {

    private final ObjectMapper objectMapper;

    @Override
    public ForecastResult deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        JsonNode node = p.getCodec()
            .readTree(p);
        JsonNode itemNode = node.findValue("response")
            .get("body")
            .get("items")
            .get("item");

        List<ForecastResultItem> items =
            Arrays.stream(objectMapper.treeToValue(itemNode, ForecastResultItem[].class))
                .filter(item -> ForecastCategory.anyMatch(item.category()))
                .toList();

        return new ForecastResult(items);
    }

}
