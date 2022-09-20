package ca.sheridancollege.soleimaa.controllers;


import ca.sheridancollege.soleimaa.beans.Student;
import ca.sheridancollege.soleimaa.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class StudentController {

    @Autowired
    DatabaseAccess da;

    ModelAndView mv;
    //public List<Student> studentList = new CopyOnWriteArrayList<Student>();

    @GetMapping("/")
    public ModelAndView getIndex(Model model) {
        mv = new ModelAndView("index", "students", da.getStudents());
        mv.addObject("myStudent", new Student());
        return mv;
    }//change to model and view bc we are optimizing this to MVC


    @PostMapping("/insertStudent")
    public ModelAndView addStudent(Model model, @ModelAttribute Student myStudent) {
        //  studentList.add(myStudent);
        da.insertStudent(myStudent.getName());  // actual insertion of student object into DB
        mv = new ModelAndView("index", "students", da.getStudents());
        mv.addObject("myStudent", new Student());
       /* before when the function was String type
       model.addAttribute("students", da.getStudents());
        model.addAttribute("myStudent", new Student());
        return "index"; */
        return mv;
    }

    @GetMapping("/deleteStudentById/{id}")
    public ModelAndView deleteStudentById(Model model,
                                    @PathVariable Long id) {
        da.deleteStudentById(id);
        mv = new ModelAndView("modify", "students", da.getStudents());
        /* from when it was string
        model.addAttribute("myStudent", new Student());
        model.addAttribute("students", da.getStudents()); */
        mv.addObject("myStudent", new Student());
        return mv;
    }

    @GetMapping("/editStudentById/{id}")
    public ModelAndView modifyStudentById(Model model,
                                    @PathVariable Long id) {
        Student student;
        student = da.getStudent(id).get(0); //the first element in the list (and only)
        mv = new ModelAndView("modify", "students", da.getStudents());
        mv.addObject("myStudent", student);
/*        model.addAttribute("myStudent", student);
        model.addAttribute("students", da.getStudents());
        return "modify";
*/
        return mv;
    }

    @PostMapping("/updateStudent")
    public ModelAndView updateStudentInfo(Model model, @ModelAttribute Student student) {

        da.updateStudentById(student);
   /*     model.addAttribute("students", da.getStudents());
        model.addAttribute("myStudent", new Student());
        return "index"; */
        mv.addObject("myStudent", new Student());
        return mv;

    }
}
