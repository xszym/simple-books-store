package com.ske.bookshop.book;


import com.ske.bookshop.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> all() {
        return bookRepository.findAll();
    }

    public Book getByTitle(String title) {
        return bookRepository.findByTitle(title)
                .orElseThrow(() -> new NotFoundException("Book", title));
    }

    public ResponseEntity<?> removeOne(String title){
        bookRepository.removeBookByTitle(title);
        return ResponseEntity.ok("");
    }

    public Book addOne(Book book) {
        return bookRepository.findByTitle(book.getTitle())
                .orElseGet(() -> bookRepository.insert(book));
    }
}
