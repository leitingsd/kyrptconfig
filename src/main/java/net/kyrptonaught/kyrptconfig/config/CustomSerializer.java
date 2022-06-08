package net.kyrptonaught.kyrptconfig.config;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.api.DeserializerFunction;
import blue.endless.jankson.api.Marshaller;

import java.util.function.BiFunction;

public class CustomSerializer {
    private final Class<?> saveClazz;
    private final Class<?> inputClazz;

    private BiFunction<?, Marshaller, JsonElement> serializer;
    private DeserializerFunction<?, ?> deserializer;

    public CustomSerializer(Class<?> inputClazz, Class<?> saveClazz) {
        this.inputClazz = inputClazz;
        this.saveClazz = saveClazz;
    }


    public <T> CustomSerializer registerSerializer(BiFunction<T, Marshaller, JsonElement> serializer) {
        this.serializer = serializer;
        return this;
    }

    public <A, B> CustomSerializer registerDeserializer(DeserializerFunction<A, B> function) {
        this.deserializer = function;
        return this;
    }

    public Jankson.Builder addToBuilder(Jankson.Builder builder) {
        builder.registerSerializer((Class) inputClazz, serializer);
        builder.registerDeserializer((Class) saveClazz, (Class) inputClazz, deserializer);
        return builder;
    }
}