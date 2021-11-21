/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Nader Fares
 */

package baseline;

import java.util.ArrayList;
import java.util.List;

public class ItemList {
    private List<Item> itemArrayList = new ArrayList<>();

    public List<Item> getItemArrayList() {
        return itemArrayList;
    }

    public void setItemArrayList(List<Item> itemArrayList) {
        this.itemArrayList = itemArrayList;
    }
}
