package com.hexagon.demodrop.object;

import com.hexagon.demodrop.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class ProfileData {
    public String name;
    public String description;
    public byte[] photo;
    public List<MessageData> messages;

    public ProfileData() {}

    public ProfileData(User user) {
        this.name = user.getName();
        this.description = user.getDescription();
        this.photo = user.getPhoto();

        this.messages = new ArrayList<>();
        user.getMessages().forEach(message -> this.messages.add(new MessageData(message)));
    }

}
