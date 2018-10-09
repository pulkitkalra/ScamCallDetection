package classification;

import profile.CallProfile;

/***
 * An interface to remove coupling between the classifier class and the rest of the application.
 * @author dau782
 *
 */
public interface DetectionEngine {
	
	public void setup();
	public double getProbabilityOfScam(String[] args);
}
