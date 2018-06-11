package scallCallDetection;

import java.util.Map;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.List;

import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;

import io.grpc.netty.shaded.io.netty.util.internal.StringUtil;

public class ConversationPhrase {
	private String sentence;
	private Struct entityMap;
	private DetectIntentResponse response;
	private List<String> entityList;
	
	public ConversationPhrase(DetectIntentResponse response){
		this.response = response;
		this.sentence = getQueryText();
		this.entityList = new ArrayList<>();
	}
	
	private List<String> getEntities(){
		//entityMap = response.getAllFields();
		QueryResult result = response.getQueryResult();
		entityMap = result.getParameters();
		Map<String, Value> map = entityMap.getFieldsMap();
		
		for (Entry<String, Value> entity: map.entrySet()){
			if (!entity.getValue().toString().trim().equals("string_value: \"\"")){
				entityList.add("Entity: "+ entity.getKey() + "; value = " 
						+ entity.getValue());
			}
		}
		/*Map<FieldDescriptor, Object> newMap = entityMap.getAllFields();
		
		for (Entry<FieldDescriptor, Object> entity: newMap.entrySet()){
			if (!StringUtil.isNullOrEmpty(entity.getValue().toString())){
				entityList.add("Entity: "+ entity.getKey().getFullName() + "; value = " 
						+ entity.getValue());
			}
		}*/
		return entityList;
	}
	
	private String getIntents(){
		QueryResult queryResult = response.getQueryResult();
		String intentString = "Detected Intent: "+ queryResult.getIntent().getDisplayName()+ " (confidence: " 
				+ queryResult.getIntentDetectionConfidence() + ")";
		return intentString;
	}
	
	private String getQueryText(){
		return response.getQueryResult().getQueryText();
	}
	
	public void printNLPResult(){
		System.out.println("Query: " + sentence);
		System.out.println(getIntents());
		List<String> entities = getEntities();
		System.out.println("Entities Found:");
		for (String s: entities){
			System.out.println(s);
		}
	}
}
