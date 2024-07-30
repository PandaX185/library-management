package com.panda.library.service;

import com.panda.library.exception.BookNotFoundException;
import com.panda.library.model.Book;
import com.panda.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllBooks() {
        Book book1 = new Book(1L, "Book 1", "Author 1", 2023, "1234567890");
        Book book2 = new Book(2L, "Book 2", "Author 2", 2022, "0987654321");

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> books = bookService.getAllBooks();

        assertEquals(2, books.size());
    }

    @Test
    public void testGetBookById() throws BookNotFoundException {
        Book book = new Book(1L, "Book 1", "Author 1", 2023, "1234567890");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> foundBook = Optional.ofNullable(bookService.getBookById(1L));

        assertTrue(foundBook.isPresent());
        assertEquals("Book 1", foundBook.get().getTitle());
    }

    @Test
    public void testAddBook() {
        Book book = new Book(1L, "Book 1", "Author 1", 2023, "1234567890");

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book addedBook = bookService.createBook(book);

        assertEquals("Book 1", addedBook.getTitle());
    }

    @Test
    public void testUpdateBook() throws BookNotFoundException {
        Book book = new Book(1L, "Updated book", "Author 1", 2023, "1234567890");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book updatedBook = bookService.updateBook(1L, book);

        assertEquals("Updated book", updatedBook.getTitle());
    }

    @Test
    public void testDeleteBook() throws BookNotFoundException {
        Book book = new Book(1L, "Book 1", "Author 1", 2023, "1234567890");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).delete(book);

        bookService.deleteBook(1L);
    }

    @Test
    public void testDeleteBookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.deleteBook(1L);
        });

        assertEquals("Book not found", exception.getMessage());
    }
}