import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Item {
    private String name;
    private double price;
    private int quantity;

    public Item(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}

public class Main {
    private Map<String, Item> stock;
    private Scanner scanner;
    private List<String> transactionHistory;

    public Main() {
        this.stock = new HashMap<>();
        this.scanner = new Scanner(System.in);
        this.transactionHistory = new ArrayList<>();
    }

    public void addItem() {
        System.out.println("Enter item name:");
        String name = scanner.nextLine();

        // Data validation for item name
        if (stock.containsKey(name)) {
            System.out.println("Item already exists in the stock.");
            return;
        }

        System.out.println("Enter item price:");
        double price = scanner.nextDouble();

        System.out.println("Enter item quantity:");
        int quantity = scanner.nextInt();

        // Data validation for price and quantity
        if (price <= 0 || quantity <= 0) {
            System.out.println("Price and quantity must be greater than zero.");
            return;
        }

        // Consume newline character
        scanner.nextLine();

        Item newItem = new Item(name, price, quantity);
        stock.put(name, newItem);
        transactionHistory.add("Added " + quantity + " of " + name + " to the stock");
        System.out.println("Item added to the stock successfully.");
    }

    public void removeItem() {
        System.out.println("Enter item name:");
        String name = scanner.nextLine();

        if (!stock.containsKey(name)) {
            System.out.println("Item does not exist in the stock.");
            return;
        }

        System.out.println("Do you want to remove the entire stock of " + name + "? (yes/no)");
        String option = scanner.nextLine();

        if (option.equalsIgnoreCase("yes")) {
            transactionHistory.add("Removed entire stock of " + name + " from the stock");
            stock.remove(name);
            System.out.println("Entire stock of " + name + " removed from the stock.");
        } else if (option.equalsIgnoreCase("no")) {
            System.out.println("Enter the quantity of " + name + " to remove:");
            int quantityToRemove = scanner.nextInt();
            if (quantityToRemove <= 0) {
                System.out.println("Invalid quantity.");
                return;
            }
            int currentQuantity = stock.get(name).getQuantity();
            if (quantityToRemove >= currentQuantity) {
                transactionHistory.add("Sold entire stock of " + name);
                stock.remove(name);
                System.out.println("Entire stock of " + name + " sold.");
            } else {
                stock.get(name).setQuantity(currentQuantity - quantityToRemove);
                transactionHistory.add("Sold " + quantityToRemove + " of " + name);
                System.out.println(quantityToRemove + " of " + name + " sold.");
            }
        } else {
            System.out.println("Invalid option. Please enter 'yes' or 'no'.");
        }
    }
    public void updateItemQuantity() {
        System.out.println("Enter item name:");
        String name = scanner.nextLine();

        if (!stock.containsKey(name)) {
            System.out.println("Item does not exist in the stock.");
            return;
        }

        System.out.println("Enter new quantity:");
        int quantity = scanner.nextInt();

        stock.get(name).setQuantity(quantity);
        transactionHistory.add("Updated quantity of " + name + " to " + quantity);
        System.out.println("Quantity updated successfully.");
    }

    public void searchItem(String keyword) {
        boolean found = false;
        for (Item item : stock.values()) {
            if (item.getName().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(item);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No matching items found.");
        }
    }

    public void displayStock() {
        for (Item item : stock.values()) {
            System.out.println(item);
        }
    }

    public void checkStockAlerts() {
        for (Item item : stock.values()) {
            if (item.getQuantity() < 5) {
                System.out.println("Alert: Low stock for item - " + item.getName());
            }
        }
    }

    public void printTransactionHistory() {
        System.out.println("\nTransaction History:");
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }
    public void printBill() {
        System.out.println("\nBilling Information:");
        double totalAmount = 0;
        for (Item item : stock.values()) {
            if (item.getQuantity() == 0) {
                System.out.println("Item sold out: " + item.getName());
            } else {
                System.out.println(item.getName() + " - Quantity: " + item.getQuantity() + ", Price: " + item.getPrice() + " each");
                totalAmount += item.getPrice() * item.getQuantity();
            }
        }
        System.out.println("Total Amount: $" + totalAmount);
    }
    public static void main(String[] args) {
        Main stockSystem = new Main();
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\nStock Management System");
            System.out.println("1. Add Item");
            System.out.println("2. Remove Item");
            System.out.println("3. Update Item Quantity");
            System.out.println("4. Search Item");
            System.out.println("5. Display Stock");
            System.out.println("6. Print Transaction History");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    stockSystem.addItem();
                    break;
                case 2:
                    stockSystem.removeItem();
                    break;
                case 3:
                    stockSystem.updateItemQuantity();
                    break;
                case 4:
                    System.out.println("Enter keyword to search:");
                    String keyword = scanner.next();
                    stockSystem.searchItem(keyword);
                    break;
                case 5:
                    System.out.println("\nStock:");
                    stockSystem.displayStock();
                    stockSystem.checkStockAlerts();
                    break;
                case 6:
                    stockSystem.printTransactionHistory();
                    break;
                case 7:
                    System.out.println("Printing Bill...");
                    stockSystem.printBill();
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
            }
        } while (choice != 7);
        scanner.close();
    }
}