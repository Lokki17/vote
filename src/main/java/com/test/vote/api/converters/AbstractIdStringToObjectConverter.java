package com.test.vote.api.converters;


import com.test.vote.services.exception.EntityNotFoundException;
import javaslang.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@Slf4j
public abstract class AbstractIdStringToObjectConverter<T> implements Converter<String, T> {

    public T convert(String id, Function<Long, Option<T>> func) {
        return func.apply(Long.valueOf(id))
                .getOrElseThrow(() -> {
                    log.error("Entity with id " + id + "not found");
                    return new EntityNotFoundException("Entity with id " + id + "not found");
                });
    }
}
