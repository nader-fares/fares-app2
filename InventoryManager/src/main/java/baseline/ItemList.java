package baseline;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ItemList {
    private final ObservableList<Item> itemList = FXCollections.observableArrayList();

    public ObservableList<Item> getItemList() {
        return itemList;
    }
}
