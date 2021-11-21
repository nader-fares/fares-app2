package baseline;


import java.util.Objects;
import java.util.Optional;

public class ItemControllerMethods {
    ItemList items = new ItemList();
    static final String NUMBER = "number";


    public String addItem(String nameText, String serialText, String valueText) {

        String result = checkTextFieldsEmpty(nameText, serialText, valueText);
        if (result != null)
            return result;

        String valueString = validateNumber(valueText);
        double value;
        if (!valueString.equals(NUMBER))
            value = Double.parseDouble(valueString);
        else return NUMBER;


        result = validateInput(nameText, serialText, value);

        if (result != null)
            return result;
        else {
            addItemHelper(nameText, serialText, "$" + valueString);
            return null;
        }
    }

    public String checkTextFieldsEmpty(String nameText, String serialText, String valueText) {
        if (nameText.equals("") || serialText.equals("") || valueText.equals(""))
            return "empty";
        return null;
    }
    public String validateNumber(String valueText) {
        if (valueText.matches(".*[A-Za-z].*"))
            return NUMBER;
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

    public void addItemHelper(String nameText, String serialText, String valueText) {
        items.itemList.add(new Item(nameText, serialText, valueText));
    }

    public boolean checkSerialForUnique(String serialText) {
        for (Item item : items.itemList) {
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
        items.itemList.clear();
    }

    public void deleteItem(String serialNumber) {
        items.itemList.removeIf(item -> Objects.equals(item.getItemSerial(), serialNumber));
    }

    public String editItem(String nameText, String serialText, String valueText) {

        String valueString = validateNumber(valueText);
        double value;
        if (!valueString.equals(NUMBER))
            value = Double.parseDouble(valueString);
        else return NUMBER;

        String result = validateInput(nameText, serialText, value);

//        if (result != null)
//            return result;
//        else {
            editItemHelper(serialText, nameText, serialText, valueString);
            return null;
//        }
    }

    public void editItemHelper(String selectedSerial, String nameText, String serialText, String valueText) {
        Optional<Item> tempItem = getItemBySerial(selectedSerial);
        tempItem.ifPresent(item -> {
            if (nameText.trim().length() != 0)
                item.setItemName(nameText);
            if (serialText.trim().length() != 0)
                item.setItemSerial(serialText);
            if (valueText.trim().length() != 0)
                item.setItemValue("$" + valueText);
        });
    }

    public Optional<Item> getItemBySerial(String serialNumber) {
        return items.itemList.stream().filter(item -> Objects.equals(item.getItemSerial(), serialNumber)).findFirst();
    }
}