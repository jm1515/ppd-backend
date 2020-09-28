package com.ppd.mubler.service;

import com.ppd.mubler.model.entity.Mubler;
import com.ppd.mubler.model.repository.MublerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MublerService implements Service<Mubler> {

    @Autowired
    private MublerRepository mublerRepository;

    @Override
    public void save(Mubler entity) {
        this.mublerRepository.save(entity);
    }

    public List<Mubler> findByEmail(String email) {
        return this.mublerRepository.findByEmail(email);
    }

    public Mubler getUserByEmailAndPassword(String email, String password) {
        for (Mubler mubler : this.findByEmail(email)) {
            if (mubler.getPassword().equals(password)){
                return mubler;
            }
        }
        return null;
    }

    public Mubler getMublerById(long idMubler){
        return this.mublerRepository.findById(idMubler);
    }
}
