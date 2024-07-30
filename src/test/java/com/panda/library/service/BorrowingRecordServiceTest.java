package com.panda.library.service;

import com.panda.library.exception.BookNotFoundException;
import com.panda.library.exception.BorrowingRecordNotFoundException;
import com.panda.library.exception.PatronNotFoundException;
import com.panda.library.model.Book;
import com.panda.library.model.BorrowingRecord;
import com.panda.library.model.Patron;
import com.panda.library.repository.BookRepository;
import com.panda.library.repository.BorrowingRecordRepository;
import com.panda.library.repository.PatronRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class BorrowingRecordServiceTest {

    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PatronRepository patronRepository;

    @InjectMocks
    private BorrowingRecordService borrowingRecordService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBorrowBookSuccess() throws BookNotFoundException, PatronNotFoundException {
        Book book = new Book(1L, "Book 1", "Author 1", 2023, "1234567890");
        Patron patron = new Patron(1L, "Patron 1", "+201501104144");
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDate.now());

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));
        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);

        BorrowingRecord result = borrowingRecordService.borrowBook(1L, 1L);

        assertNotNull(result);
        assertEquals(book, result.getBook());
        assertEquals(patron, result.getPatron());
        assertNotNull(result.getBorrowDate());
    }

    @Test
    public void testBorrowBookBookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BookNotFoundException.class, () -> {
            borrowingRecordService.borrowBook(1L, 1L);
        });

        assertEquals("Book not found", exception.getMessage());
    }

    @Test
    public void testBorrowBookPatronNotFound() {
        Book book = new Book(1L, "Book 1", "Author 1", 2023, "1234567890");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(patronRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(PatronNotFoundException.class, () -> {
            borrowingRecordService.borrowBook(1L, 1L);
        });

        assertEquals("Patron not found", exception.getMessage());
    }

    @Test
    public void testReturnBookSuccess() throws BorrowingRecordNotFoundException {
        Book book = new Book(1L, "Book 1", "Author 1", 2023, "1234567890");
        Patron patron = new Patron(1L, "Patron 1", "contact@example.com");
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDate.now());

        when(borrowingRecordRepository.findByBookIdAndPatronId(1L, 1L)).thenReturn(Optional.of(borrowingRecord));
        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);

        BorrowingRecord result = borrowingRecordService.returnBook(1L, 1L);

        assertNotNull(result);
        assertNotNull(result.getReturnDate());
    }

    @Test
    public void testReturnBookNotFound() {
        when(borrowingRecordRepository.findByBookIdAndPatronId(1L, 1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BorrowingRecordNotFoundException.class, () -> {
            borrowingRecordService.returnBook(1L, 1L);
        });

        assertEquals("Borrowing record not found", exception.getMessage());
    }
}