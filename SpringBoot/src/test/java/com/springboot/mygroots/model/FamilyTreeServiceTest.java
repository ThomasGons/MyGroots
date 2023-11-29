package com.springboot.mygroots.model;


import com.springboot.mygroots.model.FamilyTree;
import com.springboot.mygroots.model.Person;
import com.springboot.mygroots.repository.FamilyTreeRepository;
import com.springboot.mygroots.service.FamilyTreeService;
import com.springboot.mygroots.service.PersonService;
import com.springboot.mygroots.utils.Enumerations;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

@DataMongoTest
public class FamilyTreeServiceTest {

	@InjectMocks
	private FamilyTreeService familyTreeService;

	@Mock
	private FamilyTreeRepository familyTreeRepository;

	@Mock
	private PersonService personService;

	@Test
	public void testSaveFamilyTree() {
		Person person1 = new Person("John", "Doe", Enumerations.Gender.FEMALE);
		FamilyTree familyTree = new FamilyTree("doe",person1);
		familyTreeService.saveFamilyTree(familyTree);

		Mockito.verify(familyTreeRepository, times(1)).save(familyTree);
	}

	@Test
	public void testDeleteFamilyTree() {
		Person person1 = new Person("John", "Doe", Enumerations.Gender.FEMALE);
		FamilyTree familyTree = new FamilyTree("doe",person1);
		familyTreeService.deleteFamilyTree(familyTree);

		Mockito.verify(familyTreeRepository, times(1)).delete(familyTree);
	}

	@Test
	public void testGetFamilyTreeById() {
		String familyTreeId = "1";
		Person person1 = new Person("John", "Doe", Enumerations.Gender.FEMALE);
		FamilyTree familyTree = new FamilyTree("doe",person1);
		Mockito.when(familyTreeRepository.findById(familyTreeId)).thenReturn(Optional.of(familyTree));

		FamilyTree result = familyTreeService.getFamilyTreeById(familyTreeId);

		assertEquals(familyTree, result);
	}

	@Test
	public void testGetFamilyTreeByOwner() {
		Person person1 = new Person("John", "Doe", Enumerations.Gender.FEMALE);
		FamilyTree familyTree = new FamilyTree("doe",person1);
		Mockito.when(familyTreeRepository.findFamilyTreeByOwner(person1)).thenReturn(familyTree);

		FamilyTree result = familyTreeService.getFamilyTreeByOwner(person1);

		assertEquals(familyTree, result);
	}

	@Test
	public void testUpdateFamilyTree() {
		Person person1 = new Person("John", "Doe", Enumerations.Gender.FEMALE);
		Person person2 = new Person("jane", "Doe", Enumerations.Gender.MALE);
		FamilyTree familyTree = new FamilyTree("doe",person1);
		familyTree.addChild(person1, person2);


		familyTreeService.updateFamilyTree(familyTree);

		Mockito.verify(personService, times(2)).updatePerson(Mockito.any());
		Mockito.verify(familyTreeRepository, times(1)).save(familyTree);
	}

	@Test
	public void testGetAllFamilyTrees() {
		Person person1 = new Person("John", "Doe", Enumerations.Gender.FEMALE);
		Person person2 = new Person("jane", "Doe", Enumerations.Gender.MALE);
		List<FamilyTree> expectedFamilyTrees = Arrays.asList(new FamilyTree("doe",person1), new FamilyTree("dane",person2));
		Mockito.when(familyTreeRepository.findAll()).thenReturn(expectedFamilyTrees);

		List<FamilyTree> result = familyTreeService.getAllFamilyTrees();

		assertEquals(expectedFamilyTrees, result);
	}

	@Test
	public void testRemoveFamilyTree() {
		Person person2 = new Person("jane", "Doe", Enumerations.Gender.MALE);
		FamilyTree familyTree = new FamilyTree("doe",person2);
		familyTreeService.removeFamilyTree(familyTree);

		Mockito.verify(familyTreeRepository, times(1)).delete(familyTree);
	}
}
