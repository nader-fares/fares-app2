# fares-app1-impl
COP3330 Application Assignment 2. This is an inventory manager GUI-based desktop application that was created using Intellij and Gradle.



## Copyright
UCF COP3330 Fall 2021 Application Assignment 2 Solution
Copyright 2021 Nader Fares


## Installation

Install the gradle dependencies by entering the following into the terminal window:

```bash
  ./gradlew build
```

## Overview
JavaFX GUI-based desktop application thats allows the user to track their personal inventory. The user has the ability to add/delete/edit items, or clear the entire inventory. More features include the ability to save the inventory, load a previously saved inventory, sort the inventory based on property, and search the inventory by item name or serial number. 

![Screenshot 2021-11-21 180352](https://user-images.githubusercontent.com/59976904/142782942-e6877d07-20dd-4297-ab03-113f65536052.png)



## Application Controls
Guide on how to navigate and use the application.

### Table of Contents
* [Edit Inventory](https://github.com/nader-fares/fares-app2#editing-inventory) 
* [Load/Saving Files](https://github.com/nader-fares/fares-app2#savingloading-files)
* [Sort Inventory](https://github.com/nader-fares/fares-app2#sort-inventory)
* [Search Inventory](https://github.com/nader-fares/fares-app2#sort-inventory)


**Editing Inventory**
---
Each item in the inventory must have their own unique serial number and follow the format of A-XXX-XXX-XXX, where A must be a letter and X can be either a letter or digit, item names cannot be less than 2 characters nor exceed 256 characters in length, and every item must have a monetary value that is $0 or greater

### Adding item
User has the ability to add items to the inventory. To do this, user must fill out all three fields then click the add button.

### Deleting Item
Delete an item in the inventory by clicking on it in the table then clicking the delete button.

### Editing Item

User may edit items in the inventory, either by editing the entire item at once or each property individually. Fill out the fields you wish to edit then click the edit button to pull the drop-down menu and choose what property you wish to edit.

### Clear Inventory

Clear the entire inventory by clicking the clear button on the bottom.


<p align="center">
<img src="https://user-images.githubusercontent.com/59976904/142784955-002b9ffe-abbc-4e61-991e-1d7d1d1d5652.gif" style="width: 75%; height:75%">
</p>


**Saving/Loading Files**
---

### Save File

Inventory may be saved as as a TSV in a .txt file, JSON in a .json file, or a table element in an .html file depending on the user's choice. Click on the File button on the menu bar, hovering Save as gives you the file save options, click the format you wish to save as.

### Load File

A previously saved inventory may be loaded into the table. Click on the File button on the menu bar then click Load. Selected file will load onto the table and overwrite the current list.

<p align="center">
<img src="https://user-images.githubusercontent.com/59976904/142784973-dfbe164c-2ad1-493d-9dba-1280d31eede0.gif" style="width: 75%; height:75%">
</p>

**Sort Inventory**
---

User has the ability to sort the inventory based on property. Inventory may be sorted by add order, name, serial number, or value. To sort, click on Sort button on the menu bar at the top of the application, then select the property to sort by.


<p align="center">
<img src="https://user-images.githubusercontent.com/59976904/142784988-150efe05-0055-49a6-adbd-07e9e99ba720.gif" style="width: 75%; height:75%">
</p>

**Search Inventory**
---

User may search through the inventory. Enter the serial number or name of the item you wish to find then click the search button. Table will display the item with the matching serial number, or display all items that match the name of the entered text.
<p align="center">
<img src="https://user-images.githubusercontent.com/59976904/142784981-cfbfbd96-66d5-4e17-b3c4-fc05a75f567c.gif" style="width: 75%; height:75%">
</p>


## Contributors
* [Nader Fares](https://github.com/nader-fares)