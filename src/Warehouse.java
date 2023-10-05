
/*
 * Daniel Gibbs-Egan
 * 
 * Warehouse Assignment:
 * 
 * keeps track of the receiving, 
 * storing and shipping of items
 * through itemCodes
 * 
 * 
 */

public class Warehouse {
	
	
	/* Define Class Variables */
	
	private int size; // maximum amount of items the warehouse can store
	private int limitPerItem; // maximum amount of a specific item the warehouse allows
	
	private int amountOfItemCodes; // amount of different items in the warehouse
	private int warehouseTotalStock; // amount of items in the warehouse
	
	private int[] itemCodes; // stored item identifiers
	private int[] itemStock; // amount of a specific item in the warehouse index according to itemCodes

	/* Define the Classes Contructor */
	
	public Warehouse(int size, int limitPerItem) {
		
		// Instantiate Variables
			// given instatiations
		this.size = size;
		this.limitPerItem = limitPerItem;
			// the warehouse starts empty
		amountOfItemCodes = 0;
		warehouseTotalStock = 0;
			// the warehouse can store at most (size) items
		itemCodes = new int[size];
		itemStock = new int[size];
		
	}
	
	/* give other classes view access to private variables */

	// getSize() 
	// returns the size of the warehouse as an integer value
	public int getSize() {
		return size; 
	}
	
	// getLimitPerItem()
	// returns the limit per item as an integer value
	public int getLimitPerItem() {
		return limitPerItem; 
	}
	
	/*
	 * searchArray(int[] array, int input)
	 * loops through an array and
	 * returns the location of a given 
	 * integer in the specified array
	 * 
	 * if the given integer is not 
	 * contained in the array 
	 * it will return 0
	 */
	public int searchArray(int[] array, int input) {
		
		// loop through every different type of item
		for (int i = 0; i < amountOfItemCodes; i++) {
			if (array[i] == input) {
				return i;
			}
		}
		
		return -1;
	}
	
	/*
	 * loops through the given arrays 
	 * starting at the given index
	 * lowering subsequent values 
	 * down an index
	 */
	public void decreaseArraysFromIndex(int index, int[]... arrays) {

		// loop through every type of item after the given index
		for (int i = index; i < amountOfItemCodes; i++) {
			// check if the index above the current index is inside the array
			if (i+1 < size) {
				// move down an index for all given arrays
				for (int[] array: arrays) { 
					array[i] = array[i+1]; 
				};
				
				continue;
			}
			// move down an index for all given arrays
			for (int[] array: arrays) { 
				// since there is no item code above this one it will be reset
				array[i] = 0; 
			};
			// the loop reached the end of the arrays
			break;
		}
	}
	
	/*
	 * receive(int itemCode, int itemCount) 
	 * adds the specified amount of items to the warehouse 
	 * (
	 *		if the warehouse has space and does not contain
	 *		the given item it adds the itemCode to the [itemCodes] array 
	 * )
	 * and returns the amount of items rejected due to:
	 * 
	 *		- a full warehouse, or 
	 *		- the specified items have surpassed 
	 *		the warehouses item limit
	 * 
	 */
	public int receive(int itemCode, int itemCount) {
		
		// get index of the items item code
		int index = searchArray(itemCodes, itemCode);
		// if the item code doesn't exist check for space
		if (index < 0) {
			// check if the warehouse cannot store the new item
			if (amountOfItemCodes >= size) 
				return itemCount;
			
			// create a new code at the next empty index
			index = amountOfItemCodes;
			itemCodes[index] = itemCode;
			
			// increment the amount of item codes
			amountOfItemCodes++;
		}
		
		// check if the item has surpassed either limit
		int amountRejectedItemLimit = itemStock[index] + itemCount - limitPerItem;
		int amountRejectedWarehouseCapacity = warehouseTotalStock + itemCount - size;
		// store the largest rejection value
		int amountRejected = Math.max(0, Math.max(amountRejectedWarehouseCapacity, amountRejectedItemLimit));
		
		// add the items that were not rejected to the items stock
		itemStock[index] += itemCount - amountRejected;
		// and the total stored items of the warehouse
		warehouseTotalStock += itemCount - amountRejected;
		
		// if the amount of items in stock is less than 1 
		if (itemStock[index] < 1) {
			// remove the item code from the warehouse
			decreaseArraysFromIndex(index, itemCodes, itemStock);
			// decrement the amount of item codes
			amountOfItemCodes--;
		} 
		
		// return the amount of the item that was rejected from the warehouse
		return amountRejected;
		
	}
	
	/*
	 * ship(int itemCode, int itemCount)
	 * takes a specified amount of given items from the warehouse 
	 * and returns the amount of the items that could be shipped out
	 * 
	 * if the item doesn't exist it will return 0
	 */
	public int ship(int itemCode, int itemCount) {

		// get index of the items item code
		int index = searchArray(itemCodes, itemCode);
		if (index < 0) {
			return 0;
		}
		
		// check for boxes that don't exist
		int amountUnavailable = itemCount - itemStock[index];
		// cannot ship a negative amount of packages
		amountUnavailable = Math.max(0,amountUnavailable);

		// remove the items that were available from the items stock
		itemStock[index] -= itemCount - amountUnavailable;
		// and the total stored items of the warehouse
		warehouseTotalStock -= itemCount - amountUnavailable;

		// if the amount of items in stock is less than 1 
		if (itemStock[index] < 1) {
			// remove the item code from the warehouse
			decreaseArraysFromIndex(index, itemCodes, itemStock);
			// decrement the amount of item codes
			amountOfItemCodes--;
		} 
		
		//return the amount of items
		return itemCount - amountUnavailable;
		
	}
	
	
	/*
	 * toString() returns the variable as 
	 *  an easily interpretable String
	 */
	public String toString() {
		
		String idsAndQuantities = "[ ";
		
		for (int i = 0; i < amountOfItemCodes; i++) {
			if (i > 0) idsAndQuantities += "], ";
			idsAndQuantities +=  "[code:" + itemCodes[i] + ", qty:" + itemStock[i];
		}
		
		if (amountOfItemCodes <= 0) idsAndQuantities += "Empty";
		idsAndQuantities += " ]";
		
		return "Warehouse: [ size: " + size + ", limitPerItem: " + limitPerItem + ", storedItems: " + idsAndQuantities + " ]";
	}
	
}
