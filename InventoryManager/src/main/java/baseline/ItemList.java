/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Nader Fares
 */

package baseline;

import java.util.ArrayList;
import java.util.List;

public class ItemList {
    private List<Item> itemObservableList = new ArrayList<>();

    public List<Item> getItemObservableList() {
        return itemObservableList;
    }

    public void setItemObservableList(List<Item> itemObservableList) {
        this.itemObservableList = itemObservableList;
    }
}
