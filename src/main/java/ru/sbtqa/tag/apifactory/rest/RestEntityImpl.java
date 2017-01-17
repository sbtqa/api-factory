package ru.sbtqa.tag.apifactory.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
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
public class RestEntityImpl extends AbstractRestEntity implements Rest {

    private static final Logger LOG = LoggerFactory.getLogger(RestEntityImpl.class);

    @Override
    public Bullet get(String url, Map<String, String> headers) throws ApiRestException {
        HttpClient client = HttpClients.createDefault();
        Bullet bullet = null;

        try {
            LOG.info("Request url {}", url);
            final HttpGet get = new HttpGet(url);

            headers.entrySet()
                    .stream()
                    .forEach(new Consumer<Map.Entry<String, String>>() {
                        @Override
                        public void accept(Map.Entry<String, String> h) {
                            get.setHeader(h.getKey(), h.getValue());
                        }
                    });
            LOG.info("Request headers {}", headers);

            HttpResponse response = null;
            try {
                response = client.execute(get);
            } catch (IOException ex) {
                LOG.error("Ошибка при выполнении запроса", ex);
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
                LOG.error("Статус ответа '{}'", response.getStatusLine().getReasonPhrase());
                throw new AutotestError("Статус ответа не равен 200");
            }

            try {
                bullet = new Bullet(headersResponse, EntityUtils.toString(response.getEntity()));
            } catch (IOException | ParseException ex) {
                LOG.error("Error in response body get", ex);
            }

        } finally {
            HttpClientUtils.closeQuietly(client);
        }

        return bullet;
    }

    @Override
    public Bullet post(String url, Map<String, String> headers, Object body) throws ApiRestException {
        HttpClient client = HttpClients.createDefault();

        try {
            LOG.info("Sending 'POST' request to URL : {}", url);
            final HttpPost post = new HttpPost(url);

            headers.entrySet()
                    .stream()
                    .forEach(new Consumer<Map.Entry<String, String>>() {
                        @Override
                        public void accept(Map.Entry<String, String> h) {
                            post.setHeader(h.getKey(), h.getValue());
                        }
                    });
            LOG.info("Headers are: {}", headers);

            List<NameValuePair> postParams;
            if (body instanceof Map) {
                Map<String, String> params = (Map<String, String>) body;
                postParams = params.entrySet()
                        .stream().map(new Function<Map.Entry<String, String>, BasicNameValuePair>() {
                            @Override
                            public BasicNameValuePair apply(Map.Entry<String, String> e) {
                                return new BasicNameValuePair(e.getKey(), e.getValue());
                            }
                        })
                        .collect(Collectors.toCollection(new Supplier<List<NameValuePair>>() {
                            @Override
                            public List<NameValuePair> get() {
                                return new ArrayList<>();
                            }
                        }));
                if (!postParams.isEmpty()) {
                    post.setEntity(new UrlEncodedFormEntity(postParams));
                }
                LOG.info("Body (form-data) is: {}", body);
            } else if (body instanceof String) {
                post.setEntity(new StringEntity((String) body, Props.get("api.encoding")));
            }

            HttpResponse response = client.execute(post);

            if (response.getStatusLine().getStatusCode() != 200) {
                LOG.error("Статус ответа '{}'", response.getStatusLine().getReasonPhrase());
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

            return new Bullet(headersResponse, EntityUtils.toString(response.getEntity()));
        } catch (IOException ex) {
            LOG.error("Failed to get response", ex);
        } finally {
            HttpClientUtils.closeQuietly(client);
        }

        return null;
    }

}
