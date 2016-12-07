package ru.sbtqa.tag.apifactory.repositories;

import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sbtqa.tag.apifactory.ApiEntry;

/**
 * Response repository. Contains responses as pair of ApiRequestEntry.class and
 * {@link ru.sbtqa.tag.apifactory.repositories.Bullet} object.
 *
 *
 */
public class ResponseRepository {

    private static final Logger log = LoggerFactory.getLogger(ResponseRepository.class);

    private final Map<Class<? extends ApiEntry>, Bullet> responseRepository = new LinkedHashMap<>();

    /**
     * Get headers pairs name-value by ApiRequestEntry.class
     *
     * @param apiEntry api object class of request
     * @return headers as Map
     */
    public Map<String, String> getHeaders(Class<? extends ApiEntry> apiEntry) {
        return responseRepository.get(apiEntry).getHeaders();
    }

    /**
     * Get header value by ApiRequestEntry.class and header name
     *
     * @param apiEntry api object class of request
     * @param headerName header name
     * @return {@link java.lang.String} header value
     */
    public String getHeader(Class<? extends ApiEntry> apiEntry, String headerName) {
        return responseRepository.get(apiEntry).getHeader(headerName);
    }

    /**
     * Get body as String by ApiRequestEntry.class
     *
     * @param apiEntry api object class of request
     * @return {@link java.lang.String} body
     */
    public String getBody(Class<? extends ApiEntry> apiEntry) {
        return responseRepository.get(apiEntry).getBody();
    }

    /**
     * Add body by ApiRequestEntry.class
     *
     * @param apiEntry api object class of request
     * @param body {@link java.lang.String} body
     */
    public void addBody(Class<? extends ApiEntry> apiEntry, String body) {
        Bullet bullet;
        if (responseRepository.containsKey(apiEntry)) {
            bullet = (Bullet) responseRepository.get(apiEntry);
        } else {
            bullet = new Bullet();
        }
        bullet.setBody(body);

        responseRepository.put(apiEntry, bullet);
        log.info("Added to repository key {} body {{}}", apiEntry.getName(), body);
    }

    /**
     * Add headers by ApiRequestEntry.class
     *
     * @param apiEntry api object class of request
     * @param headers {@link java.util.Map} headers
     */
    public void addHeaders(Class<? extends ApiEntry> apiEntry, Map<String, String> headers) {
        Bullet bullet;
        if (responseRepository.containsKey(apiEntry)) {
            bullet = (Bullet) responseRepository.get(apiEntry);
        } else {
            bullet = new Bullet();
        }
        bullet.setHeaders(headers);

        responseRepository.put(apiEntry, bullet);
        log.info("Added to repository key {} headers {}", apiEntry.getName(), headers);
    }

    /**
     * Get last response in repository
     *
     * @return {@link ru.sbtqa.tag.apifactory.repositories.Bullet} object
     */
    public Bullet getLastResponseInRepository() {
        Bullet response = null;
        for (Map.Entry<Class<? extends ApiEntry>, Bullet> entry : responseRepository.entrySet()) {
            response = entry.getValue();
        }
        return response;
    }
}
