package com.sg.cristina.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: jiangwei
 * @Date: 2019-05-24
 * @Desc:
 */
public class CasPrincipalUser implements Serializable {

    private String id ;

    /**
     * 需要返回实现org.apereo.cas.authentication.principal.Principal的类名接口
     */
    @JsonProperty("@class")
    private String clazz = "org.apereo.cas.authentication.principal.SimplePrincipal";


    @JsonProperty("attributes")
    private Map<String, Object> attributes = new HashMap<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public CasPrincipalUser(com.sg.SgUser user) {
        this.id = user.getUsername();
    }
}
