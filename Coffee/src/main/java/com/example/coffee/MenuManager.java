package com.example.coffee;

import com.example.coffee.DAO.*;
import com.example.coffee.DTO.CategoryData;
import com.example.coffee.DTO.FoodsData;
import com.example.coffee.DTO.MenuData;
import com.example.coffee.DTO.TableFoodData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MenuManager implements Initializable {

    @FXML
    public MenuBar mnb;
    @FXML
    private TableView<MenuData> tbvMenumanager;

    @FXML
    private TableColumn<MenuData, ?> tbcFoodNameMenuManager;

    @FXML
    private TableColumn<MenuData, ?> tbcCountMenuManager;

    @FXML
    private TableColumn<MenuData, ?> tbcPriceMenuManager;

    @FXML
    private TableColumn<MenuData, ?> tbcTotalPriceMenuManager;

    @FXML
    private Spinner<Integer> sDiscount;

    @FXML
    private ComboBox<CategoryData> cbboxFoodCategories;

    @FXML
    private ComboBox<?> cbbTableFood;

    @FXML
    private ComboBox<?> cbboxFoods;

    @FXML
    private TextField tfTotalPrice;

    @FXML
    private Spinner<Integer> sCount;

    @FXML
    private FlowPane flpManager;
    private ResultSet rs = null;
    private PreparedStatement pst = null;
    private ObservableList<MenuData> mnTable;
    private final ObservableList<CategoryData> dtCategory = FXCollections.observableArrayList();
    private final ObservableList<TableFoodData> dtCbbTableFood = FXCollections.observableArrayList();
    private final ObservableList<FoodsData> dtFoods = FXCollections.observableArrayList();
    public int idTable;
    public int idTable2;
    public String userName;


    void tableFood() throws SQLException {
        flpManager.getChildren().clear();
        int c = ManagerDAO.getInstance().CountTableFood();
        Button [] btn = new Button[c];

        pst = DBConnection.connection().prepareStatement("select * from table_food");
        rs = pst.executeQuery();
        int i=0;
        while (rs.next())
        {
            btn[i] = new Button();
            String tableName = rs.getString(2) + "\n" + rs.getString(3);
            int id = rs.getInt(1);
            btn[i].setText(tableName);
            btn[i].setMinWidth(80);
            btn[i].setMinHeight(80);

            if (rs.getString(3).equals("Active")) {
                btn[i].setStyle("-fx-background-color:#b1ff65");
            }
            btn[i].setOnAction(event -> {
                try {
                    tbvMenumanager.getItems().clear();
                    ManagerDAO.getInstance().LoadTable(id, tbvMenumanager, mnTable, tfTotalPrice);
                    idTable = id;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

            i++;

        }
        flpManager.getChildren().addAll(btn);

    }
    void addFood() throws SQLException {
        try{
            SpinnerValueFactory<Integer> initialValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(-100, 100);
            initialValue.setValue(0);
            int idBill = ManagerDAO.getInstance().getIdBill(idTable);
            String name = String.valueOf(cbboxFoods.getValue());
            int idFood = ManagerDAO.getInstance().getIDFood(name);
            double count = Double.parseDouble(String.valueOf(sCount.getValue()));
            if(idBill == -1){
                ManagerDAO.getInstance().InsertBill(idTable);
                idBill = ManagerDAO.getInstance().getMaxIDBill();
                ManagerDAO.getInstance().InsertBillInfo(idBill,idFood,count);
            }
            else {
                ManagerDAO.getInstance().InsertBillInfo(idBill,idFood,count);
            }
            sCount.setValueFactory(initialValue);
        }catch (SQLException e){
            e.printStackTrace();
        }
        tableFood();
        ManagerDAO.getInstance().LoadTable(idTable, tbvMenumanager, mnTable, tfTotalPrice);
    }
    void checkOut() throws SQLException {
        double sum = 0;
        double s;
        int idBill = ManagerDAO.getInstance().getIdBill(idTable);
        SpinnerValueFactory<Integer> initialValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(-100, 100);
        initialValue.setValue(0);
        double totalPrice = Double.parseDouble(tfTotalPrice.getText().replace("$", ""));
        s = Double.parseDouble(String.valueOf(sDiscount.getValue()));
        if (s != 0) {
            sum += totalPrice - (totalPrice * s) / 100;
            JOptionPane.showMessageDialog(null, sum,
                    "Total Price", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(null, tfTotalPrice.getText(),
                    "Total Price", JOptionPane.INFORMATION_MESSAGE);
        }
        ManagerDAO.getInstance().UpdateBill(idBill, s, totalPrice);
        ManagerDAO.getInstance().LoadTable(idTable, tbvMenumanager, mnTable, tfTotalPrice);
        tableFood();
        sDiscount.setValueFactory(initialValue);
    }



    @FXML
    void clickOnMenuAdmin() throws IOException {
        AdminDAO.getInstance().MenuAdmin(mnb);
    }
    @FXML
    void clickOnMenuPersonalInformation(ActionEvent event) throws IOException {
        ChangePasswordDAO.getInstance().MenuChangePassword(event);
    }
    @FXML
    void clickOnMenuLogout() throws IOException {
        LoginDAO.getInstance().MenuLogin();
    }
    @FXML
    void clickHotkeyAddFood() throws SQLException {
        addFood();
    }
    @FXML
    void selectcbbTableFood() throws SQLException {
        ManagerDAO.getInstance().selectcbbTableFood(cbbTableFood, idTable2);
    }
    @FXML
    void SwitchTableFood() throws SQLException {
        String query = "USP_SwitchTable @idTable1 = " + idTable + ",@idTable2 = " + idTable2;
        pst = DBConnection.connection().prepareStatement(query);
        pst.executeUpdate();
        ManagerDAO.getInstance().LoadTable(idTable, tbvMenumanager, mnTable, tfTotalPrice);
        tableFood();
        pst.close();
    }
    @FXML
    void clickOnAddFood() throws SQLException {
        addFood();
    }
    @FXML
    void clickHotkeyCheckout() throws SQLException {
        checkOut();
    }
    @FXML
    void clickOnCheckout() throws SQLException {
        checkOut();
    }

    @FXML
    void selectcbbCategories() throws SQLException {
        cbboxFoods.getItems().clear();
        int id;
        String name = String.valueOf(cbboxFoodCategories.getValue());

        pst = DBConnection.connection().prepareStatement("select id from food_category\n" +
                "where category_name = '" + name + "'");
        rs = pst.executeQuery();
        rs.next();
        id = rs.getInt("id");
        ManagerDAO.getInstance().getFoodName(id, dtFoods, cbboxFoods);
    }


    private void initilizeCols(){
        tbcFoodNameMenuManager.setCellValueFactory(new PropertyValueFactory<>("foodName"));
        tbcCountMenuManager.setCellValueFactory(new PropertyValueFactory<>("count"));
        tbcPriceMenuManager.setCellValueFactory(new PropertyValueFactory<>("price"));
        tbcTotalPriceMenuManager.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            ManagerDAO.getInstance().setAccount(userName, mnb);
            mnTable = FXCollections.observableArrayList();
            initilizeCols();
            tableFood();
            ManagerDAO.getInstance().FoodCategories(dtCategory, cbboxFoodCategories);
            ManagerDAO.getInstance().LoadCbbTableFood(dtCbbTableFood, cbbTableFood);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
