package com.hexagon.demodrop.service;

import com.hexagon.demodrop.model.*;
import com.hexagon.demodrop.object.DemoData;
import com.hexagon.demodrop.object.InboxData;
import com.hexagon.demodrop.object.InboxItemData;
import com.hexagon.demodrop.repository.DemoRepository;
import com.hexagon.demodrop.repository.MessageRepository;
import com.hexagon.demodrop.repository.TemplateRepository;
import com.hexagon.demodrop.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BackOfficeService {

    private UserRepository userRepository;
    private DemoRepository demoRepository;
    private TemplateRepository templateRepository;
    private MessageRepository messageRepository;
    private EmailService emailService;

    public BackOfficeService(UserRepository userRepository, DemoRepository demoRepository, TemplateRepository templateRepository, MessageRepository messageRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.demoRepository = demoRepository;
        this.templateRepository = templateRepository;
        this.messageRepository = messageRepository;
        this.emailService = emailService;
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

    public List<Template> getTemplates(){
        List<Template> templates = new ArrayList<>();
        templateRepository.findAll().forEach(template -> templates.add(template));
        return templates;
    }

    public void setReview(long id, String email) throws NotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) throw new UsernameNotFoundException("User not found");
        Demo demo = demoRepository.findById(id).orElseThrow(() -> new NotFoundException("No template found with id " + id));
        demo.setStatus("REVIEW");
        demo.setReviewedBy(user.getId());
        demoRepository.save(demo);
    }

    public void setComment(long id, String email, String comment, String status) throws NotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) throw new UsernameNotFoundException("User not found");
        Demo demo = demoRepository.findById(id).orElseThrow(() -> new NotFoundException("No template found with id " + id));
        demo.setStatus(status);
        demo.setReviewed(new Date());
        demo.setReviewedBy(user.getId());
        notifyUser(demo.getUser().getId(), comment);
        demoRepository.save(demo);
        if (status == "ACCEPT"){
            notifyDonDiablo(comment,demo.getId());
        }
    }

    private void notifyDonDiablo(String text,long demoId){
        emailService.sendMail(
                "Check out this Demo",
                text,
                "the_don@dondiablo.com");
    }

    private void notifyUser(long id, String text){
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            throw new UsernameNotFoundException("User not found"); //TODO: change throw
        Message message = new Message("Your Demo has been reviewed", text, user);
        messageRepository.save(message);
        user.getMessages().add(message);
        userRepository.save(user);
        emailService.sendMail(
                "Your Demo has been reviewed",
                text,
                user.getEmail());
    }
}
