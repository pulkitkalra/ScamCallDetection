package rules;

import profile.CallSource;
import scallCallDetection.DFEntity;

/**
 * IntroRule inherits from Rule. This class defines behaviour associated
 * with intents related to CallIntro. 
 * @author Pulkit
 *
 */
public class IntroRule extends Rule {
	private CallSource source;
	
	public IntroRule(CallSource source) {
		super();
		this.source = source;
	}
	
	/**
	 * Apply rules based on entity that is passed in as input.
	 * This currently includes organisations/ name in source. 
	 */
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
