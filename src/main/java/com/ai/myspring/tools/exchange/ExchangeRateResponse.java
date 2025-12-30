package com.ai.myspring.tools.exchange;

public record ExchangeRateResponse(
        int statusCode,
        ExchangeRateData data
) {}
