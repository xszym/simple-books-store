package com.ske.bookshop.person;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/persons")
@AllArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping()
    public List<Person> all(){
        return personService.all();
    }

    @GetMapping("{email}")
    public Person one(@PathVariable String email){
        return personService.getByEmail(email);
    }

    @PostMapping("")
    public Person addOne(@RequestBody Person person){
        return personService.addOne(person);
    }

    @DeleteMapping("{email}")
    public void removeOne(@PathVariable String email){
        personService.removeOne(email);
    }

}
