package net.testiteasy.generatexml.http;

import net.testiteasy.generatexml.data.ConfigDataProvider;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class MyClient {

    private final HttpClient client;
    private final String uri;
    private final String path;
    private final Duration timeOut;

    public MyClient(final String filePath) {
        path = filePath;
        timeOut = Duration.ofSeconds(ConfigDataProvider.TIMEOUT);
        client = HttpClient.newHttpClient();
        uri = ConfigDataProvider.URL
                .concat(ConfigDataProvider.VERSION)
                .concat(ConfigDataProvider.PROJECT_NAME)
                .concat(ConfigDataProvider.SERVICE_URL);
    }

    public void post() throws IOException, InterruptedException {
        final Logger log = Logger.getLogger(MyClient.class.getName());
        MultiPartBodyPublisher publisher = new MultiPartBodyPublisher()
                .addString("projectName", ConfigDataProvider.PROJECT_NAME)
                .addFile("file", Path.of(path));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", "multipart/form-data; boundary=" + publisher.getBoundary())
                .header("Authorization", "Bearer " + ConfigDataProvider.TOKEN)
                .timeout(timeOut)
                .POST(publisher.build())
                .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        log.info(String.format("Response code: %s message: %s", response.statusCode(), response.body()));
    }

    private static class MultiPartBodyPublisher {
        private List<Part> specification = new ArrayList<>();
        private final String boundary = UUID.randomUUID().toString();

        public HttpRequest.BodyPublisher build() {
            if (specification.size() == 0) {
                throw new IllegalStateException("Can't build multipart request; received empty specification.");
            }
            addFinalBoundaryPart();
            return HttpRequest.BodyPublishers.ofByteArrays(PartsIterator::new);
        }

        public String getBoundary() {
            return boundary;
        }

        public MultiPartBodyPublisher addString(final String name, final String value) {
            specification.add(new Part(name, value));
            return this;
        }

        public MultiPartBodyPublisher addFile(final String name, final Path path) {
            specification.add(new FilePart(name, path));
            return this;
        }

        public MultiPartBodyPublisher addStream(
                final String name,
                final Supplier<InputStream> stream,
                final String filename,
                final String contentType
        ) {
            specification.add(new StreamPart(name, stream, filename, contentType));
            return this;
        }

        private void addFinalBoundaryPart() {
            specification.add(new Part(TYPE.FINAL_BOUNDARY, null, "--" + boundary + "--"));
        }

        public enum TYPE {
            STRING, FILE, STREAM, FINAL_BOUNDARY
        }

        class Part {
            TYPE type;
            String name;
            String value;

            Part(final TYPE type, final String name, final String value) {
                this.type = type;
                this.name = name;
                this.value = value;
            }

            Part(final String name, final String value) {
                this.type = TYPE.STRING;
                this.name = name;
                this.value = value;
            }
        }

        class FilePart extends Part {
            Path path;

            FilePart(final String name, Path path) {
                super(TYPE.FILE, name, null);
                this.path = path;
            }
        }

        class StreamPart extends Part {
            Supplier<InputStream> stream;
            String filename;
            String contentType;

            StreamPart(
                    final String name,
                    final Supplier<InputStream> stream,
                    final String filename,
                    final String contentType
            ) {
                super(TYPE.STREAM, name, null);
                this.stream = stream;
                this.filename = filename;
                this.contentType = contentType;
            }
        }

        class PartsIterator implements Iterator<byte[]> {

            private Iterator<Part> iter;
            private InputStream currentFileInput;

            private boolean done;
            private byte[] next;

            PartsIterator() {
                iter = specification.iterator();
            }

            @Override
            public boolean hasNext() {
                if (done) return false;
                if (next != null) return true;
                try {
                    next = computeNext();
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
                if (next == null) {
                    done = true;
                    return false;
                }
                return true;
            }

            @Override
            public byte[] next() {
                if (!hasNext()) throw new NoSuchElementException();
                byte[] res = next;
                next = null;
                return res;
            }

            private byte[] computeNext() throws IOException {
                if (currentFileInput == null) {
                    if (!iter.hasNext()) return null;
                    Part nextPart = iter.next();
                    String filename;
                    String contentType;

                    switch (nextPart.type) {
                        case STRING: {
                            String part =
                                    "--" + boundary + "\r\n" +
                                            "Content-Disposition: form-data; name=" + nextPart.name + "\r\n" +
                                            "Content-Type: text/plain; charset=UTF-8\r\n\r\n" +
                                            nextPart.value + "\r\n";
                            return part.getBytes(StandardCharsets.UTF_8);
                        }
                        case FILE: {
                            FilePart part = (FilePart) nextPart;
                            Path path = part.path;
                            filename = path.getFileName().toString();
                            contentType = Files.probeContentType(path);
                            if (contentType == null) contentType = "application/octet-stream";
                            currentFileInput = Files.newInputStream(path);
                            return getHeader(part.name, filename, contentType);
                        }
                        case STREAM: {
                            StreamPart part = (StreamPart) nextPart;
                            contentType = part.contentType == null ? "application/octet-stream" : part.contentType;
                            currentFileInput = part.stream.get();
                            return getHeader(part.name, part.filename, contentType);
                        }
                        case FINAL_BOUNDARY: {
                            return nextPart.value.getBytes(StandardCharsets.UTF_8);
                        }
                        default: {
                            throw new RuntimeException("Unsupported multi-type request part");
                        }
                    }
                } else {
                    byte[] buf = new byte[8192];
                    int r = currentFileInput.read(buf);
                    if (r > 0) {
                        byte[] actualBytes = new byte[r];
                        System.arraycopy(buf, 0, actualBytes, 0, r);
                        return actualBytes;
                    } else {
                        currentFileInput.close();
                        currentFileInput = null;
                        return "\r\n".getBytes(StandardCharsets.UTF_8);
                    }
                }
            }

            private byte[] getHeader(final String name, final String filename, final String contentType) {
                String partHeader =
                        "--" + boundary + "\r\n" +
                                "Content-Disposition: form-data; name=" + name + "; filename=" + filename + "\r\n" +
                                "Content-Type: " + contentType + "\r\n\r\n";
                return partHeader.getBytes(StandardCharsets.UTF_8);
            }
        }
    }
}
