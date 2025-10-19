package com.profilewebsite.finalproject.repository;

import com.profilewebsite.finalproject.model.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {
    Optional<ClassRoom> findByCode(String code);
    List<ClassRoom> findByTeacherId(Long teacherId);
    List<ClassRoom> findByStudents_Id(Long studentId); // <-- added for student dashboard
}

