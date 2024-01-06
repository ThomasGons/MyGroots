package com.springboot.mygroots.model;


import com.springboot.mygroots.repository.FamilyTreeRepository;
import com.springboot.mygroots.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.util.ClassUtils.isPresent;

@DataMongoTest
public class FamilyTreeServiceTest {

	@Autowired
	private FamilyTreeRepository familyTreeRepository;


	@Autowired
	private PersonRepository personRepository;

	@Test
	public void father() {
		Optional<Person> op = personRepository.findById("65986a3d02c0c84b953f800b");
		Person p = op.orElse(null);
		FamilyTree ft = familyTreeRepository.findByOwner(p);
		Person father = ft.getFather(p);
		String trueFatherId = "6598917cd505437b747d97ba";
		assertEquals(trueFatherId, father.getId());
	}

	@Test
	public void getParents() {
		Optional<Person> src_op = personRepository.findById("6598917cd505437b747d97ba");
		Person p = src_op.orElse(null);
		Optional<Person> own_op = personRepository.findById("65986a3d02c0c84b953f800b");
		Person own = own_op.orElse(null);
		FamilyTree ft = familyTreeRepository.findByOwner(own);
		List<Person> parents = ft.getParents(p);
		List <String> parentsId = parents.stream().map(Person::getId).toList();
		List<String> trueParentsId = List.of("65989749d505437b747d97c7", "659891bfd505437b747d97bc");
		assertEquals(trueParentsId.size(), parentsId.size());
		assertTrue(parentsId.containsAll(trueParentsId));
		assertTrue(trueParentsId.containsAll(parentsId));
	}

	@Test
	public void grandParents() {
		Optional<Person> op = personRepository.findById("65986a3d02c0c84b953f800b");
		Person p = op.orElse(null);
		FamilyTree ft = familyTreeRepository.findByOwner(p);
		List<Person> grandParents = ft.getGrandParents(p);
		List <String> grandParentsId = grandParents.stream().map(Person::getId).toList();
		List<String> trueGrandParentsId = List.of("65989749d505437b747d97c7", "659891bfd505437b747d97bc");
		assertEquals(trueGrandParentsId.size(), grandParents.size());
		assertTrue(grandParentsId.containsAll(trueGrandParentsId));
		assertTrue(trueGrandParentsId.containsAll(grandParentsId));
	}

	@Test
	public void children() {
		Optional<Person> src_op = personRepository.findById("6598917cd505437b747d97ba");
		Person p = src_op.orElse(null);
		Optional<Person> own_op = personRepository.findById("65986a3d02c0c84b953f800b");
		Person own = own_op.orElse(null);
		FamilyTree ft = familyTreeRepository.findByOwner(own);
		List<Person> children = ft.getChildren(p);
		List <String> childrenId = children.stream().map(Person::getId).toList();
		List<String> trueChildrenId = List.of("65986a3d02c0c84b953f800b");
		assertEquals(trueChildrenId.size(), children.size());
		assertTrue(childrenId.containsAll(trueChildrenId));
		assertTrue(trueChildrenId.containsAll(childrenId));
	}

	@Test
	public void unclesAndAunts() {
		Optional<Person> op = personRepository.findById("65986a3d02c0c84b953f800b");
		Person p = op.orElse(null);
		FamilyTree ft = familyTreeRepository.findByOwner(p);
		List<Person> unclesAndAunts = ft.getUnclesAndAunts(p);
		List <String> unclesAndAuntsId = unclesAndAunts.stream().map(Person::getId).toList();
		List<String> trueUnclesAndAuntsId = List.of("659891dfd505437b747d97bd", "65989760d505437b747d97c8");
		assertEquals(trueUnclesAndAuntsId.size(), unclesAndAunts.size());
		assertTrue(unclesAndAuntsId.containsAll(trueUnclesAndAuntsId));
		assertTrue(trueUnclesAndAuntsId.containsAll(unclesAndAuntsId));
	}
}

