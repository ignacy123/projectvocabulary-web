//package com.github.ignacy123.projectvocabulary.web.interceptor;
//
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * Created by ignacy on 14.07.17.
// */
//public class CorsInterceptor extends HandlerInterceptorAdapter {
//
//    public boolean preHandle(HttpServletRequest request,
//                             HttpServletResponse response, Object handler)
//            throws Exception {
//
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
//
//        return true;
//
//    }
//}
