package api.dev.infrastructure.security.userdetails;

import api.dev.domain.user.model.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Wraps the domain User as a Spring Security UserDetails.
 * This is the equivalent of Symfony's #[CurrentUser] User $user —
 * you get this object via @AuthenticationPrincipal in controllers.
 */
public class AuthenticatedUser implements UserDetails {

    private final User user;

    public AuthenticatedUser(User user) {
        this.user = user;
    }

    /** Access the domain User from a controller */
    public User getDomainUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Spring Security expects "ROLE_" prefix
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    @Override public String getPassword()                       { return user.getPassword(); }
    @Override public String getUsername()                       { return user.getEmail().value(); }
    @Override public boolean isAccountNonExpired()              { return true; }
    @Override public boolean isAccountNonLocked()               { return !user.isMaster() || true; }
    @Override public boolean isCredentialsNonExpired()          { return true; }
    @Override public boolean isEnabled()                        { return !user.isDeleted(); }
}
