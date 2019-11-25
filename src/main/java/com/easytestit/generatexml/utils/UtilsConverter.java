package com.easytestit.generatexml.utils;

import java.text.DecimalFormat;
import java.util.function.Function;

public interface UtilsConverter {

    Function<String, String> removeRedundantSymbols = (s) -> s.replace("[(&^%$#@!:)(0-9)(\\n)]","").trim();
    Function<Object, Double> round = (d) -> {
        DecimalFormat df = new DecimalFormat("####0.00");
        return Double.valueOf(df.format(d));
    };

}
