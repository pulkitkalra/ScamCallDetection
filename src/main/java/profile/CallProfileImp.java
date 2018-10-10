package profile;

/**
 * CallProfileImp is the implementation of the abstract CallProfile. 
 * Currently, the implementation defines and initialises a call source,
 * call reason, call action, threats and any other scam specifics. 
 * @author Pulkit
 *
 */
public class CallProfileImp implements CallProfile {
	
	private CallSource source;
	private CallReason reason;
	private CallAction action;
	private CallScamSpecifics scamSpecifics;
	private Threat threat;
	
	/**
	 * Should only be instantiated once. Convert to singleton in future.
	 */
	public CallProfileImp() {
		this.source = new CallSourceImp();
		this.reason = new CallReasonImp();
		this.action = new CallActionImp();
		this.threat = new ThreatImp();
		this.scamSpecifics = new CallScamSpecificsImp();
	}
	
	@Override
	public CallSource getCallSource() {
		return this.source;
	}
	
	@Override
	public CallReason getCallReason() {
		return this.reason;
	}
	
	@Override
	public CallAction getCallAction() {
		return this.action;
	}
	
	@Override
	public Threat getCallThreat() {
		return this.threat;
	}

	@Override
	public CallScamSpecifics getCallScamSpecifics() {
		return scamSpecifics;
	}
}
