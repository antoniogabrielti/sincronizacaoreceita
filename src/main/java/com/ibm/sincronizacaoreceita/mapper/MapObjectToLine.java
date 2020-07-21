package com.ibm.sincronizacaoreceita.mapper;

import org.springframework.core.convert.converter.Converter;

public interface MapObjectToLine<T> extends  Converter<T,String> {
}
