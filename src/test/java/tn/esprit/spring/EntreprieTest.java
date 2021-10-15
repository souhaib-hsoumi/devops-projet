package tn.esprit.spring;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.services.IEntrepriseService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntreprieTest {
	@Autowired
	IEntrepriseService ie ;
	
	@Test
	public void ajouterEntreprise(){
		Entreprise e = new Entreprise();
		Assert.assertNotEquals(0, ie.ajouterEntreprise(e));

	}
	

}
