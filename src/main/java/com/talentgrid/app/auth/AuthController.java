package com.talentgrid.app.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talentgrid.app.dto.LoginRequestDto;
import com.talentgrid.app.dto.LoginResponseDto;
import com.talentgrid.app.dto.UserDto;
import com.talentgrid.app.security.util.JwtUtil;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

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

}
