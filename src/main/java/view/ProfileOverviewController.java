package view;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import profile.ProfileDTO;
import scallCallDetection.DetectIntentTexts;

public class ProfileOverviewController {
	private ProfileDTO dto;
    @FXML
    private Label orgList;
    @FXML
    private Label callerNameList;
    @FXML
    private Label taxRelated;
    @FXML
    private Label taxConfidence;
    @FXML
    private Label amountRequested;
    @FXML
    private Label paymentMethod;
    @FXML
    private Label arrestThreat;
    @FXML
    private Label prisonThreat;
    @FXML
    private Label privacyThreat;
    @FXML
    private Label operationPhrases;
    @FXML
    private Label urgencyIndex;
    
    // TEST!
    @FXML
    private Button changeButton;
    
    ExecutorService threadPool = Executors.newWorkStealingPool();
    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public ProfileOverviewController() {
    	dto = new ProfileDTO();
    }
    
    public void addListners() {
    	changeButton.setOnAction((ActionEvent e) -> {
    		changeDTO();
        });
    	
    	dto.getCallerNames().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				callerNameList.setText(newValue);
			}
		});
    	
    	dto.getListOfSourceOrgs().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				orgList.setText(newValue);
			}
		});
    	
    	dto.getTaxConfidence().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				taxConfidence.setText(newValue);
			}
		});
    	
    	dto.getTaxRelated().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				taxRelated.setText(String.valueOf(newValue));
			}
		});
    	
    	dto.getAmountRequested().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				amountRequested.setText(String.valueOf(newValue));
			}
		});
    	
    	dto.getArrestThreat().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				arrestThreat.setText(String.valueOf(newValue));
			}
		});
    	
    	dto.getPrisonThreat().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				prisonThreat.setText(String.valueOf(newValue));
			}
		});
    	
    	dto.getPrivacyThreat().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				privacyThreat.setText(String.valueOf(newValue));
			}
		});
    	
    	dto.getPaymentMethods().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				paymentMethod.setText(newValue);
			}
		});
    	
    	dto.getOpererationPhrases().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				operationPhrases.setText(newValue);
			}
		});
    	
    	dto.getUrgencyIndex().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				urgencyIndex.setText(newValue);
			}
		});
    }
    
    @FXML
    private void initialize() {
    	addListners();
    }
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    public void changeDTO() {
    	changeButton.setDisable(true);
    	changeButton.setVisible(false);
    }
    
    public ProfileDTO getProfileDTO() {
    	return this.dto;
    }
}