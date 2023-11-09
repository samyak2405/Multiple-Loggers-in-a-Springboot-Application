package com.javahunter.multipleloggers.filter;


import java.io.IOException;
import java.io.UnsupportedEncodingException;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger serverLogger = LoggerFactory.getLogger(LoggingFilter.class);
    private static final Logger accessLogger = LoggerFactory.getLogger("access-log");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();
        filterChain.doFilter(requestWrapper, responseWrapper);
        long timeTaken = System.currentTimeMillis() - startTime;

        String requestBody = getStringValue(requestWrapper.getContentAsByteArray(),
                request.getCharacterEncoding());

        accessLogger.info("Request Method: {},Time taken in ms: {}, User email: {},"
                ,request.getMethod(),timeTaken,fieldValue("email",requestBody));

        responseWrapper.copyBodyToResponse();
    }
    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) {
        try {
            return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);
        } catch (UnsupportedEncodingException e) {
            serverLogger.info("Character Encoding not supported");
        }
        return "";
    }

    private String fieldValue(String field, String body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(body);
        String result = "";
        if(jsonNode.get(field)!=null){
            result = jsonNode.get(field).asText();
        }
        return result;
    }

}

