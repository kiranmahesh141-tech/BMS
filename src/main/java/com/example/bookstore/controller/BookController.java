package com.example.bookstore.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore.model.Book;
import com.example.bookstore.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<Book> getAllBooks(
            @RequestParam(name = "page") Optional<Integer> page,
            @RequestParam(name = "size") Optional<Integer> size) {

        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(10));
        return bookService.getAllBooks(pageable);
    }

  
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Book getBook(@PathVariable("id") Long id) {
        return bookService.getBook(id);
    }

  
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Book createBook(@RequestBody @Valid Book book) {
        return bookService.createBook(book);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Book updateBook(@PathVariable("id") Long id,
                           @RequestBody @Valid Book book) {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
    }
}
