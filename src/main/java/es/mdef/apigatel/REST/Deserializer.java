package es.mdef.apigatel.REST;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;

public interface Deserializer<T> {
    T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException;
}