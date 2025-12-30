package com.ai.myspring.tools.exchange;

public record ExchangeRateData(
        String base,
        String target,
        Double mid,
        Integer unit,
        String timestamp
) {}
