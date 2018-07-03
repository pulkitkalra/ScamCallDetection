package featureExtraction;

import java.util.List;

import profile.CallAction;
import profile.CallProfile;
import scallCallDetection.ConversationPhrase;
import scallCallDetection.DFEntity;

public class CallActionExtraction implements Extraction {
	private ConversationPhrase phrase;
	
	public CallActionExtraction(ConversationPhrase phrase) {
		this.phrase = phrase;
	}
	
	@Override
	public void updateProfile(CallProfile profile) {
		CallAction action = profile.getCallAction();
		List<DFEntity> entityList = phrase.getEntities();
		
		for (DFEntity ent: entityList) {
			
		}
	}

}
