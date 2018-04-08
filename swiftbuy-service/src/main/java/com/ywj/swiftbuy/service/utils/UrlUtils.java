package com.ywj.swiftbuy.service.utils;

import com.ywj.swiftbuy.service.common.ServiceContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriUtils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author haoli <lihao@wandoujia.com>
 */
public class UrlUtils {

    private static final Logger LOG = LoggerFactory.getLogger(UrlUtils.class);
    private static final String UTF_8 = "UTF-8";

    private static final Pattern PATTERN_ILLEGAL_CHARATER = Pattern.compile("[\"\'%<>\\[\\]}{]\\+;");

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

    public static String encode(String url) {
        try {
            return UriUtils.encodeQuery(url, UTF_8);
        } catch (UnsupportedEncodingException e) {
            LOG.warn("", e);
            return url;
        }
    }

    public static String encodeAll(String path) {
        try {
            return URLEncoder.encode(path, UTF_8);
        } catch (UnsupportedEncodingException e) {
            LOG.warn("", e);
            return path;
        }
    }

    public static String parseParameter(String url, String name) {
        try {
            List<NameValuePair> params = URLEncodedUtils.parse(new URI(url), UTF_8);
            for (NameValuePair param : params) {
                if (name.equals(param.getName())) {
                    return param.getValue();
                }
            }
        } catch (URISyntaxException e) {
            LOG.warn("", e);
        }
        return null;
    }

    public static String getPrefix(String url) {
        try {
            URI uri = new URI(url);
            return uri.getScheme() + "://" + uri.getHost() + uri.getPath();
        } catch (URISyntaxException e) {
            LOG.warn("", e);
        }
        return StringUtils.EMPTY;
    }

    public static boolean validateUrl(String url) {
        Matcher m = PATTERN_ILLEGAL_CHARATER.matcher(url);
        return !m.find();
    }

//    public static String generateUrl(String path) {
//        return generateUrl(path, new ArrayList<>());
//    }

//    public static String generateUrl(String path, List<Parameter> parameterList) {
//        URIBuilder uriBuilder = new URIBuilder()
//                .setScheme(ServiceContext.current().getRequestScheme())
//                .setHost(ServiceContext.current().getRequestHost())
//                .setPath(path);
//        for (Parameter parameter : parameterList) {
//            uriBuilder.addParameter(parameter.getName(), parameter.getValue());
//        }
//        try {
//            return uriBuilder.build().toString();
//        } catch (URISyntaxException e) {
//            LOG.warn("Generate Url Error", e);
//            return null;
//        }
//    }

    public static String generateUrl(String path, List<Parameter> parameterList,String host) {
        URIBuilder uriBuilder = new URIBuilder()
                .setScheme(ServiceContext.current().getRequestScheme())
                .setHost(host)
                .setPath(path);
        for (Parameter parameter : parameterList) {
            uriBuilder.addParameter(parameter.getName(), parameter.getValue());
        }
        try {
            return uriBuilder.build().toString();
        } catch (URISyntaxException e) {
            LOG.warn("Generate Url Error", e);
            return null;
        }
    }

    public static boolean isURL(String url){
        return url.matches("^((https|http|ftp|rtsp|mms)?://)"
                + "+(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?"
                + "(([0-9]{1,3}\\.){3}[0-9]{1,3}"
                + "|"
                + "([0-9a-z_!~*'()-]+\\.)*"
                + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\."
                + "[a-z]{2,6})"
                + "(:[0-9]{1,4})?"
                + "((/?)|"
                + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$");
    }

    public static void main(String[] args) {
        System.out.println(isURL("http://www.baidu.com"));
        System.out.println(getPrefix("http://www.kaiyanapp.com/campaign/business_upload.html?cookie=cookie&shareable=true&mz_ca=2070101&mz_sp=7CSaE"));
    }
}
