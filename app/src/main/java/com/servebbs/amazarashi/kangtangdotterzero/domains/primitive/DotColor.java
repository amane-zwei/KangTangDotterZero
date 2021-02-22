package com.servebbs.amazarashi.kangtangdotterzero.domains.primitive;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.servebbs.amazarashi.kangtangdotterzero.domains.bitmap.IndexedBitmap;
import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Palette;

import java.io.IOException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonSerialize(using = DotColor.DotColorSerializer.class)
@JsonDeserialize(using = DotColor.DotColorDeserializer.class)
public class DotColor {
    private int value;
    private final int index;

    public static DotColor fromColorValue(int value) {
        return new DotColor(value, 0);
    }

    public static DotColor create(int value, int index) {
        return new DotColor(value, IndexedBitmap.toSaveIndex(index));
    }

    public static DotColor empty() {
        return new DotColor(0, 0);
    }

    public int intValue() {
        return value;
    }

    public int plainIndex() {
        return IndexedBitmap.toPlainIndex(index);
    }

    public int saveIndex() {
        return index;
    }

    public DotColor copy() {
        return new DotColor(value, index);
    }

    @JsonIgnore
    public boolean isIndexedColor() {
        return (index & 0xff000000) != 0;
    }

    public DotColor applyPalette(Palette palette) {
        value = palette.getColor(plainIndex()).getValue();
        return this;
    }

    @Override
    @NonNull
    public String toString() {
        if (isIndexedColor()) {
            return Integer.toString(plainIndex());
        } else {
            return String.format("#%08x", value);
        }
    }

    public static DotColor fromString(String src) {
        int value;
        int index;
        if (src.startsWith("#")) {
            value = (int) Long.parseLong(src.replace("#", ""), 16);
            index = 0;
        } else {
            value = 0;
            index = (int) Long.parseLong(src);
        }
        return new DotColor(value, index);
    }

    public static class DotColorSerializer extends StdSerializer<DotColor> {
        public DotColorSerializer() {
            this(null);
        }

        public DotColorSerializer(Class<DotColor> dotColor) { super(dotColor); }

        @Override
        public void serialize(
                DotColor value,
                JsonGenerator jsonGenerator,
                SerializerProvider provider)
                throws IOException {

            jsonGenerator.writeString(value.toString());
        }
    }

    public static class DotColorDeserializer extends StdDeserializer<DotColor> {
        public DotColorDeserializer() {
            this(null);
        }

        public DotColorDeserializer(Class<DotColor> dotColor) {
            super(dotColor);
        }

        @Override
        public DotColor deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {

            return fromString(jsonParser.getText());
        }
    }
}
