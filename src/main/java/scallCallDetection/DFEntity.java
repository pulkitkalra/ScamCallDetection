package scallCallDetection;

import com.google.protobuf.Value;

public class DFEntity {
	private String entityName;
	private Value entityValue;
	
	public DFEntity(String name, Value value) {
		this.entityName = name;
		this.entityValue = value;
	}
	
	public String getEntityName() {
		return entityName;
	}
	
	public Value getEntityValue() {
		return entityValue;
	}
	
	public String toString() {
		return "Entity: "+ this.entityName + "; value = " 
				+ this.entityValue.toString();
	}
}
