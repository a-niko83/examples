package org.grabber.lg.controller;


import org.grabber.lg.service.ya.YandexClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LJController {
    private final YandexClient yandexClient;

    public LJController(YandexClient yandexClient) {
        this.yandexClient = yandexClient;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/translate", produces = "application/json")
    public ResponseEntity translate(@RequestParam("text") String text) {
        return ResponseEntity.ok(yandexClient.translateToEn(text));
    }

}
