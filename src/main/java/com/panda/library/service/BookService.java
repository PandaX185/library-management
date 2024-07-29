package com.panda.library.service;

import com.panda.library.model.Book;
import com.panda.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book book) {
        Book oldBook = getBookById(id);
        if (oldBook == null) {
            return null;
        }
        oldBook.setTitle(book.getTitle());
        oldBook.setAuthor(book.getAuthor());
        oldBook.setPublicationYear(book.getPublicationYear());
        oldBook.setIsbn(book.getIsbn());
        return bookRepository.save(oldBook);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
