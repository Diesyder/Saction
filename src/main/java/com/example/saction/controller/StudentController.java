package com.example.saction.controller;

import com.example.saction.pojo.Student;
import com.example.saction.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping("/toindex")
    public String toindex() {
        return "index";
    }

    //查询所有页面
    @GetMapping("/findStudentList")
    public String findStudentList(Model model) {
        List<Student> studentList=studentService.findAllStudent();
        //传进去的是一个键值对
        model.addAttribute("studentList",studentList);//传进前端的东西
        //返回值==html文件名
        return "findStudentList";
    }

    //跳转到添加页面
    @GetMapping("/toaddStudent")
    public String toaddStudent() {
        //返回值为文件名
        return "addStudent";
    }

    //真正执行添加
    @PostMapping("/addStudent")
    public String addStudent(Student student) {
        studentService.addStudent(student);
        //跳转到哪里(文件名)
        return "redirect:/findStudentList";
    }

    @GetMapping("/toupdateStudent/{id}")
    public String toupdateStudent(@PathVariable("id")int id, Model model) {
        //先找到被修改的对象
        Student student=studentService.findStudentById(id);
        //将对象保存到model中
        model.addAttribute("student",student);
        //html文件名
        return "updateStudent";
    }

    @PostMapping("/updateStudent")
    public String updateStudent(Student student) {
        //获取当前页面学生信息,传入按照id修改学生信息的Service,进行信息修改
        studentService.updateStudent(student);
        return "redirect:/findStudentList";
    }

    @GetMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable("id") int id) {
        studentService.deleteStudent(id);
        return "redirect:/findStudentList";
    }
}
