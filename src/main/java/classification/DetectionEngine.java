package classification;

import profile.CallProfile;

public interface DetectionEngine {
	public void setup();
	public double getProbabilityOfScam(String[] args);
}
