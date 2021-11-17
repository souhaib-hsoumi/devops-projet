package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;

import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
public class EmployeServiceImpl implements IEmployeService {

	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	
	@Autowired
	TimesheetRepository timesheetRepository;
	
	 Logger logger = LoggerFactory.getLogger(EmployeServiceImpl.class);
	public int ajouterEmploye(Employe employe) {
		try {
			logger.debug("Process ajout d'un employe");
			employeRepository.save(employe);
			logger.info("Employe ajouté avec success! ");
		}catch(Exception e) {
			logger.error("Erreur dans ajouterEmploye():"+ e);
		}finally {
			logger.info("l'ajout d'un employé est términé");
		}
		
		return employe.getId();
	}

	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		try {
			logger.debug("Process de mise ajour de l'email d'un employe");
		Employe employe = employeRepository.findById(employeId).get();
		employe.setEmail(email);
		employeRepository.save(employe);
		logger.info("l'employé "+employe.getNom()+"a changé son email à"+email );
		}catch(Exception e) {
			logger.error("Erreur dansmettreAjourEmailByEmployeId:"+ e);
		}finally {
			logger.info("mettreAjourEmailByEmployeId est terminée");
		}

	}

	@Transactional	
	public void affecterEmployeADepartement(int employeId, int depId) {
		Departement depManagedEntity = deptRepoistory.findById(depId).get();
		Employe employeManagedEntity = employeRepository.findById(employeId).get();

		if(depManagedEntity.getEmployes() == null){
			logger.info("verifier s'il y'a deja une liste d'employe et creation d'une nouvelle s'il n'ya pas");
			List<Employe> employes = new ArrayList<>();
			employes.add(employeManagedEntity);
			depManagedEntity.setEmployes(employes);
		}else{

			depManagedEntity.getEmployes().add(employeManagedEntity);

		}
		logger.info("affecterEmployeADepartement est términé");

	}


	public String getEmployePrenomById(int employeId) {
		logger.debug("getEmployePrenomById est en cours");
		Employe employeManagedEntity = employeRepository.findById(employeId).get();
		logger.info("find l'mploye par son id ");
		return employeManagedEntity.getPrenom();
	}

	public void deleteEmployeById(int employeId) {
		if (!(Integer.toString(employeId).equals(""))) {
		logger.info("Deleting Employe");
		try {
		Optional<Employe> contratManagedEntity = employeRepository.findById(employeId);
		if(contratManagedEntity.isPresent()) {
			Employe contrat = contratManagedEntity.get();
			employeRepository.delete(contrat);
		}
		logger.info("Employe Deleted Succefully ! ");
		}catch(Exception e) {
			logger.error(e.toString());
		}
		}else {
			logger.warn("No Employe To Delete ! ");
		}

	}


	public int getNombreEmployeJPQL() {
		logger.debug("getNombreEmployeJPQL() est en cours");
		return employeRepository.countemp();
	}
	
	public List<String> getAllEmployeNamesJPQL() {
		logger.debug("getAllEmployeNamesJPQL() est en cours");
		return employeRepository.employeNames();

	}
	
	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		List<Employe> employes=null;
		try {
			logger.debug("getAllEmployeByEntreprise est en cours");
		employes=employeRepository.getAllEmployeByEntreprisec(entreprise);
		logger.info("liste des employes selon entreprise");
		}catch (Exception e) {
			logger.error("Erreur dans getAllEmployeByEntreprise(): "+ e);
		}
		return employes;
	}


	
	
	public List<Employe> getAllEmployes() {
				logger.debug("affichage de la liste de tout les employes ");
				return (List<Employe>) employeRepository.findAll();
	}

	@Override
	public void desaffecterEmployeDuDepartement(int employeId, int depId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteEmployeById() {
		// TODO Auto-generated method stub
		
	}

}
