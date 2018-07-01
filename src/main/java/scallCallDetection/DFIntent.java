package scallCallDetection;

import com.google.cloud.dialogflow.v2.Intent;

public class DFIntent {
	private Intent intent;
	private float detectionCondifence;
	
	public DFIntent(Intent intent, float detectionCondifence) {
		this.intent = intent;
		this.detectionCondifence = detectionCondifence;
	}
	
	public Intent getIntent() {
		return intent;
	}
	
	public float getDetectionConfidence() {
		return detectionCondifence;
	}
	
	public String toString() {
		return "Detected Intent: "+ intent.getDisplayName()+ " (confidence: " 
				+ detectionCondifence + ")";
	}

}
