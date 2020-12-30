package com.some.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotNull;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-11-11 15:20
 */
@Configuration
public class LocalDateTimeConfig {
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Bean
    public Converter<String, LocalDate> localDateConverter() {
        return new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(@NotNull String source) {
                if (StringUtils.hasText(source)) {
                    return LocalDate.parse(source, dateFormat);
                }
                return null;
            }
        };
    }

    /** String --> LocalDatetime */
    @Bean
    public Converter<String, LocalDateTime> localDateTimeConverter() {
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(@NotNull String source) {
                if (StringUtils.hasText(source)) {
                    return LocalDateTime.parse(source, dateTimeFormat);
                }
                return null;
            }
        };
    }

    /** String --> LocalTime */
    @Bean
    public Converter<String, LocalTime> localTimeConverter() {
        return new Converter<String, LocalTime>() {
            @Override
            public LocalTime convert(@NotNull String source) {
                if (StringUtils.hasText(source)) {
                    return LocalTime.parse(source, DateTimeFormatter.ISO_OFFSET_TIME);
                }
                return null;
            }
        };
    }

}
