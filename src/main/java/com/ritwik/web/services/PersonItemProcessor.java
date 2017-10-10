package com.ritwik.web.services;

import org.springframework.batch.item.ItemProcessor;

import com.ritwik.web.model.Person;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {
    @Override
    public Person process(final Person person) throws Exception {

        return person;
    }
}