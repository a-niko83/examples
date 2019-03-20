package org.grabber.lg.controller;


import org.grabber.lg.service.lj.LJClient;
import org.grabber.lg.service.lj.LJClientImpl;
import org.grabber.lg.service.ya.YandexClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@RestController
public class WebController {
    private final YandexClient yandexClient;
    private final LJClient ljClient;

    public WebController(YandexClient yandexClient, LJClient ljClient) {
        this.yandexClient = yandexClient;
        this.ljClient = ljClient;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/translate", produces = "application/json")
    public ResponseEntity translate(@RequestParam("text") String text) {
        return ResponseEntity.ok(yandexClient.translateToEn(text));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getEvents", produces = "application/json")
    public ResponseEntity getEvents(
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "day", required = false) Integer day) throws Exception {
        if (year == null || month == null || day == null) {
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DATE);
        }
        return ResponseEntity.ok(ljClient.load(year, month, day));
    }
}
