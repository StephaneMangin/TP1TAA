package org.istic.taa.todoapp.config;

import org.istic.taa.todoapp.aop.UserToOwnerAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableAspectJAutoProxy
public class UserToOwnerAspectConfiguration {

    @Bean
    @Profile(Constants.SPRING_PROFILE_DEVELOPMENT)
    public UserToOwnerAspect userToOwnerAspect() {
        return new UserToOwnerAspect();
    }
}
