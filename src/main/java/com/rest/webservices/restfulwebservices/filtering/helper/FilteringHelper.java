package com.rest.webservices.restfulwebservices.filtering.helper;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;

public class FilteringHelper {

    public static MappingJacksonValue getFilter(Object bean, String filterName, String... filterArray) {
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(filterArray);

        FilterProvider filters = new SimpleFilterProvider().addFilter(filterName, filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(bean);
        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }
}
