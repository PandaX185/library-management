package com.panda.library.service;

import com.panda.library.exception.PatronNotFoundException;
import com.panda.library.model.Patron;
import com.panda.library.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatronService {

    @Autowired
    private PatronRepository patronRepository;

    @Cacheable("patrons")
    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    public Patron getPatronById(Long id) throws PatronNotFoundException {
        Optional<Patron> patron = patronRepository.findById(id);
        if (patron.isPresent()) {
            return patron.get();
        }
        throw new PatronNotFoundException("Patron not found");
    }

    @CacheEvict(value = "patrons", allEntries = true)
    public Patron createPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    @CacheEvict(value = "patrons", allEntries = true)
    public Patron updatePatron(Long id, Patron patron) throws PatronNotFoundException {
        Patron oldPatron = getPatronById(id);
        if(patron.getName() != null)
            oldPatron.setName(patron.getName());
        if(patron.getPhoneNumber() != null)
            oldPatron.setPhoneNumber(patron.getPhoneNumber());
        return patronRepository.save(oldPatron);
    }

    @CacheEvict(value = "patrons", key = "#id")
    public void deletePatron(Long id) throws PatronNotFoundException {
        getPatronById(id);
        patronRepository.deleteById(id);
    }
}
