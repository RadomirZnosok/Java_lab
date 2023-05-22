package com.lab.convertion.services;

import com.lab.convertion.entity.PhysQuantity;
import com.lab.convertion.models.DbEntity;
import com.lab.convertion.repository.DbPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@Transactional
public class DbRepositoryService {
    @Autowired
    private DbPostRepository repository;

    public void save(DbEntity entity){
        repository.save(entity);
    }
    public List<DbEntity> listAll(){
        return (List<DbEntity>) repository.findAll();
    }
    public DbEntity get(String id){
        return this.isHas(id) ? repository.findById(id).get() : new DbEntity("", new PhysQuantity(""));
    }
    public void delete(String id){
        repository.deleteById(id);
    }
    public void deleteAll(){
        repository.deleteAll();
    }
    public boolean isHas(String id){
        return repository.count() > 0 && repository.existsById(id);
    }

    public int count(){
        return (int) repository.count();
    }
}





