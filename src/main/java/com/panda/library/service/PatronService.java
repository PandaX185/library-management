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

    public Patron updatePatron(Long id, Patron patron) {
        Patron oldPatron = patronRepository.findById(id).orElse(null);
        if(oldPatron == null){
            return null;
        }
        if(patron.getName() != null)
            oldPatron.setName(patron.getName());
        if(patron.getContactInformation() != null)
            oldPatron.setContactInformation(patron.getContactInformation());
        return patronRepository.save(oldPatron);
    }

    public void deletePatron(Long id) {
        patronRepository.deleteById(id);
    }
}
