package tn.esprit.spring.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class TimesheetServiceImpl implements ITimesheetService {

	private static final Logger l = LogManager.getLogger(TimesheetServiceImpl.class);

	@Autowired
	MissionRepository missionRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;
	@Autowired
	EmployeRepository employeRepository;
	
	public int ajouterMission(Mission mission) {
		missionRepository.save(mission);
		return mission.getId();
	}
    
	public void affecterMissionADepartement(int missionId, int depId) {
		l.info("In affecterMissionADepartement");
		l.trace("Message TRACE: In affecterMissionADepartement function");
		Optional<Departement> departementOpt = deptRepoistory.findById(depId);
		Departement departement = null;
		if (departementOpt.isPresent())
			departement = departementOpt.get();
		Optional<Mission> missionOpt = missionRepository.findById(missionId);
		Mission mission = null;
		if (missionOpt.isPresent())
			mission = missionOpt.get();
		if (mission != null){		
		    mission.setDepartement(departement);
		    l.trace("Message TRACE: affecterMissionADepartement saved");
		    missionRepository.save(mission);
			l.trace("Message TRACE: affecterMissionADepartement function ended");

		}
		else { l.error("MESSAGE ERROR: In affecterMissionADepartement function ");}

	}

	public void ajouterTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin) {
		try{
		l.info("In ajouter Timesheet");
		l.trace("Message TRACE: In ajouterTimesheet function");
		TimesheetPK timesheetPK = new TimesheetPK();
		timesheetPK.setDateDebut(dateDebut);
		timesheetPK.setDateFin(dateFin);
		timesheetPK.setIdEmploye(employeId);
		timesheetPK.setIdMission(missionId);
		
		Timesheet timesheet = new Timesheet();
		timesheet.setTimesheetPK(timesheetPK);
		timesheet.setValide(false);
		timesheetRepository.save(timesheet);
		} catch (NullPointerException e) {
			l.error("MESSAGE ERROR: In ajouterTimesheet function ");
			l.error(e.getMessage());
		}
		l.trace("Message TRACE: ajouterTimesheet function ended");		
	}

	
	public void validerTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin, int validateurId) {
		l.info("In valider Timesheet");
		Optional<Mission> missionOpt = missionRepository.findById(missionId);
		Mission mission = new Mission();
		if (missionOpt.isPresent())
			mission = missionOpt.get();
		Optional<Employe> employeOpt = employeRepository.findById(employeId);
		Employe employe = new Employe();
		if (employeOpt.isPresent())
			employe = employeOpt.get();
		if(!employe.getRole().equals(Role.CHEF_DEPARTEMENT)){
			l.info("l'employe doit etre chef de departement pour valider une feuille de temps !");
			l.error("l'employe doit etre chef de departement pour valider une feuille de temps !");
			return;
		}
		boolean chefDeLaMission = false;
		for(Departement dep : employe.getDepartements()){
			if(dep.getId() == mission.getDepartement().getId()){
				chefDeLaMission = true;
				break;
			}
		}
		if(!chefDeLaMission){
			l.info("l'employe doit etre chef de departement de la mission en question");
			l.error("l'employe doit etre chef de departement de la mission en question");
			return;
		}
		l.trace("Message TRACE: In validerTimesheet function");
		TimesheetPK timesheetPK = new TimesheetPK(missionId, employeId, dateDebut, dateFin);
		Timesheet timesheet =timesheetRepository.findBytimesheetPK(timesheetPK);
		timesheet.setValide(true);
		l.trace("MESSAGE TRACE: validerTimesheet function ended");

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dated = dateFormat.format(timesheet.getTimesheetPK().getDateDebut());
		if(l.isInfoEnabled() && dated != null){
		l.info(dated);
		}
		
	}

	
	public List<Mission> findAllMissionByEmployeJPQL(int employeId) {
		l.trace("MESSAGE TRACE: in findAllMissionByEmployeJPQL function");
		return timesheetRepository.findAllMissionByEmployeJPQL(employeId);
	}

	
	public List<Employe> getAllEmployeByMission(int missionId) {
		l.trace("MESSAGE TRACE: in getAllEmployeByMission function");
		return timesheetRepository.getAllEmployeByMission(missionId);
	}

}
