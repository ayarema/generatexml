package net.testiteasy.generatexml.com.google.gson.adapter;

import net.testiteasy.generatexml.com.google.gson.Gson;
import net.testiteasy.generatexml.com.google.gson.internal.$Json$Types;
import net.testiteasy.generatexml.com.google.gson.reflect.TypeToken;
import net.testiteasy.generatexml.com.google.gson.stream.JsonReader;
import net.testiteasy.generatexml.com.google.gson.stream.JsonToken;
import net.testiteasy.generatexml.com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapt an array of objects.
 */
public final class ArrayTypeAdapter<E> extends TypeAdapter<Object> {
    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
        @SuppressWarnings({"unchecked", "rawtypes"})
        @Override public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            Type type = typeToken.getType();
            if (!(type instanceof GenericArrayType || type instanceof Class && ((Class<?>) type).isArray())) {
                return null;
            }

            Type componentType = $Json$Types.getArrayComponentType(type);
            TypeAdapter<?> componentTypeAdapter = gson.getAdapter(TypeToken.get(componentType));
            return new ArrayTypeAdapter(
                    gson, componentTypeAdapter, $Json$Types.getRawType(componentType));
        }
    };

    private final Class<E> componentType;
    private final TypeAdapter<E> componentTypeAdapter;

    public ArrayTypeAdapter(Gson context, TypeAdapter<E> componentTypeAdapter, Class<E> componentType) {
        this.componentTypeAdapter =
                new TypeAdapterRuntimeTypeWrapper<>(context, componentTypeAdapter, componentType);
        this.componentType = componentType;
    }

    @Override public Object read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        List<E> list = new ArrayList<>();
        in.beginArray();
        while (in.hasNext()) {
            E instance = componentTypeAdapter.read(in);
            list.add(instance);
        }
        in.endArray();

        int size = list.size();
        Object array = Array.newInstance(componentType, size);
        for (int i = 0; i < size; i++) {
            Array.set(array, i, list.get(i));
        }
        return array;
    }

    @SuppressWarnings("unchecked")
    @Override public void write(JsonWriter out, Object array) throws IOException {
        if (array == null) {
            out.nullValue();
            return;
        }

        out.beginArray();
        for (int i = 0, length = Array.getLength(array); i < length; i++) {
            E value = (E) Array.get(array, i);
            componentTypeAdapter.write(out, value);
        }
        out.endArray();
    }
}
