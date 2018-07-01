package featureExtraction;

import java.util.List;

import entities.Organisation;
import profile.CallProfile;
import profile.CallProfileImp;
import profile.CallSource;
import profile.CallSourceImp;
import scallCallDetection.ConversationPhrase;
import scallCallDetection.DFEntity;

public class CallFeatureExtraction {
	private CallProfile profile;
	private CallSource source;
	
	public CallFeatureExtraction() {
		this.profile = new CallProfileImp();
		this.source = new CallSourceImp();
	}
	
	/**
	 * Assume we are only dealing with Call_intro intent for now.
	 * @param phrase
	 */
	public void processConversationPhrase(ConversationPhrase phrase) {
		// process intent
		// Call_intro
		if (phrase.getIntent().getIntent().getDisplayName().equals("Call_intro")) {
			// We now know this is a call source, so we can process entity
			List<DFEntity> entityList = phrase.getEntities();
			for (DFEntity ent: entityList) {
				if (ent.getEntityName().equals("IRS")) {
					source.addOrganisation(Organisation.IRS);
				} // do the same for all other types of entities.
				if (ent.getEntityName().equals("given-name")) {
					source.addName(ent.getEntityValue().getStringValue());
				}
			}
		}
	}
	
	public CallProfile getCallProfile() {
		return profile;
	}

}
