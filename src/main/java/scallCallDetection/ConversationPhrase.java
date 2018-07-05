package scallCallDetection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;

public class ConversationPhrase {
	private String sentence;
	private DetectIntentResponse response;
	private List<DFEntity> entityList;
	
	public ConversationPhrase(DetectIntentResponse response){
		this.response = response;
		this.sentence = getQueryText();
		this.entityList = new ArrayList<>();
	}
	
	public List<DFEntity> getEntities(){
		//entityMap = response.getAllFields();
		QueryResult result = response.getQueryResult();
		Struct entityMap = result.getParameters();
		Map<String, Value> map = entityMap.getFieldsMap();
		
		for (Entry<String, Value> entity: map.entrySet()){
			if (!entity.getValue().toString().trim().equals("string_value: \"\"") ) {
				if (entity.getValue().getKindCase().equals(Value.KindCase.LIST_VALUE)){
					if (entity.getValue().toString().trim().equals("list_value {\n}")){
						continue;
					}
				}
				DFEntity ent = new DFEntity(entity.getKey(), entity.getValue());
				entityList.add(ent);
			}
		}
		return entityList;
	}
	
	public DFIntent getIntent(){
		QueryResult queryResult = response.getQueryResult();
		DFIntent intent = new DFIntent(queryResult.getIntent(),queryResult.getIntentDetectionConfidence());
		return intent;
	}
	
	public String getQueryText(){
		return response.getQueryResult().getQueryText();
	}
	
	public void printNLPResult(){
		System.out.println("Query: " + sentence);
		System.out.println(getIntent().toString());
		List<DFEntity> entities = getEntities();
		System.out.println("Entities Found:");
		for (DFEntity e: entities){
			System.out.println(e.toString());
		}
	}
}
