package com.example.app;

import com.example.dao.IspitRepository;
import com.example.entity.IspitId;
import com.example.service.IspitService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Optional;

@SpringBootTest
class IspitServiceTests {

    @Autowired
    IspitService ispitService;

    @SpyBean
    IspitRepository ispitRepository;


    @Test
    void contextLoads() {
    }

    @Test
    void getIspit__nepostojeciIspit() {

        //Given
        Mockito.when(ispitRepository.findById(Mockito.any(IspitId.class))).thenReturn(Optional.empty());

        //When
        ispitService.getIspit(1, 1);


        //Then

    }

}
