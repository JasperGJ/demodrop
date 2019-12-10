package com.hexagon.demodrop.object;

import com.hexagon.demodrop.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class LoginData {
    public String name;
    public byte[] thumbnail;
    public String role;

    public LoginData(User user) {
        this.name = user.getName();
        this.thumbnail = user.getThumbnail();
        role = user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
                ?  "/admin?login=admin"
                : user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_BACKOFFICE"))
                ? "/backoffice?login=backoffice"
                : "/profile?login=user";
    }

}
