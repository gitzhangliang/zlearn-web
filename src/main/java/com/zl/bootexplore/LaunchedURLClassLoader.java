package com.zl.bootexplore;



import com.zl.bootexplore.jar.Handler;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Enumeration;
import java.util.jar.JarFile;
import org.springframework.lang.UsesJava7;
public class LaunchedURLClassLoader extends URLClassLoader {
    public LaunchedURLClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }
    @Override
    public URL findResource(String name) {
        Handler.setUseFastConnectionExceptions(true);

        URL var2;
        try {
            var2 = super.findResource(name);
        } finally {
            Handler.setUseFastConnectionExceptions(false);
        }

        return var2;
    }
    @Override
    public Enumeration<URL> findResources(String name) throws IOException {
        Handler.setUseFastConnectionExceptions(true);

        Enumeration var2;
        try {
            var2 = super.findResources(name);
        } finally {
            Handler.setUseFastConnectionExceptions(false);
        }

        return var2;
    }
    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Handler.setUseFastConnectionExceptions(true);

        Class var3;
        try {
            try {
                this.definePackageIfNecessary(name);
            } catch (IllegalArgumentException var7) {
                if (this.getPackage(name) == null) {
                    throw new AssertionError("Package " + name + " has already been defined but it could not be found");
                }
            }

            var3 = super.loadClass(name, resolve);
        } finally {
            Handler.setUseFastConnectionExceptions(false);
        }

        return var3;
    }

    private void definePackageIfNecessary(String className) {
        int lastDot = className.lastIndexOf(46);
        if (lastDot >= 0) {
            String packageName = className.substring(0, lastDot);
            if (this.getPackage(packageName) == null) {
                try {
                    this.definePackage(className, packageName);
                } catch (IllegalArgumentException var5) {
                    if (this.getPackage(packageName) == null) {
                        throw new AssertionError("Package " + packageName + " has already been defined but it could not be found");
                    }
                }
            }
        }

    }

    private void definePackage(final String className, final String packageName) {
        try {
            AccessController.doPrivileged(new PrivilegedExceptionAction<Object>() {
                @Override
                public Object run() throws ClassNotFoundException {

                    String packageEntryName = packageName.replace('.', '/') + "/";
                    String classEntryName = className.replace('.', '/') + ".class";
                    URL[] var3 = LaunchedURLClassLoader.this.getURLs();
                    int var4 = var3.length;

                    for(int var5 = 0; var5 < var4; ++var5) {
                        URL url = var3[var5];

                        try {
                            URLConnection connection = url.openConnection();
                            if (connection instanceof JarURLConnection) {
                                JarFile jarFile = ((JarURLConnection)connection).getJarFile();
                                if (jarFile.getEntry(classEntryName) != null && jarFile.getEntry(packageEntryName) != null && jarFile.getManifest() != null) {
                                    LaunchedURLClassLoader.this.definePackage(packageName, jarFile.getManifest(), url);
                                    return null;
                                }
                            }
                        } catch (IOException var9) {
                        }
                    }

                    return null;
                }
            }, AccessController.getContext());
        } catch (PrivilegedActionException var4) {
        }

    }

    public void clearCache() {
        URL[] var1 = this.getURLs();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            URL url = var1[var3];

            try {
                URLConnection connection = url.openConnection();
                if (connection instanceof JarURLConnection) {
                    this.clearCache(connection);
                }
            } catch (IOException var6) {
            }
        }

    }

    private void clearCache(URLConnection connection) throws IOException {
        Object jarFile = ((JarURLConnection)connection).getJarFile();
        if (jarFile instanceof org.springframework.boot.loader.jar.JarFile) {
            ((org.springframework.boot.loader.jar.JarFile)jarFile).clearCache();
        }

    }

    @UsesJava7
    private static void performParallelCapableRegistration() {
        try {
            ClassLoader.registerAsParallelCapable();
        } catch (NoSuchMethodError var1) {
        }

    }

    static {
        performParallelCapableRegistration();
    }
}
