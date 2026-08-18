package com.talentgrid.app.security;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.talentgrid.app.entity.TalentgridUser;
import com.talentgrid.app.repository.TalentgridUserRepository;

import lombok.RequiredArgsConstructor;

@Profile("!prod")
@Component
@RequiredArgsConstructor
public class TalentgridNonProdUsernamePwdAuthenticationProvider implements AuthenticationProvider{
    
    private final TalentgridUserRepository talentgridUserRepository;

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
       String username = authentication.getName();
       TalentgridUser talentgridUser = talentgridUserRepository.findTalentgridUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User details not found for the user: " + username)
        );
        
        List<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority(talentgridUser.getRole().getName()));
            return new UsernamePasswordAuthenticationToken(talentgridUser, null, authorities);
       
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
