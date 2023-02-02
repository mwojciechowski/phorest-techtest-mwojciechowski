package com.phorest.techtest.wojciechowski.clientservice.service.parsing;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class EntityCSVParser {

    private CSVFormat format;

    public EntityCSVParser(String delimiter) {
        format = CSVFormat.Builder.create()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setDelimiter(delimiter)
                .build();
    }

    public List<CSVRecord> parse(Reader reader) throws IOException {
        CSVParser parser = CSVParser.parse(reader, format);
        return parser.getRecords();
    }
}
