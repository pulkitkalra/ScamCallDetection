package view;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MapTableView extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        try {
            // sample data
            /*Map<String, String> map = new HashMap<>();
            map.put("one", "One");
            map.put("two", "Two");
            map.put("three", "Three");


            // use fully detailed type for Map.Entry<String, String> 
            TableColumn<Map.Entry<String, String>, String> column1 = new TableColumn<>("Key");
            column1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, String>, String>, ObservableValue<String>>() {

                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {
                    // this callback returns property for just one cell, you can't use a loop here
                    // for first column we use key
                    return new SimpleStringProperty(p.getValue().getKey());
                }
            });

            TableColumn<Map.Entry<String, String>, String> column2 = new TableColumn<>("Value");
            column2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, String>, String>, ObservableValue<String>>() {

                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {
                    // for second column we use value
                    return new SimpleStringProperty(p.getValue().getValue());
                }
            });*/

            ObservableMap<String, String[]> map = FXCollections.observableHashMap();

            ObservableList<String> keys = FXCollections.observableArrayList();

            map.addListener((MapChangeListener.Change<? extends String, ? extends String[]> change) -> {
                boolean removed = change.wasRemoved();
                if (removed != change.wasAdded()) {
                    // no put for existing key
                    if (removed) {
                        keys.remove(change.getKey());
                    } else {
                        keys.add(change.getKey());
                    }
                }
            });

            map.put("one", new String[] {"one","2"});
            map.put("two", new String[] {"two","7"});
            //map.put("three", "Three;2");

            final TableView<String> table = new TableView<>(keys);

            TableColumn<String, String> column1 = new TableColumn<>("Key");
            // display item value (= constant)
            column1.setCellValueFactory(cd -> Bindings.createStringBinding(() -> cd.getValue()));
            
            //String[] split = cd.getValue().split(';');
            
            TableColumn<String, String> column2 = new TableColumn<>("Value");
            column2.setCellValueFactory(cd -> Bindings.valueAt(map, cd.getValue()));
            
            TableColumn<String[], String> column3 = new TableColumn<>("Value1");
            column3.setCellValueFactory(cd -> Bindings.valueAt(map, cd.getValue()));

            table.getColumns().setAll(column1, column2, column3);

            Button changeButton = new Button("Change");
            changeButton.setOnAction((ActionEvent e) -> {
            	map.put("two", new String[] {"two","2"});
                System.out.println(map);
            });
            VBox vBox = new VBox(8);
            vBox.getChildren().addAll(table, changeButton);

            Scene scene = new Scene(vBox, 400, 400);
            stage.setScene(scene);
            stage.show();
        }  catch(Exception e) {
            e.printStackTrace();
        }
    } 

    public static void main(String[] args) {
        launch();
    }

}
