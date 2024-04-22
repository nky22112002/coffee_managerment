package com.example.coffee;

import com.example.coffee.DAO.AdminDAO;
import com.example.coffee.DTO.*;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.*;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class MenuAdmin implements Initializable {




    @FXML
    private TableView<TurnoverData> tbvTurnover;
    @FXML
    private TableColumn<TurnoverData, Integer> tbcIDTurnover;

    @FXML
    private TableColumn<TurnoverData, String> tbcTableTurnover;

    @FXML
    private TableColumn<TurnoverData, Double> tbcTotalPriceTurnover;

    @FXML
    private TableColumn<TurnoverData, Date> tbcCheckInTurnover;

    @FXML
    private TableColumn<TurnoverData, Date> tbcCheckOutTurnover;

    @FXML
    private TableColumn<TurnoverData, Integer> tbcDiscountTurnover;

    @FXML
    private ComboBox<String> cbbCategoryFood;

    @FXML
    private DatePicker dtpStartDate;

    @FXML
    private DatePicker dtpLastDate;

    @FXML
    private TableView<FoodsData> tbvFoods;

    @FXML
    private TableColumn<FoodsData, Integer > tbcIDFoods;

    @FXML
    private TableColumn<FoodsData, String> tbcNameFoods;

    @FXML
    private TableColumn<FoodsData, String > tbcPriceFoods;

    @FXML
    private TableColumn<FoodsData, String> tbcCategoryNameFoods;
    @FXML
    private TextField tfPageTurnover;

    @FXML
    private TextField tfFoodName;

    @FXML
    private TextField tfPriceFoods;

    @FXML
    private TableView<TableFoodData> tbvTableFood;

    @FXML
    private TableColumn<TableFoodData, Integer> tbcIDTableFood;

    @FXML
    private TableColumn<TableFoodData, String> tbcTNTableFood;

    @FXML
    private TableColumn<TableFoodData, String> tbcStatusTableFood;

    @FXML
    private TextField tfIDFoods;

    @FXML
    private TextField tfIdTableFood;

    @FXML
    private TextField tfTableName;

    @FXML
    private ComboBox<String> cbbStatusTableFood;

    @FXML
    private TableView<CategoryData> tbvCategory;

    @FXML
    private TableColumn<CategoryData, Integer> tbcIDCategory;

    @FXML
    private TableColumn<CategoryData, String> tbcNameCategory;

    @FXML
    private TextField tfIdCategory;

    @FXML
    private TextField tfCategoryName;

    @FXML
    private TableView<AccountData> tbvAccount;

    @FXML
    private TableColumn<AccountData, String> tbcDisplayNameAccount;

    @FXML
    private TableColumn<AccountData, String > tbcUserNameAccount;

    @FXML
    private TableColumn<AccountData, String> tbcTypeAccount;

    @FXML
    private TextField tfDisplayNameAccount;

    @FXML
    private TextField tfUserNameAccount;

    @FXML
    private ComboBox<String> cbbAccountType;

    private ObservableList<TurnoverData> dtTurnover;
    private ObservableList<CategoryData> dtCategory;
    private ObservableList<TableFoodData> dtTableFood;
    private ObservableList<AccountData> dtAccount;
    private ObservableList<FoodsData> dtFoods;

    void loadDataAccount(){
        try {
            AdminDAO.getInstance().LoadDataAccount(tbvAccount, dtAccount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tfUserNameAccount.setText("");
        tfDisplayNameAccount.setText("");
        cbbAccountType.setValue("");
    }

    int getTypeAccount(){
        if (cbbAccountType.getValue().equals("Admin")){
            return 1;
        }
        return 0;
    }
    void setDataTypeAccount() throws SQLException {
        ObservableList<String> listPosition = cbbAccountType.getItems();
        listPosition.add("Admin");
        listPosition.add("Staff");
        cbbAccountType.setEditable(true);
    }
    @FXML
    void clickGetDataOnAccount() {
        AccountData accountData = tbvAccount.getSelectionModel().getSelectedItem();
        tfDisplayNameAccount.setText(accountData.getDisplayName());
        tfUserNameAccount.setText(accountData.getUserName());
        cbbAccountType.setValue(accountData.getTypeAccount());
    }
    @FXML
    void clickButtonRepairAccount() throws SQLException {
        String displayName = tfDisplayNameAccount.getText();
        int typeAccount = getTypeAccount();
        String userName = tfUserNameAccount.getText();
        AdminDAO.getInstance().RepairAccount(displayName, typeAccount, userName);
        loadDataAccount();
    }
    @FXML
    void clickButtonViewAccount(){
        loadDataAccount();
    }
    @FXML
    void clickButtonDeleteAccount() throws SQLException {
        String userName = tfUserNameAccount.getText();
        AdminDAO.getInstance().DeleteAccount(userName);
        loadDataAccount();
    }
    @FXML
    void clickButtonAddAccount(){
        try{
            if (!AdminDAO.getInstance().isExistAccount(tfUserNameAccount)){
                String displayName = tfDisplayNameAccount.getText();
                String userName = tfUserNameAccount.getText();
                String typeAccount = cbbAccountType.getValue();
                if (typeAccount.equals("Staff")){
                    AdminDAO.getInstance().AddAcount(displayName, userName, 0);
                    JOptionPane.showMessageDialog(null,"Successful!!!","Warning!!!",JOptionPane.INFORMATION_MESSAGE);
                }
                else if(typeAccount.equals("Admin")){
                    AdminDAO.getInstance().AddAcount(displayName, userName, 1);
                    JOptionPane.showMessageDialog(null,"Successful!!!","Warning!!!",JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else if(AdminDAO.getInstance().isExistAccount(tfUserNameAccount)) {
                JOptionPane.showMessageDialog(null,"User name is already","Warning!!!",JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch (Exception exception){
            JOptionPane.showMessageDialog(null,"Please enter the full information","Warning!!!",JOptionPane.INFORMATION_MESSAGE);
        }
        loadDataAccount();
    }



    private void loadDataFood() throws SQLException {
        AdminDAO.getInstance().LoadDataFood(tbvFoods, dtFoods);
        tfIDFoods.setDisable(true);
    }


    @FXML
    void clickGetDataOnFood() {
        FoodsData Food = tbvFoods.getSelectionModel().getSelectedItem();
        tfIDFoods.setText("" + Food.getID());
        tfFoodName.setText(Food.getFoodName());
        cbbCategoryFood.setValue(Food.getCategoryName());
        tfPriceFoods.setText("" + Food.getPrice());
    }
    @FXML
    void clickButtonDeleteFood() throws SQLException {
        AdminDAO.getInstance().DeleteFood(tfFoodName.getText());
        loadDataFood();
    }
    @FXML
    void clickButtonViewFood() throws SQLException {
        loadDataFood();
    }
    @FXML
    void clickButtonAddFood(){
        String foodName = tfFoodName.getText();
        int id = Integer.parseInt(tfIDFoods.getText());
        double price = Double.parseDouble(tfPriceFoods.getText());
        try{
            AdminDAO.getInstance().isExistFood(tfFoodName, tfPriceFoods);
            AdminDAO.getInstance().AddFoods(foodName, id, price);
            loadDataFood();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Please enter the full information","Warning!!!",JOptionPane.INFORMATION_MESSAGE);
        }

    }
    @FXML
    void clickButtonRepairFood() throws SQLException {
        double price = Double.parseDouble(tfPriceFoods.getText());
        int id = Integer.parseInt(tfIDFoods.getText());
        String categoryFood = cbbCategoryFood.getValue();
        int idCategory = AdminDAO.getInstance().selectIDCategory(categoryFood);
        String foodName = tfFoodName.getText();
        AdminDAO.getInstance().RepairFood(id, foodName, idCategory, price);
        loadDataFood();
    }
    @FXML
    void clickButtonSearchFood() throws SQLException {
        AdminDAO.getInstance().SearchFood(tfFoodName.getText(), tbvFoods, dtFoods);
    }



    void loadDataTurnover(LocalDate dateStart, LocalDate dateLast, int pages) throws SQLException {
        AdminDAO.getInstance().LoadDataTurnover(tbvTurnover, dtTurnover, dateStart, dateLast, pages);
    }

    @FXML
    void viewDataTurnover() throws SQLException {
        dtTurnover = FXCollections.observableArrayList();
        LocalDate dateStart = dtpStartDate.getValue();
        LocalDate dateLast = dtpLastDate.getValue();
        int pages = Integer.parseInt(tfPageTurnover.getText());
        loadDataTurnover(dateStart,dateLast,pages);
    }
    @FXML
    void clickButtonHeadTurnover() throws SQLException {
        LocalDate dateStart = dtpStartDate.getValue();
        LocalDate dateLast = dtpLastDate.getValue();

        loadDataTurnover(dateStart, dateLast, 1);
        tfPageTurnover.setText(String.valueOf(1));
    }
    @FXML
    void clickButtonPreviousTurnover() throws SQLException {
        try{
            int pages = Integer.parseInt(tfPageTurnover.getText());
            LocalDate dateStart = dtpStartDate.getValue();
            LocalDate dateLast = dtpLastDate.getValue();
            if (pages > 1){
                loadDataTurnover(dateStart, dateLast, --pages);
                tfPageTurnover.setText(String.valueOf(pages));
            }
        }
        catch (SQLServerException e){
            JOptionPane.showMessageDialog(null,"NULL","Warning!!!",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    @FXML
    void clickButtonLastTurnover() throws SQLException {
        int maxPages;
        LocalDate dateStart = dtpStartDate.getValue();
        LocalDate dateLast = dtpLastDate.getValue();
        int numRow = AdminDAO.getInstance().LoadNumPages();
        maxPages = numRow / 11;
        if (numRow % 11 != 0){
            maxPages = numRow / 11 + 1;
        }
        loadDataTurnover(dateStart, dateLast, maxPages);
        tfPageTurnover.setText(String.valueOf(maxPages));
    }
    @FXML
    void clickButtonNextTurnover() throws SQLException {
        int maxPages;
        int pages = Integer.parseInt(tfPageTurnover.getText());
        LocalDate dateStart = dtpStartDate.getValue();
        LocalDate dateLast = dtpLastDate.getValue();
        int numRow = AdminDAO.getInstance().LoadNumPages();
        maxPages = numRow / 11;
        if (numRow % 11 != 0){
            maxPages = numRow / 11 + 1;
        }
        if (pages < maxPages){
            loadDataTurnover(dateStart, dateLast, ++pages);
            tfPageTurnover.setText(String.valueOf(pages));
        }
    }



    void loadDataCategory() throws SQLException {
        AdminDAO.getInstance().LoadDataCategory(tbvCategory, dtCategory);
        tfIdCategory.setDisable(true);
        tbvCategory.setItems(dtCategory);
        tfIdCategory.setText("");
        tfCategoryName.setText("");
    }


    @FXML
    void clickGetDataOnCategory(){
        CategoryData categoryData = tbvCategory.getSelectionModel().getSelectedItem();
        tfIdCategory.setText("" + categoryData.getId());
        tfCategoryName.setText(categoryData.getCategoryName());
    }
    @FXML
    void clickButtonViewCategory() throws SQLException {
        loadDataCategory();
    }
    @FXML
    void clickButtonAddCategory() throws SQLException {
        if (tfCategoryName.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Please enter the full information","Warning!!!",JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            AdminDAO.getInstance().isExistCategory(tfCategoryName);
            loadDataCategory();
        }

    }
    @FXML
    void clickButtonRepairCategory() throws SQLException {
        int id = Integer.parseInt(tfIdCategory.getText()) ;
        String categoryName = tfCategoryName.getText();
        AdminDAO.getInstance().RepairCategory(categoryName, id);
        loadDataCategory();
    }
    @FXML
    void clickButtonDeleteCategory() throws SQLException {
        String categoryName = tfCategoryName.getText();
        AdminDAO.getInstance().DeleteCategory(categoryName);
        loadDataCategory();
    }



    void loadDataTableFood() throws SQLException {
        AdminDAO.getInstance().LoadDataTableFood(tbvTableFood, dtTableFood);
        tfIdTableFood.setDisable(true);
        tfIdTableFood.setText("");
        tfTableName.setText("");
        cbbStatusTableFood.setValue("");
    }
    void setDataStatusTable(){
        ObservableList<String> listStatus = cbbStatusTableFood.getItems();
        listStatus.add("Active");
        listStatus.add("Empty");
        cbbAccountType.setEditable(true);
    }

    @FXML
    void clickGetDataOnTableFood(){
        TableFoodData tableFoodData = tbvTableFood.getSelectionModel().getSelectedItem();
        tfIdTableFood.setText("" + tableFoodData.getID());
        tfTableName.setText(tableFoodData.getTableName());
        cbbStatusTableFood.setValue("" + tableFoodData.getStatus());
    }
    @FXML
    void clickButtonViewTableFood() throws SQLException {
        loadDataTableFood();
    }
    @FXML
    void clickButtonRepairTableFood() throws SQLException {
        int id = Integer.parseInt(tfIdTableFood.getText());
        String tableName = tfTableName.getText();
        AdminDAO.getInstance().RepairTableFood(id, tableName);
        loadDataTableFood();
    }
    @FXML
    void clickButtonDeleteTableFood() throws SQLException {
        String tableName = tfTableName.getText();
        AdminDAO.getInstance().DeleteTableFood(tableName);
        loadDataTableFood();
    }
    @FXML
    void clickButtonAddTableFood() throws SQLException {
        String tableName = tfTableName.getText();
        AdminDAO.getInstance().AddTableFood(tableName);
        loadDataTableFood();
    }



    private void initiateCols(){
        tbcDisplayNameAccount.setCellValueFactory(new PropertyValueFactory<>("displayName"));
        tbcUserNameAccount.setCellValueFactory(new PropertyValueFactory<>("userName"));
        tbcTypeAccount.setCellValueFactory(new PropertyValueFactory<>("typeAccount"));

        tbcIDFoods.setCellValueFactory(new PropertyValueFactory<>("iD"));
        tbcNameFoods.setCellValueFactory(new PropertyValueFactory<>("foodName"));
        tbcPriceFoods.setCellValueFactory(new PropertyValueFactory<>("price"));
        tbcCategoryNameFoods.setCellValueFactory(new PropertyValueFactory<>("categoryName"));

        tbcIDTurnover.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbcTableTurnover.setCellValueFactory(new PropertyValueFactory<>("tableName"));
        tbcTotalPriceTurnover.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        tbcCheckInTurnover.setCellValueFactory(new PropertyValueFactory<>("dateCheckIn"));
        tbcCheckOutTurnover.setCellValueFactory(new PropertyValueFactory<>("dateCheckOut"));
        tbcDiscountTurnover.setCellValueFactory(new PropertyValueFactory<>("discount"));

        tbcIDCategory.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbcNameCategory.setCellValueFactory(new PropertyValueFactory<>("categoryName"));

        tbcIDTableFood.setCellValueFactory(new PropertyValueFactory<>("iD"));
        tbcTNTableFood.setCellValueFactory(new PropertyValueFactory<>("tableName"));
        tbcStatusTableFood.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    @Override
    public void initialize(URL url, java.util.ResourceBundle resourceBundle) {

        dtpStartDate.setValue(LocalDate.now());
        dtpLastDate.setValue(LocalDate.now());
        dtAccount = FXCollections.observableArrayList();
        dtFoods = FXCollections.observableArrayList();
        dtCategory = FXCollections.observableArrayList();
        dtTableFood = FXCollections.observableArrayList();
        initiateCols();

        try {
            AdminDAO.getInstance().setDataCategory(cbbCategoryFood);
            setDataTypeAccount();
            setDataStatusTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
