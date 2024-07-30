package com.panda.library.service;

import com.panda.library.exception.PatronNotFoundException;
import com.panda.library.model.Patron;
import com.panda.library.repository.PatronRepository;
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

class PatronServiceTest {

    @Mock
    private PatronRepository patronRepository;

    @InjectMocks
    private PatronService patronService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllPatrons() {
        Patron patron1 = new Patron(1L, "Patron 1", "+201501104144");
        Patron patron2 = new Patron(2L, "Patron 2", "+201501104140");

        when(patronRepository.findAll()).thenReturn(Arrays.asList(patron1, patron2));

        List<Patron> patrons = patronService.getAllPatrons();

        assertEquals(2, patrons.size());
    }

    @Test
    public void testGetPatronById() throws PatronNotFoundException {
        Patron patron = new Patron(1L, "Patron 1", "+201501104144");

        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));

        Optional<Patron> foundPatron = Optional.ofNullable(patronService.getPatronById(1L));

        assertTrue(foundPatron.isPresent());
        assertEquals(patron.getPhoneNumber(), foundPatron.get().getPhoneNumber());
    }

    @Test
    public void testAddPatron() {
        Patron patron = new Patron(1L, "Patron 1", "+201501104144");

        when(patronRepository.save(any(Patron.class))).thenReturn(patron);

        Patron addedPatron = patronService.createPatron(patron);

        assertEquals(patron.getPhoneNumber(), addedPatron.getPhoneNumber());
    }

    @Test
    public void testUpdatePatron() throws PatronNotFoundException {
        Patron patron = new Patron(1L, "Updated patron", "+201501104144");

        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));
        when(patronRepository.save(any(Patron.class))).thenReturn(patron);

        Patron updatedPatron = patronService.updatePatron(1L, patron);

        assertEquals("Updated patron", updatedPatron.getName());
    }

    @Test
    public void testDeletePatron() throws PatronNotFoundException {
        Patron patron = new Patron(1L, "Patron 1", "+201501104144");

        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));
        doNothing().when(patronRepository).delete(patron);

        patronService.deletePatron(1L);
    }

    @Test
    public void testDeletePatronNotFound() {
        when(patronRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(PatronNotFoundException.class, () -> {
            patronService.deletePatron(1L);
        });

        assertEquals("Patron not found", exception.getMessage());
    }
}