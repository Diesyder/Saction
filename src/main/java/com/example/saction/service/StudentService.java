package com.example.saction.service;

import com.example.saction.mapper.StudentMapper;
import com.example.saction.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentMapper studentMapper;

    //查
    public List<Student> findAllStudent() {
        return studentMapper.findAllStudent();
    }

    public Student findStudentById(int id) {
        return  studentMapper.findStudentById(id);
    }

    //改
    public int updateStudent(Student student) {
        return studentMapper.updateStudent(student);
    }

    //增
    public int addStudent(Student student) {
        return  studentMapper.addStudent(student);
    }

    //删
    public int deleteStudent(int id) {
        return studentMapper.deleteStudent(id);
    }

}
