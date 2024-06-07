package de.ait_tr.DiaHelper.service;

import de.ait_tr.DiaHelper.domain.dto.UserDto;
import de.ait_tr.DiaHelper.domain.entity.User;
import de.ait_tr.DiaHelper.exception_handling.exceptions.UserNotFoundException;
import de.ait_tr.DiaHelper.exception_handling.exceptions.UserWithThisEmailIsAlreadyRegisteredException;
import de.ait_tr.DiaHelper.exception_handling.exceptions.UserWithThisEmailNotFoundException;
import de.ait_tr.DiaHelper.repository.UserRepository;
import de.ait_tr.DiaHelper.service.interfaces.EmailService;
import de.ait_tr.DiaHelper.service.interfaces.RoleService;
import de.ait_tr.DiaHelper.service.interfaces.UserService;
import de.ait_tr.DiaHelper.service.mapping.UserMappingService;
import jakarta.transaction.Transactional;
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
    private UserMappingService mappingService;

    public UserServiceImpl(UserRepository repository, BCryptPasswordEncoder encoder, RoleService roleService, EmailService emailService,UserMappingService mappingService) {
        this.repository = repository;
        this.encoder = encoder;
        this.roleService = roleService;
        this.emailService = emailService;
        this.mappingService=mappingService;
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

        if (repository.findByEmail(user.getEmail()) != null) {
            throw new UserWithThisEmailIsAlreadyRegisteredException(user.getEmail());
        }
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
        if (repository.findByEmail(user.getEmail()) == null) {
            throw new UserWithThisEmailNotFoundException(user.getEmail());
        }
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

    public User getUserByEmail(String email) {
        if (email == null) {
            throw new UserNotFoundException(1L);
        }
        User user = repository.findByEmail(email);
        return user;
    }

    public UserDto getUserProfile(String email) {
        User user = getUserByEmail(email);
        return mappingService.mapEntityToDto(user);}

    @Override
    @Transactional
    public User update(User updatedUser) {

        User user = repository.findById(updatedUser.getId()).orElseThrow(() -> new UserNotFoundException(updatedUser.getId()));

        user.setAge(updatedUser.getAge());
        user.setHeight(updatedUser.getHeight());
        user.setWeight(updatedUser.getWeight());
        user.setGlucoseLevel(updatedUser.getGlucoseLevel());

        return repository.save(user);
    }


//    @Override
//    public void deleteById(Long id) {
//    }

}
