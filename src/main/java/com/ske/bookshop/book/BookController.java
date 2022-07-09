package com.ske.bookshop.book;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/books")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping()
    public List<Book> all(){
        return bookService.all();
    }

    @GetMapping("{title}")
    public Book one(@PathVariable String title) {
        return bookService.getByTitle(title);
    }

    @PostMapping("")
    public Book addOne(@RequestBody Book book){
        return bookService.addOne(book);
    }

    @DeleteMapping("{name}")
    public ResponseEntity<?> removeOne(@PathVariable String name){
        return bookService.removeOne(name);
    }
}
