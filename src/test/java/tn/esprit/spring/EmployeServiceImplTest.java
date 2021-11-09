package tn.esprit.spring;

import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.List;



import org.apache.logging.log4j.LogManager;


import tn.esprit.spring.entities.Employe;

import tn.esprit.spring.entities.Role;
import tn.esprit.spring.services.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeServiceImplTest {
	
	
	  @Autowired
	 
	IEmployeService emprep; 
	
	private static final Logger l = LogManager.getLogger(EmployeServiceImplTest.class); 
	
	@Test
	public void A_testAddEmploye() throws ParseException {
		Employe e = new Employe("baghouli", "aymen", "aymen.baghouli@esprit.tn", true, Role.ADMINISTRATEUR); 
		Employe employeAdded = emprep.addEmploye(e); 
		Assert.assertEquals(e.getNom(), employeAdded.getNom());
		l.info(" employe ajouté avec succès");
	}
	
	
	@Test
	public void B_testRetrieveAllEmploye() {
		List<Employe> listEmployes = emprep.retrieveAllEmployes(); 
		// if there are 7 users in DB : 
		Assert.assertEquals(25, listEmployes.size());
	}
	
	
	@Test
	public void C_testModifyEmploye() throws ParseException   {
		Employe e = new Employe ("baghouli", "aymen", "aymen.baghouli@esprit.tn", true, Role.ADMINISTRATEUR); 
		Employe employeUpdated  = emprep.updateEmploye(e); 
		Assert.assertEquals(e.getNom(), employeUpdated.getNom());
		l.info(" Employe modifié avec succès");
	}
	
	@Test
	public void AB_testRetrieveEmploye() {
		Employe employeRetrieved = emprep.retrieveEmploye(9); 
		Assert.assertEquals(9, employeRetrieved.getId());
		l.info("retrieve Employe : ", emprep);
	}
	
	
	@Test
	public void D_testDeleteEmploye() {
		emprep.deleteEmploye(38);
		Assert.assertNull(emprep.retrieveEmploye(38));
		l.info(" employe supprimé avec succès");
	} 
	
}
