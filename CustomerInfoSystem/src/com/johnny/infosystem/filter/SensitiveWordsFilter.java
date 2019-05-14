package com.johnny.infosystem.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@WebFilter("/*")
public class SensitiveWordsFilter implements Filter {

    private List<String> words = new ArrayList<String>();

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        ServletRequest proxyRequest = (ServletRequest) Proxy.newProxyInstance(req.getClass().getClassLoader(), req.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                System.out.println(method.getName());
                if (method.getName().equals("getParameter")) {
                    String value = (String) method.invoke(req, args);
                    if (value != null && !"".equals(value)) {
                        for (String str : words) {
                            if (value.contains(str)){
                                value=value.replaceAll(str,"***");
                            }
                        }
                    }
                    return value;
                }
                return method.invoke(req, args);
            }
        });
        chain.doFilter(proxyRequest, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        ServletContext context = config.getServletContext();

        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("sensitive_words");
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                words.add(line);
            }
            br.close();
            System.out.println(words);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
