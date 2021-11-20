package baseline;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Optional;

public class ItemControllerMethods {
    ItemList items = new ItemList();


    public String addItem(String nameText, String serialText, String valueText) {
        String numberString = "number";

        String valueString = validateNumber(valueText);
        Double value;
        if (!valueString.equals(numberString))
            value = Double.parseDouble(valueString);
        else return numberString;

        String result = validateInput(nameText, serialText, value);

        if (result != null)
            return result;
        else {
            addItemHelper(nameText, serialText, value);
            return null;
        }
    }

    public String validateNumber(String valueText) {
        if (valueText.matches(".*[a-z].*"))
            return "number";
        else return valueText;
    }

    public String validateInput(String nameText, String serialText, Double valueText) {
        if (!checkSerialForUnique(serialText))
            return "unique";
        if (!validateSerialInput(serialText))
            return "serial";
        if (!validateValueInput(valueText))
            return "value";
        if (!validateNameInput(nameText))
            return "name";
        return null;
    }

    public void addItemHelper(String nameText, String serialText, Double valueText) {
        items.getItemList().add(new Item(nameText, serialText, valueText));
    }

    public boolean checkSerialForUnique(String serialText) {
        for (Item item : items.getItemList()) {
            if (serialText.equals(item.getItemSerial()))
                return false;
        }
        return true;
    }

    public boolean validateSerialInput(String serialText) {
        return serialText.matches("^[A-Za-z]-[A-Za-z0-9]{3}-[A-Za-z0-9]{3}-[A-Za-z0-9]{3}");
    }

    public boolean validateValueInput(Double valueText) {
        return valueText >= 0;
    }

    public boolean validateNameInput(String nameText) {
        return nameText.length() >= 2 && nameText.length() <= 256;
    }

    public void clearItems() {
        items.getItemList().clear();
    }

    public void deleteItem(int itemId) {
        items.getItemList().removeIf(item -> item.getItemId() == itemId);
    }

    public String editItem(int itemId, String nameText, String serialText, Double valueText) {
        String result = validateInput(nameText, serialText, valueText);

        if (result != null)
            return result;
        else {
            editItemHelper(itemId, nameText, serialText, valueText);
            return null;
        }
    }

    public void editItemHelper(int itemId, String nameText, String serialText, Double valueText) {
        Optional<Item> tempItem = getItemById(itemId);
        tempItem.ifPresent(item -> {
            if (nameText != null)
                item.setItemName(nameText);
            if (serialText != null)
                item.setItemSerial(serialText);
            if (valueText != null)
                item.setItemValue(valueText);
        });
    }

    public Optional<Item> getItemById(int itemId) {
        return items.getItemList().stream().filter(item -> item.getItemId() == itemId).findFirst();
    }
}
