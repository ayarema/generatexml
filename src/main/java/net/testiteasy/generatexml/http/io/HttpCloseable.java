package net.testiteasy.generatexml.http.io;

import java.io.Closeable;

/**
 * Use this {@link HttpCloseable} if a process or endpoint that can be closed is either immediate or elegant.
 */
public interface HttpCloseable extends Closeable {

    /**
     * Use this method to close a process or endpoint and free up any system resources
     * associated with it (process or endpoint). If the endpoint or process is already closed,
     * then calling this method has no effect.
     *
     * @param var1 parameter that determines how the process or endpoint will be closed
     */
    void close(HttpCloseMode var1);

}
