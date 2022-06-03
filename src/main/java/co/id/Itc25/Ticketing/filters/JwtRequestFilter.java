package co.id.Itc25.Ticketing.filters;

import co.id.Itc25.Ticketing.utilities.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private UserDetailsService userDetailsService;
    private JwtUtil jwtUtil;

    @Autowired
    public JwtRequestFilter(UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        //memeriksa jika header adalah JWT
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            //jwt akan di ambil, tidak termasuk "Bearer "
            jwt = authorizationHeader.substring(7);
            //mengambil username dari JWT
            username = jwtUtil.extractUsername(jwt);
        }

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        //jika usernamenya tidak kosong, namun otentikasinya null
        if (username != null && authentication == null) {

            //ambil dari username-nya yang ada di token
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            //jika token valid sesuai dengan userdetails yang kita miliki
            if (jwtUtil.validateToken(jwt, userDetails)) {

                //biasanya di bikinin secara otomatis dari si Spring Securitynya kalo kita basic auth
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //set otentikasinya dengan yang baru.
                securityContext.setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        //mirip kaya save kalo di repository
        chain.doFilter(request, response);
    }
}
