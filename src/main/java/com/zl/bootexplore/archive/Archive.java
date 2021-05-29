package com.zl.bootexplore.archive;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.jar.Manifest;

public interface Archive extends Iterable<Archive.Entry> {
    URL getUrl() throws MalformedURLException;

    Manifest getManifest() throws IOException;

    List<Archive> getNestedArchives(Archive.EntryFilter var1) throws IOException;

    public interface EntryFilter {
        boolean matches(Archive.Entry var1);
    }

    public interface Entry {
        boolean isDirectory();

        String getName();
    }
}