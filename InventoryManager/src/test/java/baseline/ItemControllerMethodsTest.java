/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Nader Fares
 */
package baseline;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//all weak warnings ignored because we want expected outcomes to be stored in variables for clarity
class ItemControllerMethodsTest {
    ItemControllerMethods itemControls = new ItemControllerMethods();

    @BeforeEach
    public void startUp() {
        itemControls.items.getItemArrayList().add(new Item("test", "a-333-333-333", "300"));
        itemControls.items.getItemArrayList().add(new Item("test", "a-333-333-334", "300"));
        itemControls.items.getItemArrayList().add(new Item("test", "a-333-333-335", "300"));
        itemControls.items.getItemArrayList().add(new Item("test", "a-333-333-336", "300"));

        Item.resetCount();
    }


    @Test
    void addItem() {
        itemControls.addItem("test", "a-333-333-337", "300");
        itemControls.addItem("test", "a-333-333-338", "300");
        itemControls.addItem("test", "a-333-333-338", "300");
        itemControls.addItem("test", "a-333-333-338", "30aa0");
        itemControls.addItem("t", "a-333-333-338", "300");

        int sizeActual = itemControls.items.getItemArrayList().size();
        int sizeExpected = 6;   //initialized with 4 items then added 2 || rejected 3 invalid input items

        assertEquals(sizeExpected, sizeActual);
    }

    @Test
    void addItemHelper() {
        itemControls.addItemHelper("test", "a-333-333-337", "300");
        itemControls.addItemHelper("test", "a-333-333-338", "300");

        int sizeActual = itemControls.items.getItemArrayList().size();
        int sizeExpected = 6;   //initialized with 4 items then added 2
        assertEquals(sizeExpected, sizeActual);
    }

    @Test
    void checkTextFieldsEmpty() {
        //returns "empty" string if any textfield is empty, null if filled
        String actualOutcome1 = itemControls.checkTextFieldsEmpty("test","","300");
        String expectedOutcome1 = "empty";
        assertEquals(expectedOutcome1, actualOutcome1);

        String actualOutcome2 = itemControls.checkTextFieldsEmpty("test", "test", "test");
        String expectedOutcome2 = null;
        assertEquals(expectedOutcome2, actualOutcome2);
    }

    @Test
    void checkSerialForUnique() {
        //returns false if duplicate, true if unique
        boolean actualOutcome1 = itemControls.checkSerialForUnique("a-333-333-336");
        boolean expectedOutcome1 = false;
        assertEquals(expectedOutcome1, actualOutcome1);

        boolean actualOutcome2 = itemControls.checkSerialForUnique("a-333-333-337");
        boolean expectedOutcome2 = true;
        assertEquals(expectedOutcome2, actualOutcome2);
    }

    @Test
    void validateNumber() {
        //returns "number" string if value contains non-numbers, returns value if doesn't
        String actualOutcome1 = itemControls.validateNumber("300");
        String expectedOutcome1 = "300";
        assertEquals(expectedOutcome1, actualOutcome1);

        String actualOutcome2 = itemControls.validateNumber("30aa0");
        String expectedOutcome2 = "number";
        assertEquals(expectedOutcome2, actualOutcome2);
    }

    @Test
    void validateInput() {
        //function will return following strings based on condition
            //"unique" if fails unique checker
            //"serial" if fails serial format checker
            //"value" if fails value checker
            //"name" if fails name checker
            //null if passes

        String actualOutcome1 = itemControls.validateInput("test", "a-333-333-336", 300.0);
        String expectedOutcome1 = "unique";
        assertEquals(expectedOutcome1, actualOutcome1);

        String actualOutcome2 = itemControls.validateInput("test", "a-333-33aaaa3-337", 300.0);
        String expectedOutcome2 = "serial";
        assertEquals(expectedOutcome2, actualOutcome2);

        String actualOutcome3 = itemControls.validateInput("test", "a-333-333-337", -300.0);
        String expectedOutcome3 = "value";
        assertEquals(expectedOutcome3, actualOutcome3);

        String actualOutcome4 = itemControls.validateInput("t", "a-333-333-337", 300.0);
        String expectedOutcome4 = "name";
        assertEquals(expectedOutcome4, actualOutcome4);

        String actualOutcome5 = itemControls.validateInput("test", "a-333-333-337", 300.0);
        String expectedOutcome5 = null;
        assertEquals(expectedOutcome5, actualOutcome5);
    }


    @Test
    void validateSerialInput() {
        //returns false if matches format, true if does not match format
        boolean actualOutcome1 = itemControls.validateSerialInput("a-333-333-336");
        boolean expectedOutcome1 = false;
        assertEquals(expectedOutcome1, actualOutcome1);

        boolean actualOutcome2 = itemControls.validateSerialInput("a-333-3aaa33-337");
        boolean expectedOutcome2 = true;
        assertEquals(expectedOutcome2, actualOutcome2);
    }

    @Test
    void validateValueInput() {
        //returns false if > 0, true if < 0
        boolean actualOutcome1 = itemControls.validateValueInput(300.0);
        boolean expectedOutcome1 = false;
        assertEquals(expectedOutcome1, actualOutcome1);

        boolean actualOutcome2 = itemControls.validateValueInput(-300.0);
        boolean expectedOutcome2 = true;
        assertEquals(expectedOutcome2, actualOutcome2);
    }

    @Test
    void validateNameInput() {
        //returns true if < 2 or > 256, returns false otherwise
        boolean actualOutcome1 = itemControls.validateNameInput("test");
        boolean expectedOutcome1 = false;
        assertEquals(expectedOutcome1, actualOutcome1);

        boolean actualOutcome2 = itemControls.validateSerialInput("t");
        boolean expectedOutcome2 = true;
        assertEquals(expectedOutcome2, actualOutcome2);
    }

    @Test
    void clearItems() {
        int sizeActual = itemControls.items.getItemArrayList().size();
        int sizeExpected = 4;   //initialized with 4 items
        assertEquals(sizeExpected, sizeActual);

        itemControls.clearItems();
        int sizeActual2 = itemControls.items.getItemArrayList().size();
        int sizeExpected2 = 0;

        assertEquals(sizeExpected2, sizeActual2);

    }

    @Test
    void deleteItem() {
        int sizeActual = itemControls.items.getItemArrayList().size();
        int sizeExpected = 4;   //initialized with 4 items
        assertEquals(sizeExpected, sizeActual);

        itemControls.deleteItem("a-333-333-336");
        int sizeActual2 = itemControls.items.getItemArrayList().size();
        int sizeExpected2 = 3;
        assertEquals(sizeExpected2, sizeActual2);
    }

    @Test
    void editItem() {
        //before edit
        String actualInitialSerial = itemControls.items.getItemArrayList().get(0).getItemSerial();
        String actualInitialName = itemControls.items.getItemArrayList().get(0).getItemName();
        String actualInitialValue = itemControls.items.getItemArrayList().get(0).getItemValue();

        String expectedInitialSerial = "a-333-333-333";
        String expectedInitialName = "test";
        String expectedInitialValue = "300";

        assertEquals(expectedInitialSerial, actualInitialSerial);
        assertEquals(expectedInitialName, actualInitialName);
        assertEquals(expectedInitialValue, actualInitialValue);


        itemControls.editItem(expectedInitialSerial, "test2", "b-333-333-333", "720");

        //after edit
        String actualFinalSerial = itemControls.items.getItemArrayList().get(0).getItemSerial();
        String actualFinalName = itemControls.items.getItemArrayList().get(0).getItemName();
        String actualFinalValue = itemControls.items.getItemArrayList().get(0).getItemValue();

        String expectedFinalSerial = "b-333-333-333";
        String expectedFinalName = "test2";
        String expectedFinalValue = "720";

        assertEquals(expectedFinalSerial, actualFinalSerial);
        assertEquals(expectedFinalName, actualFinalName);
        assertEquals(expectedFinalValue, actualFinalValue.replace("$", ""));    //automatically formats the dollar sign
    }

    @Test
    void editItemHelper() {
        //before edit
        String actualInitialSerial = itemControls.items.getItemArrayList().get(0).getItemSerial();
        String actualInitialName = itemControls.items.getItemArrayList().get(0).getItemName();
        String actualInitialValue = itemControls.items.getItemArrayList().get(0).getItemValue();

        String expectedInitialSerial = "a-333-333-333";
        String expectedInitialName = "test";
        String expectedInitialValue = "300";

        assertEquals(expectedInitialSerial, actualInitialSerial);
        assertEquals(expectedInitialName, actualInitialName);
        assertEquals(expectedInitialValue, actualInitialValue);


        itemControls.editItemHelper(expectedInitialSerial, "name", "serial", "123");

        //after edit
        String actualFinalSerial = itemControls.items.getItemArrayList().get(0).getItemSerial();
        String actualFinalName = itemControls.items.getItemArrayList().get(0).getItemName();
        String actualFinalValue = itemControls.items.getItemArrayList().get(0).getItemValue();

        String expectedFinalSerial = "serial";
        String expectedFinalName = "name";
        String expectedFinalValue = "123";

        assertEquals(expectedFinalSerial, actualFinalSerial);
        assertEquals(expectedFinalName, actualFinalName);
        assertEquals(expectedFinalValue, actualFinalValue.replace("$", ""));    //automatically formats the dollar sign
    }

    @Test
    void editItemName() {
        //before edit
        String actualInitialName = itemControls.items.getItemArrayList().get(0).getItemName();
        String expectedInitialName = "test";

        assertEquals(expectedInitialName, actualInitialName);

        itemControls.editItemName("a-333-333-333", "name");

        //after edit
        String actualFinalName = itemControls.items.getItemArrayList().get(0).getItemName();
        String expectedFinalName = "name";

        assertEquals(expectedFinalName, actualFinalName);

    }

    @Test
    void editItemSerial() {
        //before edit
        String actualInitialSerial = itemControls.items.getItemArrayList().get(0).getItemSerial();
        String expectedInitialSerial = "a-333-333-333";

        assertEquals(expectedInitialSerial, actualInitialSerial);

        itemControls.editItemSerial(expectedInitialSerial,"b-333-333-333");

        //after edit
        String actualFinalSerial = itemControls.items.getItemArrayList().get(0).getItemSerial();
        String expectedFinalSerial = "b-333-333-333";

        assertEquals(expectedFinalSerial, actualFinalSerial);

    }

    @Test
    void editValue() {
        //before edit
        String actualInitialValue = itemControls.items.getItemArrayList().get(0).getItemValue();
        String expectedInitialValue = "300";

        assertEquals(expectedInitialValue, actualInitialValue);

        itemControls.editValue("a-333-333-333", "123");

        //after edit
        String actualFinalValue = itemControls.items.getItemArrayList().get(0).getItemValue();
        String expectedFinalValue = "123.0";

        assertEquals(expectedFinalValue, actualFinalValue.replace("$", ""));    //automatically formats the dollar sign

    }

    @Test
    void editValueHelper() {
        //before edit
        String actualInitialValue = itemControls.items.getItemArrayList().get(0).getItemValue();
        String expectedInitialValue = "300";

        assertEquals(expectedInitialValue, actualInitialValue);

        itemControls.editValueHelper("a-333-333-333", 252.0);

        //after edit
        String actualFinalValue = itemControls.items.getItemArrayList().get(0).getItemValue();
        String expectedFinalValue = "252.0";

        assertEquals(expectedFinalValue, actualFinalValue.replace("$", ""));    //automatically formats the dollar sign
    }

    @Test
    void getItemBySerial() {
        if (itemControls.getItemBySerial("a-333-333-335").isPresent()) {
            int expectedId = 3;         //3rd item to be added
            int actualId = itemControls.getItemBySerial("a-333-333-335").get().getItemId();

            assertEquals(expectedId, actualId);
        }
    }
}