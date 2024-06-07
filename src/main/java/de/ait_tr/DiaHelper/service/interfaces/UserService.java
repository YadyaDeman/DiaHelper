package de.ait_tr.DiaHelper.service.interfaces;

import de.ait_tr.DiaHelper.domain.dto.UserDto;
import de.ait_tr.DiaHelper.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;

public interface UserService extends UserDetailsService {

    User save(User user);
    User getUserById(Long id);
    UserDto getUserProfile(Long id);
    User update(User updatedUser);
   // void deleteById(Long id);

    UserDetails loadUserByEmail(String email);

    void register(User user);

    void updatePassword(User user);
}
