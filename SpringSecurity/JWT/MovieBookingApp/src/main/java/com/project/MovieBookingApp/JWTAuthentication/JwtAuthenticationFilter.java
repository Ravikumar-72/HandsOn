package com.project.MovieBookingApp.JWTAuthentication;

import com.project.MovieBookingApp.Repository.UserRepo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String username;
        final String jwtToken;

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        //Extract JWT token from the header
        jwtToken = authHeader.substring(7);
        username = jwtService.extractUsername(jwtToken);

        //check if we have a username and no authentication exists
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            var userDetails = userRepo.findByUsername(username).orElseThrow(()-> new RuntimeException("User not found!"));

            //validate token
            if(jwtService.isTokenValid(jwtToken, userDetails)){

                //create the authentication with user roles
                List<SimpleGrantedAuthority> authorities = userDetails.getRoles()
                        .stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,authorities);

                //set authentication details
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //update security
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
