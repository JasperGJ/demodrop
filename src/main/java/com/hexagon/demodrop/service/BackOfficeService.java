package com.hexagon.demodrop.service;

import com.hexagon.demodrop.model.Demo;
import com.hexagon.demodrop.model.User;
import com.hexagon.demodrop.object.DemoData;
import com.hexagon.demodrop.object.InboxData;
import com.hexagon.demodrop.object.InboxItemData;
import com.hexagon.demodrop.repository.DemoRepository;
import com.hexagon.demodrop.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BackOfficeService {

    private UserRepository userRepository;
    private DemoRepository demoRepository;

    public BackOfficeService(UserRepository userRepository, DemoRepository demoRepository) {
        this.userRepository = userRepository;
        this.demoRepository = demoRepository;
    }

    public InboxData getInboxData(String email) {
        InboxData inboxData = new InboxData();
        User user = userRepository.findByEmail(email);
        if (user == null) throw new UsernameNotFoundException("User not found"); //TODO: change throw
        demoRepository.findAll().forEach(demo -> inboxData.items.add(new InboxItemData(demo))); //TODO: filter demos
        return inboxData;
    }

    public DemoData getDemoData(long id){
        Optional<Demo> demo = demoRepository.findById(id);
        if (demo.isPresent()) {
            return new DemoData(demo.get());
        }
        throw new UsernameNotFoundException("User not found"); //TODO: change throw
    }
}
