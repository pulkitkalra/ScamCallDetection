package scallCallDetection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;

/**
 * This class is responsible for communicating with the DF agent queries
 * and retrieving the intents and entities extracted by that agent.
 * The behaviour in this class is used to format the intents/ entities so 
 * further processing can be performed.
 * @author Pulkit and Darius
 *
 */
public class ConversationPhrase {
	private String sentence;
	private DetectIntentResponse response;
	private List<DFEntity> entityList;
	
	public ConversationPhrase(DetectIntentResponse response){
		this.response = response;
		this.sentence = getQueryText();
		this.entityList = new ArrayList<>();
	}
	
	/**
	 * Get Entities from a DF Query Result.
	 * This includes any string triming/ processing to ensure that resulting
	 * entity objects are well-formated
	 * @return a DFEntity.
	 */
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
	
	/**
	 * Extract intent from a DF Query.
	 * @return
	 */
	public DFIntent getIntent(){
		QueryResult queryResult = response.getQueryResult();
		DFIntent intent = new DFIntent(queryResult.getIntent(),queryResult.getIntentDetectionConfidence());
		return intent;
	}
	
	/**
	 * Retrieve the string form of the query.
	 * @return
	 */
	public String getQueryText(){
		return response.getQueryResult().getQueryText();
	}
	
	/**
	 * Method used to print the NLP result when debugging/ testing.
	 */
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
