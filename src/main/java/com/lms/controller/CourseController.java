package com.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lms.model.entity.Course;
import com.lms.service.CategoryService;
import com.lms.service.CourseService;

@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET)
    public String courses(Model model) {
        model.addAttribute("courses", courseService.getAll());
        return "courses/courses";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(@ModelAttribute Course course, Model model) {
        model.addAttribute("categories", categoryService.getAllCategoriesMap());
        return "courses/edit_course";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute Course course, @RequestParam("categoryIds") List<String> categoryId, Model model) {
        courseService.save(course, categoryId);
        return "redirect:/courses";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model) {
        Course course = courseService.get(id);
        model.addAttribute("course", course);
        model.addAttribute("categories", categoryService.getAllCategoriesMap());
        model.addAttribute("course_categories", courseService.getCourseCategoriesList(course));
        return "courses/edit_course";
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String remove(@PathVariable Long id, Model model) {
        courseService.remove(id);
        return "redirect:/courses";
    }
}