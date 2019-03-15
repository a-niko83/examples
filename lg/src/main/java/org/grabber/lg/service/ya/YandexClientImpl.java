package org.grabber.lg.service.ya;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.grabber.lg.config.YandexProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Service
public class YandexClientImpl implements YandexClient {

    private static final String URL_TRANSLATE = "https://translate.yandex.net/api/v1.5/tr.json/translate";
    // Максимальная длина текста для перевода
    private static final int MAX_TEXT_LEN = 10000;
    private RestTemplate restTemplate;
    private YandexProperties props;
    private JsonParser parser = new JsonParser();

    @Autowired
    public YandexClientImpl(RestTemplate restTemplate, YandexProperties props){
        this.restTemplate = restTemplate;
        this.props = props;
    }

    @Override
    public String translateToEn(String text) {
        return translate(YandexClient.RU_EN, text);
    }

    @Override
    public String translateToRu(String text) {
        return translate(YandexClient.EN_RU, text);
    }

    private String translate(String lang, String text) {

        StringBuilder resultText = new StringBuilder();
        if (text.length() > MAX_TEXT_LEN) { // Если длина текста больше максимально допустимой, то разбиваем на подзапросы по разделителю
            int separatorIndex =  text.indexOf(".");
            if (separatorIndex > 0 && separatorIndex < MAX_TEXT_LEN) {
                resultText.append(translate(lang, text.substring(0, separatorIndex+1)));
                resultText.append(translate(lang, text.substring(separatorIndex+1)));
            } else {
                throw new RuntimeException("Длина исходного текста превышает " + MAX_TEXT_LEN + " символов и не содержит разделителей");
            }
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_TRANSLATE)
                    .queryParam("key", props.getKey())
                    .queryParam("lang", lang)
                    .queryParam("text", text);
            System.out.println(builder.build().toUriString());
            ResponseEntity<String> responseEntity = restTemplate.exchange(builder.build().toUriString(), HttpMethod.POST, entity, String.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                JsonObject dataObj = parser.parse(responseEntity.getBody()).getAsJsonObject();
                resultText.append(dataObj.get("text").getAsString());
            } else {
                String msgError = responseEntity.getBody();
                if (responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                    msgError = "Неправильный API-ключ";
                }
                if (responseEntity.getStatusCode() == HttpStatus.PAYMENT_REQUIRED) {
                    msgError = "API-ключ заблокирован";
                }
                if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND) {
                    msgError = "Превышено суточное ограничение на объем переведенного текста";
                }
                if (responseEntity.getStatusCode() == HttpStatus.PAYLOAD_TOO_LARGE) {
                    msgError = "Превышен максимально допустимый размер текста";
                }
                if (responseEntity.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) {
                    msgError = "Текст не может быть переведен";
                }
                if (responseEntity.getStatusCode() == HttpStatus.NOT_IMPLEMENTED) {
                    msgError = "Заданное направление перевода не поддерживается";
                }
                throw new RuntimeException(msgError);
            }
        }
        return resultText.toString();
    }

}
