/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Nader Fares
 */
package baseline;

public class Item {
    //item data
    private String itemName;
    private String itemSerial;
    private Double itemValue;

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

    public Double getItemValue() {
        return itemValue;
    }

    public void setItemValue(Double itemValue) {
        this.itemValue = itemValue;
    }

    public Item(String itemName, String itemSerial, Double itemValue) {
        this.itemName = itemName;
        this.itemSerial = itemSerial;
        this.itemValue = itemValue;
        this.initItemId();      //initialized id
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

    public static void resetCount() {       //for testing purposes only
        count = 0;
    }
}
