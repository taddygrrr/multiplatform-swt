package com.github.jendap.multiplatformswt;

import static junit.framework.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.junit.Test;

import com.github.jendap.multiplatformswt.loader.MultiPlatformSwtHelper;

public class MultiPlatformSwtHelperIT {
	private static final String SOME_SWT_CLASS_NAME = "org.eclipse.swt.SWT";

	@Test(expected = ClassNotFoundException.class)
	public void importSwtJarUninitializedSystemClassLoaderTest() throws ClassNotFoundException {
		final URLClassLoader systemClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		systemClassLoader.loadClass(SOME_SWT_CLASS_NAME);
	}

	@Test(expected = ClassNotFoundException.class)
	public void importSwtJarUninitializedURLClassLoaderTest() throws ClassNotFoundException, MalformedURLException {
		final URL swtJArUrl = new URL("file:/non-existing-path");
		final URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[] { swtJArUrl });
		urlClassLoader.loadClass(SOME_SWT_CLASS_NAME);
	}

	@Test
	public void importSwtJarInitializedURLClassLoaderTest() throws ClassNotFoundException, MalformedURLException {
		final MultiPlatformSwtHelper multiPlatformSwtHelper = new MultiPlatformSwtHelper();
		final URL swtJarUrl = multiPlatformSwtHelper.getSwtPlatformDependentJarFileUrl();
		final URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[] { swtJarUrl });
		final Class<?> someSwtClass = urlClassLoader.loadClass(SOME_SWT_CLASS_NAME);
		assertNotNull(someSwtClass);
	}
}
