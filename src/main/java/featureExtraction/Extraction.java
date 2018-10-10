package featureExtraction;

import profile.CallProfile;

/**
 * The Extraction interface is a contract implemented by any classes that need
 * to extract features from the NLP class.
 * 
 * When defining new extraction logic, implement the updateProfile method and inherit 
 * from this interface.
 * @author Pulkit
 *
 */
public interface Extraction {
	
	void updateProfile(CallProfile profile);

}
