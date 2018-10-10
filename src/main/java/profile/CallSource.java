package profile;

import java.util.List;

import entities.Organisation;

/**
 * The CallSource abstraction enforces behavior related to the origin of the call.
 * Any logic related to the claimed source of the caller is defined in this interface.
 * @author Pulkit
 *
 */
public interface CallSource {
	
	void addOrganisation(Organisation o);
	void addName(String name);
	List<String> getName();
	List<Organisation> getOrganisations();

}
