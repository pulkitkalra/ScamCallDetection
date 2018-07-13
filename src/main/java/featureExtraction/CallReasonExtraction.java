package featureExtraction;

import java.util.Arrays;
import java.util.List;

import com.google.protobuf.Struct;

import entities.Organisation;
import profile.CallAction;
import profile.CallProfile;
import profile.CallReason;
import profile.CallSource;
import scallCallDetection.ConversationPhrase;
import scallCallDetection.DFEntity;

public class CallReasonExtraction implements Extraction {
	private ConversationPhrase phrase;

	public CallReasonExtraction(ConversationPhrase phrase) {
		this.phrase = phrase;
	}

	@Override
	public void updateProfile(CallProfile profile) {
		CallReason reason = profile.getCallReason();
		CallAction action = profile.getCallAction();
		CallSource source = profile.getCallSource();
		List<DFEntity> entityList = phrase.getEntities();
		// no of bools is the number of boolean parameters to pass into updateConfidence method.
		int numberOfBools = 0;
		// tax visited is the boolean representing that a 'tax' entity has already beeen found.
		boolean taxVisited = false;
		for (DFEntity ent: entityList) {
			if (!taxVisited && ent.getEntityName().equals("Tax")) {
				// TODO: update the call to set call reason tax when we can determine action of call.
				reason.setCallReasonTax(true, "");
				numberOfBools++;
				taxVisited = true;
			} else if (ent.getEntityName().equals("GovernmentEntity")) {
				if (!source.getOrganisations().contains(Organisation.GOVERNMENT_ENTITY)) {
					source.addOrganisation(Organisation.GOVERNMENT_ENTITY);
				}
				numberOfBools++;
			} else if (ent.getEntityName().equals("IRS")) {
				if (!source.getOrganisations().contains(Organisation.IRS)) {
					source.addOrganisation(Organisation.IRS);
				}
				numberOfBools++;
			} else if (ent.getEntityName().equals("unit-currency")) {
				Struct s = ent.getEntityValue().getStructValue();
				Double amount = s.getFieldsMap().get("amount").getNumberValue();
				action.addPaymentRequest(amount);
			}
		}

		numberOfBools = (numberOfBools > 3) ? 3: numberOfBools;
		boolean[] params = new boolean[numberOfBools];
		Arrays.fill(params, true);
		reason.updateConfidence(params);
	}

}
