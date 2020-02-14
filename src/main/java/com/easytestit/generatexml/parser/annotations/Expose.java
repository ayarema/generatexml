package com.easytestit.generatexml.parser.annotations;

import com.easytestit.generatexml.parser.JsonParser;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that indicates this member should be exposed for JSON
 * serialization or deserialization.
 *
 * <p>This annotation has no effect unless you build {@link JsonParser}
 * with a {@link com.easytestit.generatexml.parser.JsonBuilder} and invoke
 * {@link com.easytestit.generatexml.parser.JsonBuilder#excludeFieldsWithoutExposeAnnotation()}
 * method.</p>
 *
 * <p>Here is an example of how this annotation is meant to be used:
 * <p><pre>
 * public class User {
 *   @ Expose private String firstName;
 *   @ Expose(serialize = false) private String lastName;
 *   @ Expose (serialize = false, deserialize = false) private String emailAddress;
 *   private String password;
 * }
 * </pre></p>
 * If you created JsonParser with {@code new JsonParser()}, the {@code toJson()} and {@code fromJson()}
 * methods will use the {@code password} field along-with {@code firstName}, {@code lastName},
 * and {@code emailAddress} for serialization and deserialization. However, if you created JsonParser
 * with {@code JsonParser jsonParser = new JsonBuilder().excludeFieldsWithoutExposeAnnotation().create()}
 * then the {@code toJson()} and {@code fromJson()} methods of JsonParser will exclude the
 * {@code password} field. This is because the {@code password} field is not marked with the
 * {@code @Expose} annotation. JsonParser will also exclude {@code lastName} and {@code emailAddress}
 * from serialization since {@code serialize} is set to {@code false}. Similarly, JsonParser will
 * exclude {@code emailAddress} from deserialization since {@code deserialize} is set to false.
 *
 * <p>Note that another way to achieve the same effect would have been to just mark the
 * {@code password} field as {@code transient}, and JsonParser would have excluded it even with default
 * settings. The {@code @Expose} annotation is useful in a style of programming where you want to
 * explicitly specify all fields that should get considered for serialization or deserialization.
 *
 * @author GSON library
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Expose {
    /**
     * If {@code true}, the field marked with this annotation is written out in the JSON while
     * serializing. If {@code false}, the field marked with this annotation is skipped from the
     * serialized output. Defaults to {@code true}.
     */
    boolean serialize() default true;

    /**
     * If {@code true}, the field marked with this annotation is deserialized from the JSON.
     * If {@code false}, the field marked with this annotation is skipped during deserialization.
     * Defaults to {@code true}.
     */
    boolean deserialize() default true;
}
