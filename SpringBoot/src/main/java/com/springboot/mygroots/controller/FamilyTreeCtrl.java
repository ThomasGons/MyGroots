package com.springboot.mygroots.controller;

import com.springboot.mygroots.model.Account;
import com.springboot.mygroots.model.FamilyTree;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.service.AccountService;
import com.springboot.mygroots.service.FamilyTreeService;
import com.springboot.mygroots.service.PersonService;
import com.springboot.mygroots.utils.Enumerations.Gender;
import com.springboot.mygroots.utils.Enumerations.Relation;
import com.springboot.mygroots.utils.ExtResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.mygroots.dto.FamilyTreeDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;


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
            FamilyTree ft = acc.getFamilyTree();
            if (ft == null) {
                return new ExtResponseEntity<>("Aucun arbre correspondant a cet id !", HttpStatus.BAD_REQUEST);
            }
            switch (ft.getVisibility()) {
                case PRIVATE -> {
                    if (!acc.getPerson().getId().equals(ft.getOwner().getId())) {
                        return new ExtResponseEntity<>("Cet arbre est privé !", HttpStatus.BAD_REQUEST);
                    }
                }
                case PROTECTED -> {
                	for (Person p : ft.getMembers()) {
                		if (p.getId().equals(acc.getPerson().getId())) {
                			return new ExtResponseEntity<>(new FamilyTreeDTO(ft), HttpStatus.OK);
                		}
                	}
                	return new ExtResponseEntity<>("Cet arbre est protégé !", HttpStatus.BAD_REQUEST);
                }
                case PUBLIC -> {}
            }
            return new ExtResponseEntity<>(new FamilyTreeDTO(ft), HttpStatus.OK);
        }
        return new ExtResponseEntity<>("Aucun compte correspondant à cet id ou est authentifié !", HttpStatus.BAD_REQUEST);
    }


    @PostMapping(value= "/view/other")
    public ExtResponseEntity<FamilyTreeDTO> getOtherTree(@RequestBody Map<String, String> data) {
        Account watcher = accountService.getAccountById(data.get("watcherId"));
        Account watched = accountService.getAccountById(data.get("watchedId"));
        System.out.println("watcher : " + watcher.getPerson().getFirstName());
        System.out.println("watched : " + watched.getPerson().getFirstName());
        if ( watched != null) {
            FamilyTree ft = watched.getFamilyTree();
            if (ft == null) {
                return new ExtResponseEntity<>("Aucun arbre correspondant a cet id !", HttpStatus.BAD_REQUEST);
            }
            switch (ft.getVisibility()) {
                case PRIVATE -> {
                	if (!watched.getId().equals(watcher.getId())) {
                        return new ExtResponseEntity<>("Cet arbre est privé !", HttpStatus.BAD_REQUEST);
                    }
                }
                case PROTECTED -> {
                	for (Person p : ft.getMembers()) {
                		if (p.getId().equals(watcher.getPerson().getId())) {
                			return new ExtResponseEntity<>(new FamilyTreeDTO(ft), HttpStatus.OK);
                		}
                	}
                	return new ExtResponseEntity<>("Cet arbre est protégé !", HttpStatus.BAD_REQUEST);
                }
                case PUBLIC -> {}
            }
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
    @PostMapping(value="/node/search")
    public ExtResponseEntity<List<Person>> search(@RequestBody Map<String, String> data) {
        String srcId = data.get("srcId");
        String relation = data.get("relation");
        String ownerId = data.get("ownerId");
        
        Account owner = accountService.getAccountById(ownerId);
        if (owner == null) {
            return new ExtResponseEntity<>("Aucune compte (propriétaire) correspondant à cet id !", HttpStatus.BAD_REQUEST);
        }
        FamilyTree ft = owner.getFamilyTree();
        if (ft == null) {
            return new ExtResponseEntity<>("Aucun arbre correspondant a cet id !", HttpStatus.BAD_REQUEST);
        }
        Person sp = personService.getPersonById(srcId);
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
     * @param data map containing the id of the source account,
     *                            the id of the destination account,
     *                            the relation between the source and the destination and
     *                            the id of the owner whether the source_id is not the owner
     *
     * @return message to indicate whether the addition has been carried out correctly
     */
    @PutMapping(value="/node/add/id")
    public ExtResponseEntity<?> addNodeByID(@RequestBody Map<String, String> data) {
        String owner_id = data.get("ownerId");//ownerId account of the owner of the tree
        String src_id = data.get("srcId"); //srcId id of the person who wants to add someone to his tree
        String dst_id = data.get("accountId"); //accountId account of the person to add to the tree
        Relation relation = Relation.valueOf(data.get("relation"));
        System.out.println("ça ajoute un compte à l'arbre");
        Account owner = accountService.getAccountById(owner_id);
        Person src = personService.getPersonById(src_id);
        Account dst = accountService.getAccountById(dst_id);

        if (owner == null) {
            return new ExtResponseEntity<>("Aucun account (propriétaire de l'arbre) ne correspond à cet id!", HttpStatus.BAD_REQUEST);
        }
        if(src == null) {
        	return new ExtResponseEntity<>("Aucun account (source) ne correspond à cet id!", HttpStatus.BAD_REQUEST);
        }
        if(dst == null) {
        	return new ExtResponseEntity<>("Aucun account (destination) ne correspond à cet id!", HttpStatus.BAD_REQUEST);
        }

        FamilyTree ft = owner.getFamilyTree();
        if (ft == null) {
            return new ExtResponseEntity<>("Ce propriétaire n'a pas d'arbre! ???? wtf pourquoi", HttpStatus.BAD_REQUEST);
        }

        owner.getNotifs().removeIf(Objects::isNull);
        dst.getNotifs().removeIf(Objects::isNull);

        ft.addAccountToTree(owner, src, dst,relation);

        accountService.updateAccount(dst);
        accountService.updateAccount(owner);
        familyTreeService.updateFamilyTree(ft);
        return new ExtResponseEntity<>("Notifications d'ajout envoyé à "+dst.getPerson().getFirstName()+" "+dst.getPerson().getLastName()+".", HttpStatus.OK);
    }

    /**
     * Add a person to the family tree
     * @param data map containing the id of the source account,
     *               the information of the destination person to be created for the tree,
     *             the relation between the source and the destination and
     *             the id of the owner whether the source_id is not the owner
     *
     * @return message to indicate whether the addition has been carried out correctly
     */
    @PutMapping(value="/node/add/name")
    public ExtResponseEntity<?> addNodeNewPerson(@RequestBody Map<String, String> data) {
        String owner_id = data.get("ownerId");
        String src_id = data.get("srcId");
        String personName = data.get("firstName");
        String personLastName = data.get("lastName");
        LocalDate personBirthDate = LocalDate.parse(data.get("birthDate"));
        Gender personGender = Gender.valueOf(data.get("gender"));
        Relation relation = Relation.valueOf(data.get("relation"));
        String nationality = data.get("nationality");

        Account owner = accountService.getAccountById(owner_id);
        Person src = personService.getPersonById(src_id);

        if (owner == null) {
            return new ExtResponseEntity<>("Aucun compte (propriétaire de l'arbre) ne correspond à cet id !", HttpStatus.BAD_REQUEST);
        }
        if(src == null) {
            return new ExtResponseEntity<>("Aucune personne (source) ne correspond à cet id !", HttpStatus.BAD_REQUEST);
        }

        FamilyTree ft = owner.getFamilyTree();
        if (ft == null) {
            return new ExtResponseEntity<>("Ce propriétaire n'a pas d'arbre! ???? wtf pourquoi", HttpStatus.BAD_REQUEST);
        }

        Person newPers = new Person(personName, personLastName, personGender);
        newPers.setBirthDate(personBirthDate);
        newPers.setNationality(nationality);
        personService.addPerson(newPers);

        ft.addPersonToTree(src, newPers, relation);
        familyTreeService.updateFamilyTree(ft);
        return new ExtResponseEntity<>("Ajout du noeud réussi !", HttpStatus.OK);
    }



    /**
     * Remove a person from the family tree
     * @param data map containing the id of the owner person,
     *                            the id of the person to remove
     *
     * @return message to indicate whether the deletion has been carried out correctly
     */
    @PostMapping(value="/node/delete")
    public ExtResponseEntity<?> removeNode(@RequestBody Map<String, String> data) {
        Account owner = accountService.AuthenticatedAccount(data.get("token"),data.get("accountId"));
        if (owner == null) {
            return new ExtResponseEntity<>("Aucune personne (propriétaire) ne correspond à cet id!", HttpStatus.BAD_REQUEST);
        }
        FamilyTree ft = owner.getFamilyTree();
        if (ft == null) {
            return new ExtResponseEntity<>("Aucun arbre ne correspond à cet id!", HttpStatus.BAD_REQUEST);
        }
        String r_id = data.get("personId");
        Person p = personService.getPersonById(r_id);
        if (p == null) {
            return new ExtResponseEntity<>("Aucune personne (destination) ne correspond à cet id!", HttpStatus.BAD_REQUEST);
        }
        ft.removeMemberFromTree(p);
        familyTreeService.updateFamilyTree(ft);
        return new ExtResponseEntity<>("Suppression du noeud réussie!", HttpStatus.OK);
    }

}
