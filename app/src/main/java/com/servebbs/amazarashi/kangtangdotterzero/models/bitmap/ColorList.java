package com.servebbs.amazarashi.kangtangdotterzero.models.bitmap;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.servebbs.amazarashi.kangtangdotterzero.models.primitive.DotColor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonSerialize(using = ColorList.ColorListSerializer.class)
public class ColorList {
    public static final int colorMax = 256;

    private final List<DotColor> array;

    public ColorList(int[] colors) {
        array = new ArrayList<>();
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
        array.add(DotColor.create(value, size()));
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

    public DotColor removeColor(int index) {
        if (array.size() < 2) {
            return DotColor.empty();
        }
        return this.array.remove(index);
    }

    public int size() {
        return array.size();
    }

    public DotColor getColor(int index) {
        return array.get(index);
    }

    public void setColor(int index, DotColor color) {
        array.set(index, color);
    }

    public int findIndex(int intValue) {
        int index;
        for (index = 0; index < array.size(); index++) {
            if (array.get(index).intValue() == intValue) {
                return index;
            }
        }
        if (canAddColor()) {
            array.add(DotColor.fromColorValue(intValue));
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

            jsonGenerator.writeStartObject();
            jsonGenerator.writeArray(value.toStringArray(), 0, value.size());
            jsonGenerator.writeEndObject();
        }
    }
}
