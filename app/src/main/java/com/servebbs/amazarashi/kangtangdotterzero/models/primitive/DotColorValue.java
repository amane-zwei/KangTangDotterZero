package com.servebbs.amazarashi.kangtangdotterzero.models.primitive;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@JsonSerialize(using = DotColorValue.DotColorValueSerializer.class)
@JsonDeserialize(using = DotColorValue.DotColorValueDeserializer.class)
public class DotColorValue {
    @Getter
    private final int value;

    public static DotColorValue empty() {
        return new DotColorValue(0);
    }

    public DotColorValue copy() {
        return new DotColorValue(value);
    }

    public boolean equals(DotColorValue dotColorValue) {
        return this.value == dotColorValue.value;
    }

    public boolean equals(int value) {
        return this.value == value;
    }

    @Override
    public String toString() {
        return String.format("#%08x", value);
    }

    public static DotColorValue fromString(String src) {
        return new DotColorValue((int) Long.parseLong(src.replace("#", ""), 16));
    }

    public static class DotColorValueSerializer extends StdSerializer<DotColorValue> {
        public DotColorValueSerializer() {
            this(null);
        }

        public DotColorValueSerializer(Class<DotColorValue> dotColor) {
            super(dotColor);
        }

        @Override
        public void serialize(
                DotColorValue value,
                JsonGenerator jsonGenerator,
                SerializerProvider provider)
                throws IOException {

            jsonGenerator.writeString(value.toString());
        }
    }

    public static class DotColorValueDeserializer extends StdDeserializer<DotColorValue> {
        public DotColorValueDeserializer() {
            this(null);
        }

        public DotColorValueDeserializer(Class<DotColorValue> dotColor) {
            super(dotColor);
        }

        @Override
        public DotColorValue deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {

            return fromString(jsonParser.getText());
        }
    }
}
