package profile;

import java.util.ArrayList;
import java.util.List;

import entities.Organisation;

public class CallSourceImp implements CallSource {
	
	private List<Organisation> organisations;
	private List<String> callerName;
	
	public CallSourceImp() {
		this.organisations = new ArrayList<>();
		this.callerName = new ArrayList<String>();
	}
	
	@Override
	public List<Organisation> getOrganisations() {
		return organisations;
	}
	
	@Override
	public List<String> getName() {
		return callerName;
	}

	@Override
	public void addOrganisation(Organisation o) {
		organisations.add(o);
	}

	@Override
	public void addName(String name) {
		callerName.add(name);
	}
}
