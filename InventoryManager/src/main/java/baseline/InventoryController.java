/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Nader Fares
 */
package baseline;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;

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
    private TableColumn<Item, String> tableValue;


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
    private MenuItem sortValueButton;
    @FXML
    private MenuItem sortSerialButton;
    @FXML
    private MenuItem sortNameButton;
    @FXML
    private MenuItem sortOrderButton;

    private static final String ORDER = "order";
    private static final String INPUT_ERROR = "Input Error";
    private static final String TAB = " {5}";
    private static final String COL_TAG = "</td>";

    private String currentView = ORDER;

    @FXML
    public void addItemToList(ActionEvent actionEvent) {
        try {
            String errorType = itemList.addItem(nameTextField.getText(), serialTextField.getText(), valueTextField.getText().replace("$", "")); //create item object with data from textfields and add to list
            if (errorType != null) {
                new ErrorMap(errorType);    //        else throw alert depending on which fails
            }
        } catch (InputMismatchException e) {
            System.out.println(INPUT_ERROR);
        } finally {
            refresh();
        }
    }


    //edit entire item
    @FXML
    public void editItem(ActionEvent actionEvent) {
        String serialNumber = itemTableView.getSelectionModel().getSelectedItem().getItemSerial();

        try {
            String errorType = itemList.editItem(serialNumber, nameTextField.getText(), serialTextField.getText(), valueTextField.getText().replace("$", ""));
            if (errorType != null) {
                new ErrorMap(errorType);
            }
        } catch (InputMismatchException e) {
            System.out.println(INPUT_ERROR);
        } finally {
            refresh();
        }
    }

    //edit item name only
    @FXML
    public void editName(ActionEvent actionEvent) {
        String serialNumber = itemTableView.getSelectionModel().getSelectedItem().getItemSerial();

        try {
            itemList.editItemName(serialNumber, nameTextField.getText());

        } catch (InputMismatchException e) {
            System.out.println(INPUT_ERROR);
        } finally {
            refresh();
        }
    }

    //edit item serial number only
    @FXML
    public void editSerial(ActionEvent actionEvent) {
        String serialNumber = itemTableView.getSelectionModel().getSelectedItem().getItemSerial();

        try {
            itemList.editItemSerial(serialNumber, serialTextField.getText());

        } catch (InputMismatchException e) {
            System.out.println(INPUT_ERROR);
        } finally {
            refresh();
        }
    }

    //edit item value only
    @FXML
    public void editValue(ActionEvent actionEvent) {
        String serialNumber = itemTableView.getSelectionModel().getSelectedItem().getItemSerial();

        try {
            itemList.editValue(serialNumber, valueTextField.getText().replace("$", ""));

        } catch (InputMismatchException e) {
            System.out.println(INPUT_ERROR);
        } finally {
            refresh();
        }
    }

    @FXML
    public void deleteItemFromList(ActionEvent actionEvent) {
        //get serial of selected item
        String serialNumber = itemTableView.getSelectionModel().getSelectedItem().getItemSerial();

        //delete item with the same serial
        itemList.deleteItem(serialNumber);
        refresh();
    }

    @FXML
    public void clearList(ActionEvent actionEvent) {
        //clear item list
        itemList.clearItems();
        refresh();
    }

    @FXML
    public void sortByValue(ActionEvent actionEvent) {
        //button on tableview
        currentView = "value";

        itemList.items.getItemArrayList().sort(Comparator.comparing(Item::getItemValueDouble));
        ObservableList<Item> tempList = FXCollections.observableArrayList(itemList.items.getItemArrayList());
        itemTableView.setItems(tempList);
    }

    @FXML
    public void sortByOrder(ActionEvent actionEvent) {
        //update current screen
        currentView = ORDER;

        //sort by add order
        itemList.items.getItemArrayList().sort(Comparator.comparing(Item::getItemId));
        ObservableList<Item> tempList = FXCollections.observableArrayList(itemList.items.getItemArrayList());
        itemTableView.setItems(tempList);
    }


    @FXML
    public void sortBySerial(ActionEvent actionEvent) {
        //update current screen
        currentView = "serial";

        //sort by serial
        itemList.items.getItemArrayList().sort(Comparator.comparing(Item::getItemSerial));
        ObservableList<Item> tempList = FXCollections.observableArrayList(itemList.items.getItemArrayList());
        itemTableView.setItems(tempList);
    }

    @FXML
    public void sortByName(ActionEvent actionEvent) {
        //update current screen
        currentView = "name";

        //sort by name
        itemList.items.getItemArrayList().sort(Comparator.comparing(Item::getItemName));
        ObservableList<Item> tempList = FXCollections.observableArrayList(itemList.items.getItemArrayList());
        itemTableView.setItems(tempList);
    }

    @FXML
    public void loadList(ActionEvent actionEvent) {
        //        JfileChooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Load File");

        fileChooser.showOpenDialog(null);

        File selectedFile = fileChooser.getSelectedFile();//        get chosen file


        if (selectedFile != null) {
            itemList.clearItems();      //        clear list

            //use different load function depending on selected file type
            if (selectedFile.getName().endsWith(".txt")) {
                loadTSVFile(selectedFile);

            } else if (selectedFile.getName().endsWith(".json")) {
                loadJSONFile(selectedFile);

            } else if (selectedFile.getName().endsWith(".html")) {
                loadHTMLFile(selectedFile);

            } else {
                new ErrorMap("file");     //            throw error alert if not
            }


        }
    }

    public void loadHTMLFile(File selectedFile) {
        Document doc = null;
        try {
            doc = Jsoup.parse(selectedFile, null);  //read selected html file
        } catch (IOException e) {
            e.printStackTrace();
        }


        assert doc != null;
        Elements rows = doc.select("tr");   //select items surrounded by <tr> tag to get all the list items

        //iterate through every row
        for (int i = 1; i < rows.size(); i++) {
            Element row = rows.get(i);
            Elements cols = row.select("td");   //select items surrounded by <td> to get name, serial, value
            String[] words = new String[3];         //declare string array to hold each item

            //iterate through every column
            for (int j = 0; j < cols.size(); j++) {
                Element col = cols.get(j);
                words[j] = col.text();          //split each item into string array
            }

            setTextFields(words);       //fill textfields with data
            addButton.fire();       //add items
        }
    }

    public void loadJSONFile(File selectedFile) {
        //read json file
        try {
            try (Reader reader = Files.newBufferedReader(selectedFile.toPath())) {

                Gson gson = new Gson();
                Type collectionType = new TypeToken<List<Item>>() {}.getType();
                itemList.items.setItemArrayList(gson.fromJson(reader, collectionType));

                refresh();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void loadTSVFile(File selectedFile) {
        String currentLine;
        try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {

            //iterate until end of file
            while ((currentLine = reader.readLine()) != null) {     //every line is one item
                String[] words = currentLine.split(TAB);    //split every 5 spaces (tab) line into name, serial, value
                setTextFields(words);   //fill data
                addButton.fire();       //add item
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void saveAsTSV(ActionEvent actionEvent) throws IOException {
        //open jfilechooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save file TSV");
        fileChooser.showSaveDialog(null);


//        user chooses file name and location
        File selectedFile = fileChooser.getSelectedFile();      //save location and name

        if (selectedFile != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile + ".txt"))) {   //        create text file
                for (int i = 0; i < itemTableView.getItems().size(); i++) {

//                    print item list onto newly created text file
                    writer.write(itemTableView.getItems().get(i).getItemSerial() + TAB +    //seperate data
                            itemTableView.getItems().get(i).getItemName() + TAB +
                            itemTableView.getItems().get(i).getItemValue() + "\n");
                }
            }
        } else
            System.out.println("File not found");
    }

    @FXML
    public void saveAsJSON(ActionEvent actionEvent) throws IOException {
        //json converter
        Gson gson = new Gson();
        String json = gson.toJson(itemList.items.getItemArrayList());     //translate objects into json using gson

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save file JSON");
        fileChooser.showSaveDialog(null);

        //        user chooses file
        File selectedFile = fileChooser.getSelectedFile();      //save location and name

//        create text file
        if (selectedFile != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile + ".json"))) {      //create json file
                writer.write(json);     //print json onto newly created file
            }
        } else
            System.out.println("File not found");
    }

    @FXML
    public void saveAsHTML(ActionEvent actionEvent) throws FileNotFoundException {
        //opens jfilechooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save file HTML");
        fileChooser.showSaveDialog(null);

        //        user chooses file
        File selectedFile = fileChooser.getSelectedFile();      //save location and name

        PrintWriter pw = new PrintWriter(selectedFile + ".html");       //create html file

        //add necessary elements
        pw.println("<html>" +
                "<head>" +
                "<title>Inventory Manager</title>" +       //title
                "</head>" +
                "<body>" +  //body tag
                "<table>" +         //create table
                "<tr>" +       //title row
                "<th>Serial Number</th>" +
                "<th>Name</th>" +
                "<th>Value</th>" +
                "</tr>");

        //fill table row with each item
        for (int i = 0; i < itemTableView.getItems().size(); i++) {
            pw.write("<tr>" + "<td>" + itemTableView.getItems().get(i).getItemSerial() + COL_TAG +      //fill individual columns with data
                    "<td>" + itemTableView.getItems().get(i).getItemName() + COL_TAG +
                    "<td>" + itemTableView.getItems().get(i).getItemValue() + COL_TAG + "</tr>");
        }

        pw.println("</table>" +     //close table
                "</body>");
        pw.println("</html>");      //closing tags
        pw.close();
    }

    @FXML
    public void searchItem(ActionEvent actionEvent) {
        //declare filtered list

        List<Item> filteredList;

//        //if textfield matches serial number format filter for serial
        if (searchTextField.getText().matches("^[A-Za-z]-[A-Za-z0-9]{3}-[A-Za-z0-9]{3}-[A-Za-z0-9]{3}"))
            filteredList = itemList.items.getItemArrayList().stream().filter(item -> item.getItemSerial().equals(searchTextField.getText())).toList();
//            //else filter for name
        else
            filteredList = itemList.items.getItemArrayList().stream().filter(item -> item.getItemName().equals(searchTextField.getText())).toList();

        ObservableList<Item> tempList = FXCollections.observableArrayList(filteredList);
        itemTableView.setItems(tempList);
        refreshTextFields();
    }

    public void refresh() {
        //refresh text fields and screen after every addition
        refreshTextFields();
        refreshScreen(currentView);
    }

    public void refreshTextFields() {
        //empty the textfields
        nameTextField.setText("");
        serialTextField.setText("");
        valueTextField.setText("");
        searchTextField.setText("");
    }

    //refresh screen after every change
    public void refreshScreen(String currentView) {
        switch (currentView) {
            case ORDER -> sortOrderButton.fire();
            case "serial" -> sortSerialButton.fire();
            case "name" -> sortNameButton.fire();
            case "value" -> sortValueButton.fire();
            default -> throw new IllegalStateException("Unexpected value: " + currentView);
        }
        itemTableView.refresh();
    }

    public void setTextFields(String[] words) {
        //fill in textfields with acquired data
        serialTextField.setText(words[0]);
        nameTextField.setText(words[1]);
        valueTextField.setText(words[2]);
    }

    public void initializeTable() {
        //set each column to corresponding value
        tableName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        tableSN.setCellValueFactory(new PropertyValueFactory<>("itemSerial"));
        tableValue.setCellValueFactory(new PropertyValueFactory<>("itemValue"));
        disableTableSort();     //disable built in sort

        itemTableView.setItems(null);
    }

    //disable built in sort
    public void disableTableSort() {
        tableValue.setSortable(false);
        tableSN.setSortable(false);
        tableName.setSortable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTable();
        itemTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }

}
