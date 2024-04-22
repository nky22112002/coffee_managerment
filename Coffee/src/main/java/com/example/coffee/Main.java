package com.example.coffee;
import com.example.coffee.DAO.LoginDAO;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;



public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        LoginDAO.getInstance().MenuLogin();

    }

    public static void main(String[] args) {
        launch();
    }
}