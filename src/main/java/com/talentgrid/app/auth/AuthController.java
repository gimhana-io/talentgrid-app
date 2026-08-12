package com.talentgrid.app.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.authentication.password.CompromisedPasswordDecision;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talentgrid.app.constants.ApplicationConstants;
import com.talentgrid.app.dto.LoginRequestDto;
import com.talentgrid.app.dto.LoginResponseDto;
import com.talentgrid.app.dto.RegisterRequestDto;
import com.talentgrid.app.dto.UserDto;
import com.talentgrid.app.entity.Role;
import com.talentgrid.app.entity.TalentGridUser;
import com.talentgrid.app.repository.RoleRepository;
import com.talentgrid.app.repository.TalentGridUserRepository;
import com.talentgrid.app.security.util.JwtUtil;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final TalentGridUserRepository talentGridUserRepository;
    private final RoleRepository roleRepository;
    private final CompromisedPasswordChecker compromisedPasswordChecker;


    @PostMapping(value="/login/public", version="1.0")
    public ResponseEntity<LoginResponseDto> apiLogin(@RequestBody LoginRequestDto loginRequestDto){
        // System.out.print(loginRequestDto.username()+loginRequestDto.password());
       
        try{
            var resultAuthentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.username(), loginRequestDto.password()));

            String jwtToken = jwtUtil.generateJwtToken(resultAuthentication);

            var userDto = new UserDto();

            return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDto(HttpStatus.OK.getReasonPhrase(), userDto, jwtToken));
       
        }catch(BadCredentialsException ex){
            return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }catch(AuthenticationException ex){
            return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Authentication failed");
        }catch(Exception ex){
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occured");
        }

    }


    private ResponseEntity<LoginResponseDto> buildErrorResponse(HttpStatus status, String message){

        return ResponseEntity.status(status).body(new LoginResponseDto(message, null, null));
    }


    @PostMapping(value="/register/public", version="1.0")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDto registerRequestDto){

        CompromisedPasswordDecision decision = compromisedPasswordChecker
                .check(registerRequestDto.password());
        if (decision.isCompromised()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("password", "Choose a strong password"));
        }


        Optional<TalentGridUser> existingTalentGridUser = talentGridUserRepository.readUserByEmailOrMobileNumber(registerRequestDto.email(), registerRequestDto.mobileNumber());

        if (existingTalentGridUser.isPresent()) {
            Map<String, String> errors = new HashMap<>();
            TalentGridUser jobPortalUser = existingTalentGridUser.get();
            if (jobPortalUser.getEmail().equalsIgnoreCase(registerRequestDto.email())) {
                errors.put("email", "Email is already registered");
            }
            if (jobPortalUser.getMobileNumber().equals(registerRequestDto.mobileNumber())) {
                errors.put("mobileNumber", "Mobile number is already registered");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        TalentGridUser talentGridUser = new TalentGridUser();
        
        BeanUtils.copyProperties(registerRequestDto, talentGridUser);
        
        talentGridUser.setPasswordHash(passwordEncoder.encode(registerRequestDto.password()));

        Role role = roleRepository.findRoleByName(ApplicationConstants.ROLE_JOB_SEEKER).orElseThrow(() -> new IllegalArgumentException("Role not found: " + ApplicationConstants.ROLE_JOB_SEEKER));

        talentGridUser.setRole(role);

        talentGridUserRepository.save(talentGridUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

}
