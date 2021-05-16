package com.example.gorski.domain.products.search;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProductPredicatesBuilder {
    private final List<SearchCriteria> paramsAND;
    private final List<SearchCriteria> paramsOR;

    public ProductPredicatesBuilder() {
        paramsAND = new ArrayList<>();
        paramsOR = new ArrayList<>();
    }

    public ProductPredicatesBuilder with(final String key, final String operation, final Object value) {
        for (SearchCriteria searchCriteria : paramsAND) {
            if (key.equals(searchCriteria.getKey())){
                paramsOR.add(new SearchCriteria(key, operation, value));
                paramsOR.add(searchCriteria);
                paramsAND.remove(searchCriteria);
                return this;
            }
        }
        paramsAND.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public BooleanExpression build() {
        if (paramsAND.size() == 0 && paramsOR.size() == 0) {
            return null;
        }

        final List<BooleanExpression> predicates = paramsAND.stream().map(param -> {
            ProductPredicate predicate = new ProductPredicate(param);
            return predicate.getPredicate();
        }).filter(Objects::nonNull).collect(Collectors.toList());

        final List<BooleanExpression> predicatesOR = paramsOR.stream().map(param -> {
            ProductPredicate predicate = new ProductPredicate(param);
            return predicate.getPredicate();
        }).filter(Objects::nonNull).collect(Collectors.toList());

        BooleanExpression result = Expressions.asBoolean(true).isTrue();
        for (BooleanExpression predicate : predicates) {
            result = result.and(predicate);
        }
        if (paramsOR.size() != 0) {
            int counter = 0;
            do {
                result = result.andAnyOf(predicatesOR.get(counter), predicatesOR.get(counter+1));
                counter+=2;
            } while(paramsOR.size() > counter);
        }

        return result;
    }

}
