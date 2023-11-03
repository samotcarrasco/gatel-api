package es.mdef.apigatel.security;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebFilter("/filter-response-header/*")
public class AddResponseHeaderFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

		String token = httpServletRequest.getHeader("Authorization");

		if (token != null) {
			if (token.startsWith("Bearer ")) {
				httpServletResponse.setHeader("Authorization", JwtTokenService.updateExpirationDateToken(token));
		        httpServletResponse.setHeader("Access-control-expose-headers", "Authorization");
			}
		}

        chain.doFilter(request, response);
    }

}