/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Nader Fares
 */
package baseline;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {

    //declare FXML componenets

    //display item list
    @FXML
    private TableView<Item> itemTableView;

    @FXML
    private TableColumn<Item, String> tableName;

    @FXML
    private TableColumn<Item, String> tableSN;

    @FXML
    private TableColumn<Item, Double> tableValue;


    //textfield to search for item
    @FXML
    private TextField serialTextField;


    //textfields to enter item data
    @FXML
    private TextField valueTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField searchTextField;


    public void addItemToList(ActionEvent actionEvent) {
        /*
        checks if serial number input is unique
        checks if value input invalid
        checks if name input is invalid

        if passes all checks add item to list
            create item object with data from textfields
            add to list

        else throw alert depending on which fails
         */
    }

    public void deleteItemFromList(ActionEvent actionEvent) {
        //get id of selected item
        int itemId;

        //delete item with the same itemId
    }

    public void clearList(ActionEvent actionEvent) {
        //clear item list
    }

    public void editItemOnList(ActionEvent actionEvent) {
        //get id of selected item
        int itemId;

        /*
        find item in list with matching itemId

        check for invalid value
        check for duplicate serial number
        check for invalid name

        if passes all edit item
            ignore empty textfields
            replace item data with textfield input

        else throw alert
         */
    }

    public void sortByValue(ActionEvent actionEvent) {
        //button on tableview
    }

    public void sortBySerial(ActionEvent actionEvent) {
        //button on tableview
    }

    public void sortByName(ActionEvent actionEvent) {
        //button on tableview
    }

    public void loadList(ActionEvent actionEvent) {
        /*
        JfileChooser
        get chosen file
        if file matches one of the 3 accepted formats
            clear list
            create object for each item on file
            add to list
         */


    }

    public void saveAsTSV(ActionEvent actionEvent) {
        /*
        opens Jfilechooser
        user chooses
            save location
            name
        create text file
        separate data
        print item list onto newly created text file
         */
    }

    public void saveAsJSON(ActionEvent actionEvent) {
        /*
        opens Jfilechooser
        user chooses
            save location
            name
        create json file
        translate objects into json using gson
        print item list onto newly created json file
         */
    }

    public void saveAsHTML(ActionEvent actionEvent) {
        /*
        opens Jfilechooser
        user chooses
            save location
            name
        create html file
        create table with 3 columns and rows depending on size of list
        fill table accordingly
         */
    }

    public void searchItem(ActionEvent actionEvent) {
        /*
        check if textfield is empty
        if empty throw alert
        else
            filter list to only item that matches name/sn
        display filtered list
         */
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
