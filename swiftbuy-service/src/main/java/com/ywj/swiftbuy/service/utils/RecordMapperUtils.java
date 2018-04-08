package com.ywj.swiftbuy.service.utils;

import org.modelmapper.ModelMapper;
public final class RecordMapperUtils {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    private RecordMapperUtils() {
    }

    public static <B> B mapObject(Object from, Class<B> targetClass) {
        if (from == null) {
            return null;
        }
        return MODEL_MAPPER.map(from, targetClass);
    }
}
