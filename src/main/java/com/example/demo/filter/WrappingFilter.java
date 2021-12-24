package com.example.demo.filter;

import com.example.demo.dto.Response;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class WrappingFilter extends OncePerRequestFilter {
    @Autowired private Gson gson;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper req = null;
        ContentCachingResponseWrapper res = null;

        log.debug("request path : {}", request.getRequestURI());

//        if(request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)){
//            req = new ContentCachingRequestWrapper(request);
//            res = new ContentCachingResponseWrapper(response);
//        }
//
//        // 사이즈 설정
//        if(request.getContentLength() > 100000000){
//            returnResponseError(HttpStatus.INTERNAL_SERVER_ERROR, response, new RuntimeException("에러"));
//            return;
//        }
//
        filterChain.doFilter(req!=null?req:request, res!=null?res:response);
//
//        if(res!=null){
//            res.copyBodyToResponse();
//        }
    }

    /**
     * filter  단계에서 에러 결과값 처리 하기 위함
     */
    public void returnResponseError(HttpStatus httpStatus, HttpServletResponse response, Throwable ex) throws IOException {
        response.setStatus(httpStatus.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        
        String code = "";
        String message = "";
        
        // RuntimeException을 커스텀 이셉션으로 변경해야함
        if(ex instanceof RuntimeException){
            code = "커스텀 에러코드";
            message = ex.getMessage();
        }

        Response res = Response.builder().stateCode(Integer.parseInt(code)).message(message).build();
        
        response.getWriter().write(gson.toJson(res));
    }
}
