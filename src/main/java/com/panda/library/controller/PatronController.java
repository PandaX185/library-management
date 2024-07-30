package com.panda.library.controller;

import com.panda.library.exception.PatronNotFoundException;
import com.panda.library.model.Patron;
import com.panda.library.service.PatronService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {
    private final PatronService patronService;

    @Autowired
    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }

    @GetMapping
    public List<Patron> getPatrons() {
        return patronService.getAllPatrons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patron> getPatron(@PathVariable Long id) throws PatronNotFoundException {
        Patron patron = patronService.getPatronById(id);
        if (patron == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patron);
    }

    @PostMapping
    public ResponseEntity<Patron> addPatron(@RequestBody @Valid Patron patron) {
        Patron newPatron = patronService.createPatron(patron);
        return ResponseEntity.ok(newPatron);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patron> updatePatron(@PathVariable Long id, @RequestBody Patron patron) throws PatronNotFoundException {
        Patron updatedPatron = patronService.updatePatron(id,patron);
        if (updatedPatron == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedPatron);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable Long id) throws PatronNotFoundException {
        patronService.deletePatron(id);
        return ResponseEntity.noContent().build();
    }
}
