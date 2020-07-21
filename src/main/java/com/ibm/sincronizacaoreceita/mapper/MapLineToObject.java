package com.ibm.sincronizacaoreceita.mapper;

import org.springframework.core.convert.converter.Converter;

public interface MapLineToObject<T> extends Converter<String,T> {
}
