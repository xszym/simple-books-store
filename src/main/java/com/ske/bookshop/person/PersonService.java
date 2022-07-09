package com.ske.bookshop.person;


import com.ske.bookshop.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public List<Person> all() {
        return personRepository.findAll();
    }

    public Person getByEmail(String email) {
        return personRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Person", email));

    }
    public void removeOne(String email){
        personRepository.removePersonByEmail(email);
    }
    public Person addOne(Person person) {
        return personRepository.findByEmail(person.getEmail())
                .orElseGet(() ->
                {
                    return personRepository.insert(person);
                });
    }

}
