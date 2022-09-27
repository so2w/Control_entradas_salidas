package com.UdeA.Ciclo3.repo;

import com.UdeA.Ciclo3.modelos.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    //procedimiento para filtrar los movimientos por empleado
    @Query(value = "select * from transaction where user_id = ?1", nativeQuery = true)
    public abstract ArrayList<Transaction> findByEmployee (Long id);

    //procedimiento para filtrar movimientos por empresa
    @Query (value = "select * from transaction where user_id in (select id from employee where enterprise_id = ?1)", nativeQuery = true)
    public abstract ArrayList<Transaction> findByEnterprise (Long id);

    //procedimiento para ver la sumatoria de los movimientos
    @Query(value = "select coalesce(sum(amount),0) from transaction", nativeQuery = true)
    public abstract long sumOfAmount();

    //procedimiento para ver la sumatoria de monto por empleado
    @Query (value = "select sum(amount) from transaction where user_id = ?1", nativeQuery = true)
    public abstract long sumByEmployee(Long id);

    //procedimiento para ver la sumatoria de monto por empresa
    @Query (value = "select sum(amount) from transaction where user_id in (select id from employee where enterprise_id = ?1)", nativeQuery = true)
    public abstract long sumByEmterprise(Long id);

    @Query(value="select id from employee where email=?1", nativeQuery = true)
    public abstract long IdPorCorreo(String correo);




}
