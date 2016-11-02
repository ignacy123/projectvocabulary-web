package com.github.ignacy123.projectvocabulary.web.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ignacy on 31.08.16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Group {
    @XmlAttribute
    private Long teacherId;
    @XmlAttribute
    private String name;
    private List<Long> studentIds = new ArrayList<>();
    @XmlAttribute
    private Long id;

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public List<Long> getStudentIds() {
        return studentIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
