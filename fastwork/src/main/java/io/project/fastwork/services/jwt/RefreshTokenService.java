package io.project.fastwork.services.jwt;

import io.project.fastwork.controller.advice.exception.TokenRefreshException;
import io.project.fastwork.domains.RefreshToken;
import io.project.fastwork.repositories.RefreshTokenRepository;
import io.project.fastwork.services.api.RefreshTokenServiceApi;
import io.project.fastwork.services.api.UserServiceApi;
import io.project.fastwork.services.exception.UserInvalidDataParemeter;
import io.project.fastwork.services.exception.UserNotFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class RefreshTokenService implements RefreshTokenServiceApi {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserServiceApi userService;
    @Value("${fastwork.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Transactional
    @Override
    public RefreshToken createRefreshToken(String username){
        RefreshToken refreshToken = null;
        try {
            refreshToken = RefreshToken.builder()
                    .user(userService.findByLogin(username))
                    .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs*24))
                    .token(UUID.randomUUID().toString())
                    .build();
        } catch (UserNotFound e) {
            log.error("User with username {} not found in {}",username,new Date());
            e.printStackTrace();
        } catch (UserInvalidDataParemeter e) {
             e.printStackTrace();
        }
        log.info("Get refresh token for user with username {} in {}",username,new Date());
        return refreshTokenRepository.save(Objects.requireNonNull(refreshToken));
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken refreshTokenExpiry) throws TokenRefreshException {
        if (refreshTokenExpiry.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshTokenExpiry);
            throw new TokenRefreshException(refreshTokenExpiry.getToken(),"Refresh token was expired. Please make a new sign in request");
        }

        return refreshTokenExpiry;
    }

    @Transactional
    @Override
    public void deleteTokenByUserId(String username){
        try {
            refreshTokenRepository.deleteByUser(userService.findByLogin(username));
        } catch (UserNotFound e) {
            log.error("User with id {} not found, throw exception in {}",username,new Date());
            e.printStackTrace();
        } catch (UserInvalidDataParemeter e) {
            throw new RuntimeException(e);
        }
    }
}
