package com.panda.library.service;

import com.panda.library.model.Patron;
import com.panda.library.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatronService {

    @Autowired
    private PatronRepository patronRepository;

    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    public Patron getPatronById(Long id) {
        return patronRepository.findById(id).orElse(null);
    }

    public Patron createPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    public Patron updatePatron(Patron patron) {
        return patronRepository.save(patron);
    }

    public void deletePatron(Patron patron) {
        patronRepository.delete(patron);
    }
}
