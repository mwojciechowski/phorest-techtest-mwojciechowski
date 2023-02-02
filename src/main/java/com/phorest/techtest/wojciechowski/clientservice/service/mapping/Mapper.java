package com.phorest.techtest.wojciechowski.clientservice.service.mapping;

import com.phorest.techtest.wojciechowski.clientservice.service.exception.MappingError;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class Mapper<T, U> {
    private final Supplier<U> initializer;

    private final Collection<GetterSetterMapping<T, U>> getterSetterMapping;

    public U map(T input) throws MappingError {
        U result = initializer.get();
        try {
            getterSetterMapping.forEach(mapping -> mapping.getSetter().accept(result, mapping.getGetter().apply(input)));
            return result;
        } catch (ClassCastException exc) {
            throw new MappingError("Cannot assign given value", exc);
        }
    }
}
