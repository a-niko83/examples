package org.grabber.lg.service.ya;

public interface YandexClient {
    // Напрвления перевода
    String RU_EN = "ru-en";
    String EN_RU = "en-ru";

    String translateToEn(String text);
    String translateToRu(String text);

}
