package com.example.bookstore.service;


import com.example.bookstore.exception.BookNotFoundException;
import com.example.bookstore.exception.InvalidBookException;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    private BookService bookService;

    private Book sampleBook;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository);
        sampleBook = new Book();
        sampleBook.setId(1L);
        sampleBook.setTitle("Effective Java");
        sampleBook.setAuthor("Joshua Bloch");
        sampleBook.setPublishedDate(LocalDate.of(2018,1,6));
        sampleBook.setGenre("Programming");
        sampleBook.setPrice(new BigDecimal("950.50"));
        sampleBook.setIsbn("9780134685991");
    }

    @Test
    public void testGetAllBooksReturnsList() {
        Pageable pageable = PageRequest.of(0, 10);
        when(bookRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(sampleBook)));

        List<Book> books = bookService.getAllBooks(pageable);
        assertThat(books).isNotEmpty();
        assertThat(books).contains(sampleBook);
    }

    @Test
    public void testGetBook_ValidId_ReturnsBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(sampleBook));

        Book book = bookService.getBook(1L);
        assertThat(book).isEqualTo(sampleBook);
    }

    @Test
    public void testGetBook_InvalidId_ThrowsException() {
        when(bookRepository.findById(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.getBook(10L))
                .isInstanceOf(BookNotFoundException.class);
    }

    @Test
    public void testCreateBook_ValidIsbn_Success() {
        when(bookRepository.save(sampleBook)).thenReturn(sampleBook);

        Book created = bookService.createBook(sampleBook);
        assertThat(created).isEqualTo(sampleBook);
    }

    @Test
    public void testCreateBook_InvalidIsbn_ThrowsException() {
        sampleBook.setIsbn("invalidisbn");
        assertThatThrownBy(() -> bookService.createBook(sampleBook))
                .isInstanceOf(InvalidBookException.class);
    }

    @Test
    public void testUpdateBook_ValidIsbn_Success() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(sampleBook));
        when(bookRepository.save(any(Book.class))).thenReturn(sampleBook);

        Book updatedDetails = new Book();
        updatedDetails.setTitle("Updated Title");
        updatedDetails.setAuthor("Author");
        updatedDetails.setPublishedDate(LocalDate.now());
        updatedDetails.setGenre("Genre");
        updatedDetails.setPrice(BigDecimal.TEN);
        updatedDetails.setIsbn("9780134685991");

        Book updated = bookService.updateBook(1L, updatedDetails);
        assertThat(updated.getTitle()).isEqualTo("Updated Title");
    }

    @Test
    public void testUpdateBook_InvalidIsbn_ThrowsException() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(sampleBook));
        Book updatedDetails = new Book();
        updatedDetails.setIsbn("badisbn");

        assertThatThrownBy(() -> bookService.updateBook(1L, updatedDetails))
                .isInstanceOf(InvalidBookException.class);
    }

    @Test
    public void testDeleteBook_Success() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(sampleBook));
        doNothing().when(bookRepository).delete(sampleBook);

        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).delete(sampleBook);
    }
}

