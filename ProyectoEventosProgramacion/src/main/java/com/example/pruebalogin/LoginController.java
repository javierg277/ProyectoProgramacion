package com.example.pruebalogin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.pruebalogin.Domain.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    private final String bd = "eventos_programacion";
    private final String url = "jdbc:mysql://localhost:3306/";
    private final String login = "root";
    private final String password = "";

    private Connection c = null;

    @FXML
    private Button Clearbtt;
    @FXML
    private TextField UserNametxt;
    @FXML
    private TextField Passwordtxt;

    @FXML
    private Label Errorlbl;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public LoginController() {
        conectar();
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
    @FXML
    private void UserValidate(MouseEvent mouseEvent) throws SQLException, IOException {
        ArrayList<String> nombreusuarios = RecogeUser();
        String nombreintroducido = (String) UserNametxt.getText();
        for(String nombrearraylist : nombreusuarios){
            if(nombrearraylist.equals(nombreintroducido)){
                if(Passwordtxt.getText().equals(recogecontraseña(nombreintroducido))){
                    UserMenu();
                }
            }
        }

    }
    @FXML
    public void UserMenu() throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("UserView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 560, 480);
            UserController controlador = fxmlLoader.getController();
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
    }
    public ArrayList RecogeUser() throws SQLException {
        ArrayList<String> usuarios = new ArrayList<>();

        Statement stat = c.createStatement();
        ResultSet rs = stat.executeQuery("SELECT UserName from users");
        while(rs.next()){
            String nombre = rs.getString("UserName");
            usuarios.add(nombre);
        }
        return usuarios;
    }
    public String recogecontraseña(String usuario) throws SQLException {
        String contraseña = "";

        PreparedStatement stat = c.prepareStatement("SELECT Password from users where UserName = ?");
        stat.setString(1 , usuario);
        ResultSet res = stat.executeQuery();
        while(res.next()){
            contraseña = res.getString("password");
        }
        return contraseña;
    }


}

