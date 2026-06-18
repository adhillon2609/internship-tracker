package com.tracker.internship_tracker;
import javafx.scene.control.TextField;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        StackPane root=new StackPane();
            root.setStyle("-fx-background-color: lightpink;");
        Scene scene1=new Scene(root,800,800,Color.LIGHTPINK);
        VBox card=new VBox(30);
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(150,150);
        card.setStyle("-fx-background-color: white;"+
                "-fx-background-radius: 40;"+"-fx-padding: 10;"+
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 15, 0, 0, 5);");
        Label title=new Label("Internship Tracker");
        title.setStyle(
                "-fx-font-size: 32px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #1F2937;");
        Label subtitle=new Label("Track all your internship applications in one place");
        subtitle.setStyle(
                "-fx-font-size: 12px;" +
                        "-fx-text-fill: black;");
        Button startB=new Button("Get Started");
        startB.setStyle(
                "-fx-background-color: #EC4899;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 16px;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10 25 10 25;"
        );
        card.setMinSize(300, 300);
        card.setMaxSize(300, 300);
        startB.setOnAction(event -> track(stage));
        card.getChildren().addAll(title, subtitle, startB);
        root.getChildren().add(card);
        stage.setTitle("Internship Tracker");
        stage.setScene(scene1);
        stage.show();
    }

    public void track(Stage stages){
        Group root2=new Group();
        Scene scene2=new Scene(root2, 800, 800, Color.LIGHTPINK);
        Stage stage2=stages;
        stage2.setScene(scene2);
        TextField company=new TextField();
        company.setPromptText("COMPANY ");
        company.setLayoutX(200);
        company.setLayoutY(80);
        company.setPrefWidth(150);
        company.setStyle("-fx-control-inner-background: #FFFFFF; -fx-text-fill: #000000; -fx-prompt-text-fill: darkslategray;");
        root2.getChildren().add(company);

        TextField start=new TextField();
        start.setPromptText("APPLICATION START DATE");
        start.setLayoutX(430);
        start.setLayoutY(80);
        start.setPrefWidth(190);
        start.setStyle("-fx-control-inner-background: #FFFFFF; -fx-text-fill: #000000; -fx-prompt-text-fill: darkslategray;");
        root2.getChildren().add(start);

        Label statuslabel=new Label();
        statuslabel.setLayoutX(80);
        statuslabel.setLayoutY(120);
        statuslabel.setTextFill(Color.LIGHTGREEN);
        root2.getChildren().add(statuslabel);

        ObservableList<Track> data=FXCollections.observableArrayList();
        loadDataFromFile(data);

        TableColumn<Track, String> nameCol=new TableColumn<>("Company name");
        nameCol.setPrefWidth(190);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("companyname"));

        TableColumn<Track, String> startCol=new TableColumn<>("Application start date");
        startCol.setPrefWidth(190);
        startCol.setCellValueFactory(new PropertyValueFactory<>("applicationopen"));

        TableColumn<Track, Boolean> applyCol=new TableColumn<>("Applied?");
        applyCol.setPrefWidth(100);
        applyCol.setCellValueFactory(new PropertyValueFactory<>("apply"));
        applyCol.setCellValueFactory(cellData -> cellData.getValue().applyProperty());
        applyCol.setCellFactory(CheckBoxTableCell.forTableColumn(applyCol));

        TableColumn<Track, Boolean> statusCol=new TableColumn<>("status");
        statusCol.setPrefWidth(100);
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        statusCol.setCellFactory(CheckBoxTableCell.forTableColumn(statusCol));

        TableView<Track> table=new TableView<>(data);
        table.setEditable(true);
        table.getColumns().addAll(nameCol, startCol, applyCol, statusCol);
        table.setLayoutX(100);
        table.setLayoutY(160);
        table.setPrefSize(580, 450);
        table.setStyle("-fx-background-color: transparent; -fx-text-fill: bisque;");
        root2.getChildren().add(table);

        Button saveButton=new Button("Save Internship");
        saveButton.setLayoutX(120);
        saveButton.setLayoutY(700);
        saveButton.setBorder(new Border(new BorderStroke(
                Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        saveButton.setStyle("-fx-background-color: lemonchiffon; -fx-text-fill: darkslategray;");
        root2.getChildren().add(saveButton);

        saveButton.setOnAction(event -> {
            String nameval=company.getText().trim();
            String startval=start.getText().trim();
            if (!nameval.isEmpty() || !startval.isEmpty()) {
                if (nameval.isEmpty() || startval.isEmpty()) {
                    statuslabel.setTextFill(Color.TOMATO);
                    statuslabel.setText("Please fill in both fields to add a new entry!");
                    return;
                }
                data.add(new Track(nameval, startval));
                company.clear();
                start.clear();
            }

            try (FileWriter fw=new FileWriter("track.txt", false);
                 BufferedWriter bw=new BufferedWriter(fw)) {
                for (Track item : data) {
                    boolean isApplied=item.getApply();
                    boolean currentStatus=item.getStatus();
                    String entry=item.getCompanyname()+","+item.getApplicationopen()+","+isApplied+","+currentStatus+"\n";
                    bw.write(entry);
                }

                statuslabel.setTextFill(Color.LIGHTGREEN);
                statuslabel.setText("Data synced and saved successfully!");
            } catch (IOException e){
                statuslabel.setTextFill(Color.TOMATO);
                statuslabel.setText("Error saving data: "+e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void loadDataFromFile(ObservableList<Track> data){
        File file=new File("track.txt");
        if (!file.exists()) {
            return;
        }
        try (BufferedReader br=new BufferedReader(new FileReader(file))) {
            String line;
            while ((line=br.readLine()) !=null) {
                if (!line.trim().isEmpty()) {
                    String[] parts=line.split(",");
                    if (parts.length>=4) {
                        String companyName=parts[0].trim();
                        String startDate=parts[1].trim();
                        boolean applied=Boolean.parseBoolean(parts[2].trim());
                        boolean status=Boolean.parseBoolean(parts[3].trim());
                        Track savedTrack=new Track(companyName, startDate);
                        savedTrack.setApply(applied);
                        savedTrack.setStatus(status);
                        data.add(savedTrack);
                    } else if (parts.length==2) {
                        data.add(new Track(parts[0].trim(), parts[1].trim()));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading track.txt: "+e.getMessage());
        }
    }

    public static void main(String[] args){
        launch(args);
    }
}