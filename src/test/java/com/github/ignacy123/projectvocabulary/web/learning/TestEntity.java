package com.github.ignacy123.projectvocabulary.web.learning;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TestEntity {

    private String field;
    private Integer numField;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Integer getNumField() {
        return numField;
    }

    public void setNumField(Integer numField) {
        this.numField = numField;
    }
}
