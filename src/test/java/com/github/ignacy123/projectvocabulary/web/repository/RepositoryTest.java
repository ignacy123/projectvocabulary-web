package com.github.ignacy123.projectvocabulary.web.repository;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import static org.junit.Assert.assertThat;
import static org.xmlunit.matchers.CompareMatcher.isIdenticalTo;

/**
 * Created by ignacy on 26.10.16.
 */
public abstract class RepositoryTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    protected File repositoryFile;


    protected abstract void createRepository();

    protected void prepareRepository(String resourcePath) throws IOException {
        repositoryFile = temporaryFolder.newFile();
        InputStream input = getClass().getResourceAsStream(resourcePath);
        String inputContent = IOUtils.toString(input, "UTF-8");
        FileUtils.writeStringToFile(repositoryFile, inputContent, "UTF-8");
        createRepository();
    }

    protected void assertRepositoryXml(String expectedXml) throws IOException {
        String repositoryXml = FileUtils.readFileToString(repositoryFile, "UTF-8");
        assertThat(repositoryXml, isIdenticalTo(expectedXml).ignoreWhitespace());
    }

    protected void assertRepositoryXmlWithResource(String resourcePath) throws IOException {
        String expectedXml = IOUtils.toString(getClass().getResource(resourcePath), "UTF-8");
        assertRepositoryXml(expectedXml);
    }

    protected void assertRepositoryXml(String expectedXml, Set<String> ignoredAttrs) throws IOException {
        String repositoryXml = FileUtils.readFileToString(repositoryFile, "UTF-8");
        assertThat(repositoryXml, isIdenticalTo(expectedXml)
                .ignoreWhitespace()
                .withAttributeFilter(attr -> !ignoredAttrs.contains(attr.getName())));
    }

    protected void assertRepositoryXmlWithResource(String resourcePath, Set<String> ignoredAttrs) throws IOException {
        String expectedXml = IOUtils.toString(getClass().getResource(resourcePath), "UTF-8");
        assertRepositoryXml(expectedXml, ignoredAttrs);
    }

}
