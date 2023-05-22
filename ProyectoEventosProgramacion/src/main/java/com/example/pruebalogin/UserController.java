package com.example.pruebalogin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.pruebalogin.Domain.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.control.Button;

public class UserController implements Initializable{
    private final String bd = "eventos_programacion";
    private final String url = "jdbc:mysql://localhost:3306/";
    private final String login = "root";
    private final String password = "";

    private Connection c = null;
    @FXML
    private Button backbtt3;
    @FXML
    private Button Profilebtt;
    @FXML
    private TableView<Event> tblEvents;
    @FXML
    private TableColumn<Event, String> NameCol;
    @FXML
    private TableColumn<Event, String> dateCol;
    @FXML
    private TableColumn<Event, String> DescriptionCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Event> items = getEvent();
            this.tblEvents.setItems(items);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public UserController() {
        conectar();
    }
    public ObservableList<Event> getEvent() throws SQLException{
        ObservableList<Event> obs = FXCollections.observableArrayList();
        Statement stat = c.createStatement();
        ResultSet rs = stat.executeQuery("SELECT * from events");
        while(rs.next()){
            String Event_Name = rs.getString("Event_name");
            String Event_Date = rs.getString("Event_Date");
            String Event_Description = rs.getString("Event_Description");
            Event e = new Event(Event_Name, Event_Date, Event_Description);
            obs.add(e);
        }
        return obs;
    }

    public void conectar() {
        try {
            c = DriverManager.getConnection(url + bd, login, password);
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    public void close() {
        try {
            if (c != null) {
                c.close();
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }
    public void Rellenatabla() throws SQLException {
        ObservableList<Event> obs = FXCollections.observableArrayList();
        Statement stat = c.createStatement();
        ResultSet rs = stat.executeQuery("SELECT * from events");
        while(rs.next()){
            Event e = new Event();
            e.setEvent_Date(rs.getString("Event_Date"));
            e.setEvent_name(rs.getString("Event_name"));
            e.setEvent_Description(rs.getString("Event_Description"));
            obs.add(e);
        }
    }

    @FXML
    public void EditProfile(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("EditProfile.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 560, 480);
        EditProfile controlador = fxmlLoader.getController();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();

    }
    @FXML
    public void BackLogin1(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 560, 480);
        LoginController controlador = fxmlLoader.getController();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();

    }

    public ArrayList<Event> buscareventos() throws SQLException{
        ArrayList<Event> eventos = new ArrayList<>();
        Statement stat = c.createStatement();
        ResultSet rs = stat.executeQuery("SELECT * from events");
        while(rs.next()){
            Event e = new Event();
            e.setEvent_Date(rs.getString("Event_Date"));
            e.setEvent_name(rs.getString("Event_name"));
            e.setEvent_Description(rs.getString("Event_Description"));
            eventos.add(e);
        }
        return eventos;
    }

}
