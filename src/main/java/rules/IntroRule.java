package rules;

import java.util.List;

import com.google.protobuf.Value;

import profile.CallSource;
import rules.Rule.MapType;
import scallCallDetection.DFEntity;

public class IntroRule extends Rule {
	private CallSource source;
	
	public IntroRule(CallSource source) {
		super();
		this.source = source;
	}
	
	@Override
	public void applyRule(DFEntity ent) {
		String entityName = ent.getEntityName();
		MapType type = getMapType(entityName);
		switch (type) {
			case ORGANISATION_MAP:
				source.addOrganisation(getOrganisationMap().get(entityName));
				break;
			case NAME:
				source.addName(ent.getEntityValue().getStringValue());
				break;
			default:
				break;
		}

	}

}
