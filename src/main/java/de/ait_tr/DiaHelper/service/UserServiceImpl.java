package de.ait_tr.DiaHelper.service;

import de.ait_tr.DiaHelper.domain.entity.User;
import de.ait_tr.DiaHelper.exception_handling.exceptions.UserNotFoundException;
import de.ait_tr.DiaHelper.repository.UserRepository;
import de.ait_tr.DiaHelper.service.interfaces.EmailService;
import de.ait_tr.DiaHelper.service.interfaces.RoleService;
import de.ait_tr.DiaHelper.service.interfaces.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository repository;
    private BCryptPasswordEncoder encoder;
    private RoleService roleService;
    private EmailService emailService;

    public UserServiceImpl(UserRepository repository, BCryptPasswordEncoder encoder, RoleService roleService, EmailService emailService) {
        this.repository = repository;
        this.encoder = encoder;
        this.roleService = roleService;
        this.emailService = emailService;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
    @Override
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {

        User user = repository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return user;
    }

    public void register(User user) {
        String password = PasswordHelper.generatePassword(8);
        user.setId(null);
        user.setPassword(encoder.encode(password));//sgenirirovat parol is 6 znakov
        user.setRoles(Set.of(roleService.getRoleUser()));
        user.setActive(true);

        user.setGlucoseLevel(BigDecimal.valueOf(0.00));

        repository.save(user);

        emailService.sendConfirmationEmail(user, password);
    }

    public void updatePassword(User user) {
        String password = PasswordHelper.generatePassword(8);
        //user.setId(null);
        user.setPassword(encoder.encode(password));//sgenirirovat parol is 6 znakov
        //user.setRoles(Set.of(roleService.getRoleUser()));
        user.setActive(true);

     //   user.setGlucoseLevel(BigDecimal.valueOf(0.00));

        repository.save(user);

        emailService.sendUpdateToPassword(user, password);
    }

    @Override
    public User save(User user) {
        return user;
    }



    @Override
    public User getUserById(Long id) {
        if (id == null || id < 1) {
            throw new UserNotFoundException(id);
        }
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return user;
    }

    @Override
    public User update(User user) {
        return null;
    }


    @Override
    public void deleteById(Long id) {
    }

}
