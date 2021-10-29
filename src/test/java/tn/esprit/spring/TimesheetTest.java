package tn.esprit.spring;
import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;
import tn.esprit.spring.services.EmployeServiceImpl;
import tn.esprit.spring.services.EntrepriseServiceImpl;
import tn.esprit.spring.services.TimesheetServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TimesheetTest {
	private static final Logger l = (Logger) LogManager.getLogger(TimesheetTest.class);
	
	@Autowired
	TimesheetServiceImpl ts;
	
	@Autowired
	EmployeRepository emr;
	
	@Autowired 
	EmployeServiceImpl ems;
	
	@Autowired
	EntrepriseServiceImpl es;
	
	@Autowired
	TimesheetRepository tr;
	
	@Autowired
	MissionRepository mr;
	
	@Autowired
	DepartementRepository dr;
	
	@Test
	public void ajouterMissionTEST() {
		try{
		Mission M = new Mission("Mission1","20/10/2021");
		int ID = ts.ajouterMission(M);
		assertNotNull(ID);
		mr.delete(mr.findById(ID).get());	
		l.info("Mission added successfully.");
	} catch (NullPointerException e) {
		l.error(e.getMessage());
	}
		
	}
	@Test
	public void affecterMissionADepartementTEST() {
		try {
			Mission M = new Mission("Mission1","20/10/2021");
			int IDM = ts.ajouterMission(M);
			Departement D = new Departement("Info");
			int IDD = es.ajouterDepartement(D);
			assertNull(D.getEntreprise());
			ts.affecterMissionADepartement(IDM, IDD);
			assertNotNull(D.getEntreprise().getId());
			assertEquals(D.getEntreprise().getId(),IDD);
			es.deleteDepartementById(IDD);
			es.deleteEntrepriseById(IDM);
			l.info("Mission affected successfully.");
			} catch (NullPointerException e) {
				l.error(e.getMessage());
			}
		System.out.println("Mission affected successfully.");
		}
	
	@Test
	public void ajouterTimesheetTEST(){
		try{
		Date date = new Date();  
		TimesheetPK timesheetPK = new TimesheetPK();
		timesheetPK.setDateDebut(date);
		timesheetPK.setDateFin(date);
		timesheetPK.setIdEmploye(1);
		timesheetPK.setIdMission(7);
		
		Timesheet timesheet = new Timesheet();
		timesheet.setTimesheetPK(timesheetPK);
		timesheet.setValide(false); 
		assertNotNull(timesheet);
		tr.save(timesheet);
		l.info("Timesheet added successfully.");
	} catch (NullPointerException e) {
		l.error(e.getMessage());
	}
	}
	
	@Test
	public void findAllMissionByEmployeJPQLTEST(){
		try{
	tr.findAllMissionByEmployeJPQL(1);
	List<Mission> Missions = tr.findAllMissionByEmployeJPQL(1);
	assertNotNull(Missions);
	l.info("Missions found successfully.");
		} catch (NullPointerException e) {
			l.error(e.getMessage());
		}
}
	@Test
	public void getAllEmployeByMissionTEST()
	{
		try{
			tr.getAllEmployeByMission(7);
			List<Employe> Employees = tr.getAllEmployeByMission(7);
			assertNotNull(Employees);
			l.info("Employees found successfully.");
		}catch (NullPointerException e){
			l.error(e.getMessage());
		}
	}
	
	@Test 
	public void validerTimesheetTEST(){
		Employe validateur = emr.findById(2).get();
		Mission mission = mr.findById(7).get();
		System.out.println("nhar azra9");

		if(!validateur.getRole().equals(Role.CHEF_DEPARTEMENT)){
			assertEquals(validateur.getRole(),Role.CHEF_DEPARTEMENT);
			System.out.println("WRONG USER");
			return;
		}
		boolean chefDeLaMission = false;
		for(Departement dep : validateur.getDepartements()){
			if(dep.getId() == mission.getDepartement().getId()){
				System.out.println("test bloc UwU");
				chefDeLaMission = true;
				break;
			}
		}
		if(!chefDeLaMission){
			System.out.println("WRONG USER >.<");
			return;
		}
		Date date = new Date(); 
		TimesheetPK timesheetPK = new TimesheetPK(7, 2, date, date);
		Timesheet timesheet =tr.findBytimesheetPK(timesheetPK);
		System.out.println(timesheet);
		assertNotNull(timesheet);
		timesheet.setValide(true);
		
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("dateDebut : " + dateFormat.format(timesheet.getTimesheetPK().getDateDebut()));
		}

}
