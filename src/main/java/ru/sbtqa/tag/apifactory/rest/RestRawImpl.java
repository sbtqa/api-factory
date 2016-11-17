package ru.sbtqa.tag.apifactory.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sbtqa.tag.apifactory.exception.ApiRestException;
import ru.sbtqa.tag.apifactory.repositories.Bullet;
import ru.sbtqa.tag.qautils.properties.Props;

/**
 *
 * @author Konstantin Maltsev <mkypers@gmail.com>
 */
public class RestRawImpl implements Rest {

    private static final Logger log = LoggerFactory.getLogger(RestRawImpl.class);

    @Override
    public Bullet get(String url, Map<String, String> headers) throws ApiRestException {
        return fire("GET", url, headers, null, null);
    }

    @Override
    public Bullet post(String url, Map<String, String> headers, Object body) throws ApiRestException {
        return fire("POST", url, headers, body, null);
    }

    @Override
    public Bullet put(String url, Map<String, String> headers, Object body) throws ApiRestException {
        return fire("PUT", url, headers, body, null);
    }

    @Override
    public Bullet patch(String url, Map<String, String> headers, Object body) throws ApiRestException {
        return fire("PATCH", url, headers, body, null);
    }

    @Override
    public Bullet delete(String url, Map<String, String> headers) throws ApiRestException {
        return fire("DELETE", url, headers, null, null);
    }

    private Bullet fire(String method, String url, Map<String, String> headers, Object body, Proxy proxy) throws ApiRestException {
        try {
            URL obj = new URL(url.replaceAll(" ", "%20"));
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

            //stackoverflow.com/question/25163131
            if ("PATCH".equals(method)) {
                method = "POST";
                connection.setRequestProperty("X-HTTP-Method-Override", "PATCH");
            }

            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            String encoding = Props.get("api.encoding");
            log.info("Sending '" + method + "' request to URL : {}", URLDecoder.decode(url, encoding));

            //add request header
            if (null != headers && !headers.isEmpty()) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    connection.setRequestProperty(header.getKey(), header.getValue());
                }
                log.info("Headers are: {}", headers);
            }

            //add body
            if (null != body && !"".equals(body)) {
                try (OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), encoding)) {
                    out.write((String) body);
                }
            }

            log.info("Body is: {}", body);

            Object response;
            try {
                response = IOUtils.toString((InputStream) connection.getContent(), encoding);
                log.info("Response is {}", response);
            } catch (IOException e) {
                response = IOUtils.toString((InputStream) connection.getErrorStream(), encoding);
                log.error("Response is {}", response, e);
            } catch (ClassCastException e) {
                response = connection.getContent();
                log.error("Response return an error", e);
            }
            
            Bullet bullet = new Bullet(null, response.toString());
            return bullet;
        } catch (Exception ex) {
            log.error("There are an error in fire method", ex);
        }
        return null;
    }
}

