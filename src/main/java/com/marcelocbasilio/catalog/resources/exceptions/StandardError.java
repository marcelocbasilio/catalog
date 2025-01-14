package com.marcelocbasilio.catalog.resources.exceptions;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class StandardError implements Serializable {

    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    public StandardError() {
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        StandardError that = (StandardError) o;
        return Objects.equals(timestamp, that.timestamp) && Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(timestamp);
        result = 31 * result + Objects.hashCode(path);
        return result;
    }

    @Override
    public String toString() {
        return "StandardError{" +
               "timestamp=" + timestamp +
               ", status=" + status +
               ", error='" + error + '\'' +
               ", message='" + message + '\'' +
               ", path='" + path + '\'' +
               '}';
    }
}
