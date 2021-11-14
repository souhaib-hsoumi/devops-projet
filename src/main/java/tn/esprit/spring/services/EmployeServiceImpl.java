package tn.esprit.spring.services;

import java.util.ArrayList;


import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
public class EmployeServiceImpl implements IEmployeService {

	private static final Logger l = LogManager.getLogger(EmployeServiceImpl.class);
	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;

	public int ajouterEmploye(Employe employe) {
		l.info("START ajouterEmploye ");
		try{
			l.debug(employe.getId());

			l.trace("Debut d'ajout du employe: " + employe.getNom());
			employeRepository.save(employe);
			l.trace("Fin Ajout Employe");

			l.debug("L'employe: " + employe.getNom() + " de l'id: " + employe.getId() + " a été ajoutée avec succé");
		} catch(Exception e) {
			l.error("Error in ajouterEmploye:"  + e);
		}
		l.info("END ajouterEmploye ");

		return employe.getId();
	}
	
	@Override
	public List<Employe> retrieveAllEmployes() {
		l.info("START retrieveAllEmployes ");
		List<Employe> employes = null; 
		try {
			l.trace("Debut de récupérer tous les employes: ");
			employes = (List<Employe>) employeRepository.findAll();  
			for (Employe employes1 : employes) {
				l.debug("user +++ : " , employes1);
			} 
			l.trace("fin de  récupérer tous les employes.");
		}catch (Exception e) {
			l.error("Erreur à retrieveAllEmployes() : " , e);
		}

		return employes;
	}
	
	@Override
	public Employe addEmploye(Employe e) {
		l.info("START addEmploye");
		try{
			l.trace("Début ajouter employe : ");
			employeRepository.save(e);
			l.debug("Employe à ajouter : ", e);
			l.trace("Fin ajouter employe.");
		}catch(Exception err){
			l.error("Erreur à addEmploye() : ", err);
		}
		return  e;
	}
	
	
	@Override
	public void deleteEmploye(int id) {
		
		l.info("START deleteEmploye");
		try{
			l.trace("Début effacer employe : ");
			employeRepository.deleteById(id);
			l.debug("Employe à effacer: ", employeRepository.findById(id));
			l.trace("Fin effacer employe.");
		}catch(Exception err){
			l.error("Erreur à deleteEmploye() : ", err);
		}
		l.info("END deleteEmploye");
	}

	@Override
	public Employe updateEmploye(Employe e) {
		l.info("START updateEmploye");
		try{
			l.trace("Début modifer employe : ");
			employeRepository.save(e);
			l.debug("Employe à modifier : ", e);
			l.trace("Fin modifier employe.");
		}catch(Exception err){
			l.error("Erreur à updateEmploye() : ", err);
		}
		return  e;
	}
	@Override
	public Employe retrieveEmploye(int id) {
		l.info("START retrieveEmploye");
		try{
			l.trace("Début de récupérer l'employe avec l'id = " , id);
			Employe e =  employeRepository.findById(id).orElse(null);
			l.debug("Employe récupéré : ", e);
			l.trace("Fin récupérer l'emloye : " , e);
			return e; 
		}catch(Exception err){
			l.error("Erreur à retrieveEmploye() : ", err);
		}
		return null;
	}

	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		Employe employe = employeRepository.findById(employeId).orElseGet(Employe::new);
		employe.setEmail(email);
		employeRepository.save(employe);

	}

	@Transactional	
	public void affecterEmployeADepartement(int employeId, int depId) {
		Departement depManagedEntity = deptRepoistory.findById(depId).orElseGet(Departement::new);
		Employe employeManagedEntity = employeRepository.findById(employeId).orElseGet(Employe::new);

		if(depManagedEntity.getEmployes() == null){

			List<Employe> employes = new ArrayList<>();
			employes.add(employeManagedEntity);
			depManagedEntity.setEmployes(employes);
		}else{

			depManagedEntity.getEmployes().add(employeManagedEntity);

		}

	}
	@Transactional
	public void desaffecterEmployeDuDepartement(int employeId, int depId)
	{
		Departement dep = deptRepoistory.findById(depId).orElseGet(Departement::new);

		int employeNb = dep.getEmployes().size();
		for(int index = 0; index < employeNb; index++){
			if(dep.getEmployes().get(index).getId() == employeId){
				dep.getEmployes().remove(index);
				break;//a revoir
			}
		}
	}

	public int ajouterContrat(Contrat contrat) {
		contratRepoistory.save(contrat);
		return contrat.getReference();
	}

	public void affecterContratAEmploye(int contratId, int employeId) {
		Contrat contratManagedEntity = contratRepoistory.findById(contratId).orElseGet(Contrat::new);
		Employe employeManagedEntity = employeRepository.findById(employeId).orElseGet(Employe::new);

		contratManagedEntity.setEmploye(employeManagedEntity);
		contratRepoistory.save(contratManagedEntity);
		
	}

	public String getEmployePrenomById(int employeId) {
		Employe employeManagedEntity = employeRepository.findById(employeId).orElseGet(Employe::new);
		return employeManagedEntity.getPrenom();
	}
	public void deleteEmployeById(int employeId)
	{
		Employe employe = employeRepository.findById(employeId).orElseGet(Employe::new);

		//Desaffecter l'employe de tous les departements
		//c'est le bout master qui permet de mettre a jour
		//la table d'association
		for(Departement dep : employe.getDepartements()){
			dep.getEmployes().remove(employe);
		}

		employeRepository.delete(employe);
	}

	public void deleteContratById(int contratId) {
		Contrat contratManagedEntity = contratRepoistory.findById(contratId).orElseGet(Contrat::new);
		contratRepoistory.delete(contratManagedEntity);

	}

	public int getNombreEmployeJPQL() {
		return employeRepository.countemp();
	}
	
	public List<String> getAllEmployeNamesJPQL() {
		return employeRepository.employeNames();

	}
	
	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		return employeRepository.getAllEmployeByEntreprisec(entreprise);
	}

	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		employeRepository.mettreAjourEmailByEmployeIdJPQL(email, employeId);

	}
	public void deleteAllContratJPQL() {
         employeRepository.deleteAllContratJPQL();
	}
	
	public float getSalaireByEmployeIdJPQL(int employeId) {
		return employeRepository.getSalaireByEmployeIdJPQL(employeId);
	}

	public Double getSalaireMoyenByDepartementId(int departementId) {
		return employeRepository.getSalaireMoyenByDepartementId(departementId);
	}
	
	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
			Date dateFin) {
		return timesheetRepository.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
	}

	public List<Employe> getAllEmployes() {
				return (List<Employe>) employeRepository.findAll();
	}

}
