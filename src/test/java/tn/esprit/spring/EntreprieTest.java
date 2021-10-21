package tn.esprit.spring;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.services.EntrepriseServiceImpl;
import tn.esprit.spring.services.IEntrepriseService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntreprieTest {
	
	@Autowired
	IEntrepriseService entreservice;
	
	
	private static final Logger L = LogManager.getLogger(EntrepriseServiceImpl.class);
	
	@Test
	public void ajouterEntrepriseTest  ()
	{
		Entreprise e =new Entreprise("Dev", "junit2");
		int entreId = entreservice.ajouterEntreprise(e);
		assertThat(entreId).isGreaterThan(0);
		L.info("Entreprise added successfully!");
	}
	
	
	@Test
	public void affecterDepartementAEntrepriseTest ()
	{
		Entreprise e =new Entreprise("Dev", "junit3");
		int entreId = entreservice.ajouterEntreprise(e);
		Departement d = new Departement("departement2");
		int depId = entreservice.ajouterDepartement(d);
		entreservice.affecterDepartementAEntreprise(depId,entreId);
		L.info("Departement with id=" + depId + " added successfully to Entreprise with id=" + entreId);
	   
	}
	
	@Test
	public void deleteEntreprisebyId ()
	{
		Entreprise e =new Entreprise("Devfor", "junit5");
		int entreId = entreservice.ajouterEntreprise(e);
		entreservice.deleteEntrepriseById(entreId);
		L.info("Entreprise deleted successfully!");
	}
	
	@Test
	public void getEntreprisebyId ()
	{
		Entreprise e =new Entreprise("Devfor", "junit4");
	 	int entreId = entreservice.ajouterEntreprise(e);
		entreservice.getEntrepriseById(entreId);
		assertThat(e).isNotNull();
		L.info("Entreprise with id=" + entreId +" geted successfully!");
	}
	

	
	
	
}