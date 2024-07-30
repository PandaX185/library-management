package com.panda.library.service;

import com.panda.library.exception.BookNotFoundException;
import com.panda.library.model.Book;
import com.panda.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) throws BookNotFoundException {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        }
        throw new BookNotFoundException("Book not found");
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book book) throws BookNotFoundException {
        Book oldBook = getBookById(id);
        if(book.getTitle() != null)
            oldBook.setTitle(book.getTitle());
        if(book.getAuthor() != null)
            oldBook.setAuthor(book.getAuthor());
        if(book.getIsbn() != null)
            oldBook.setIsbn(book.getIsbn());
        if(book.getPublicationYear() != 0)
            oldBook.setPublicationYear(book.getPublicationYear());
        return bookRepository.save(oldBook);
    }

    public void deleteBook(Long id) throws BookNotFoundException {
        getBookById(id);
        bookRepository.deleteById(id);
    }
}
