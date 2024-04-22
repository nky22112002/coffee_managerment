package com.example.coffee.DAO;

import com.example.coffee.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.coffee.constant.constants.ICON_COFFEE;

public class LoginDAO {
    private static LoginDAO instance;
    private Stage stage = new Stage();
    public static LoginDAO getInstance() {
        if (instance == null)
            instance = new LoginDAO();
        return instance;
    }
    public void UpdateAccountLogin(String userName) throws SQLException {
        String query = "update account set active = 1 where user_name = '" + userName + "'";
        PreparedStatement pst = DBConnection.connection().prepareStatement(query);
        pst.executeUpdate();
    }
    public int CheckLogin(String userName, String password) throws SQLException {
        PreparedStatement pst;
        String sql = "exec USP_checkLogin @userName = '" + userName + "', @password = '" + password + "'";
        pst = DBConnection.connection().prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            if (rs.getInt(1) == 1){
                return 1;
            }

        }
        return 0;
    }
    public void MenuLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("rsLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.getIcons().add(new Image(ICON_COFFEE));
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    private LoginDAO(){}
    public static void setInstance(LoginDAO instance) {
        LoginDAO.instance = instance;
    }
}
