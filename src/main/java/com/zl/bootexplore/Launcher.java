package com.zl.bootexplore;

import com.zl.bootexplore.archive.Archive;
import com.zl.bootexplore.archive.ExplodedArchive;
import com.zl.bootexplore.archive.JarFileArchive;
import com.zl.bootexplore.jar.JarFile;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Launcher {
    public Launcher() {
    }

    protected void launch(String[] args) throws Exception {
        JarFile.registerUrlProtocolHandler();
        ClassLoader classLoader = this.createClassLoader(this.getClassPathArchives());
        this.launch(args, this.getMainClass(), classLoader);
    }

    protected ClassLoader createClassLoader(List<Archive> archives) throws Exception {
        List<URL> urls = new ArrayList(archives.size());
        Iterator var3 = archives.iterator();

        while(var3.hasNext()) {
            Archive archive = (Archive)var3.next();
            urls.add(archive.getUrl());
        }

        return this.createClassLoader((URL[])urls.toArray(new URL[urls.size()]));
    }

    protected ClassLoader createClassLoader(URL[] urls) throws Exception {
        return new LaunchedURLClassLoader(urls, this.getClass().getClassLoader());
    }

    protected void launch(String[] args, String mainClass, ClassLoader classLoader) throws Exception {
        Thread.currentThread().setContextClassLoader(classLoader);
        this.createMainMethodRunner(mainClass, args, classLoader).run();
    }

    protected MainMethodRunner createMainMethodRunner(String mainClass, String[] args, ClassLoader classLoader) {
        return new MainMethodRunner(mainClass, args);
    }

    protected abstract String getMainClass() throws Exception;

    protected abstract List<Archive> getClassPathArchives() throws Exception;

    protected final Archive createArchive() throws Exception {
        ProtectionDomain protectionDomain = this.getClass().getProtectionDomain();
        CodeSource codeSource = protectionDomain.getCodeSource();
        URI location = codeSource == null ? null : codeSource.getLocation().toURI();
        String path = location == null ? null : location.getSchemeSpecificPart();
        if (path == null) {
            throw new IllegalStateException("Unable to determine code source archive");
        } else {
            File root = new File(path);
            if (!root.exists()) {
                throw new IllegalStateException("Unable to determine code source archive from " + root);
            } else {
                return (Archive)(root.isDirectory() ? new ExplodedArchive(root) : new JarFileArchive(root));
            }
        }
    }
}
