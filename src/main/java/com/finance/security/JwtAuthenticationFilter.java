package com.finance.security;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
 private final JwtService jwtService;
 public JwtAuthenticationFilter(JwtService jwtService){this.jwtService=jwtService;}
 @Override protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain)throws ServletException,IOException{
  String h=req.getHeader("Authorization");
  if(h!=null&&h.startsWith("Bearer ")){String token=h.substring(7);if(jwtService.isValid(token)){String email=jwtService.extractEmail(token);SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(email,null,AuthorityUtils.NO_AUTHORITIES));}}
  chain.doFilter(req,res);
 }
}