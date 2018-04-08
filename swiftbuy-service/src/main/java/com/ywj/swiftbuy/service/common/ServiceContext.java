package com.ywj.swiftbuy.service.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public final class ServiceContext {

    private static final ThreadLocal<ServiceContext> WEB_CONTEXT_THREAD_LOCAL = new ThreadLocal<>();

    private String requestScheme;
    private String requestHost;
    private String requestPath;
    private String requestIp;
    private String kyCookie;

    public static ServiceContext current() {
        ServiceContext serviceContext = WEB_CONTEXT_THREAD_LOCAL.get();
        return serviceContext != null ? serviceContext : getDefault();
    }

    //TODO 修改  ip是动态的随时能修改   127.0.0.1
    public static ServiceContext getDefault() {
        return new ServiceContext("http", "localhost:8081", "api", "192.168.0.18", "");
    }

    public static void setCurrent(ServiceContext serviceContext) {
        WEB_CONTEXT_THREAD_LOCAL.set(serviceContext);
    }

    public static void reset() {
        WEB_CONTEXT_THREAD_LOCAL.remove();
    }

}
