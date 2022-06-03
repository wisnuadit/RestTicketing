package co.id.Itc25.Ticketing.services;

import co.id.Itc25.Ticketing.models.MyUserDetails;
import co.id.Itc25.Ticketing.models.User;
import co.id.Itc25.Ticketing.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //nyari User berdasarkan ID
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username tidak ditemukan"));
        //mengubah dari User -> UserDetails
        return new MyUserDetails(user);
    }
}
