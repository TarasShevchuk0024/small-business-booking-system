package com.system.SmallBusinessBookingSystem.service.token;

import com.system.SmallBusinessBookingSystem.service.models.User;
import com.system.SmallBusinessBookingSystem.service.models.UserType;

public interface TokenService {
    String createToken(User user);

    boolean isValidToken(String token);

    String getUserId(String token);

    UserType getUserType(String token);
}
