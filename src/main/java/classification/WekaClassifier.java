package classification;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils;
import weka.gui.SysErrLog;

public class WekaClassifier implements DetectionEngine{
	final public static String ATTRIBUTE_IRS_STATUS = "IRS Status";
	final public static String ATTRIBUTE_TAX_RELATED = "Tax related";
	final public static String ATTRIBUTE_TAX_CONFIDENCE = "tax confidence";
	final public static String ATTRIBUTE_ARREST = "arrest";
	final public static String ATTRIBUTE_PRISON = "prison";
	final public static String ATTRIBUTE_IDENTITY_PRIVACY_THREAT = "privacy (Identity) threat";
	final public static String ATTRIBUTE_IDENTITY_BANK_THREAT = "privacy (bank) threat";
	final public static String ATTRIBUTE_AMOUNT_REQUESTED = "amount requested";
	final public static String ATTRIBUTE_PAYMENT_METHODS = "payment methods";
	final public static String ATTRIBUTE_SCAM_SIGNALS = "scam signals";
	final public static String ATTRIBUTE_SCAM_SIGNAL_INCLUDES_THREAT = "scam signal includes threat?";
	final public static String ATTRIBUTE_COURT_MENTIONED = "court mentioned";
	final public static String ATTRIBUTE_URGENCY_INDEX = "urgency index";
	final public static String ATTRIBUTE_SCAM = "SCAM YES/NO?";

	static ArrayList<Attribute> callAttributes = new ArrayList<Attribute>();
	private RandomForest _randomForest;

	public void setup() {

		ArrayList<String> irsStatus = new ArrayList<String>();
		irsStatus.add("IRS");
		irsStatus.add("MAYBE_IRS");
		irsStatus.add("NOTIRS");

		ArrayList<String> booleanValues = new ArrayList<String>();
		booleanValues.add("FALSE");
		booleanValues.add("TRUE");

		ArrayList<String> taxConfidence = new ArrayList<String>();
		taxConfidence.add("NONE");
		taxConfidence.add("LOW");
		taxConfidence.add("MEDIUM");
		taxConfidence.add("HIGH");

		ArrayList<String> amountRequested = new ArrayList<String>();
		amountRequested.add("NONE");
		amountRequested.add("LOW");
		amountRequested.add("MEDIUM");
		amountRequested.add("HIGH");

		ArrayList<String> paymentMethods = new ArrayList<String>();
		paymentMethods.add("NONE");
		paymentMethods.add("TRANSFER");
		paymentMethods.add("TAX_VOUCHER");
		paymentMethods.add("BANK_CARD");
		paymentMethods.add("GIFT_CARD");

		ArrayList<String> scamSignals = new ArrayList<String>();
		scamSignals.add("NONE");
		scamSignals.add("MULTIPLE");
		scamSignals.add("RECORD");
		scamSignals.add("ON_HOLD");

		ArrayList<String> courtMentioned = new ArrayList<String>();
		courtMentioned.add("NONE");
		courtMentioned.add("LOW");
		courtMentioned.add("MEDIUM");
		courtMentioned.add("HIGH");

		ArrayList<String> urgencyIndex = new ArrayList<String>();
		urgencyIndex.add("NONE");
		urgencyIndex.add("LOW");
		urgencyIndex.add("MEDIUM");
		urgencyIndex.add("HIGH");

		ArrayList<String> classes = new ArrayList<String>();
		classes.add("NO");
		classes.add("YES");

		callAttributes.add(new Attribute(ATTRIBUTE_IRS_STATUS, irsStatus));
		callAttributes.add(new Attribute(ATTRIBUTE_TAX_RELATED, booleanValues));
		callAttributes.add(new Attribute(ATTRIBUTE_TAX_CONFIDENCE, taxConfidence));
		callAttributes.add(new Attribute(ATTRIBUTE_ARREST, booleanValues));
		callAttributes.add(new Attribute(ATTRIBUTE_PRISON, booleanValues));
		callAttributes.add(new Attribute(ATTRIBUTE_IDENTITY_PRIVACY_THREAT, booleanValues));
		callAttributes.add(new Attribute(ATTRIBUTE_IDENTITY_BANK_THREAT, booleanValues));
		callAttributes.add(new Attribute(ATTRIBUTE_AMOUNT_REQUESTED, amountRequested));
		callAttributes.add(new Attribute(ATTRIBUTE_PAYMENT_METHODS, paymentMethods));
		callAttributes.add(new Attribute(ATTRIBUTE_SCAM_SIGNALS, scamSignals));
		callAttributes.add(new Attribute(ATTRIBUTE_SCAM_SIGNAL_INCLUDES_THREAT, booleanValues));
		callAttributes.add(new Attribute(ATTRIBUTE_COURT_MENTIONED, courtMentioned));
		callAttributes.add(new Attribute(ATTRIBUTE_URGENCY_INDEX, urgencyIndex));		
		callAttributes.add(new Attribute("@@SCAM YES/NO@@", classes));

		try {
			_randomForest = (RandomForest) SerializationHelper.read(new FileInputStream("dataset.model"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Could not find required dataset!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public double getProbabilityOfScam(String[] profileInstance) {	
		Instances data = new Instances("scamcall", callAttributes, 0);
		data.setClassIndex(data.numAttributes() - 1);

		DenseInstance instance = new DenseInstance(14);
		instance.setValue(data.attribute(ATTRIBUTE_IRS_STATUS), profileInstance[0]);
		instance.setValue(data.attribute(ATTRIBUTE_TAX_RELATED), profileInstance[1]);
		instance.setValue(data.attribute(ATTRIBUTE_TAX_CONFIDENCE), profileInstance[2]);
		instance.setValue(data.attribute(ATTRIBUTE_ARREST), profileInstance[3]);
		instance.setValue(data.attribute(ATTRIBUTE_PRISON), profileInstance[4]);
		instance.setValue(data.attribute(ATTRIBUTE_IDENTITY_PRIVACY_THREAT), profileInstance[5]);
		instance.setValue(data.attribute(ATTRIBUTE_IDENTITY_BANK_THREAT), profileInstance[6]);
		instance.setValue(data.attribute(ATTRIBUTE_AMOUNT_REQUESTED), profileInstance[7]);
		instance.setValue(data.attribute(ATTRIBUTE_PAYMENT_METHODS), profileInstance[8]);
		instance.setValue(data.attribute(ATTRIBUTE_SCAM_SIGNALS), profileInstance[9]);
		instance.setValue(data.attribute(ATTRIBUTE_SCAM_SIGNAL_INCLUDES_THREAT), profileInstance[10]);
		instance.setValue(data.attribute(ATTRIBUTE_COURT_MENTIONED), profileInstance[11]);
		instance.setValue(data.attribute(ATTRIBUTE_URGENCY_INDEX), profileInstance[12]);	


		data.add(instance);
		data.add(instance);
		data.add(instance);

		//		// model NaiveBayes
		//		NaiveBayes naiveBayes = new NaiveBayes();
		//		naiveBayes.buildClassifier(train);

		// model RandomForest with number of subtrees
		//		RandomForest forest=new RandomForest();
		//		forest.setNumIterations(1000);
		//		forest.buildClassifier(train);



		Evaluation RFEvaluation;
		try {
			RFEvaluation = new Evaluation(data);
			RFEvaluation.evaluateModel(_randomForest, data);
			//		Evaluation NBEvaluation = new Evaluation(train);
			//		NBEvaluation.evaluateModel(naiveBayes, data);

			double RFlabel = _randomForest.classifyInstance(data.instance(0));
			data.instance(0).setClassValue(RFlabel);
			//		double NBlabel = naiveBayes.classifyInstance(data.instance(1));
			//		data.instance(1).setClassValue(NBlabel);

			System.out.println(data.instance(0).stringValue(13));
			double[] RFprediction = _randomForest.distributionForInstance(data.instance(0));
			for (int i=0; i<RFprediction.length; i++){
				System.out.println("Probability of class " + data.classAttribute().value(i) + " : " + Double.toString(RFprediction[i]));
			}


			//		System.out.println(data.instance(1).stringValue(4));
			//		double[] NBprediction = naiveBayes.distributionForInstance(data.instance(1));
			//		for (int i=0; i<NBprediction.length; i++){
			//			System.out.println("Probability of class " + data.classAttribute().value(i) + " : " + Double.toString(NBprediction[i]));
			//		}

			return RFprediction[1];
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Profile instance unable to be evaluated!");
			return 0;
		}

	}
	
	public static void main(String[] args){
		WekaClassifier test = new WekaClassifier();
		test.setup();
		String[] profileInstance = {"IRS","TRUE","NONE","TRUE","TRUE","TRUE","TRUE","HIGH","TAX_VOUCHER","ON_HOLD","TRUE","HIGH","LOW"};
		
		double isScam = test.getProbabilityOfScam(profileInstance);
		DecimalFormat df = new DecimalFormat("####0.00");
		System.out.println("The probability of this call at this point in time being a scam is " + df.format(isScam*100) + "%");
	}
}


