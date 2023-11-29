package com.springboot.mygroots.model;

import com.springboot.mygroots.utils.Enumerations;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FamilyTreeTest {



    @Test
    void partnerAndFather() {
        // Créer une instance de FamilyTree
        FamilyTree familyTree = new FamilyTree("Famille Test", new Person("Père", "Père", Enumerations.Gender.MALE));

        // Créer deux personnes pour tester l'ajout du partenaire
        Person member1 = new Person("Membre 1", "Membre 1", Enumerations.Gender.FEMALE);
        Person member2 = new Person("Membre 2", "Membre 2", Enumerations.Gender.MALE);

        // Ajouter les membres à l'arbre généalogique
        familyTree.addChild(familyTree.getOwner(),member1);

        // Ajouter un partenaire à member1
        familyTree.addPartner(member1, member2);

        // Vérifier si member2 est maintenant le partenaire de member1
        assertEquals(member2, familyTree.getPartner(member1));
        assertEquals(member1, familyTree.getPartner(member2));

        // Vérifier si member1 est maintenant le père de member2
        for(Person child : familyTree.getChildren(familyTree.getOwner())){
            assertEquals(familyTree.getOwner(), familyTree.getFather(child));
        }
        assertEquals(familyTree.getOwner(), familyTree.getFather(member1));

        //vérifier la taille de l'arbre
        assertEquals(3, familyTree.getMembers().size());


    }

    @Test
    void familyComparison() {

        Person pere = new Person("Membre 1", "Membre 1", Enumerations.Gender.FEMALE);
        Person mere = new Person("Membre 2", "Membre 2", Enumerations.Gender.MALE);
        Person enfant = new Person("membre 3", "membre 3" , Enumerations.Gender.MALE);
        Person soeur = new Person("membre 4", "membre 4" , Enumerations.Gender.FEMALE);
        Person grandPere = new Person("membre 5", "membre 5" , Enumerations.Gender.MALE);
        Person oncle = new Person("membre 6", "membre 6",Enumerations.Gender.MALE );
        Person cousin = new Person("membre 7", "membre 7", Enumerations.Gender.FEMALE);

        // Créer une instance de FamilyTree
        FamilyTree familyTree = new FamilyTree("Famille Test", pere);

        // Ajouter les membres à l'arbre généalogique
        familyTree.addChild(pere, enfant);
        familyTree.addChild(pere, soeur);
        familyTree.addFather(pere, grandPere);
        familyTree.addPartner(pere, mere);
        familyTree.addChild(grandPere, oncle);
        familyTree.addChild(oncle, cousin);

        //Creer une deuxieme famille avec les même membre mais de manière différente
        FamilyTree familyTree2 = new FamilyTree("Famille Test", enfant);

        // Ajouter les membres à l'arbre généalogique
        Person extraPerson = new Person("membre 8", "membre 8", Enumerations.Gender.FEMALE);

        familyTree2.addMother(enfant, pere);
        familyTree2.addPartner(pere, mere);
        familyTree2.addChild(mere, soeur);
        familyTree2.addFather(pere, grandPere);
        familyTree2.addChild(grandPere, oncle);
        familyTree2.addChild(mere, extraPerson);


        //Vérifier si les deux arbres sont équivalents
        assertTrue(familyTree.isEquivalant(familyTree2));


        //creation d'un autre arbre non equivalant
        FamilyTree familyTree3 = new FamilyTree("Famille Test", enfant);

        // Ajouter les membres à l'arbre généalogique

        familyTree3.addMother(enfant, pere);
        familyTree3.addPartner(pere, mere);
        familyTree3.addChild(mere, soeur);
        familyTree3.addFather(mere, grandPere);
        familyTree3.addChild(grandPere, oncle);
        familyTree3.addChild(oncle, cousin);

        //Vérifier si les deux arbres sont équivalents
        assertFalse(familyTree.isEquivalant(familyTree3));

    }

    @Test
    void getFather() {
        Person pere = new Person("Membre 1", "Membre 1", Enumerations.Gender.MALE);
        Person mere = new Person("Membre 2", "Membre 2", Enumerations.Gender.FEMALE);
        Person enfant = new Person("membre 3", "membre 3" , Enumerations.Gender.MALE);
        Person soeur = new Person("membre 4", "membre 4" , Enumerations.Gender.FEMALE);
        Person grandPere = new Person("membre 5", "membre 5" , Enumerations.Gender.MALE);
        Person oncle = new Person("membre 6", "membre 6",Enumerations.Gender.MALE );
        Person cousin = new Person("membre 7", "membre 7", Enumerations.Gender.FEMALE);

        // Créer une instance de FamilyTree
        FamilyTree familyTree = new FamilyTree("Famille Test", pere);

        // Ajouter les membres à l'arbre généalogique
        familyTree.addChild(pere, enfant);
        familyTree.addChild(pere, soeur);
        familyTree.addFather(pere, grandPere);
        familyTree.addPartner(pere, mere);
        familyTree.addChild(grandPere, oncle);
        familyTree.addChild(oncle, cousin);

        assertEquals(grandPere, familyTree.getFather(pere));
        assertEquals(pere, familyTree.getFather(enfant));
        assertEquals(pere, familyTree.getFather(soeur));
        assertEquals(oncle, familyTree.getFather(cousin));

    }

    @Test
    void getMother() {
        Person pere = new Person("Membre 1", "Membre 1", Enumerations.Gender.MALE);
        Person mere = new Person("Membre 2", "Membre 2", Enumerations.Gender.FEMALE);
        Person enfant = new Person("membre 3", "membre 3" , Enumerations.Gender.MALE);
        Person soeur = new Person("membre 4", "membre 4" , Enumerations.Gender.FEMALE);
        Person grandPere = new Person("membre 5", "membre 5" , Enumerations.Gender.MALE);
        Person tante = new Person("membre 6", "membre 6",Enumerations.Gender.FEMALE );
        Person cousin = new Person("membre 7", "membre 7", Enumerations.Gender.FEMALE);

        // Créer une instance de FamilyTree
        FamilyTree familyTree = new FamilyTree("Famille Test", pere);

        // Ajouter les membres à l'arbre généalogique
        familyTree.addChild(pere, enfant);
        familyTree.addChild(pere, soeur);
        familyTree.addFather(pere, grandPere);
        familyTree.addPartner(pere, mere);
        familyTree.addChild(grandPere, tante);
        familyTree.addChild(tante, cousin);

        assertEquals(mere, familyTree.getMother(enfant));
        assertEquals(mere, familyTree.getMother(soeur));
        assertEquals(tante, familyTree.getMother(cousin));

    }

    @Test
    void getPartner() {
        Person pere = new Person("Membre 1", "Membre 1", Enumerations.Gender.MALE);
        Person mere = new Person("Membre 2", "Membre 2", Enumerations.Gender.FEMALE);
        Person enfant = new Person("membre 3", "membre 3" , Enumerations.Gender.MALE);
        Person soeur = new Person("membre 4", "membre 4" , Enumerations.Gender.FEMALE);
        Person grandPere = new Person("membre 5", "membre 5" , Enumerations.Gender.MALE);
        Person tante = new Person("membre 6", "membre 6",Enumerations.Gender.FEMALE );
        Person cousin = new Person("membre 7", "membre 7", Enumerations.Gender.FEMALE);
        Person oncle = new Person("membre 8", "membre 8", Enumerations.Gender.MALE);

        // Créer une instance de FamilyTree
        FamilyTree familyTree = new FamilyTree("Famille Test", pere);

        // Ajouter les membres à l'arbre généalogique
        familyTree.addChild(pere, enfant);
        familyTree.addChild(pere, soeur);
        familyTree.addFather(pere, grandPere);
        familyTree.addMother(enfant, mere);
        familyTree.addChild(grandPere, tante);
        familyTree.addChild(tante, cousin);
        familyTree.addFather(cousin, oncle);

        assertEquals(mere, familyTree.getPartner(pere));
        assertEquals(pere, familyTree.getPartner(mere));
        assertEquals(tante, familyTree.getPartner(oncle));
        assertEquals(oncle, familyTree.getPartner(tante));



    }

    @Test
    void getParents() {
        Person pere = new Person("Membre 1", "Membre 1", Enumerations.Gender.MALE);
        Person mere = new Person("Membre 2", "Membre 2", Enumerations.Gender.FEMALE);
        Person enfant = new Person("membre 3", "membre 3" , Enumerations.Gender.MALE);
        Person soeur = new Person("membre 4", "membre 4" , Enumerations.Gender.FEMALE);
        Person grandPere = new Person("membre 5", "membre 5" , Enumerations.Gender.MALE);
        Person tante = new Person("membre 6", "membre 6",Enumerations.Gender.FEMALE );
        Person cousin = new Person("membre 7", "membre 7", Enumerations.Gender.FEMALE);
        Person oncle = new Person("membre 8", "membre 8", Enumerations.Gender.MALE);

        // Créer une instance de FamilyTree
        FamilyTree familyTree = new FamilyTree("Famille Test", pere);

        // Ajouter les membres à l'arbre généalogique
        familyTree.addChild(pere, enfant);
        familyTree.addChild(pere, soeur);
        familyTree.addFather(pere, grandPere);
        familyTree.addMother(enfant, mere);
        familyTree.addChild(grandPere, tante);
        familyTree.addChild(tante, cousin);
        familyTree.addFather(cousin, oncle);

        assertTrue(familyTree.getParents(enfant).contains(pere));
        assertTrue(familyTree.getParents(enfant).contains(mere));
        assertTrue(familyTree.getParents(cousin).contains(tante));
        assertTrue(familyTree.getParents(cousin).contains(oncle));
        assertTrue(familyTree.getParents(pere).contains(grandPere));

    }

    @Test
    void getChildren() {

        Person pere = new Person("Membre 1", "Membre 1", Enumerations.Gender.MALE);
        Person mere = new Person("Membre 2", "Membre 2", Enumerations.Gender.FEMALE);
        Person enfant = new Person("membre 3", "membre 3" , Enumerations.Gender.MALE);
        Person soeur = new Person("membre 4", "membre 4" , Enumerations.Gender.FEMALE);
        Person grandPere = new Person("membre 5", "membre 5" , Enumerations.Gender.MALE);
        Person tante = new Person("membre 6", "membre 6",Enumerations.Gender.FEMALE );
        Person cousin = new Person("membre 7", "membre 7", Enumerations.Gender.FEMALE);
        Person oncle = new Person("membre 8", "membre 8", Enumerations.Gender.MALE);

        // Créer une instance de FamilyTree
        FamilyTree familyTree = new FamilyTree("Famille Test", pere);

        // Ajouter les membres à l'arbre généalogique
        familyTree.addChild(pere, enfant);
        familyTree.addChild(pere, soeur);
        familyTree.addFather(pere, grandPere);
        familyTree.addMother(enfant, mere);
        familyTree.addChild(grandPere, tante);
        familyTree.addChild(tante, cousin);
        familyTree.addFather(cousin, oncle);

        assertTrue(familyTree.getChildren(pere).contains(enfant));
        assertTrue(familyTree.getChildren(pere).contains(soeur));
        assertTrue(familyTree.getChildren(grandPere).contains(tante));
        assertTrue(familyTree.getChildren(tante).contains(cousin));
        assertFalse(familyTree.getChildren(cousin).contains(oncle));


    }

    @Test
    void getGrandParents() {

        Person pere = new Person("Membre 1", "Membre 1", Enumerations.Gender.MALE);
        Person mere = new Person("Membre 2", "Membre 2", Enumerations.Gender.FEMALE);
        Person enfant = new Person("membre 3", "membre 3" , Enumerations.Gender.MALE);
        Person soeur = new Person("membre 4", "membre 4" , Enumerations.Gender.FEMALE);
        Person grandPere = new Person("membre 5", "membre 5" , Enumerations.Gender.MALE);
        Person tante = new Person("membre 6", "membre 6",Enumerations.Gender.FEMALE );
        Person cousin = new Person("membre 7", "membre 7", Enumerations.Gender.FEMALE);
        Person oncle = new Person("membre 8", "membre 8", Enumerations.Gender.MALE);

        // Créer une instance de FamilyTree
        FamilyTree familyTree = new FamilyTree("Famille Test", pere);

        // Ajouter les membres à l'arbre généalogique
        familyTree.addChild(pere, enfant);
        familyTree.addChild(pere, soeur);
        familyTree.addFather(pere, grandPere);
        familyTree.addMother(enfant, mere);
        familyTree.addChild(grandPere, tante);
        familyTree.addChild(tante, cousin);
        familyTree.addFather(cousin, oncle);

        assertTrue(familyTree.getGrandParents(enfant).contains(grandPere));
        assertFalse(familyTree.getGrandParents(enfant).contains(tante));
        assertTrue(familyTree.getGrandParents(cousin).contains(grandPere));
        assertFalse(familyTree.getGrandParents(cousin).contains(tante));
        assertFalse(familyTree.getGrandParents(pere).contains(grandPere));
        assertFalse(familyTree.getGrandParents(pere).contains(tante));
        assertTrue(familyTree.getGrandParents(soeur).contains(grandPere));
    }

    @Test
    void getGrandChildren() {
        Person pere = new Person("Membre 1", "Membre 1", Enumerations.Gender.MALE);
        Person mere = new Person("Membre 2", "Membre 2", Enumerations.Gender.FEMALE);
        Person enfant = new Person("membre 3", "membre 3" , Enumerations.Gender.MALE);
        Person soeur = new Person("membre 4", "membre 4" , Enumerations.Gender.FEMALE);
        Person grandPere = new Person("membre 5", "membre 5" , Enumerations.Gender.MALE);
        Person tante = new Person("membre 6", "membre 6",Enumerations.Gender.FEMALE );
        Person cousin = new Person("membre 7", "membre 7", Enumerations.Gender.FEMALE);
        Person oncle = new Person("membre 8", "membre 8", Enumerations.Gender.MALE);

        // Créer une instance de FamilyTree
        FamilyTree familyTree = new FamilyTree("Famille Test", pere);

        // Ajouter les membres à l'arbre généalogique
        familyTree.addChild(pere, enfant);
        familyTree.addChild(pere, soeur);
        familyTree.addFather(pere, grandPere);
        familyTree.addMother(enfant, mere);
        familyTree.addChild(grandPere, tante);
        familyTree.addChild(tante, cousin);
        familyTree.addFather(cousin, oncle);

        assertTrue(familyTree.getGrandChildren(grandPere).contains(enfant));
        assertTrue(familyTree.getGrandChildren(grandPere).contains(soeur));
        assertTrue(familyTree.getGrandChildren(grandPere).contains(cousin));
        assertFalse(familyTree.getGrandChildren(grandPere).contains(pere));
        assertFalse(familyTree.getGrandChildren(grandPere).contains(tante));
        assertFalse(familyTree.getGrandChildren(grandPere).contains(mere));

    }

    @Test
    void getSiblings() {
        Person pere = new Person("Membre 1", "Membre 1", Enumerations.Gender.MALE);
        Person mere = new Person("Membre 2", "Membre 2", Enumerations.Gender.FEMALE);
        Person enfant = new Person("membre 3", "membre 3" , Enumerations.Gender.MALE);
        Person soeur = new Person("membre 4", "membre 4" , Enumerations.Gender.FEMALE);
        Person grandPere = new Person("membre 5", "membre 5" , Enumerations.Gender.MALE);
        Person tante = new Person("membre 6", "membre 6",Enumerations.Gender.FEMALE );
        Person cousin = new Person("membre 7", "membre 7", Enumerations.Gender.FEMALE);
        Person oncle = new Person("membre 8", "membre 8", Enumerations.Gender.MALE);

        // Créer une instance de FamilyTree
        FamilyTree familyTree = new FamilyTree("Famille Test", pere);

        // Ajouter les membres à l'arbre généalogique
        familyTree.addChild(pere, enfant);
        familyTree.addChild(pere, soeur);
        familyTree.addFather(pere, grandPere);
        familyTree.addMother(enfant, mere);
        familyTree.addChild(grandPere, tante);
        familyTree.addChild(tante, cousin);
        familyTree.addFather(cousin, oncle);

        assertTrue(familyTree.getSiblings(soeur).contains(enfant));
        assertTrue(familyTree.getSiblings(enfant).contains(soeur));
        assertFalse(familyTree.getSiblings(enfant).contains(cousin));
        assertFalse(familyTree.getSiblings(enfant).contains(tante));
        assertFalse(familyTree.getSiblings(enfant).contains(pere));
        assertTrue(familyTree.getSiblings(pere).contains(tante));
    }

    @Test
    void getUnclesAndAunts() {

        Person pere = new Person("Membre 1", "Membre 1", Enumerations.Gender.MALE);
        Person mere = new Person("Membre 2", "Membre 2", Enumerations.Gender.FEMALE);
        Person enfant = new Person("membre 3", "membre 3" , Enumerations.Gender.MALE);
        Person soeur = new Person("membre 4", "membre 4" , Enumerations.Gender.FEMALE);
        Person grandPere = new Person("membre 5", "membre 5" , Enumerations.Gender.MALE);
        Person tante = new Person("membre 6", "membre 6",Enumerations.Gender.FEMALE );
        Person cousin = new Person("membre 7", "membre 7", Enumerations.Gender.FEMALE);
        Person oncle = new Person("membre 8", "membre 8", Enumerations.Gender.MALE);

        // Créer une instance de FamilyTree
        FamilyTree familyTree = new FamilyTree("Famille Test", pere);

        // Ajouter les membres à l'arbre généalogique
        familyTree.addChild(pere, enfant);
        familyTree.addChild(pere, soeur);
        familyTree.addFather(pere, grandPere);
        familyTree.addMother(enfant, mere);
        familyTree.addChild(grandPere, tante);
        familyTree.addChild(tante, cousin);
        familyTree.addFather(cousin, oncle);

        assertTrue(familyTree.getUnclesAndAunts(enfant).contains(tante));
        assertTrue(familyTree.getUnclesAndAunts(cousin).contains(pere));

        assertFalse(familyTree.getUnclesAndAunts(enfant).contains(grandPere));
        assertFalse(familyTree.getUnclesAndAunts(enfant).contains(mere));

    }

    @Test
    void getNephewsAndNieces() {
        Person pere = new Person("Membre 1", "Membre 1", Enumerations.Gender.MALE);
        Person mere = new Person("Membre 2", "Membre 2", Enumerations.Gender.FEMALE);
        Person enfant = new Person("membre 3", "membre 3" , Enumerations.Gender.MALE);
        Person soeur = new Person("membre 4", "membre 4" , Enumerations.Gender.FEMALE);
        Person grandPere = new Person("membre 5", "membre 5" , Enumerations.Gender.MALE);
        Person tante = new Person("membre 6", "membre 6",Enumerations.Gender.FEMALE );
        Person cousin = new Person("membre 7", "membre 7", Enumerations.Gender.FEMALE);
        Person oncle = new Person("membre 8", "membre 8", Enumerations.Gender.MALE);

        // Créer une instance de FamilyTree
        FamilyTree familyTree = new FamilyTree("Famille Test", pere);

        // Ajouter les membres à l'arbre généalogique
        familyTree.addChild(pere, enfant);
        familyTree.addChild(pere, soeur);
        familyTree.addFather(pere, grandPere);
        familyTree.addMother(enfant, mere);
        familyTree.addChild(grandPere, tante);
        familyTree.addChild(tante, cousin);
        familyTree.addFather(cousin, oncle);

        assertTrue(familyTree.getNephewsAndNieces(tante).contains(enfant));
        assertTrue(familyTree.getNephewsAndNieces(tante).contains(soeur));
        assertTrue(familyTree.getNephewsAndNieces(pere).contains(cousin));
        assertFalse(familyTree.getNephewsAndNieces(pere).contains(enfant));
        assertFalse(familyTree.getNephewsAndNieces(pere).contains(soeur));
        assertFalse(familyTree.getNephewsAndNieces(tante).contains(cousin));



    }

    @Test
    void getCousins() {
        Person pere = new Person("Membre 1", "Membre 1", Enumerations.Gender.MALE);
        Person mere = new Person("Membre 2", "Membre 2", Enumerations.Gender.FEMALE);
        Person enfant = new Person("membre 3", "membre 3" , Enumerations.Gender.MALE);
        Person soeur = new Person("membre 4", "membre 4" , Enumerations.Gender.FEMALE);
        Person grandPere = new Person("membre 5", "membre 5" , Enumerations.Gender.MALE);
        Person tante = new Person("membre 6", "membre 6",Enumerations.Gender.FEMALE );
        Person cousin = new Person("membre 7", "membre 7", Enumerations.Gender.FEMALE);
        Person oncle = new Person("membre 8", "membre 8", Enumerations.Gender.MALE);

        // Créer une instance de FamilyTree
        FamilyTree familyTree = new FamilyTree("Famille Test", pere);

        // Ajouter les membres à l'arbre généalogique
        familyTree.addChild(pere, enfant);
        familyTree.addChild(pere, soeur);
        familyTree.addFather(pere, grandPere);
        familyTree.addMother(enfant, mere);
        familyTree.addChild(grandPere, tante);
        familyTree.addChild(tante, cousin);
        familyTree.addFather(cousin, oncle);

        assertTrue(familyTree.getCousins(enfant).contains(cousin));
        assertTrue(familyTree.getCousins(soeur).contains(cousin));
        assertTrue(familyTree.getCousins(cousin).contains(soeur));
        assertTrue(familyTree.getCousins(cousin).contains(enfant));
        assertFalse(familyTree.getCousins(enfant).contains(soeur));
        assertFalse(familyTree.getCousins(enfant).contains(tante));
        assertFalse(familyTree.getCousins(enfant).contains(pere));
    }

    @Test
    void removeMemberFromTree() {
        Person pere = new Person("Membre 1", "Membre 1", Enumerations.Gender.MALE);
        Person mere = new Person("Membre 2", "Membre 2", Enumerations.Gender.FEMALE);
        Person enfant = new Person("membre 3", "membre 3" , Enumerations.Gender.MALE);
        Person soeur = new Person("membre 4", "membre 4" , Enumerations.Gender.FEMALE);
        Person grandPere = new Person("membre 5", "membre 5" , Enumerations.Gender.MALE);
        Person tante = new Person("membre 6", "membre 6",Enumerations.Gender.FEMALE );
        Person cousin = new Person("membre 7", "membre 7", Enumerations.Gender.FEMALE);
        Person oncle = new Person("membre 8", "membre 8", Enumerations.Gender.MALE);

        // Créer une instance de FamilyTree
        FamilyTree familyTree = new FamilyTree("Famille Test", pere);

        // Ajouter les membres à l'arbre généalogique
        familyTree.addChild(pere, enfant);
        familyTree.addChild(pere, soeur);
        familyTree.addFather(pere, grandPere);
        familyTree.addMother(enfant, mere);
        familyTree.addChild(grandPere, tante);
        familyTree.addChild(tante, cousin);
        familyTree.addFather(cousin, oncle);

        familyTree.removeMemberFromTree(cousin);
        familyTree.removeMemberFromTree(tante);

        assertFalse(familyTree.getMembers().contains(cousin));
        assertFalse(familyTree.getMembers().contains(tante));
        assertFalse(familyTree.getChildren(grandPere).contains(tante));
        assertFalse(familyTree.getChildren(tante).contains(cousin));
        assertFalse(familyTree.getUnclesAndAunts(enfant).contains(tante));
        assertFalse(familyTree.getNephewsAndNieces(tante).contains(enfant));
        assertFalse(familyTree.getCousins(enfant).contains(cousin));




    }

    @Test
    void getNode() {



    }

    @Test
    void getChildrenNodes() {
    }


}