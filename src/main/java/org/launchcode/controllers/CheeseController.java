package org.launchcode.controllers;


import org.launchcode.models.Category;
import org.launchcode.models.Cheese;
import org.launchcode.models.data.CategoryDao;
import org.launchcode.models.data.CheeseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;




/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("cheese")
public class CheeseController {


    @Autowired
    private CheeseDao cheeseDao;

    @Autowired
    private CategoryDao categoryDao;


    // Request path: /cheese
    @RequestMapping(value = "")
    public String index(Model model) {


        model.addAttribute("cheeses", cheeseDao.findAll());

        model.addAttribute("title", "My Cheeses");

        return "cheese/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCheeseForm(Model model) {
        model.addAttribute("title", "Add Cheese");
        model.addAttribute(new Cheese());
        model.addAttribute("categories", categoryDao.findAll());

        return "cheese/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddCheeseForm(@ModelAttribute  @Valid Cheese newCheese,

                                       Errors errors, @RequestParam int categoryId, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Cheese");
            model.addAttribute("categories", categoryDao.findAll());
            return "cheese/add";
        }
        Category cat=categoryDao.findOne(categoryId);
        newCheese.setCategory(cat);
        cheeseDao.save(newCheese);

        return "redirect:";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveCheeseForm(Model model) {

        model.addAttribute("cheeses", cheeseDao.findAll());

        model.addAttribute("title", "Remove Cheese");
        return "cheese/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)

    public String processRemoveCheeseForm(@RequestParam() int[] cheeseIds) {

    //problem: getting error when trying to remove certain cheeses
        for (int cheeseId: cheeseIds) {

            cheeseDao.delete(cheeseId);

        }

        return "redirect:";
    }


    @RequestMapping(value="category/{id}", method=RequestMethod.GET)
    public String category(Model model, @PathVariable("id") int id){

        Category cat= categoryDao.findOne(id);
        List<Cheese> cheeses=cat.getCheeses();
        model.addAttribute("cheeses", cheeses);
        model.addAttribute("title", "Cheeses in Category :" +cat.getName());
        return "cheese/index";
    }

    @RequestMapping(value="edit/{cheeseId}", method=RequestMethod.GET)
    public String displayEditForm(Model model, @PathVariable int cheeseId){
        model.addAttribute("title","edit cheese");
        Cheese editedCheese=cheeseDao.findOne(cheeseId);
        model.addAttribute("cheese",editedCheese);
        model.addAttribute("categories", categoryDao.findAll());
        return "cheese/edit";
    }
    @RequestMapping(value="edit/{cheeseId}", method=RequestMethod.POST)
    public String processEditForm(Model model, @PathVariable("cheeseId") int id ,@ ModelAttribute @Valid Cheese editedCheese,Errors errors,
                                  @RequestParam("name")String name,
                                  @RequestParam("description") String description) {


        if (errors.hasErrors()){
            model.addAttribute("title","edit cheese");
            model.addAttribute("categories", categoryDao.findAll());
            return "cheese/edit";
        }
//
//        CheeseData.remove(editedCheese.getCheeseId());
//        CheeseData.add(editedCheese);
        editedCheese=cheeseDao.findOne(id);
        editedCheese.setName(name);
        editedCheese.setDescription(description);
//problem with updating the cheese in the database
        return "redirect:/cheese";
    }



}
