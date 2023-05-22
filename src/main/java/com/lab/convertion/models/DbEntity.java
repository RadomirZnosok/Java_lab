package com.lab.convertion.models;


import com.lab.convertion.entity.PhysQuantity;
import jakarta.persistence.*;
//import org.hibernate.annotations.TypeDef;
//import org.hibernate.annotations.TypeDefs;

@Entity
@Table(name = "table2")
//@TypeDefs({
//        @TypeDef(name = "json", typeClass = JsonType.class)
//})
public class DbEntity {
    @Id
    @Column(name = "param", length = 40)
    private String gotParam;

    @Column(name = "numr", length = 15)
    private Double number;

    @Column(name = "typer", length = 8)
    private String type;

    public DbEntity(String param, PhysQuantity result){
        this.gotParam = param;
        this.number = result.getNum();
        this.type = result.getType();
    }

    public DbEntity(){}



    @Override
    public String toString(){
        return "ResultDB:{" +
                ", old_param = " + gotParam +
                ", result = " + number +
                " " + type + "}";
    }

    public void setGotParam(String gotParam){
        this.gotParam = gotParam;
    }

    public String getGotParam(){
        return this.gotParam;
    }

    public void setNumber(Double number){
        this.number = number;
    }

    public Double getNumber(){
        return this.number;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }
}






/*
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
    public DbEntity get(Long id){
        return this.isHas(id) ? repository.findById(id).get() : new DbEntity("", new PhysQuantity(""));
    }
    public void delete(Long id){
        repository.deleteById(id);
    }
    public void deleteAll(){
        repository.deleteAll();
    }
    public boolean isHas(Long id){
        return repository.count() > 0 && repository.existsById(id);
    }

    public int count(){
        return (int) repository.count();
    }
}
 */