package com.springboot.mygroots.controller;

import com.springboot.mygroots.model.FamilyTree;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.service.FamilyTreeService;
import com.springboot.mygroots.service.PersonService;
import com.springboot.mygroots.utils.Enumerations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.mygroots.dto.FamilyTreeDTO;

import java.util.List;

@RestController
@RequestMapping(value="/family-tree")
public class FamilyTreeCtrl {

    @Autowired
    FamilyTreeService familyTreeService;

    @Autowired
    PersonService personService;

    @RequestMapping(value= "/")
    public FamilyTreeDTO root(@RequestBody String id) {
        FamilyTree ft = familyTreeService.getFamilyTreeById(id);
        return new FamilyTreeDTO(ft);
    }

    @RequestMapping(value= "/help")
    public List<String> help() {
        return familyTreeService.getHelp();
    }

    @RequestMapping(value="/nodes", method=RequestMethod.GET)
    public List<Person> search(@RequestBody String src_id, @RequestBody String relation, @RequestBody String owner_id) {
        FamilyTree ft = familyTreeService.getFamilyTreeById(owner_id == null ? src_id : owner_id);
        Person p = personService.getPersonById(src_id);
        return switch (relation) {
            case "father" -> List.of(ft.getFather(p));
            case "mother" -> List.of(ft.getMother(p));
            case "partner" -> List.of(ft.getPartner(p));
            case "parents" -> ft.getParents(p);
            case "children" -> ft.getChildren(p);
            case "siblings" -> ft.getSiblings(p);
            case "grandparents" -> ft.getGrandParents(p);
            case "grandchildren" -> ft.getGrandChildren(p);
            case "cousins" -> ft.getCousins(p);
            case "uncles_aunts" -> ft.getUnclesAndAunts(p);
            case "nephews_nieces" -> ft.getNephewsAndNieces(p);
            default -> null;
        };
    }


    @RequestMapping(value="/nodes", method=RequestMethod.PUT)
    public void addNode(@RequestBody String src_id, @RequestBody String dst_id, @RequestBody String relation, @RequestBody String owner_id) {
        FamilyTree ft = familyTreeService.getFamilyTreeById(owner_id == null ? src_id : owner_id);
        Person sp = personService.getPersonById(src_id);
        Person dp = personService.getPersonById(dst_id);
        ft.addMemberToTree(sp, dp, Enumerations.Relation.valueOf(relation));
        familyTreeService.updateFamilyTree(ft);
    }

    @RequestMapping(value="/nodes", method=RequestMethod.DELETE)
    public void removeNode(@RequestBody String owner_id, @RequestBody String removal_id) {
        FamilyTree ft = familyTreeService.getFamilyTreeById(owner_id);
        Person p = personService.getPersonById(removal_id);
        ft.removeMemberFromTree(p);
        familyTreeService.updateFamilyTree(ft);
    }

}
