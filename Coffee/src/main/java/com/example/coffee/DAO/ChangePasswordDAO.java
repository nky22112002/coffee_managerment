package com.example.coffee.DAO;

import com.example.coffee.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChangePasswordDAO {
    private static ChangePasswordDAO instance;
    public static ChangePasswordDAO getInstance() {
        if (instance == null)
            instance = new ChangePasswordDAO();
        return instance;
    }
    public void UpdateAccount(String userName, String password, String newPassword) throws SQLException {
        String query = "exec USP_UpdateAccount @userName = '" + userName + "' , @Password = '" + password + "' , @newPassword = '" + newPassword + "'";
        PreparedStatement pst = DBConnection.connection().prepareStatement(query);
        pst.executeUpdate();
        pst.close();
    }

    public void MenuChangePassword(ActionEvent event) throws IOException {
        //URL url = Paths.get("src/main/resources/com/example/coffee/rsChangePassword.fxml").toUri().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("rsChangePassword.fxml"));
        //root = FXMLLoader.load(url);
        Stage stage = new Stage();
        //stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 450, 400);
        stage.setTitle("Change Password");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    private ChangePasswordDAO(){}
    public static void setInstance(ChangePasswordDAO instance) {
        ChangePasswordDAO.instance = instance;
    }
}
