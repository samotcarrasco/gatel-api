package es.mdef.apigatel.security;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String token = request.getHeader("Authorization");

		if (token == null || !token.startsWith("Bearer ")) {
			return true;
		}

		String newToken = JwtTokenService.updateExpirationDateToken(token);
		response.setHeader("Authorization", newToken);
		response.setHeader("Access-control-expose-headers", "Authorization");

		System.out.println("Cabecera interceptada");

		return true;

	}
}