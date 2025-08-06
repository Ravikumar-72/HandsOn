package com.project.FinWallet.mapper;

import com.project.FinWallet.dto.response.UserProfileResponse;
import com.project.FinWallet.entity.UserProfile;

public class UserProfileMapper {

    public static UserProfileResponse mapToUserProfileResponse(UserProfile user){
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setName(user.getName());
        userProfileResponse.setEmail(user.getEmail());
        userProfileResponse.setProvider(user.getProvider());
        userProfileResponse.setBalance(user.getBalance());
        return userProfileResponse;
    }
}
