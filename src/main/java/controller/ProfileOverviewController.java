package controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
import javafx.scene.layout.AnchorPane;
import profile.ProfileDTO;
import view.MainApp;

public class ProfileOverviewController {
	private final Double scamDetectedThreshold = 90.0;
	private final Double scamLikelyThreshold = 60.0;
	private final Double scamUnlikely = 40.0;
	
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
						callerNameList.setText(newValue);
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
						taxRelated.setText(String.valueOf(newValue));
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
						arrestThreat.setText(String.valueOf(newValue));
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
						prisonThreat.setText(String.valueOf(newValue));
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
						privacyThreat.setText(String.valueOf(newValue));
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
						
						series.getData().add(new Data<Number, Number>(programDuration, scamValue*100));
						
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
				final NumberAxis xAxis = new NumberAxis();
			    final NumberAxis yAxis = new NumberAxis();
			    xAxis.setLabel("Time");
			    yAxis.setLabel("Probablity of Scam");
				probOfScamChart.getData().add(series);
			}
		}); 
		
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