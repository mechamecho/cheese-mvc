package org.launchcode.controllers;

import org.launchcode.models.Category;
import org.launchcode.models.data.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.validation.Valid;


/**
 * Created by Engineer on 4/10/2017.
 */
// TODO: 4/10/2017 1st handler to view all categories

@Controller
@RequestMapping(value="category")
public class CategoryController {

    @Autowired
    private CategoryDao categoryDao;

    @RequestMapping(value="")
    public String index(Model model){

        Iterable<Category> categories=categoryDao.findAll();
        //I don't know what I am doing here
        model.addAttribute("categories", categories);
        model.addAttribute("title", "Categories");
        return "category/index";

    }

    // TODO: 4/10/2017  3rd part add handlers for categories

    @RequestMapping(value="add" , method=RequestMethod.GET)
    public String addDisplay(Model model){

        model.addAttribute(new Category());
        //short to create a new Category object using the
        // default constructor and pass it into the view with key "category"
        model.addAttribute("title", "Add Category");
        return "category/add";
    }

    @RequestMapping(value="add", method= RequestMethod.POST)
    public String add(Model model,
                      @ModelAttribute @Valid Category category
                       ,Errors errors){

        if (errors.hasErrors()){
            model.addAttribute("category", category);
            return "category/add";
        }
        else{
            categoryDao.save(category);
            return "redirect:";
        }

    }
}
