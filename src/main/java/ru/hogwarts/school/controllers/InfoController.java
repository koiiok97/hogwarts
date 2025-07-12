package ru.hogwarts.school.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.services.InfoService;

@RestController
@RequestMapping("/info")
public class InfoController {
    private InfoService infoService;

    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @Value("${server.port}")
    private int serverPort;

    @GetMapping
    public int getServerPort(){
        return serverPort;
    }

    @GetMapping("/get-int")
    public Long getNumber(){
        return infoService.getNumber();
    }
}
