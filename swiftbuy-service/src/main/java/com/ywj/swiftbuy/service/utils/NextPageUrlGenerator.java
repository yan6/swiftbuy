package com.ywj.swiftbuy.service.utils;

import com.ywj.swiftbuy.constant.RequestParameter;
import com.ywj.swiftbuy.service.common.ServiceContext;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public abstract class NextPageUrlGenerator {

    public static class Parameter {
        private String name;
        private String value;

        public Parameter(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    private static final Logger LOG = LoggerFactory.getLogger(NextPageUrlGenerator.class);
    private int start;
    private int num;
    private List<Parameter> parameterList = new ArrayList<>();

    public NextPageUrlGenerator(int start, int num, String name, String value) {
        this.start = start;
        this.num = num;
        addParameter(name, value);
    }

    public NextPageUrlGenerator(int start, int num, List<Parameter> parameterList) {
        this.start = start;
        this.num = num;
        this.parameterList = parameterList;
    }

    public abstract boolean nextPageExists();

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNextStart() {
        return start + num;
    }

    public void addParameter(String name, String value) {
        this.parameterList.add(new Parameter(name, value));
    }

    public String nextPageUrl() {
        return nextPageUrl(ServiceContext.current().getRequestPath());
    }

    public String nextPageUrl(String path) {
        if (!nextPageExists()) {
            return null;
        }

        URIBuilder uriBuilder = new URIBuilder()
                .setScheme(ServiceContext.current().getRequestScheme())
                .setHost(ServiceContext.current().getRequestHost())
                .setPath(path)
                .addParameter(RequestParameter.START, Integer.toString(getNextStart()))
                .addParameter(RequestParameter.NUM, Integer.toString(num));
        for (Parameter parameter : parameterList) {
            uriBuilder.addParameter(parameter.getName(), parameter.getValue());
        }

        try {
            return uriBuilder.build().toString();
        } catch (URISyntaxException e) {
            LOG.warn("Get next page url error!", e);
            return null;
        }
    }
}
