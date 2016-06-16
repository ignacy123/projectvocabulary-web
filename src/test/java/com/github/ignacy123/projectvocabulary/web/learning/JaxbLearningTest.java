package com.github.ignacy123.projectvocabulary.web.learning;

import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.xmlunit.matchers.CompareMatcher.isIdenticalTo;

public class JaxbLearningTest {

    @Test
    public void writeXml() throws Exception {
        TestEntity testEntity = new TestEntity();
        testEntity.setField("asdf");
        testEntity.setNumField(100);

        StringWriter stringWriter = new StringWriter();
        JAXBContext context = JAXBContext.newInstance(TestEntity.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(testEntity, stringWriter);

        String expectedXml = "<testEntity><field>asdf</field><numField>100</numField> </testEntity>";

        assertThat(stringWriter.toString(), isIdenticalTo(expectedXml).ignoreWhitespace());
    }

    @Test
    public void readXml() throws Exception {
        String xml = "<testEntity><field>asdf</field><numField>100</numField></testEntity>";
        StringReader stringReader = new StringReader(xml);
        JAXBContext context = JAXBContext.newInstance(TestEntity.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        TestEntity expectedEntity = (TestEntity) unmarshaller.unmarshal(stringReader);

        assertThat(expectedEntity.getField(), is("asdf"));
        assertThat(expectedEntity.getNumField(), is(100));
    }
}
