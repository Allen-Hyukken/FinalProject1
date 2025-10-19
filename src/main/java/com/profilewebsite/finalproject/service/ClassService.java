package com.profilewebsite.finalproject.service;

import com.profilewebsite.finalproject.model.ClassRoom;
import com.profilewebsite.finalproject.model.User;
import com.profilewebsite.finalproject.repository.ClassRoomRepository;
import com.profilewebsite.finalproject.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile; // Added for createClass

import java.io.IOException; // Added for createClass
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID; // Added for createClass

@Service
public class ClassService {

    // CHANGED: Switched to constructor injection for consistency
    private final ClassRoomRepository classRoomRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    // CHANGED: Added constructor
    public ClassService(ClassRoomRepository classRoomRepository, UserRepository userRepository, UserService userService) {
        this.classRoomRepository = classRoomRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    // --- Methods for StudentController (Existing) ---

    // Fetch all classes for the logged-in student
    public List<ClassRoom> getClassesForStudent() {
        Long studentId = userService.getCurrentUserId(); //
        return classRoomRepository.findByStudents_Id(studentId); //
    }

    // Join class by code
    public boolean joinClassByCode(String code) {
        Long studentId = userService.getCurrentUserId(); //
        Optional<ClassRoom> optionalClass = classRoomRepository.findByCode(code); //

        if (optionalClass.isPresent()) {
            ClassRoom classRoom = optionalClass.get(); //

            if (classRoom.getStudents() == null) { //
                classRoom.setStudents(new ArrayList<>()); //
            }

            boolean alreadyJoined = classRoom.getStudents().stream()
                    .anyMatch(s -> s.getId().equals(studentId)); //

            if (!alreadyJoined) {
                User student = userRepository.findById(studentId).orElseThrow(); //
                classRoom.getStudents().add(student); //
                classRoomRepository.save(classRoom); //
                return true; //
            }
        }

        return false; //
    }

    // --- Methods for TeacherController (NEW) ---

    /**
     * ADDED: Required by TeacherController.dashboard
     */
    public List<ClassRoom> findByTeacherId(Long teacherId) {
        // This assumes your ClassRoomRepository has this method
        return classRoomRepository.findByTeacherId(teacherId);
    }

    /**
     * ADDED: Required by TeacherController.viewClass and StudentController.viewClassQuizzes
     */
    public Optional<ClassRoom> findById(Long id) {
        return classRoomRepository.findById(id);
    }

    /**
     * ADDED: Required by TeacherController.showCreateQuizPage
     */
    public List<ClassRoom> findAll() {
        return classRoomRepository.findAll();
    }

    /**
     * ADDED: Required by TeacherController.createClass
     * A placeholder implementation. You will need to add your
     * file storage logic (e.g., to S3 or a local disk).
     */
    public ClassRoom createClass(String name, MultipartFile banner, User teacher) throws IOException {
        ClassRoom newClass = new ClassRoom();
        newClass.setName(name);
        newClass.setTeacher(teacher);
        newClass.setCode(UUID.randomUUID().toString().substring(0, 6).toUpperCase()); // Generate a unique code

        // TODO: Add your file storage logic here
        if (banner != null && !banner.isEmpty()) {
            // 1. Save the banner file (e.g., to a directory or cloud storage)
            // String bannerUrl = myFileStorageService.save(banner);
            // newClass.setBannerUrl(bannerUrl);
            System.out.println("Banner file received: " + banner.getOriginalFilename());
        }

        return classRoomRepository.save(newClass);
    }
}