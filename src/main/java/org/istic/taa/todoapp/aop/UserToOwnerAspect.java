package org.istic.taa.todoapp.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.istic.taa.todoapp.config.Constants;
import org.istic.taa.todoapp.domain.Owner;
import org.istic.taa.todoapp.domain.User;
import org.istic.taa.todoapp.repository.OwnerRepository;
import org.istic.taa.todoapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Optional;


/**
 * Aspetc for owner manadgement
 *
 */
@Aspect
public class UserToOwnerAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Inject
    private UserRepository userRepository;

    @Inject
    private OwnerRepository ownerRepository;

    @Inject
    private Environment env;

    @Pointcut("execution(* org.istic.taa.todoapp.service.UserService.activateRegistration(..))")
    public void activateRegistration() {
    }

    @AfterReturning(value = "activateRegistration()", returning = "option")
    public void createOwner(Optional<User> option) {
        log.info(option.toString());
        if (option.isPresent()) {
            User user = option.get();
            log.debug("owner created for user " + user.getLogin());
            if (!ownerRepository.findOneByName(user.getLogin()).isPresent()) {
                Owner owner = new Owner();
                owner.setName(user.getLogin());
                owner.setUser(user);
                ownerRepository.save(owner);
            }
        }
    }
}
