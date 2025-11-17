package study.LoginWithJWT.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import study.LoginWithJWT.domain.user.entity.User;
import study.LoginWithJWT.domain.user.repository.UserRepository;
import study.LoginWithJWT.global.apiPayload.code.ErrorCode;
import study.LoginWithJWT.global.apiPayload.exception.AuthException;
import study.LoginWithJWT.global.apiPayload.response.Response;

import java.io.IOException;
import java.util.List;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    private static final List<String> EXCLUDED_URLS = List.of(
            "/swagger-ui", "/v3/api-docs"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 요청 URL이 인증 예외 경로인지 확인
        String requestURI = request.getRequestURI();

        // 예외 경로라면 통과시킴
        if (isExCluded(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 헤더에서 jwt 추출
            String token= extractToken(request);

            // 토큰이 존재하는지, 유효한지 검사
            if (token != null&& jwtTokenProvider.validateToken(token)) {
                // 토큰에서 이메일 정보 추출
                String email = jwtTokenProvider.extractEmail(token);

                // db에서 사용자 정보 확인
                User user = userRepository.findByEmail(email)
                        .orElseThrow(()-> new AuthException(ErrorCode.USER_NOT_FOUND));

                // 사용자의 권한 목록 생성
                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

                // 인증된 사용자의 정보를 담은 Authentication 객체 생성
                JwtAuthenticationToken authentication = new JwtAuthenticationToken(user.getEmail(), authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (AuthException e) {
            log.warn("[401] jwt 필터 인증 실패",e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json; charset=utf-8");

            Response<Void> errorResponse = e.toResponse();
            String json=new ObjectMapper().writeValueAsString(errorResponse);
            response.getWriter().write(json);
        }
    }

    private boolean isExCluded(String requestURI) {
        return EXCLUDED_URLS.stream().anyMatch(requestURI::startsWith);
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null; // 헤더에 토큰이 없으면 null 반환
    }


}
