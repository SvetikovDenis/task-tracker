package com.denis.svetikov.tasktracker.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * JwtAuthEntryPoint class that gives custom unauthorized rest json response instead of Spring Security default html login file
 *
 * @author Denis Svetikov
 * @version v2
 */

public class JwtAuthEntryPoint implements AuthenticationEntryPoint {


    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ServletServerHttpResponse res = new ServletServerHttpResponse(response);
        Map<String, Object> errorResponse = buildUnauthorizedExceptionResponse(e, HttpStatus.UNAUTHORIZED);
        res.setStatusCode(HttpStatus.UNAUTHORIZED);
        res.getServletResponse().setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        res.getBody().write(mapper.writeValueAsString(errorResponse).getBytes());
    }

    private Map<String, Object> buildUnauthorizedExceptionResponse(Exception e, HttpStatus status) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("status", status.getReasonPhrase());
        data.put("timestamp", LocalDateTime.now().format(dateTimeFormatter));
        data.put("exception", e.getMessage());
        return data;

    }



}