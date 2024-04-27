package com.example.saction.mapper;

import com.example.saction.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentMapper {
    //查
    List<Student> findAllStudent();//查询全部学生信息,返回的类型应该是一个学生集合(所以我们要用list来接)

    Student findStudentById(@Param("id") int id);//按照id找学生

    //改
    int updateStudent(@Param("student")Student student);

    //增
    int addStudent(@Param("student")Student student);

    //删
    int deleteStudent(@Param("id") int id);
}
