package com.cydeo.service.impl;

import com.cydeo.entity.User;
import com.cydeo.entity.common.UserPrincipal;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Get user by username using related message in repository
        // and check if the user is null. If it's null, throw exception
        User user = userRepository.fetchUserByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        //Check if the user is null. If it's null, throw exception .
        // (If do not use Optional. But using Optional orElseThrow is better)
        /*if (user == null){
            throw new UsernameNotFoundException("User not found");
        }*/

        //Return user information as UserDetails object by passing our user abject as constructor parameter
        return new UserPrincipal(user);

    }
}
