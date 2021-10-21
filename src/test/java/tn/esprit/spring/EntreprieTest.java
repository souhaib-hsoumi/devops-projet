package tn.esprit.spring;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.services.IEntrepriseService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EntreprieTest {
	
	
	

	@Autowired
	IEntrepriseService entreService;
	
	@Autowired
	EntrepriseRepository entreRep;
	
	@Autowired
	DepartementRepository departementRerpository;


	Entreprise entreprise;
	
	@Test
	public void ajouterEntrepriseTest  ()
	{
		Entreprise entrepTest = new Entreprise ("entrepriseTest1","raisonTest1");
		int entreId = entreService.ajouterEntreprise(entrepTest);
		Assert.assertTrue(entreRep.findById(entreId).get()!= null);
		Assert.assertTrue(entreRep.findById(entreId).get().getName().equals("entrepriseTest1"));
	
		entreService.deleteEntrepriseById(entreId);	
	}
	
	
	@Test
	public void affecterDepartementAEntrepriseTest ()
	{
		Entreprise entreprise2 = new Entreprise ("entrepriseTest2","raisonTest2");
		Departement department = new Departement("departmenTest1");
		int entreId=entreService.ajouterEntreprise(entreprise2);
		int depId=entreService.ajouterDepartement(department);
	
		entreService.affecterDepartementAEntreprise(depId, entreId);
        List<String> result = entreService.getAllDepartementsNamesByEntreprise(entreId);
		assertThat(result).containsExactly("departmenTest1");
		assertThat(result).size().isEqualTo(1);
		entreService.deleteDepartementById(depId);
		entreService.deleteEntrepriseById(entreId);
	   
	   
	}
	
	@Test
	public void deleteEntreprisebyIdTest ()
	{
		 Entreprise entreprise3 = new Entreprise ("entrepriseTest3","raisonTest3");
			int idEntreprise=entreService.ajouterEntreprise(entreprise3);
			Assert.assertTrue(entreRep.findById(idEntreprise).isPresent());
			entreService.deleteEntrepriseById(idEntreprise);
			Assert.assertFalse(entreRep.findById(idEntreprise).isPresent());
	}
	
	@Test
	public void getEntreprisebyIdTest ()
	{
		Entreprise entreprise4 = new Entreprise ("entrepriseTest4","raisonTest4");
		int entreId = entreService.ajouterEntreprise(entreprise4);
		entreService.getEntrepriseById(entreId);
	
		assertThat(entreprise).isNull();
		entreService.deleteEntrepriseById(entreId);
	}
	

	
}