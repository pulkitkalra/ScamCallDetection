package profile;

public interface CallProfile {
	
	CallSource getCallSource();
	CallReason getCallReason();
	CallAction getCallAction();
	CallScamSpecifics getCallScamSpecifics();
	Threat getCallThreat();
}
