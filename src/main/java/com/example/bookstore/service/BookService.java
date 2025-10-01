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

    // Constructor Dependency Injection of repository
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Retrieves all books with optional pagination.
     * @param page Pageable page info
     * @return List of Book entities
     */
    public List<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable).getContent();
    }

    /**
     * Retrieves a single book by its ID.
     * Throws BookNotFoundException if not found.
     * @param id Book ID
     * @return Book entity
     */
    public Book getBook(Long id) {
        return bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));
    }

    /**
     * Creates a new book after validating its ISBN.
     * Throws InvalidBookException if ISBN is invalid.
     * @param book Book entity to create
     * @return Created Book entity
     */
    public Book createBook(Book book) {
        if (!BookValidator.isValidIsbn(book.getIsbn())) {
            throw new InvalidBookException("Invalid ISBN format");
        }
        return bookRepository.save(book);
    }

    /**
     * Updates the book identified by ID with data from bookDetails.
     * Validates ISBN and throws exceptions if any issue occurs.
     * @param id Book ID to update
     * @param bookDetails Updated book data
     * @return Updated Book entity
     */
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

    /**
     * Deletes the book with given ID.
     * @param id Book ID to delete
     */
    public void deleteBook(Long id) {
        Book book = getBook(id);  // Throws if not found
        bookRepository.delete(book);
    }
}
