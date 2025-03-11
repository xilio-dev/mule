package com.stackoak.stackoak.application.actors.cut;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
public class ResponseJsonFilterSerializer {
    private final CustomerJacksonFilterProvider filterProvider = new CustomerJacksonFilterProvider();
    private final ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .serializationInclusion(JsonInclude.Include.NON_NULL)
            .build();
    public void filter(Class<?> clazz, String[] include, String[] exclude) {
        if (clazz == null) {
            return;
        }
        if (include != null && include.length > 0) {
            filterProvider.include(clazz, include);
        } else if (exclude != null && exclude.length > 0) {
            filterProvider.exclude(clazz, exclude);
        }
        mapper.addMixIn(clazz, filterProvider.getClass());
    }

    public void filter(JsonFieldFilters fieldFilters) {
        for (JsonFieldFilter json : fieldFilters.value()) {
            this.filter(json.type(), json.include(), json.exclude());
        }
    }

    public String toJson(Object object) throws JsonProcessingException {
        mapper.setFilterProvider(filterProvider);
        return mapper.writeValueAsString(object);
    }
}
