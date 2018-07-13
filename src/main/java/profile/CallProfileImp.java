package profile;

public class CallProfileImp implements CallProfile {
	
	private CallSource source;
	private CallReason reason;
	private CallAction action;
	private CallScamSpecifics scamSpecifics;
	private Threat threat;
	
	/**
	 * Should only be instantiated once. MAKE IT SINGLETON?
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
