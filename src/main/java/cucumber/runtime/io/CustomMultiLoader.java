package cucumber.runtime.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class CustomMultiLoader implements ResourceLoader {
	public static final String CLASSPATH_SCHEME = "classpath*:";
	public static final String CLASSPATH_SCHEME_TO_REPLACE = "classpath:";
	private final ClasspathResourceLoader classpath;
	private final FileResourceLoader fs;

	public CustomMultiLoader(ClassLoader classLoader) {
		classpath = new ClasspathResourceLoader(classLoader);
		fs = new FileResourceLoader();
	}

	@Override
	public Iterable<Resource> resources(String path, String suffix) {
		if (isClasspathPath(path)) {
			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			String locationPattern = path.replace(CLASSPATH_SCHEME_TO_REPLACE, CLASSPATH_SCHEME) + "/**/*" + suffix;
			org.springframework.core.io.Resource[] resources;
			try {
				resources = resolver.getResources(locationPattern);
			} catch (IOException e) {
				resources = null;
				e.printStackTrace();
			}
			return convertToCucumberIterator(resources);
		} else {
			return fs.resources(path, suffix);
		}
	}

	private Iterable<Resource> convertToCucumberIterator(org.springframework.core.io.Resource[] resources) {
		List<Resource> results = new ArrayList<Resource>();
		for (org.springframework.core.io.Resource resource : resources) {
			results.add(new ResourceAdapter(resource));
		}
		return results;
	}

	public static String packageName(String gluePath) {
		if (isClasspathPath(gluePath)) {
			gluePath = stripClasspathPrefix(gluePath);
		}
		return gluePath.replace('/', '.').replace('\\', '.');
	}

	private static boolean isClasspathPath(String path) {
		if (path.startsWith(CLASSPATH_SCHEME_TO_REPLACE)) {
			path = path.replace(CLASSPATH_SCHEME_TO_REPLACE, CLASSPATH_SCHEME);
		}
		return path.startsWith(CLASSPATH_SCHEME);
	}

	private static String stripClasspathPrefix(String path) {
		if (path.startsWith(CLASSPATH_SCHEME_TO_REPLACE)) {
			path = path.replace(CLASSPATH_SCHEME_TO_REPLACE, CLASSPATH_SCHEME);
		}
		return path.substring(CLASSPATH_SCHEME.length());
	}

}
