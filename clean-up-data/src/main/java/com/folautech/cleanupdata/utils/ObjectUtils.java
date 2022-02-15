package com.folautech.cleanupdata.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ObjectUtils {

    private static Logger log = LoggerFactory.getLogger(ObjectUtils.class);

    public static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        // Serialization
        // Serialization
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, true);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        objectMapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, true);
        objectMapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, true);
        // Deserialization
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, false);
        objectMapper.setSerializationInclusion(Include.NON_NULL);

        // Date and Time Format
        objectMapper.setDateFormat(getDateFormat());
        // format LocalDate and LocalDateTime
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setTimeZone(TimeZone.getTimeZone("UTC"));

        objectMapper.setSerializationInclusion(Include.NON_NULL);
        return objectMapper;
    }

    public static DateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
    }

    public static ObjectNode getObjectNode() {
        return getObjectMapper().createObjectNode();
    }

    public static ArrayNode getArrayNode() {
        return getObjectMapper().createArrayNode();
    }

    public static <T> T map(Object object, Class<T> clazz) {
        try {
            return getObjectMapper().convertValue(object, clazz);
        } catch (Exception e) {
            log.warn("ObjectUtil - printJson - JsonProcessingException - Msg: " + e.getLocalizedMessage());
            return null;
        }
    }

    public static String toJson(Object object) {
        try {
            return ObjectUtils.getObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.warn("JsonProcessingException, msg: " + e.getLocalizedMessage());
            return "{}";
        }
    }

    public static <T> T fromJsonString(String json, Class<T> clazz) {
        try {
            return ObjectUtils.getObjectMapper().readValue(json, clazz);
        } catch (Exception e) {
            log.warn("JsonProcessingException, msg: " + e.getLocalizedMessage());
            return null;
        }
    }

    /**
     * Serialize the given object to a byte array.
     * 
     * @param object
     *            the object to serialize
     * @return an array of bytes representing the object in a portable fashion
     */
    @Nullable
    public static byte[] serialize(@Nullable Object object) {
        if (object == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            oos.flush();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to serialize object of type: " + object.getClass(), ex);
        }
        return baos.toByteArray();
    }

    /**
     * Deserialize the byte array into an object.
     * 
     * @param bytes
     *            a serialized object
     * @return the result of deserializing the bytes
     */
    @Nullable
    public static Object deserialize(@Nullable byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try {
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return ois.readObject();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to deserialize object", ex);
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("Failed to deserialize object type", ex);
        }
    }
}
