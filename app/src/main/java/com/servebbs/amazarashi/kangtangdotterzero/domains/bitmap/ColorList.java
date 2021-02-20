package com.servebbs.amazarashi.kangtangdotterzero.domains.bitmap;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotColorValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonSerialize(using = ColorList.ColorListSerializer.class)
@JsonDeserialize(using = ColorList.ColorListDeserializer.class)
public class ColorList {
    public static final int colorMax = 1024;

    private List<DotColorValue> array;

    protected ColorList() {
        array = new ArrayList<>();
    }

    public ColorList(int[] colors) {
        array = new ArrayList<>(colors.length);
        addColors(colors);
    }

    public ColorList(ColorList src) {
        array = new ArrayList<>(src.array.size());
        addColors(src);
    }

    public void addColor() {
        addColor(0xffffffff);
    }

    public void addColor(int value) {
        array.add(new DotColorValue(value));
    }

    public void addColors(int[] colors) {
        for (int idx = 0; idx < colors.length; idx++) {
            addColor(colors[idx]);
        }
    }

    public void addColors(ColorList src) {
        for (int index = 0; index < src.size(); index++) {
            array.add(src.array.get(index).copy());
        }
    }

    public DotColorValue removeColor(int index) {
        if (array.size() < 2) {
            return DotColorValue.empty();
        }
        return this.array.remove(index);
    }

    public int size() {
        return array.size();
    }

    public DotColorValue getColor(int index) {
        return array.get(index);
    }

    public void setColor(int index, DotColorValue color) {
        array.set(index, color);
    }

    public int findIndex(int intValue) {
        int index;
        for (index = 0; index < array.size(); index++) {
            if (array.get(index).equals(intValue)) {
                return index;
            }
        }
        if (canAddColor()) {
            array.add(new DotColorValue(intValue));
            return array.size() - 1;
        }
        return 0;
    }

    public boolean canAddColor() {
        return array.size() < colorMax;
    }

    public boolean equals(ColorList colorList) {
        if (this.size() != colorList.size()) {
            return false;
        }
        for (int index = 0; index < this.size(); index++) {
            if (!this.array.get(index).equals(colorList.array.get(index))) {
                return false;
            }
        }
        return true;
    }

    public String[] toStringArray() {
        String[] result = new String[array.size()];
        for (int index = 0; index < array.size(); index++) {
            result[index] = array.get(index).toString();
        }
        return result;
    }

    protected void copy(ColorList src) {
        this.array = src.array;
    }

    public static ColorList empty() {
        return new ColorList(new ArrayList<>());
    }

    public static class ColorListSerializer extends StdSerializer<ColorList> {
        public ColorListSerializer() {
            this(null);
        }

        public ColorListSerializer(Class<ColorList> colorList) {
            super(colorList);
        }

        @Override
        public void serialize(
                ColorList value,
                JsonGenerator jsonGenerator,
                SerializerProvider provider)
                throws IOException {

            jsonGenerator.writeArray(value.toStringArray(), 0, value.size());
        }
    }

    public static class ColorListDeserializer extends StdDeserializer<ColorList> {
        public ColorListDeserializer() {
            this(null);
        }

        public ColorListDeserializer(Class<DotColorValue> dotColor) {
            super(dotColor);
        }

        @Override
        public ColorList deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {

            ColorList colorList = new ColorList();
            colorList.array = new ArrayList<>();

            String[] values = jsonParser.readValueAs(String[].class);

            for (int index = 0; index < values.length; index++) {
                colorList.array.add(DotColorValue.fromString(values[index]));
            }
            return colorList;
        }
    }
}
