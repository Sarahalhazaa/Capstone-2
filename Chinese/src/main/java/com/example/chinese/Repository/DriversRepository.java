package com.example.chinese.Repository;

import com.example.chinese.Model.Drivers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriversRepository extends JpaRepository<Drivers,Integer> {

    Drivers findDriversByDriverId(Integer ID);
}
