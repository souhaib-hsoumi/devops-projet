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
	public void ajouterEntreprise(){
		Entreprise e = new Entreprise();
		Assert.assertNotEquals(0, entreService.ajouterEntreprise(e));

	}
	@Test
	public void ajouterDepartementTest() {
		Departement depTest = new Departement("production");
		int idDepartement=entreService.ajouterDepartement(depTest);
		Assert.assertTrue(departementRerpository.findById(idDepartement).get()!= null);
		Assert.assertTrue(departementRerpository.findById(idDepartement).get().getName().equals("production"));

		entreService.deleteDepartementById(idDepartement);	
	}
	
	@Test
	public void suprimerDepartementTest() {
		Departement depTest = new Departement("production");
		int idDepartement=entreService.ajouterDepartement(depTest);
		Assert.assertTrue(departementRerpository.findById(idDepartement).isPresent());
		entreService.deleteDepartementById(idDepartement);
		Assert.assertFalse(departementRerpository.findById(idDepartement).isPresent());
	}
	
	@Test
	public void  getAllDepartementsNamesByEntrepriseTest() {
		this.entreprise = new Entreprise();
		this.entreprise.setName("entreprise To find");
		int entreId=entreService.ajouterEntreprise(this.entreprise);
		Departement department= new Departement();
		department.setName("department Test 1");
		int depId=entreService.ajouterDepartement(department);
		
		Departement department2= new Departement();
		department2.setName("department Test 2");
		int depId2=entreService.ajouterDepartement(department2);
		
		entreService.affecterDepartementAEntreprise(depId, entreId);
		entreService.affecterDepartementAEntreprise(depId2, entreId);
		
		List<String> result = entreService.getAllDepartementsNamesByEntreprise(entreId);
		
		assertThat(result).containsExactly("department Test 1","department Test 2");

		assertThat(result).size().isEqualTo(2);
		entreService.deleteDepartementById(depId);
		entreService.deleteDepartementById(depId2);
		entreService.deleteEntrepriseById(entreId);
		
	}
	
	

}
