package com.ywj.swiftbuy.service.utils;

import com.ywj.swiftbuy.constant.RequestParameter;
import com.ywj.swiftbuy.service.common.ServiceContext;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class NextPageUrlUtils {

    private static final Logger LOG = LoggerFactory.getLogger(NextPageUrlUtils.class);

    public static String nextPageUrl(List<NextPageUrlGenerator.Parameter> parameterList) {
        URIBuilder uriBuilder = new URIBuilder()
                .setScheme(ServiceContext.current().getRequestScheme())
                .setHost(ServiceContext.current().getRequestHost())
                .setPath(ServiceContext.current().getRequestPath());
        for (NextPageUrlGenerator.Parameter parameter : parameterList) {
            uriBuilder.addParameter(parameter.getName(), parameter.getValue());
        }

        try {
            return uriBuilder.build().toString();
        } catch (URISyntaxException e) {
            LOG.warn("Get next page url error!", e);
            return null;
        }
    }

    public static String nextPageUrl(List<NextPageUrlGenerator.Parameter> parameterList,String path,String host) {
        URIBuilder uriBuilder = new URIBuilder()
                .setScheme(ServiceContext.current().getRequestScheme())
                .setHost(host)
                .setPath(path);
        for (NextPageUrlGenerator.Parameter parameter : parameterList) {
            uriBuilder.addParameter(parameter.getName(), parameter.getValue());
        }

        try {
            return uriBuilder.build().toString();
        } catch (URISyntaxException e) {
            LOG.warn("Get next page url error!", e);
            return null;
        }
    }

    public static String getCommonNextPageUrl(int start, int num, int totalSize) {
        if (start + num >= totalSize) {
            return null;
        }
        List<NextPageUrlGenerator.Parameter> parameterList = new ArrayList<>();
        parameterList.add(new NextPageUrlGenerator.Parameter(RequestParameter.START, String.valueOf(start + num)));
        parameterList.add(new NextPageUrlGenerator.Parameter(RequestParameter.NUM, String.valueOf(num)));
        return nextPageUrl(parameterList);
    }
}
