package tn.esprit.spring.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.services.IEmployeService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeTest {
	
	@Autowired
	IEmployeService es;

	
	@Autowired
	EntrepriseRepository entreRep;
	
	@Autowired
	DepartementRepository departementRerpository;

	Employe employe;
	

	@Test
	public void getEmployePrenombyIdTest ()
	{
		Employe e01 = new Employe ("Nom01","Prenom01");
		int entreId = es.ajouterEmploye(e01);
		es.getEmployePrenomById(entreId);
		
		if(employe !=null ) 		
			es.deleteEmployeById(entreId);
	}
	
	@Test
	public void mettreAjourEmailByEmployeIdTest ()
	{
		String email = "testEmail";
		Employe e02 = new Employe ("Nom02","Prenom02");
		int entreId = es.ajouterEmploye(e02);
		es.mettreAjourEmailByEmployeId(email, entreId);
		
		if(employe != null ) 		
			es.deleteEmployeById(entreId);
	}
		
}