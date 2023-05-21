package com.example.app;

import com.example.dao.RazredRepository;
import com.example.entity.Razred;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
public class RazredRepositoryTests {

    @Autowired
    private RazredRepository razredRepository;

    @Test
    public void imaPoId() {

        //Given
        final Razred razred = new Razred();
        razred.setId(1);
        razred.setOdjeljenje("A");
        razred.setGodina(1);
        razredRepository.save(razred);

        //When
        final Optional<Razred> razredOptional = razredRepository.findById(1);

        //Then
        assertTrue(razredOptional.isPresent());
        final Razred razred1 = razredOptional.get();
        assertEquals(razred.getId(), razred1.getId());
        assertEquals(razred.getGodina(), razred1.getGodina());
        assertEquals(razred.getOdjeljenje(), razred1.getOdjeljenje());
    }

    @Test
    public void nemaPoId() {

        //Given

        //When
        final Optional<Razred> razredOptional = razredRepository.findById(1);

        //Then
        assertTrue(razredOptional.isEmpty());
    }

}
