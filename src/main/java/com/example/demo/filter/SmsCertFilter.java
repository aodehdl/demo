package com.example.demo.filter;

import com.example.demo.dto.SmsCertAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SmsCertFilter extends AbstractAuthenticationProcessingFilter {
    public SmsCertFilter(String filterUrl) {
        super(filterUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException{
        String certNum = req.getParameter("certNum");
        String phoneNum = req.getParameter("phoneNum");

        return getAuthenticationManager().authenticate(new SmsCertAuthenticationToken(phoneNum, certNum));
    }

}
