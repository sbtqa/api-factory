package ru.sbtqa.tag.apifactory.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sbtqa.tag.allurehelper.ParamsHelper;
import ru.sbtqa.tag.apifactory.exception.ApiRestException;
import ru.sbtqa.tag.apifactory.repositories.Bullet;
import ru.sbtqa.tag.qautils.errors.AutotestError;
import ru.sbtqa.tag.qautils.properties.Props;

/**
 *
 *
 */
public class RestEntityImpl implements Rest {

    private static final Logger log = LoggerFactory.getLogger(RestEntityImpl.class);

    @Override
    public Bullet get(String url, Map<String, String> headers) throws ApiRestException {
        HttpClient client = HttpClients.createDefault();

        log.info("Request url {}", url);
        HttpGet get = new HttpGet(url);

        headers.entrySet()
                .stream()
                .forEach(h -> {
                    get.setHeader(h.getKey(), h.getValue());
                });
        log.info("Request headers {}", headers);

        HttpResponse response = null;
        try {
            response = client.execute(get);
        } catch (IOException ex) {
            log.error("Ошибка при выполнении запроса", ex);
            throw new AutotestError(ex);
          
        }
        Map<String, String> headersResponse = new HashMap<>();
        for (Header h : response.getAllHeaders()) {
            ParamsHelper.addParam(h.getName(), h.getValue());
            if (headersResponse.containsKey(h.getName())) {
                headersResponse.put(h.getName(), headersResponse.get(h.getName()) + "; " + h.getValue());
            } else {
                headersResponse.put(h.getName(), h.getValue());
            }
        }

        if (response.getStatusLine().getStatusCode() != 200) {
            log.error("Статус ответа '{}'", response.getStatusLine().getReasonPhrase());
            throw new AutotestError("Статус ответа не равен 200");
        }

        Bullet bullet = null;
        try {
            bullet = new Bullet(headersResponse, EntityUtils.toString(response.getEntity()));
        } catch (IOException | ParseException ex) {
            log.error("Error in response body get", ex);
        }

        return bullet;
    }

    @Override
    public Bullet post(String url, Map<String, String> headers, Object body) throws ApiRestException {
        try {
            HttpClient client = HttpClients.createDefault();

            log.info("Sending 'POST' request to URL : {}", url);
            HttpPost post = new HttpPost(url);

            headers.entrySet()
                    .stream()
                    .forEach(h -> {
                        post.setHeader(h.getKey(), h.getValue());
                    });
            log.info("Headers are: {}", headers);

            List<NameValuePair> postParams;
            if (body instanceof Map) {
                Map<String, String> params = (Map<String, String>) body;
                postParams = params.entrySet()
                        .stream()
                        .map(e -> new BasicNameValuePair(e.getKey(), e.getValue()))
                        .collect(Collectors.toList());
                if (!postParams.isEmpty()) {
                    post.setEntity(new UrlEncodedFormEntity(postParams));
                }
                log.info("Body (form-data) is: {}", body);
            } else if (body instanceof String) {
                post.setEntity(new StringEntity((String) body, Props.get("api.encoding")));
            }

            HttpResponse response = client.execute(post);

            if (response.getStatusLine().getStatusCode() != 200) {
                log.error("Статус ответа '{}'", response.getStatusLine().getReasonPhrase());
                throw new AutotestError("Статус ответа не равен 200");
            }

            Map<String, String> headersResponse = new HashMap<>();
            for (Header h : response.getAllHeaders()) {
                ParamsHelper.addParam(h.getName(), h.getValue());
                if (headersResponse.containsKey(h.getName())) {
                    headersResponse.put(h.getName(), headersResponse.get(h.getName()) + "; " + h.getValue());
                } else {
                    headersResponse.put(h.getName(), h.getValue());
                }
            }

            Bullet bullet = new Bullet(headersResponse, EntityUtils.toString(response.getEntity()));

            return bullet;
        } catch (IOException ex) {
            log.error("TODO", ex);
        }

        return null;
    }

}
