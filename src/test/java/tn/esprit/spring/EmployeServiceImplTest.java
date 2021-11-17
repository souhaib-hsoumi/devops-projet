package tn.esprit.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.services.IEmployeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeServiceImplTest {
	
	@Autowired
	IEmployeService IEmployeservice ;
	


	int idemp=0;
	@Test
    public void testAjouterEmploye(){
		Employe employee = new Employe("test", "junit", "test@junit.com", true, Role.TECHNICIEN);

		IEmployeservice.ajouterEmploye(employee);
    }

	
	@Test 
	public void testGetNombreEmployeJPQL() {
		IEmployeservice.getNombreEmployeJPQL();
	}
	@Test 
	public void testGetAllEmployeNamesJPQL() {
		IEmployeservice.getAllEmployeNamesJPQL();
	}

	@Test
	public void testDeleteEmployeById() {
		IEmployeservice.deleteEmployeById(idemp);

		
	}
}
