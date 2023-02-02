package com.phorest.techtest.wojciechowski.clientservice.service.mapping;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class UTMapperTest {
    @RequiredArgsConstructor
    @Getter
    private class Class1 {
        private final String prop1;
        private final Integer prop2;
        private final double prop3;
    }

    @NoArgsConstructor
    @Setter
    @Getter
    private class Class2 {
        String abc;
        int def;
        Double ghi;
    }

    @Test
    public void mapperShouldMapCLass1ToClass2() {
        Collection<GetterSetterMapping<Class1, Class2>> mappings = new ArrayList<>();
        mappings.add(new GetterSetterMapping<>(Class1::getProp1, (c2, s) -> c2.setAbc((String) s)));
        mappings.add(new GetterSetterMapping<>(Class1::getProp2, (c2, s) -> c2.setDef((Integer) s)));
        mappings.add(new GetterSetterMapping<>(c1 -> (Double) c1.getProp3(), (c2, s) -> c2.setGhi((Double) s)));

        Mapper<Class1, Class2> mapper = new Mapper<>(Class2::new, mappings);
        Class2 c2 = mapper.map(new Class1("Abc", 123, 4.56));
        Assertions.assertEquals("Abc", c2.getAbc());
        Assertions.assertEquals(123, c2.getDef());
        Assertions.assertEquals(4.56, c2.getGhi());
    }

    @Test
    public void mapperShouldMapNullToNullOr0() {
        Collection<GetterSetterMapping<Class1, Class2>> mappings = new ArrayList<>();
        mappings.add(new GetterSetterMapping<>(Class1::getProp1, (c2, s) -> c2.setAbc((String) s)));
        mappings.add(new GetterSetterMapping<>(Class1::getProp2, (c2, s) -> c2.setDef(Optional.ofNullable((Integer) s).orElse(0))));
        mappings.add(new GetterSetterMapping<>(c1 -> (Double) c1.getProp3(), (c2, s) -> c2.setGhi((Double) s)));

        Mapper<Class1, Class2> mapper = new Mapper<>(Class2::new, mappings);
        Class2 c2 = mapper.map(new Class1(null, null, 4.56));
        Assertions.assertEquals(null, c2.getAbc());
        Assertions.assertEquals(0, c2.getDef());
        Assertions.assertEquals(4.56, c2.getGhi());
    }

    @Test
    public void mapperShouldThrowClassCastException() {
        Collection<GetterSetterMapping<Class1, Class2>> mappings = new ArrayList<>();
        mappings.add(new GetterSetterMapping<>(Class1::getProp1, (c2, s) -> c2.setAbc((String) s)));
        mappings.add(new GetterSetterMapping<>(Class1::getProp1, (c2, s) -> c2.setDef((Integer)s)));
        mappings.add(new GetterSetterMapping<>(c1 -> (Double) c1.getProp3(), (c2, s) -> c2.setGhi((Double) s)));

        Mapper<Class1, Class2> mapper = new Mapper<>(Class2::new, mappings);
        try {
            Class2 c2 = mapper.map(new Class1("Abc", 123, 4.56));
            Assertions.fail();
        } catch (Exception exc) {
            Assertions.assertTrue(exc instanceof  ClassCastException);
        }

    }
}
