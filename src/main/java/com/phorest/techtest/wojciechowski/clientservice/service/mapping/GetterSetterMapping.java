package com.phorest.techtest.wojciechowski.clientservice.service.mapping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.BiConsumer;
import java.util.function.Function;

@RequiredArgsConstructor
@Getter
public class GetterSetterMapping<T, U> {
    private final Function<T, Object> getter;
    private final BiConsumer<U, Object> setter;
}