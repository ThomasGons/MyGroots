package com.springboot.mygroots.controller;

import com.springboot.mygroots.model.Account;
import com.springboot.mygroots.model.FamilyTree;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.service.AccountService;
import com.springboot.mygroots.service.FamilyTreeService;
import com.springboot.mygroots.service.PersonService;
import com.springboot.mygroots.utils.Enumerations;
import com.springboot.mygroots.utils.ExtResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.mygroots.dto.FamilyTreeDTO;

import java.util.List;
import java.util.Map;


/**
 * Controller for the family tree requests (e.g. search, add, remove)
 */
@RestController
@RequestMapping(value="/family-tree")
@CrossOrigin(origins = "http://localhost:4200")
public class FamilyTreeCtrl {

    @Autowired
    FamilyTreeService familyTreeService;

    @Autowired
    PersonService personService;
    
    @Autowired
    AccountService accountService;

    /**
     * Get the root of the family tree by the id of the owner
     * @param data map containing the id of the owner
     * @return the root of the family tree
     */
    @PostMapping(value= "/")
    public ExtResponseEntity<FamilyTreeDTO> root(@RequestBody Map<String, String> data) {
        Account acc = accountService.AuthenticatedAccount(data.get("token"), data.get("accountId"));
		if ( acc != null) {
//            Person p = acc.getPerson();
//            if (p == null) {
//                return new ExtResponseEntity<>("Aucune personne correspondante à cet id !", HttpStatus.BAD_REQUEST);
//            }
//            FamilyTree ft = familyTreeService.getFamilyTreeByOwner(p);
//            if (ft == null) {
//                return new ExtResponseEntity<>("Aucun arbre correspondant à cet id !", HttpStatus.BAD_REQUEST);
//            }
			FamilyTree ft = acc.getFamilyTree();
            return new ExtResponseEntity<>(new FamilyTreeDTO(ft), HttpStatus.OK);
        }
        return new ExtResponseEntity<>("Aucun compte correspondant a cet id ou est authentifié !", HttpStatus.BAD_REQUEST);
    }

    /**
     * Get the help for searchable relations
     * @return list of available relations
     */
    @GetMapping(value= "/help")
    public ExtResponseEntity<List<String>> help() {
        return new ExtResponseEntity<>(familyTreeService.getHelp(), HttpStatus.OK);
    }

    /**
     * Search a list of persons by a relation to a person
     * @param data map containing the id of the source person,
     *                            the relation between the source and the destination and
     *                            the id of the owner whether the source_id is not the owner
     * @return list of persons
     */
    @PostMapping(value="/nodes")
    public ExtResponseEntity<List<Person>> search(@RequestBody Map<String, String> data) {
        String src_id = data.get("src_id");
        String relation = data.get("relation");
        String owner_id = data.get("owner_id");
        Person owner = personService.getPersonById(owner_id == null ? src_id : owner_id);
        if (owner == null) {
            return new ExtResponseEntity<>("Aucune personne (propriétaire) correspondante a cet id !", HttpStatus.BAD_REQUEST);
        }
        FamilyTree ft = familyTreeService.getFamilyTreeByOwner(owner);
        if (ft == null) {
            return new ExtResponseEntity<>("Aucun arbre correspondant a cet id !", HttpStatus.BAD_REQUEST);
        }
        Person sp = personService.getPersonById(src_id);
        if (sp == null) {
            return new ExtResponseEntity<>("Aucune personne (source) correspondante a cet id !", HttpStatus.BAD_REQUEST);
        }
        List <Person> relatedPeople = switch (relation) {
            case "father" -> List.of(ft.getFather(sp));
            case "mother" -> List.of(ft.getMother(sp));
            case "partner" -> List.of(ft.getPartner(sp));
            case "parents" -> ft.getParents(sp);
            case "children" -> ft.getChildren(sp);
            case "siblings" -> ft.getSiblings(sp);
            case "grandparents" -> ft.getGrandParents(sp);
            case "grandchildren" -> ft.getGrandChildren(sp);
            case "cousins" -> ft.getCousins(sp);
            case "uncles_aunts" -> ft.getUnclesAndAunts(sp);
            case "nephews_nieces" -> ft.getNephewsAndNieces(sp);
            default -> null;
        };
        return new ExtResponseEntity<>(relatedPeople, HttpStatus.OK);
    }

    /**
     * Add a person to the family tree
     * @param data map containing the id of the source person,
     *                            the id of the destination person,
     *                            the relation between the source and the destination and
     *                            the id of the owner whether the source_id is not the owner
     *
     * @return message to indicate whether the addition has been carried out correctly
     */
    @PutMapping(value="/nodes")
    public ExtResponseEntity<?> addNode(@RequestBody Map<String, String> data) {
        String src_id = data.get("src_id");
        String dst_id = data.get("dst_id");
        Enumerations.Relation relation = Enumerations.Relation.valueOf(data.get("relation"));
        String owner_id = data.get("owner_id");
        Person owner = personService.getPersonById(owner_id == null ? src_id : owner_id);
        if (owner == null) {
            return new ExtResponseEntity<>("Aucune personne (propriétaire) ne correspond à cet id!", HttpStatus.BAD_REQUEST);
        }
        FamilyTree ft = familyTreeService.getFamilyTreeByOwner(owner);
        if (ft == null) {
            return new ExtResponseEntity<>("Aucun arbre ne correspond à cet id!", HttpStatus.BAD_REQUEST);
        }
        Person sp = personService.getPersonById(src_id);
        if (sp == null) {
            return new ExtResponseEntity<>("Aucune personne (source) ne correspond à cet id!", HttpStatus.BAD_REQUEST);
        }
        Person dp = personService.getPersonById(dst_id);
        if (dp == null) {
            return new ExtResponseEntity<>("Aucune personne (destination) ne correspond à cet id!", HttpStatus.BAD_REQUEST);
        }
        ft.addMemberToTree(sp, dp, relation);
        familyTreeService.updateFamilyTree(ft);
        return new ExtResponseEntity<>("Ajout réussi!", HttpStatus.OK);
    }

    /**
     * Remove a person from the family tree
     * @param data map containing the id of the owner person,
     *                            the id of the person to remove
     *
     * @return message to indicate whether the deletion has been carried out correctly
     */
    @DeleteMapping(value="/nodes")
    public ExtResponseEntity<?> removeNode(@RequestBody Map<String, String> data) {
        String owner_id = data.get("owner_id");
        Person owner = personService.getPersonById(owner_id);
        if (owner == null) {
            return new ExtResponseEntity<>("Aucune personne (propriétaire) ne correspond à cet id!", HttpStatus.BAD_REQUEST);
        }
        FamilyTree ft = familyTreeService.getFamilyTreeByOwner(owner);
        if (ft == null) {
            return new ExtResponseEntity<>("Aucun arbre ne correspond à cet id!", HttpStatus.BAD_REQUEST);
        }
        String r_id = data.get("r_id");
        Person p = personService.getPersonById(r_id);
        if (p == null) {
            return new ExtResponseEntity<>("Aucune personne (destination) ne correspond à cet id!", HttpStatus.BAD_REQUEST);
        }
        ft.removeMemberFromTree(p);
        familyTreeService.updateFamilyTree(ft);
        return new ExtResponseEntity<>("Suppression réussie!", HttpStatus.OK);
    }

}
