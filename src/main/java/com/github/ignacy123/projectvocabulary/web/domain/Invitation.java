package com.github.ignacy123.projectvocabulary.web.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by ignacy on 19.10.16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Invitation {
    @XmlAttribute
    private String uid;
    @XmlAttribute
    private String name;
    @XmlAttribute
    private String email;
    @XmlAttribute
    private Long groupId;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
