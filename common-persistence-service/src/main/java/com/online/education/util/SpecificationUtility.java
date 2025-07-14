package com.online.education.util;

import org.springframework.data.jpa.domain.Specification;

public class SpecificationUtility {

    public static Specification equalsValue(String columnName, Object value) {
        return (transaction, query, builder) -> builder.equal(transaction.get(columnName), value);
    }

    public static Specification equalsValue(String columnName, String innerColumName, Object value) {
        return (transaction, query, builder) -> builder.equal(transaction.get(columnName).get(innerColumName), value);
    }

    public static Specification containsValue(String columnName, String string) {
        return (transaction, query, builder) -> builder.like(builder.lower(transaction.get(columnName)), "%"+string.toLowerCase()+"%");
    }

    public static Specification containsValue(String columnName, String innerColumName, String string) {
        return (transaction, query, builder) -> builder.like(builder.lower(transaction.get(columnName).get(innerColumName)), "%"+string.toLowerCase()+"%");
    }
}
