package com.ritwik.web.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ritwik.web.model.Person;

public interface ServiceRepo5 extends JpaRepository<Person, Long> {

}
