/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Nader Fares
 */
package baseline;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.InputMismatchException;
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
    private TextField searchTextField;

    //textfields to enter item data
    @FXML
    private TextField nameTextField;

    @FXML
    private TextField serialTextField;

    @FXML
    private TextField valueTextField;

    @FXML
    private Button addButton;

    ItemControllerMethods itemList = new ItemControllerMethods();

    @FXML
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
        try {
            String errorType = itemList.addItem(nameTextField.getText(), serialTextField.getText(), valueTextField.getText());
            if (errorType != null) {
                new ErrorMap(errorType);
            }
        } catch(InputMismatchException e) {
            System.out.println("Input Error");
        } finally {
            refresh();
            itemTableView.setItems(itemList.items.getItemList());
        }
    }

    public void refresh() {
        //refresh text fields after every addition
        nameTextField.setText("");
        serialTextField.setText("");
        valueTextField.setText("");
    }

    @FXML
    public void deleteItemFromList(ActionEvent actionEvent) {
        //get id of selected item
        int itemId = itemTableView.getSelectionModel().getSelectedItem().getItemId();

        itemList.deleteItem(itemId);
        itemTableView.setItems(itemList.items.getItemList());
        //delete item with the same itemId
    }

    @FXML
    public void clearList(ActionEvent actionEvent) {
        //clear item list
        itemList.clearItems();
    }

    @FXML
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

    @FXML
    public void sortByValue(ActionEvent actionEvent) {
        //button on tableview
    }

    @FXML
    public void sortBySerial(ActionEvent actionEvent) {
        //button on tableview
    }

    @FXML
    public void sortByName(ActionEvent actionEvent) {
        //button on tableview
    }

    @FXML
    public void loadList(ActionEvent actionEvent) {
        /*
        JfileChooser
        get chosen file
        if file matches one of the 3 accepted formats
            clear list
            create object for each item on file
            add to list
         */
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Load File");

        fileChooser.showOpenDialog(null);

        File selectedFile = fileChooser.getSelectedFile();
        if (selectedFile != null) {
            itemList.clearItems();      //        clear original list

            //        only allow txt file to be loaded
            if (selectedFile.getName().endsWith(".txt")) {
                System.out.println("txt");
                loadTSVFile(selectedFile);
            }

            else if (selectedFile.getName().endsWith(".json")){
                System.out.println("json");
                loadJSONFile(selectedFile);

                itemTableView.setItems(itemList.items.getItemList());

            }

            else if (selectedFile.getName().endsWith(".html")) {
                System.out.println("html");
                loadHTMLFile(selectedFile);

            }
            else {
                new ErrorMap("file");     //            throw error alert if not
                System.out.println("oop");
            }


        }
    }

    private void loadHTMLFile(File selectedFile) {
    }

    private void loadJSONFile(File selectedFile) {
        //read json file
        try {
            try (Reader reader = Files.newBufferedReader(selectedFile.toPath())) {
                itemList.items = new Gson().fromJson(reader, ItemList.class);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void loadTSVFile(File selectedFile) {
        String currentLine;
        try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {

            while ((currentLine = reader.readLine()) != null) {
                String[] words = currentLine.split("     ");
                setTextFields(words);
                addButton.fire();
            }
            itemTableView.setItems(itemList.items.getItemList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setTextFields(String[] words) {
        serialTextField.setText(words[0]);
        nameTextField.setText(words[1]);
        valueTextField.setText(words[2]);
    }

    @FXML
    public void saveAsTSV(ActionEvent actionEvent) throws IOException {
        /*
        opens Jfilechooser
        user chooses
            save location
            name
        create text file
        separate data
        print item list onto newly created text file
         */

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save file TSV");
        fileChooser.showSaveDialog(null);


//        user chooses file
        File selectedFile = fileChooser.getSelectedFile();      //save location and name

//        create text file
        if (selectedFile != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile + ".txt"))) {
                for (int i = 0; i < itemTableView.getItems().size(); i++) {
                    writer.write(itemTableView.getItems().get(i).getItemSerial() + "     " +
                            itemTableView.getItems().get(i).getItemName() + "     " +
                            itemTableView.getItems().get(i).getItemValue()+ "\n");
                }
            }
        } else
            System.out.println("File not found");

    }

    @FXML
    public void saveAsJSON(ActionEvent actionEvent) throws IOException {
        /*
        opens Jfilechooser
        user chooses
            save location
            name
        create json file
        translate objects into json using gson
        print item list onto newly created json file
         */


        Gson gson = new Gson();
        String json = gson.toJson(itemList.items.getItemList());

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save file JSON");
        fileChooser.showSaveDialog(null);

        //        user chooses file
        File selectedFile = fileChooser.getSelectedFile();      //save location and name

//        create text file
        if (selectedFile != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile + ".json"))) {
                writer.write(json);
                System.out.println(json);
            }
        } else
            System.out.println("File not found");



    }

    @FXML
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

    @FXML
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

        tableName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        tableSN.setCellValueFactory(new PropertyValueFactory<>("itemSerial"));
        tableValue.setCellValueFactory(new PropertyValueFactory<>("itemValue"));
        itemTableView.setItems(itemList.items.getItemList());

    }

}
