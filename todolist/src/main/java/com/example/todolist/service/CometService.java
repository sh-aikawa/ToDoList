package com.example.todolist.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.todolist.form.CometForm;
import com.example.todolist.model.Comet;
import com.example.todolist.repository.CometRepository;

@Service
public class CometService {
    private final CometRepository cometRepository;
    private final UserService userService;

    public CometService(CometRepository cometRepository, UserService userService){
        this.cometRepository = cometRepository;
        this.userService = userService;
    }

    public void registerComet(CometForm cometForm){
        Comet comet = new Comet();
        comet.setContent(cometForm.getContent());

        long userId = userService.getId();
        comet.setUserId(userId);
        cometRepository.insertComet(comet);
    }

    public List<Comet> getAllComets(){
        List<Comet> comets = cometRepository.getAllComets();
        return comets;
    }

    public void deleteComet(long cometId){
        cometRepository.deleteComet(cometId);
    }

}
