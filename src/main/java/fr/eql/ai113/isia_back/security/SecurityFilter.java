package fr.eql.ai113.isia_back.security;

import fr.eql.ai113.isia_back.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityFilter extends OncePerRequestFilter {

    private static final Logger logger = LogManager.getLogger();

    /** Injecté par le setter */
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = extractTokenFromHeader(request);
            UserDetails user = userService.getUserFromJwt(token);
            setPrincipalInSecurityContext(user);
        } catch (Exception e) {
            logger.info("Impossible de trouver le token", e);
        }
        filterChain.doFilter(request, response);
    }

    private String extractTokenFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private void setPrincipalInSecurityContext(UserDetails user) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    /// Setters ///
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
