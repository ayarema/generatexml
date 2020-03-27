package com.easytestit.generatexml.utils;

import java.text.DecimalFormat;
import java.util.function.Function;

/**
 * The functionality described in this functional interface helps to process strings and objects,
 * for example, numerical (double), for further use of the processed data.
 */
public interface UtilsConverter {

    Function<String, String> removeRedundantSymbols = (s) -> s.replaceAll("[(&^%$#@!:,0-9\\n)]","").trim();
    Function<Object, Double> round = (d) -> {
        DecimalFormat df = new DecimalFormat("####0.0000");
        return Double.valueOf(df.format(d));
    };

}
