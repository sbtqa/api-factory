package ru.sbtqa.tag.apifactory.repositories;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple request (response) view. It consists of headers and body
 *
 * @author Konstantin Maltsev <mkypers@gmail.com>
 */
public class Bullet {

    private Map<String, String> headers = new HashMap<>();
    private String body = null;

    public Bullet() {

    }

    public Bullet(Map<String, String> headers, String body) {
        this.headers = headers;
        this.body = body;
    }

    /**
     * @return the headers
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * @param name header name
     * @return the header
     */
    public String getHeader(String name) {
        return headers.get(name);
    }

    /**
     * @param headers the headers to set
     */
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(String body) {
        this.body = body;
    }
}
