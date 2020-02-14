package com.easytestit.generatexml.parser.annotations;

import com.easytestit.generatexml.parser.JsonDeserializer;
import com.easytestit.generatexml.parser.JsonSerializer;
import com.easytestit.generatexml.parser.adapter.TypeAdapter;
import com.easytestit.generatexml.parser.adapter.TypeAdapterFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that indicates the JsonParser {@link TypeAdapter} to use with a class
 * or field.
 *
 * <p>Here is an example of how this annotation is used:</p>
 * <pre>
 * @ JsonAdapter(UserJsonAdapter.class)
 * public class User {
 *   public final String firstName, lastName;
 *   private User(String firstName, String lastName) {
 *     this.firstName = firstName;
 *     this.lastName = lastName;
 *   }
 * }
 * public class UserJsonAdapter extends TypeAdapter< User >; {
 *   @ Override public void write(JsonWriter out, User user) throws IOException {
 *     // implement write: combine firstName and lastName into name
 *     out.beginObject();
 *     out.name("name");
 *     out.value(user.firstName + " " + user.lastName);
 *     out.endObject();
 *     // implement the write method
 *   }
 *   @ Override public User read(JsonReader in) throws IOException {
 *     // implement read: split name into firstName and lastName
 *     in.beginObject();
 *     in.nextName();
 *     String[] nameParts = in.nextString().split(" ");
 *     in.endObject();
 *     return new User(nameParts[0], nameParts[1]);
 *   }
 * }
 * </pre>
 *
 * Since User class specified UserJsonAdapter.class in @ JsonAdapter annotation, it
 * will automatically be invoked to serialize/deserialize User instances. <br>
 *
 * <p> Here is an example of how to apply this annotation to a field.
 * <pre>
 * private static final class Gadget {
 *   @ JsonAdapter(UserJsonAdapter2.class)
 *   final User user;
 *   Gadget(User user) {
 *     this.user = user;
 *   }
 * }
 * </pre>
 *
 * It's possible to specify different type adapters on a field, that
 * field's type, and in the {@link com.easytestit.generatexml.parser.JsonBuilder}. Field
 * annotations take precedence over {@code JsonBuilder}-registered type
 * adapters, which in turn take precedence over annotated types.
 *
 * <p>The class referenced by this annotation must be either a {@link
 * TypeAdapter} or a {@link TypeAdapterFactory}, or must implement one
 * or both of {@link JsonDeserializer} or {@link JsonSerializer}.
 * Using {@link TypeAdapterFactory} makes it possible to delegate
 * to the enclosing {@code JsonParser} instance.
 *
 * @author GSON library
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface JsonAdapter {
    /** Either a {@link TypeAdapter} or {@link TypeAdapterFactory}, or one or both of {@link JsonDeserializer} or {@link JsonSerializer}. */
    Class<?> value();

    /** false, to be able to handle {@code null} values within the adapter, default value is true. */
    boolean nullSafe() default true;
}
