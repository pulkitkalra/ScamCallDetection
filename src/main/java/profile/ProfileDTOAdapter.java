package profile;

import java.util.HashSet;
import java.util.Set;

public class ProfileDTOAdapter {
	
	private ProfileDTO profileDTO;
	private CallProfile profile;
	
	public ProfileDTOAdapter(CallProfile profile, ProfileDTO dto) {
		this.profile = profile;
		this.profileDTO = dto;
	}
	
	public void updateProfileDTO() {
		this.profileDTO.setListOfCallerName(updateCallerName());
	}
	
	private String updateCallerName() {
		Set<String> callerName = new HashSet<String>(profile.getCallSource().getName());
		String result = "";
		for (String s: callerName) {
			result+= s + "; ";
		}
		return result;
	}

}
