package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.netlib.util.booleanW;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import profile.ProfileDTO;
import view.MainApp;

public class ProfileOverviewController {
	private final Double scamDetectedThreshold = 90.0;
	private final Double scamLikelyThreshold = 60.0;
	private final Double scamUnlikely = 40.0;
	
	private ProfileDTO dto;
	
	private static final Map<String,String[]> dtoList = new HashMap<>();
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
	@FXML
	private Label scamProbLabel;
	@FXML
	private ProgressBar scamProbProgress;
	@FXML
	private TextArea textArea;
	@FXML
	private AnchorPane scamPane;
	@FXML
	private Label scamLabel;
	@FXML
	private LineChart<Number, Number> probOfScamChart;
	
	private final Long startTime;
	private Series<Number, Number> series;

	ExecutorService threadPool = Executors.newWorkStealingPool();
	// Reference to the main application.
	@SuppressWarnings("unused")
	private MainApp mainApp;

	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public ProfileOverviewController() {
		dto = new ProfileDTO();
		startTime = System.currentTimeMillis();
	}

	public void addListners() {
		
		dto.getCallerNames().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						if (newValue.isEmpty() && oldValue.isEmpty()) {
							callerNameList.setText("No name(s) detected");
						} else {
							callerNameList.setText(newValue);
						}
					}
				}); 
			}
		});

		dto.getListOfSourceOrgs().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						orgList.setText(newValue);
					}
				}); 
			}
		});

		dto.getTaxConfidence().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						taxConfidence.setText(newValue);
					}
				}); 
			}
		});

		dto.getTaxRelated().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						setIconForLabel(taxRelated,newValue);
						//taxRelated.setText(String.valueOf(newValue));
					}
				}); 
			}
		});

		dto.getAmountRequested().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						amountRequested.setText(String.valueOf(newValue));
					}
				}); 
				
			}
		});

		dto.getArrestThreat().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						setIconForLabel(arrestThreat,newValue);
					}
				}); 
			}
		});

		dto.getPrisonThreat().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						setIconForLabel(prisonThreat,newValue);
					}
				}); 
				
			}
		});

		dto.getPrivacyThreat().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						setIconForLabel(privacyThreat,newValue);
					}
				}); 
				
			}
		});

		dto.getPaymentMethods().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						paymentMethod.setText(newValue);
					}
				}); 
			}
		});

		dto.getOpererationPhrases().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						operationPhrases.setText(newValue);
					}
				}); 
			}
		});

		dto.getUrgencyIndex().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						urgencyIndex.setText(newValue);
					}
				}); 
			}
		});
		
		dto.getProbabilityValue().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						scamProbLabel.setText("Probability: " + newValue);
					}
				}); 
			}
		});
		
		dto.getProgressProbValue().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						
						
						double scamValue = newValue.doubleValue();
						scamProbProgress.setProgress(scamValue);
						Long programDuration = (System.currentTimeMillis() - startTime)/1000;
						Data<Number, Number> data = new Data<>(programDuration, scamValue*100);
						series.getData().add(data);
						dtoList.put(data.getXValue().toString(), convertToArray(dto));
						data.getNode().setOnMouseClicked(e -> updateDto(dtoList.get(data.getXValue().toString())));
						
						if (scamValue > scamDetectedThreshold/100) {
							scamPane.setStyle("-fx-background-color: #ff5656;");
							scamLabel.setText("SCAM DETECTED!");
						} else if (scamValue > scamLikelyThreshold/100) {
							scamPane.setStyle("-fx-background-color: #ffc156;");
							scamLabel.setText("SCAM LIKELY");
						} else if (scamValue > scamUnlikely/100) {
							scamPane.setStyle("-fx-background-color: #eeff56;");
							scamLabel.setText("SCAM UNLIKELY");
						} 
					}
				}); 
			}
		});
		
		dto.getCurrentLineOfText().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						textArea.appendText(newValue + "\n");
					}
				}); 
			}
		});

	}

	@FXML
	private void initialize() {

		addListners();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				series = new XYChart.Series<>();
				series.setName("Probability of Scam");
				probOfScamChart.getData().add(series);
				series.getData().add(new Data<Number, Number>(0, 0));
			}
		}); 
		
	}
	
	private String[] convertToArray(ProfileDTO currentDto) {
		String[] array = new String[11];
		array[0] = currentDto.getCallerNames().getValue();
		array[1] = currentDto.getAmountRequested().doubleValue() + "";
		array[2] = currentDto.getArrestThreat().getValue().toString();
		array[3] = currentDto.getListOfSourceOrgs().getValue();
		array[4] = currentDto.getOpererationPhrases().getValue();
		array[5] = currentDto.getPaymentMethods().getValue();
		array[6] = currentDto.getPrisonThreat().getValue().toString();
		array[7] = currentDto.getPrivacyThreat().getValue().toString();
		array[8] = currentDto.getTaxConfidence().getValue();
		array[9] = currentDto.getTaxRelated().getValue().toString();
		array[10] = currentDto.getUrgencyIndex().getValue();
		
		return array;
	}
	
	private void updateDto(String[] stringDto) {
		dto.setListOfCallerName(stringDto[0]);
		dto.setAmountRequired(Double.valueOf(stringDto[1]));
		dto.setArrestThreat(Boolean.valueOf(stringDto[2]));
		dto.setListOfSourceOrgs(stringDto[3]);
		dto.setOperationPhrases(stringDto[4]); // 
		dto.setPaymentMethods(stringDto[5]);//
		dto.setPrisonThreat(Boolean.valueOf(stringDto[6])); // 
		dto.setPrivacyThreat(Boolean.valueOf(stringDto[7])); // 
		dto.setTaxConfidence(stringDto[8]);
		dto.setTaxRelated(Boolean.valueOf(stringDto[9]));
		dto.setUrgencyIndex(stringDto[10]);
	}
	
	private void setIconForLabel(Label label, boolean value) {
		Image image;
		label.setText("");
		if (value) {
			image = new Image("view/tick.png");
		} else {
			image = new Image("view/cross.png");
		}
		label.setGraphic(new ImageView(image));
	}
	
	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public ProfileDTO getProfileDTO() {
		return this.dto;
	}
}