package com.servebbs.amazarashi.kangtangdotterzero.models.primitive;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@JsonSerialize(using = DotColorValue.DotColorValueSerializer.class)
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
}
