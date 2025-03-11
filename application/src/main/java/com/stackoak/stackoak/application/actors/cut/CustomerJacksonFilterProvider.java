package com.stackoak.stackoak.application.actors.cut;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

import java.util.*;


@JsonFilter("JacksonJsonFilter")
public class CustomerJacksonFilterProvider extends FilterProvider {

    /**
     * 包含字段 Map
     */
    Map<Class<?>, Set<String>> includeMap = new HashMap<>();

    /**
     * 排除字段 Map
     */
    Map<Class<?>, Set<String>> excludeMap = new HashMap<>();
    public void include(Class<?> type, String... fields) {
        addToMap(includeMap, type, fields);
    }
    public void exclude(Class<?> type, String... fields) {
        addToMap(excludeMap, type, fields);
    }
    private void addToMap(Map<Class<?>, Set<String>> map, Class<?> type, String... fields) {
        Set<String> fieldSet = map.getOrDefault(type, new HashSet<>());
        fieldSet.addAll(Arrays.asList(fields));
        map.put(type, fieldSet);
    }

    @Deprecated
    @Override
    public BeanPropertyFilter findFilter(Object filterId) {
        throw new UnsupportedOperationException("Access to deprecated filters not supported");
    }

    @Override
    public PropertyFilter findPropertyFilter(Object filterId, Object valueToFilter) {
        return new SimpleBeanPropertyFilter() {
            @Override
            public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider prov, PropertyWriter writer)
                    throws Exception {
                if (apply(pojo.getClass(), writer.getName())) {
                    writer.serializeAsField(pojo, jgen, prov);
                } else if (!jgen.canOmitFields()) {
                    writer.serializeAsOmittedField(pojo, jgen, prov);
                }
            }
        };
    }
    public boolean apply(Class<?> type, String name) {
        Set<String> includeFields = includeMap.get(type);
        Set<String> excludeFields = excludeMap.get(type);
        if (includeFields != null && includeFields.contains(name)) {
            return true;
        } else if (excludeFields != null && !excludeFields.contains(name)) {
            return true;
        } else {
            return includeFields == null && excludeFields == null;
        }
    }
}
