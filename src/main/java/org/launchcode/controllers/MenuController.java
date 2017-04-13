package org.launchcode.controllers;

import org.launchcode.models.Category;
import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CategoryDao;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.awt.*;
import java.util.ArrayList;

import static java.awt.SystemColor.menu;

/**
 * Created by Engineer on 4/12/2017.
 */

@Controller
@RequestMapping(value="menu")
public class MenuController {

    @Autowired
    private CheeseDao cheeseDao;

    @Autowired
    private MenuDao menuDao;


    @RequestMapping(value="")
    String index (Model model){

        model.addAttribute("menus", menuDao.findAll());
        return "menu/index";
    }

    @RequestMapping(value="add" , method= RequestMethod.GET)
    public String addDisplay(Model model){

        model.addAttribute(new Menu());
        //short to create a new Category object using the
        // default constructor and pass it into the view with key "category"
        model.addAttribute("title", "Add Menu");
        return "menu/add";
    }

    @RequestMapping(value="add", method= RequestMethod.POST)
    public String add(Model model,
                      @ModelAttribute @Valid Menu menu
            ,Errors errors){

        if (errors.hasErrors()){
            model.addAttribute("menu", menu);
            return "menu/add";
        }
        else{
            menuDao.save(menu);
            return "redirect:view"+"/" + menu.getId();
        }

    }

    @RequestMapping(value="/view/{id}")
     String viewMenu(Model model, @PathVariable("id") int id){
        model.addAttribute("menu", menuDao.findOne(id));
        return "menu/view";
    }

    @RequestMapping(value="/add-item/{id}", method=RequestMethod.GET)
    String addItem(Model model, @PathVariable("id") int id){
        Menu menu=menuDao.findOne(id);
        model.addAttribute("menu", menu);
        Iterable<Cheese> cheeses=cheeseDao.findAll();
        AddMenuItemForm addMenuItemForm=new AddMenuItemForm(menu, cheeses);
        model.addAttribute("form", addMenuItemForm);
        model.addAttribute("title", "Add item to menu: " +menu.getName());
        return "menu/add-item";
    }

    @RequestMapping(value="/add-item/{id}", method=RequestMethod.POST)
    String addItem(Model model, @ModelAttribute @Valid AddMenuItemForm addMenuItemForm, Errors errors,
                   @RequestParam ("menuId") int menuId, @RequestParam ("cheeseId") int cheeseId){
        if(errors.hasErrors()){
            return "menu/add-item";
        }
        else{
            Menu menu=menuDao.findOne(menuId);
            Cheese theCheese=cheeseDao.findOne(cheeseId);
            menu.addItem(theCheese);
            menuDao.save(menu);

            return "redirect:/menu/view/"+menu.getId();
        }
    }

}
