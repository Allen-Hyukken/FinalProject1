package com.profilewebsite.finalproject.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.profilewebsite.finalproject.model.ClassRoom;
import java.util.Optional;

public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {
    Optional<ClassRoom> findByCode(String code);
}

