package controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import javafx.scene.control.Button;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;

import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import profile.ProfileDTO;
import scallCallDetection.DetectIntentTexts;
import view.MainApp;

/**
 * The ProfileOverviewController is a controller class that handles data
 * from a call profile and publishes to the JavaFX view.
 * 
 * This class contains the listeners for all fields in the view.
 * @author Pulkit
 *
 */
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
	@FXML 
	private TextField recordingText; 
	@FXML
	private Button startFile;
	@FXML
	private Button startRecording;
	
	private final Long startTime;
	private Series<Number, Number> series;
	private List<Data<Number, Number>> dataList = new ArrayList<>();
	private final String[] profileLabelNames = new String[] {"Caller Names","Amount Required","Arrest Threat", "Organisation", "Operation Phrases",
			"Payment Method", "Prison Threat", "Privacy Threat", "Tax Confidence", "Tax Related", "Urgency Index"};

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
	
	/**
	 * Add all listeners for fields in the view. 
	 */
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
						dataList.add(data);
						series.getData().add(data);
						dtoList.put(data.getXValue().toString(), convertToArray(dto));
						data.getNode().setOnMouseClicked(e -> updateDto(dtoList.get(data.getXValue().toString())));
						
						if (dataList.size() >= 2) {
							String data1 = dataList.get(dataList.size() - 2).getXValue().toString();
							String data2 = dataList.get(dataList.size() - 1).getXValue().toString();
							String tooltip = getChangeString(dtoList.get(data1), dtoList.get(data2));
							Tooltip tp = new Tooltip(tooltip);
							tp.setStyle("-fx-font-size: 14");
							Tooltip.install(data.getNode(), tp);
						}
						
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
		
		dto.getRecordingText().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						recordingText.setText(newValue);
					}
				}); 
			}
		});

	}
	
	/**
	 * Initialize JavaFX App based on the type of button that is pressed by user.
	 * Case 1: Start by File: Renders the file view and initializes the appropriate NLP method.
	 * Case 2: Start by Recording: Renders the live audio recording view and initializes 
	 * 	       the appropriate NLP method.
	 */
	@FXML
	private void initialize() {
		addListners();
		startFile.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	System.out.println("clicked");
		    	startRecording.setDisable(true);
		    	final Task<Void> task = new Task<Void>() {
		        	@Override 
		        	protected Void call() throws InterruptedException {
		        		try {
		                	DetectIntentTexts dit = new DetectIntentTexts(dto, false);
		                    dit.start();
		                } catch (Exception e) {
		        			e.printStackTrace();
		        		}
						return null;
		        	}            
		        };
		        new Thread(task).start();
		    }
		});
		
		startRecording.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	System.out.println("clicked");
		    	startFile.setDisable(true);
		    	recordingText.setVisible(true);
		    	
		    	final Task<Void> task = new Task<Void>() {
		        	@Override 
		        	protected Void call() throws InterruptedException {
		        		try {
		                	DetectIntentTexts dit = new DetectIntentTexts(dto, true);
		                    dit.start();
		                } catch (Exception e) {
		        			e.printStackTrace();
		        		}
						return null;
		        	}            
		        };
		        new Thread(task).start();
		    }
		});
		
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
	
	/**
	 * Convert a Profile DTO object to an array.
	 * An array of type String is returned. 
	 * @param currentDto
	 * @return
	 */
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
	
	/**
	 * Update the DTO object based on input string representation of another Profile DTO.
	 * @param stringDto
	 */
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
	
	/**
	 * Get a string that represents the changes in one node to another on the 
	 * line graph. This string delimts changes using ';'.
	 * 
	 * @param string1
	 * @param string2
	 * @return
	 */
	private String getChangeString(String[] string1, String[] string2) {
		int index = 0;
		String change = "";
		for (String s: string2) {
			if (!s.equals(string1[index])) {
				change+= " " + profileLabelNames[index] + " = " + s + ";";
			}
			index++;
		}
		return "Change:" + change;
	}
	
	/**
	 * Set icon for Privacy/ Prison/ Court labels.
	 * @param label
	 * @param value
	 */
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