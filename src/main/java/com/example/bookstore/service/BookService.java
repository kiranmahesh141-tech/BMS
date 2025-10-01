package com.example.bookstore.service;

import com.example.bookstore.exception.BookNotFoundException;
import com.example.bookstore.exception.InvalidBookException;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.util.BookValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    
    public List<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable).getContent();
    }

   
    public Book getBook(Long id) {
        return bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));
    }

 
    public Book createBook(Book book) {
        if (!BookValidator.isValidIsbn(book.getIsbn())) {
            throw new InvalidBookException("Invalid ISBN format");
        }
        return bookRepository.save(book);
    }

 
    public Book updateBook(Long id, Book bookDetails) {
        Book existingBook = getBook(id);  // Throws if not found

        if (!BookValidator.isValidIsbn(bookDetails.getIsbn())) {
            throw new InvalidBookException("Invalid ISBN format");
        }

        existingBook.setTitle(bookDetails.getTitle());
        existingBook.setAuthor(bookDetails.getAuthor());
        existingBook.setPublishedDate(bookDetails.getPublishedDate());
        existingBook.setGenre(bookDetails.getGenre());
        existingBook.setPrice(bookDetails.getPrice());
        existingBook.setIsbn(bookDetails.getIsbn());

        return bookRepository.save(existingBook);
    }

   
    public void deleteBook(Long id) {
        Book book = getBook(id);  
        bookRepository.delete(book);
    }
}
