package com.zl.bootexplore;

import com.zl.bootexplore.archive.Archive;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;

public abstract class ExecutableArchiveLauncher extends Launcher {
    private final Archive archive;

    public ExecutableArchiveLauncher() {
        try {
            this.archive = this.createArchive();
        } catch (Exception var2) {
            throw new IllegalStateException(var2);
        }
    }

    protected ExecutableArchiveLauncher(Archive archive) {
        this.archive = archive;
    }

    protected final Archive getArchive() {
        return this.archive;
    }

    @Override
    protected String getMainClass() throws Exception {
        Manifest manifest = this.archive.getManifest();
        String mainClass = null;
        if (manifest != null) {
            mainClass = manifest.getMainAttributes().getValue("Start-Class");
        }

        if (mainClass == null) {
            throw new IllegalStateException("No 'Start-Class' manifest entry specified in " + this);
        } else {
            return mainClass;
        }
    }
    @Override
    protected List<Archive> getClassPathArchives() throws Exception {
        List<Archive> archives = new ArrayList(this.archive.getNestedArchives(new com.zl.bootexplore.archive.Archive.EntryFilter() {
            @Override
            public boolean matches(com.zl.bootexplore.archive.Archive.Entry entry) {

                return ExecutableArchiveLauncher.this.isNestedArchive(entry);
            }
        }));
        this.postProcessClassPathArchives(archives);
        return archives;
    }

    protected abstract boolean isNestedArchive(Archive.Entry var1);

    protected void postProcessClassPathArchives(List<Archive> archives) throws Exception {
    }
}
