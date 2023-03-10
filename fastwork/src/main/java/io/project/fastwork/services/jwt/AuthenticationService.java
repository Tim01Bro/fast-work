package io.project.fastwork.services.jwt;

import io.project.fastwork.domains.Users;
import io.project.fastwork.dto.request.AuthenticationRequest;
import io.project.fastwork.dto.response.AuthenticationResponse;
import io.project.fastwork.dto.request.RegistrationRequest;
import io.project.fastwork.services.api.UserServiceApi;
import io.project.fastwork.services.exception.UserAlreadyExisted;
import io.project.fastwork.services.exception.UserInvalidDataParemeter;
import io.project.fastwork.services.exception.UserNotFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserServiceApi userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse registrationUser(RegistrationRequest registrationRequest){
        var user_save = Users.builder()
                .username(registrationRequest.getUserlogin())
                .userOriginalName(registrationRequest.getUsername())
                .userSoname(registrationRequest.getUsersoname())
                .userPassword(registrationRequest.getUserpassword())
                .userEmail(registrationRequest.getUseremail())
                .build();
        try {
            userService.saveUser(user_save);
        } catch (UserAlreadyExisted | UserInvalidDataParemeter e) {
            log.error("User cannot save, throw exception, in {}",new Date());
            e.printStackTrace();
        }
        var jwtToken = jwtService.generateToken(user_save);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws UserNotFound, UserInvalidDataParemeter {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUserlogin(),
                        authenticationRequest.getPassword()
                )
        );
        var user = userService.findByLogin(authenticationRequest.getUserlogin());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
