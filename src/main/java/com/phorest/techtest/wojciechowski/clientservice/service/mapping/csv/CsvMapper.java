package com.phorest.techtest.wojciechowski.clientservice.service.mapping.csv;

import com.phorest.techtest.wojciechowski.clientservice.service.mapping.GetterSetterMapping;
import com.phorest.techtest.wojciechowski.clientservice.service.mapping.Mapper;
import org.apache.commons.csv.CSVRecord;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CsvMapper<T> extends Mapper<CSVRecord, T> {

    private static <U> List<GetterSetterMapping<CSVRecord, U>> getMappings(Map<String, BiConsumer<U, Object>> fieldSetterMapping) {
        return fieldSetterMapping.entrySet().stream().map(entry -> new GetterSetterMapping<CSVRecord, U>(
                record -> record.get(entry.getKey()), entry.getValue()
        )).collect(Collectors.toList());
    }

    public CsvMapper(Supplier<T> initializer, Map<String, BiConsumer<T, Object>> fieldSetterMapping) {
        super(initializer, getMappings(fieldSetterMapping));
    }
}
