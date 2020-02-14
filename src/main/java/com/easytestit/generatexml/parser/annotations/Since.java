package com.easytestit.generatexml.parser.annotations;

import com.easytestit.generatexml.parser.JsonParser;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that indicates the version number since a member or a type has been present.
 * This annotation is useful to manage versioning of your JsonParser's classes for a web-service.
 *
 * <p>
 * This annotation has no effect unless you build {@link JsonParser} with a
 * {@link com.easytestit.generatexml.parser.JsonBuilder} and invoke
 * {@link com.easytestit.generatexml.parser.JsonBuilder#setVersion(double)} method.
 *
 * <p>Here is an example of how this annotation is meant to be used:</p>
 * <pre>
 * public class User {
 *   private String firstName;
 *   private String lastName;
 *   @ Since(1.0) private String emailAddress;
 *   @ Since(1.0) private String password;
 *   @ Since(1.1) private Address address;
 * }
 * </pre>
 *
 * <p>If you created JsonParser with {@code new JsonParser()}, the {@code toJson()} and {@code fromJson()}
 * methods will use all the fields for serialization and deserialization. However, if you created
 * JsonParser with {@code JsonParser jsonParser = new JsonBuilder().setVersion(1.0).create()} then the
 * {@code toJson()} and {@code fromJson()} methods of JsonParser will exclude the {@code address} field
 * since it's version number is set to {@code 1.1}.</p>
 *
 * @author GSON library
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Since {
    /**
     * the value indicating a version number since this member or type has been present.
     */
    double value();
}
