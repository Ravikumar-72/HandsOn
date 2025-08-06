package com.project.FinWallet.oauth;

import com.project.FinWallet.entity.UserProfile;
import com.project.FinWallet.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest){
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        UserProfile user = userProfileRepository.findByEmail(email)
                .orElseGet(()->{
                    UserProfile newUser = new UserProfile();
                    newUser.setEmail(email);
                    newUser.setName(name);
                    newUser.setProvider(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
                    return userProfileRepository.save(newUser);
                });

        System.out.println("OAuth attributes: "+  oAuth2User.getAttributes());

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                oAuth2User.getAttributes(),
                "email"
        );
    }
}
