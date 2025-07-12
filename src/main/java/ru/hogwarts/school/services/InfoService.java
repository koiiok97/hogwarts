package ru.hogwarts.school.services;

import org.springframework.stereotype.Service;

import java.util.stream.LongStream;

@Service
public class InfoService {

    public Long getNumber(){
        return LongStream.rangeClosed(1, 1_000_000)
                .parallel()
                .sum();
    }
}
