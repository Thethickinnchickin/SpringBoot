package org.springboardLogin.Security;//package org.springboardLogin.Security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Component
//public class JwtTokenInterceptor implements HandlerInterceptor {
//
//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        // Get the JWT token from the request headers
//        String authToken = request.getHeader("Authorization");
//        System.out.println("Token " + authToken);
//        // Validate the token
//        if (jwtTokenProvider.validateToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtYXR0aGV3IiwiaWF0IjoxNzA1NTYxNjk5LCJleHAiOjE3MDU1NjUyOTl9.qVVSk_1t7Pvvwsw6BD0ioPAyXm-oc1aCXyY6ZycCKezbTAyGeFCKjpR--y9FmIJZVZeyCyBoMuVOVixzBKi2iQ")) {
//            // Token is valid, proceed with the request
//            return true;
//        } else {
//            // Invalid token, send unauthorized response
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
//            return false;
//        }
//    }
//}
