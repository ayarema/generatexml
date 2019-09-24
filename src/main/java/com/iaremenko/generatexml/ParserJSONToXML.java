package com.iaremenko.generatexml;

import com.iaremenko.generatexml.configuration.Configuration;

import java.util.List;

class ParserJSONToXML {

    private Configuration configuration;

    ParserJSONToXML(Configuration configuration) {
        this.configuration = configuration;
    }

    void parseJSON(List<String> jsonFiles) {
        if (jsonFiles.isEmpty()) {
            throw new ValidationException("None report file was added!");
        }


    }
}
