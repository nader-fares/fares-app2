/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Nader Fares
 */
package baseline;

//setters, getters, constructors do not need testing
public class Item {
    //item data
    private String itemName;
    private String itemSerial;
    private String itemValue;
    private Double itemValueDouble;

    private int itemId;
    private static int count = 0;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemSerial() {
        return itemSerial;
    }

    public void setItemSerial(String itemSerial) {
        this.itemSerial = itemSerial;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
        itemValueDouble = Double.valueOf(itemValue.replace("$", ""));
    }

    public Item(String itemName, String itemSerial, String itemValue) {
        this.itemName = itemName;
        this.itemSerial = itemSerial;
        this.itemValue = itemValue;
        itemValueDouble = Double.valueOf(itemValue.replace("$", ""));
        this.initItemId();      //initialized id
    }

    public Double getItemValueDouble() {
        return itemValueDouble;
    }

    public int getItemId() {
        return itemId;
    }

    private static void incrementCount() {
        count++;
    }   //increment count variable

    private void initItemId() {
        incrementCount();
        this.itemId = count;
    }
}
