package profile;

import java.util.List;

import entities.Organisation;

public interface CallSource {
	
	void addOrganisation(Organisation o);
	void addName(String name);
	List<String> getName();
	List<Organisation> getOrganisations();

}
