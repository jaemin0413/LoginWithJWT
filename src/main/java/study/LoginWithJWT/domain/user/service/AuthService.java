package study.LoginWithJWT.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.LoginWithJWT.domain.user.dto.request.LoginRequest;
import study.LoginWithJWT.domain.user.dto.request.RegisterRequest;
import study.LoginWithJWT.domain.user.dto.response.TokenResponse;
import study.LoginWithJWT.domain.user.entity.LoginType;
import study.LoginWithJWT.domain.user.entity.User;
import study.LoginWithJWT.domain.user.repository.UserRepository;
import study.LoginWithJWT.global.apiPayload.code.ErrorCode;
import study.LoginWithJWT.global.apiPayload.exception.AuthException;
import study.LoginWithJWT.global.security.jwt.JwtTokenProvider;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public TokenResponse register(RegisterRequest request) {
        // 이메일 중복 확인
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new AuthException(ErrorCode.DUPLICATE_EMAIL);
        }

        // 로그인 ID 중복 확인
        if (userRepository.findByLoginId(request.getLoginId()).isPresent()) {
            throw new AuthException(ErrorCode.DUPLICATE_LOGIN_ID);
        }

        // 사용자 생성
        User user = new User();
        user.setEmail(request.getEmail());
        user.setLoginId(request.getLoginId());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setLoginType(LoginType.LOCAL);

        userRepository.save(user);

        // 토큰 생성
        String accessToken = jwtTokenProvider.createdAccessToken(user.getEmail());
        String refreshToken = jwtTokenProvider.createdRefreshToken(user.getEmail());

        return new TokenResponse(accessToken, refreshToken, user.getEmail(), user.getName());
    }

    @Transactional
    public TokenResponse login(LoginRequest request) {
        // 사용자 조회
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AuthException(ErrorCode.USER_NOT_FOUND));

        // 비밀번호 확인
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthException(ErrorCode.INVALID_PASSWORD);
        }

        // 토큰 생성
        String accessToken = jwtTokenProvider.createdAccessToken(user.getEmail());
        String refreshToken = jwtTokenProvider.createdRefreshToken(user.getEmail());

        return new TokenResponse(accessToken, refreshToken, user.getEmail(), user.getName());
    }

    @Transactional
    public TokenResponse refresh(String refreshToken) {
        // 리프레시 토큰 검증
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new AuthException(ErrorCode.JWT_INVALID_TOKEN);
        }

        // 이메일 추출
        String email = jwtTokenProvider.extractEmail(refreshToken);

        // 사용자 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException(ErrorCode.USER_NOT_FOUND));

        // 새로운 토큰 생성
        String newAccessToken = jwtTokenProvider.createdAccessToken(user.getEmail());
        String newRefreshToken = jwtTokenProvider.createdRefreshToken(user.getEmail());

        return new TokenResponse(newAccessToken, newRefreshToken, user.getEmail(), user.getName());
    }
}
