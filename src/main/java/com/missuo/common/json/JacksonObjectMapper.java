package com.missuo.common.json;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Object mapper: Convert Java objects to json based on jackson, or convert json to Java objects The
 * process of parsing JSON into Java objects is called [Deserializing Java objects from JSON] The
 * process of generating JSON from Java objects is called [Serialize Java Objects to JSON]
 */
public class JacksonObjectMapper extends ObjectMapper {

  public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
  // public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
  public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
  public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

  public JacksonObjectMapper() {
    super();
    // Do not report exception when receiving unknown attributes
    this.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);

    // Compatible handling of attributes that do not exist during deserialization
    this.getDeserializationConfig()
        .withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    SimpleModule simpleModule =
        new SimpleModule()
            .addDeserializer(
                LocalDateTime.class,
                new LocalDateTimeDeserializer(
                    DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
            .addDeserializer(
                LocalDate.class,
                new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)))
            .addDeserializer(
                LocalTime.class,
                new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)))
            .addSerializer(
                LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
            .addSerializer(
                LocalDate.class,
                new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)))
            .addSerializer(
                LocalTime.class,
                new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));

    // Register function modules For example, you can add custom serializers and deserializers
    this.registerModule(simpleModule);
  }
}
