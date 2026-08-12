package com.talentgrid.app.auth;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
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
import com.talentgrid.app.entity.TalentgridUser;
import com.talentgrid.app.repository.RoleRepository;
import com.talentgrid.app.repository.TalentgridUserRepository;
import com.talentgrid.app.security.util.JwtUtil;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final TalentgridUserRepository talentGridUserRepository;
    private final RoleRepository roleRepository;
    private final CompromisedPasswordChecker compromisedPasswordChecker;


    @PostMapping(value="/login/public", version="1.0")
    public ResponseEntity<LoginResponseDto> apiLogin(@RequestBody LoginRequestDto loginRequestDto){
       
        try{
            var resultAuthentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.username(), loginRequestDto.password()));

            String jwtToken = jwtUtil.generateJwtToken(resultAuthentication);

            var userDto = new UserDto();

            var loggedInUser = (TalentgridUser) resultAuthentication.getPrincipal();
            BeanUtils.copyProperties(loggedInUser, userDto);
            userDto.setRole(loggedInUser.getRole().getName());
            userDto.setUserId(loggedInUser.getId());

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

      

        TalentgridUser talentGridUser = new TalentgridUser();
        
        BeanUtils.copyProperties(registerRequestDto, talentGridUser);
        
        talentGridUser.setPasswordHash(passwordEncoder.encode(registerRequestDto.password()));

        Role role = roleRepository.findRoleByName(ApplicationConstants.ROLE_JOB_SEEKER).orElseThrow(() -> new IllegalArgumentException("Role not found: " + ApplicationConstants.ROLE_JOB_SEEKER));

        talentGridUser.setRole(role);

        talentGridUserRepository.save(talentGridUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

}
