package com.panda.library.service;

import com.panda.library.model.Book;
import com.panda.library.model.BorrowingRecord;
import com.panda.library.model.Patron;
import com.panda.library.repository.BookRepository;
import com.panda.library.repository.BorrowingRecordRepository;
import com.panda.library.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowingRecordService {

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;
    private BookRepository bookRepository;
    private PatronRepository patronRepository;

    public BorrowingRecord borrowBook(Long bookId, Long patronId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        Patron patron = patronRepository.findById(patronId).orElse(null);
        if (book == null || patron == null) {
            return null;
        }
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDate.now());
        return borrowingRecordRepository.save(borrowingRecord);
    }

    public BorrowingRecord returnBook(Long bookId, Long patronId) {
       BorrowingRecord record = borrowingRecordRepository.findByBookIdAndPatronId(bookId,patronId).orElse(null);
       if (record == null) {
           return null;
       }
       record.setReturnDate(LocalDate.now());
       return borrowingRecordRepository.save(record);
    }
}
