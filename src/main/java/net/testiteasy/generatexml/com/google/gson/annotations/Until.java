package net.testiteasy.generatexml.com.google.gson.annotations;

import net.testiteasy.generatexml.com.google.gson.GsonBuilder;
import net.testiteasy.generatexml.com.google.gson.Gson;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that indicates the version number until a member or a type should be present.
 * Basically, if JsonParser is created with a version number that exceeds the value stored in the
 * {@code Until} annotation then the field will be ignored from the JSON output. This annotation
 * is useful to manage versioning of your JSON classes for a web-service.
 *
 * <p>
 * This annotation has no effect unless you build {@link Gson} with a
 * {@link GsonBuilder} and invoke
 * {@link GsonBuilder#setVersion(double)} method.
 *
 * <p>Here is an example of how this annotation is meant to be used:</p>
 * <pre>
 * public class User {
 *   private String firstName;
 *   private String lastName;
 *   @ Until(1.1) private String emailAddress;
 *   @ Until(1.1) private String password;
 * }
 * </pre>
 *
 * <p>If you created JsonParser with {@code new JsonParser()}, the {@code toJson()} and {@code fromJson()}
 * methods will use all the fields for serialization and deserialization. However, if you created
 * JsonParser with {@code JsonParser jsonParser = new JsonBuilder().setVersion(1.2).create()} then the
 * {@code toJson()} and {@code fromJson()} methods of JsonParser will exclude the {@code emailAddress}
 * and {@code password} fields from the example above, because the version number passed to the
 * JsonBuilder, {@code 1.2}, exceeds the version number set on the {@code Until} annotation,
 * {@code 1.1}, for those fields.
 *
 * @author GSON library
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Until {
    /**
     * the value indicating a version number until this member or type should be ignored.
     */
    double value();
}
