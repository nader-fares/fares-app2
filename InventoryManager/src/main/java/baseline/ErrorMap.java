/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Nader Fares
 */
package baseline;

import javafx.scene.control.Alert;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

public class ErrorMap {
    public ErrorMap(String errorType) {
        String invalidInput = "Invalid Input!";
        //map of errors object, each different error has a different key
        Map<String, Error> errors = new HashMap<>();
        errors.put("unique", new Error("Invalid Item!", "Item already exists in list."));

        errors.put("serial", new Error(invalidInput, "Serial Number must match A-XXX-XXX-XXX, where A must be a letter and X can be either a letter or digit"));
        errors.put("value", new Error(invalidInput,"The monetary value of an item must be greater than or equal to 0"));
        errors.put("name", new Error(invalidInput, "The name of an item must be between 2 and 256 characters in length (inclusive)"));
        errors.put("number", new Error(invalidInput, "The value of an item must be a number"));
        errors.put("empty", new Error(invalidInput, "All fields must be filled"));

        errors.put("file", new Error("Invalid File!", "Must be a .txt, .json, or .html file."));

        //depending on key, error will display different message
        Error error = errors.get(errorType);
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(error.getHeaderText());
        errorAlert.setContentText(error.getContentText());
        errorAlert.showAndWait();
        throw new InputMismatchException();
    }
}
