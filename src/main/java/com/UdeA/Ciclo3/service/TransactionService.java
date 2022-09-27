package com.UdeA.Ciclo3.service;

import com.UdeA.Ciclo3.modelos.Transaction;
import com.UdeA.Ciclo3.repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    public List<Transaction> getAllMovement(){ //metodo para ver todos los moviemientos
       List<Transaction> transactionList = new ArrayList<>();
       transactionRepository.findAll().forEach(transaction -> transactionList.add(transaction));
       return transactionList;
    }

    public Transaction getMovementById (Long id){
        return transactionRepository.findById(id).get();
    }

    public Boolean saveOrUpdateMovement (Transaction transaction){
        Transaction trans = transactionRepository.save(transaction);
        if (transactionRepository.findById(trans.getId())!= null){
            return true;
        }
        return false;
    }
    public Boolean deleteMovement(Long id){
        transactionRepository.deleteById(id);
        if(this.transactionRepository.findById(id).isPresent()){
            return false;
        }
        return true;
    }

    public ArrayList<Transaction> getByEmployee(Long id){
        return transactionRepository.findByEmployee(id);
    }

    public ArrayList <Transaction> getByEnterprise (Long id){
        return transactionRepository.findByEnterprise(id);
    }

    public Long getSumAmount(){
    return transactionRepository.sumOfAmount();
    }
    public Long sumByEmployee(Long id){
        return transactionRepository.sumByEmployee(id);
    }

    public Long sumByEmterprise(Long id){
        return transactionRepository.sumByEmterprise(id);
    }

    public long IdPorCorreo(String Correo){
        return transactionRepository.IdPorCorreo(Correo);
    }
}
