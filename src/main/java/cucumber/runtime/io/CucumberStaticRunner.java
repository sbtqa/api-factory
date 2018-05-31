package cucumber.runtime.io;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.runtime.ClassFinder;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;

public class CucumberStaticRunner {

	private static String status = "\nFinished: SUCCESS";
	private static final Logger LOG = LoggerFactory.getLogger(CucumberStaticRunner.class);

	public static void startTests(String[] argv) throws Throwable {
		byte exitstatus = run(argv, Thread.currentThread().getContextClassLoader());
		System.exit(exitstatus);
	}

	public static byte run(String[] argv, ClassLoader classLoader) throws IOException {
		RuntimeOptions runtimeOptions = new RuntimeOptions(new ArrayList<String>(asList(argv)));

		ResourceLoader resourceLoader = new CustomMultiLoader(classLoader);
		ClassFinder classFinder = new ResourceLoaderClassFinder(resourceLoader, classLoader);
		Runtime runtime = new Runtime(resourceLoader, classFinder, classLoader, runtimeOptions);
		runtime.run();
		if (runtime.getErrors().size() > 0 || runtime.getSnippets().size() > 0) {
			status = "Finished: FAILURE";
		}
		LOG.info(status);
		return runtime.exitStatus();
	}
}
