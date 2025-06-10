package com.example.todolist.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.todolist.mapper.CometMapper;
import com.example.todolist.model.Comet;

@Repository
public class CometRepository {
    private final CometMapper cometMapper;

    public CometRepository(CometMapper cometMapper) {
        this.cometMapper = cometMapper;
    }

    public void insertComet(Comet comet) {
        cometMapper.insertComet(comet);
    }

    public List<Comet> getAllComets() {
        return cometMapper.getAllComets();
    }
}
