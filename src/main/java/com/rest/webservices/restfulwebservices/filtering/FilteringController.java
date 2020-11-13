package com.rest.webservices.restfulwebservices.filtering;

import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.rest.webservices.restfulwebservices.filtering.helper.FilteringHelper;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {

    // field1, field2
    @GetMapping("/filtering")
    public MappingJacksonValue retrieveSomeBean() {
        SomeBean someBean = new SomeBean("value1", "value2", "value3", "value4");

        MappingJacksonValue mapping = FilteringHelper.getFilter(
                someBean, "SomeBeanFilter", "field1", "field2");

        return mapping;
    }

    // field2, field4
    @GetMapping("/filtering-list")
    public MappingJacksonValue retrieveListOfSomeBeans() {
        List<SomeBean> someBeans = Arrays.asList(
                new SomeBean("value1.1", "value1.2", "value1.3", "value1.4"),
                new SomeBean("value2.1", "value2.2", "value2.3", "value2.4"),
                new SomeBean("value3.1", "value3.2", "value3.3", "value3.4"));

        MappingJacksonValue mapping = FilteringHelper.getFilter(
                someBeans, "SomeBeanFilter", "field2", "field4");

        return mapping;
    }
}
