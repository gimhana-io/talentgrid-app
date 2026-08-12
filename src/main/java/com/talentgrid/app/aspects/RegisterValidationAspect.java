package com.talentgrid.app.aspects;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.authentication.password.CompromisedPasswordDecision;
import org.springframework.stereotype.Component;

import com.talentgrid.app.dto.RegisterRequestDto;
import com.talentgrid.app.entity.TalentgridUser;
import com.talentgrid.app.exception.RegistrationValidationException;
import com.talentgrid.app.repository.TalentgridUserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class RegisterValidationAspect {

    private final CompromisedPasswordChecker compromisedPasswordChecker;
    private final TalentgridUserRepository talentgridUserRepository;

    @Before("""
        execution(* com.talentgrid.app.auth.AuthController
        .registerUser(..))
        """)
    public void validateBeforeRegister(JoinPoint joinPoint) {

        Object[] args = joinPoint.getArgs();

        RegisterRequestDto request = (RegisterRequestDto) args[0];

        log.info("🔍 Validating user registration request");

        Map<String, String> errors = new HashMap<>();
   
        CompromisedPasswordDecision decision =
                compromisedPasswordChecker.check(request.password());
        if (decision.isCompromised()) {
            errors.put("password", "Choose a strong password");
        }

        Optional<TalentgridUser> existingUser =
                talentgridUserRepository.readUserByEmailOrMobileNumber(
                        request.email(), request.mobileNumber());

        if (existingUser.isPresent()) {
            TalentgridUser user = existingUser.get();

            if (user.getEmail().equalsIgnoreCase(request.email())) {
                errors.put("email", "Email is already registered");
            }

            if (user.getMobileNumber().equals(request.mobileNumber())) {
                errors.put("mobileNumber", "Mobile number is already registered");
            }
        }

        if (!errors.isEmpty()) {
            log.warn("❌ Registration validation failed: {}", errors);
            throw new RegistrationValidationException(errors);
        }

        log.info("✅ Registration validation passed");
    }

}
