package ru.sbtqa.tag.apifactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;
import ru.sbtqa.tag.apifactory.annotation.ApiAction;
import ru.sbtqa.tag.apifactory.exception.ApiEntryInitializationException;
import ru.sbtqa.tag.apifactory.exception.ApiException;
import ru.sbtqa.tag.apifactory.repositories.Bullet;
import ru.sbtqa.tag.apifactory.repositories.ResponseRepository;
import ru.sbtqa.tag.apifactory.rest.Rest;
import ru.sbtqa.tag.apifactory.rest.RestRawImpl;
import ru.sbtqa.tag.apifactory.soap.Soap;
import ru.sbtqa.tag.apifactory.soap.SoapImpl;
import ru.sbtqa.tag.qautils.parsers.interfaces.callback.ParserCallback;

/**
 * <p>
 * ApiFactory class.</p>
 *
 *
 */
public class ApiFactoryCore {

    private String currentEntryTitle;
    private String currentEntryPath;
    private ApiEntry currentEntry;

    private final String entriesPackage;
    private Class<? extends ParserCallback> parser;
    private Class<? extends Rest> rest = RestRawImpl.class;
    private Class<? extends Soap> soap = SoapImpl.class;
    private ResponseRepository responseRepository;
    private final Map<Class<? extends ApiEntry>, Object> requestRepository = new LinkedHashMap<>();

    /**
     * Constructor for ApiFactory.
     *
     * @param pagesPackage a {@link java.lang.String} object.
     */
    public ApiFactoryCore(String pagesPackage) {
        this.entriesPackage = pagesPackage;
        this.responseRepository = new ResponseRepository();
    }

    /**
     * Get api entry object by title
     *
     * @param title api entry title
     * @return api entry instance
     * @throws ru.sbtqa.tag.apifactory.exception.ApiException if api entry doesn't exist
     */
    public ApiEntry getApiEntry(String title) throws ApiException {
        if (null != currentEntry) {
            currentEntry = getApiEntry(currentEntry.getClass().getPackage().getName(), title);
        }
        if (null == currentEntry) {
            currentEntry = getApiEntry(entriesPackage, title);

        }
        if (null == currentEntry) {
            throw new ApiException("Api entry with title '" + title + "' is not registered");
        }
        return currentEntry;
    }

    /**
     * Get api entry by title and packageName
     *
     * @param packageName api entry package name
     * @param title api entry title
     * @return api entry instance
     * @throws ru.sbtqa.tag.apifactory.exception.ApiException if there is an error with parameters initialize
     */
    private ApiEntry getApiEntry(String packageName, String title) throws ApiException {
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends ApiEntry>> allClasses = reflections.getSubTypesOf(ApiEntry.class);
        for (Class<? extends ApiEntry> entry : allClasses) {
            //Avoid NPE on not annotated classes
            if (null == entry.getAnnotation(ApiAction.class)) {
                continue;
            }
            String entryTitle = entry.getAnnotation(ApiAction.class).title();
            String entryPath = entry.getAnnotation(ApiAction.class).path();
            if (entryTitle.equals(title)) {
                try {
                    @SuppressWarnings("unchecked")
                    Constructor<ApiEntry> c = (Constructor<ApiEntry>) entry.getConstructor();
                    currentEntry = c.newInstance();
                    currentEntryTitle = title;
                    currentEntryPath = entryPath;
                    return currentEntry;
                } catch (NoSuchMethodException | SecurityException | InstantiationException
                        | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    throw new ApiEntryInitializationException("Can't initialize current entry parameters", ex);
                }
            }
        }
        return null;
    }

    /**
     * Returns currently initialized api entry
     *
     * @return api entry
     * @throws ru.sbtqa.tag.apifactory.exception.ApiException if there are an error with api entry initialize
     */
    public ApiEntry getCurrentApiEntry() throws ApiException {
        if (null == currentEntry) {
            throw new ApiEntryInitializationException("Current api entry not initialized");
        } else {
            return currentEntry;
        }
    }

    /**
     * @return the currentEntryTitle
     */
    public String getCurrentEntryTitle() {
        return currentEntryTitle;
    }

    /**
     * @return the currentEntryPath
     */
    public String getCurrentEntryPath() {
        return currentEntryPath;
    }

    /**
     * @return the parser
     */
    public Class<? extends ParserCallback> getParser() {
        return parser;
    }

    /**
     * @param parser the parser to set
     */
    public void setParser(Class<? extends ParserCallback> parser) {
        this.parser = parser;
    }

    /**
     * @return the responseRepository
     */
    public ResponseRepository getResponseRepository() {
        return responseRepository;
    }

    /**
     * Add response to repository
     *
     * @param clazz the api entry
     * @param bullet the {@link ru.sbtqa.tag.apifactory.repositories.Bullet} response
     */
    public void addResponseToRepository(Class<? extends ApiEntry> clazz, Bullet bullet) {
        this.responseRepository.addHeaders(clazz, bullet.getHeaders());
        this.responseRepository.addBody(clazz, bullet.getBody());
    }

    /**
     * Add response headers to repository
     *
     * @param clazz the api entry
     * @param headers the list of headers of response
     */
    public void addResponseHeadersToRepository(Class<? extends ApiEntry> clazz, Map<String, String> headers) {
        this.responseRepository.addHeaders(clazz, headers);
    }

    /**
     * Add response body to repository
     *
     * @param clazz the api entry
     * @param body the body of response
     */
    public void addResponseBodyToRepository(Class<? extends ApiEntry> clazz, String body) {
        this.responseRepository.addBody(clazz, body);
    }

    /**
     * @return the requestRepository
     */
    public Map<Class<? extends ApiEntry>, Object> getRequestRepository() {
        return requestRepository;
    }

    /**
     * Add request to repository
     *
     * @param clazz the api entry
     * @param request the request
     */
    public void addRequestToRepository(Class<? extends ApiEntry> clazz, Object request) {
        this.requestRepository.put(clazz, request);
    }

    /**
     * @return the rest
     */
    public Class<? extends Rest> getRest() {
        return rest;
    }

    /**
     * @param rest the rest to set
     */
    public void setRest(Class<? extends Rest> rest) {
        this.rest = rest;
    }

    /**
     * @return the soap
     */
    public Class<? extends Soap> getSoap() {
        return soap;
    }

    /**
     * @param soap the soap to set
     */
    public void setSoap(Class<? extends Soap> soap) {
        this.soap = soap;
    }
}
