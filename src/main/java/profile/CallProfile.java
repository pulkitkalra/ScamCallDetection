package profile;

/**
 * The Call Profile interface is an abstraction for the state of the profile
 * in the application. When new profile elements are added, they must be defined here
 * before they can be implemented.
 * @author Pulkit
 *
 */
public interface CallProfile {
	
	CallSource getCallSource();
	CallReason getCallReason();
	CallAction getCallAction();
	CallScamSpecifics getCallScamSpecifics();
	Threat getCallThreat();
}
