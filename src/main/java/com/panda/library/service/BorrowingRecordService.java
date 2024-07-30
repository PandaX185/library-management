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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BorrowingRecordService {

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PatronRepository patronRepository;

    public BorrowingRecord borrowBook(Long bookId, Long patronId) throws BookNotFoundException, PatronNotFoundException {
        Optional<Book> book = bookRepository.findById(bookId);
        Optional<Patron> patron = patronRepository.findById(patronId);
        if(book.isEmpty()){
            throw new BookNotFoundException("Book not found");
        }
        if(patron.isEmpty()){
            throw new PatronNotFoundException("Patron not found");
        }
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book.get());
        borrowingRecord.setPatron(patron.get());
        borrowingRecord.setBorrowDate(LocalDate.now());
        return borrowingRecordRepository.save(borrowingRecord);
    }

    public BorrowingRecord returnBook(Long bookId, Long patronId) throws BorrowingRecordNotFoundException {
       BorrowingRecord record = borrowingRecordRepository.findByBookIdAndPatronId(bookId,patronId).orElse(null);
       if (record == null) {
           throw new BorrowingRecordNotFoundException("Borrowing record not found");
       }
       record.setReturnDate(LocalDate.now());
       return borrowingRecordRepository.save(record);
    }
}
