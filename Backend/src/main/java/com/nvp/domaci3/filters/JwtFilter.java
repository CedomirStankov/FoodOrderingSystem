package com.nvp.domaci3.filters;

import com.nvp.domaci3.model.User;
import com.nvp.domaci3.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.nvp.domaci3.utils.JwtUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public JwtFilter(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException, ServletException {

        String authHeader = httpServletRequest.getHeader("Authorization");
        String jwt = null;
        String username = null;

        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {

              Optional<User> user = userService.findByEmail(username);

              if (user.isPresent()) {
                
                List<GrantedAuthority> authorities = new ArrayList<>();
                if (user.get().getCan_read_users()) {
                  authorities.add(new SimpleGrantedAuthority("READ_USERS"));
                }
                if (user.get().getCan_create_users()) {
                  authorities.add(new SimpleGrantedAuthority("CREATE_USERS"));
                }
                if (user.get().getCan_update_users()) {
                  authorities.add(new SimpleGrantedAuthority("UPDATE_USERS"));
                }
                if (user.get().getCan_delete_users()) {
                  authorities.add(new SimpleGrantedAuthority("DELETE_USERS"));
                }
                if (user.get().getCan_search_order()) {
                  authorities.add(new SimpleGrantedAuthority("SEARCH_ORDER"));
                }
                if (user.get().getCan_place_order()) {
                  authorities.add(new SimpleGrantedAuthority("PLACE_ORDER"));
                }
                if (user.get().getCan_cancel_order()) {
                  authorities.add(new SimpleGrantedAuthority("CANCEL_ORDER"));
                }
                if (user.get().getCan_track_order()) {
                  authorities.add(new SimpleGrantedAuthority("TRACK_ORDER"));
                }
                if (user.get().getCan_schedule_order()) {
                  authorities.add(new SimpleGrantedAuthority("SCHEDULE_ORDER"));
                }


                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                  userDetails, null, authorities);
                usernamePasswordAuthenticationToken
                  .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
              }

//                if ("GET".equalsIgnoreCase(httpServletRequest.getMethod()) &&
//                        "/api/users/all".equals(httpServletRequest.getRequestURI())) {
//
//                    Optional<User> user = userService.findByEmail(username);
//
//                    if (user.isPresent() && !user.get().getCan_read_users()) {
//                        httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied: No read permissions.");
//                        return;
//                    }
//                }
//
//                if ("POST".equalsIgnoreCase(httpServletRequest.getMethod()) &&
//                        "/api/users/add".equals(httpServletRequest.getRequestURI())) {
//
//                    Optional<User> user = userService.findByEmail(username);
//
//                    if (user.isPresent() && !user.get().getCan_create_users()) {
//                        httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied: No create permissions.");
//                        return;
//                    }
//                }
//
//                if ("DELETE".equalsIgnoreCase(httpServletRequest.getMethod()) &&
//                        httpServletRequest.getRequestURI().startsWith("/api/users/remove/")
//                ) {
//
//                    Optional<User> user = userService.findByEmail(username);
//
//                    if (user.isPresent() && !user.get().getCan_delete_users()) {
//                        httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied: No delete permissions.");
//                        return;
//                    }
//                }
//
//                if ("PUT".equalsIgnoreCase(httpServletRequest.getMethod()) &&
//                        httpServletRequest.getRequestURI().startsWith("/api/users/update/")
//                ) {
//
//                    Optional<User> user = userService.findByEmail(username);
//
//                    if (user.isPresent() && !user.get().getCan_update_users()) {
//                        httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied: No update permissions.");
//                        return;
//                    }
//                }

            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
