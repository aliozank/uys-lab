package com.alikaracor.learning.springsecuritybasicslab.service;

import com.alikaracor.learning.springsecuritybasicslab.dto.LoginRequest;
import com.alikaracor.learning.springsecuritybasicslab.dto.LoginResponse;
import com.alikaracor.learning.springsecuritybasicslab.dto.RegisterRequest;
import com.alikaracor.learning.springsecuritybasicslab.model.ActivityType;
import com.alikaracor.learning.springsecuritybasicslab.model.AppUser;
import com.alikaracor.learning.springsecuritybasicslab.model.Role;
import com.alikaracor.learning.springsecuritybasicslab.repository.AppUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;
    private final ActivityLogService activityLogService;

    public AuthService(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            AppUserRepository appUserRepository,
            PasswordEncoder passwordEncoder,
            ActivityLogService activityLogService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.activityLogService = activityLogService;
    }

    public String register(RegisterRequest registerRequest) {

        boolean usernameExists =
                appUserRepository.existsByUsername(
                        registerRequest.getUsername()
                );

        if (usernameExists) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Bu kullanıcı adı zaten kullanılıyor"
            );
        }

        String encodedPassword =
                passwordEncoder.encode(
                        registerRequest.getPassword()
                );

        AppUser appUser = new AppUser(
                registerRequest.getUsername(),
                encodedPassword,
                Role.USER,
                true
        );

        appUserRepository.save(appUser);

        activityLogService.log(
                appUser.getUsername(),
                ActivityType.REGISTER
        );

        return "Kullanıcı başarıyla kaydedildi";
    }

    public LoginResponse login(LoginRequest loginRequest) {

        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    loginRequest.getUsername(),
                                    loginRequest.getPassword()
                            )
                    );

            UserDetails userDetails =
                    (UserDetails) authentication.getPrincipal();

            String token =
                    jwtService.generateToken(userDetails);

            activityLogService.log(
                    userDetails.getUsername(),
                    ActivityType.LOGIN_SUCCESS
            );

            return new LoginResponse(token);

        } catch (AuthenticationException exception) {

            activityLogService.log(
                    loginRequest.getUsername(),
                    ActivityType.LOGIN_FAILED
            );

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Kullanıcı adı veya şifre hatalı"
            );
        }
    }
}