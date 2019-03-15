package org.grabber.lg.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "service.yandex.translate")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class YandexProperties {
    String key;
}
