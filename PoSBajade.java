import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.text.DecimalFormat;

class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
}

class ShoppingCart {
    private List<CartItem> items;

    public ShoppingCart() {
        items = new ArrayList<>();
    }

    public void addItem(Product product, int quantity) {
        CartItem newItem = new CartItem(product, quantity);
        items.add(newItem);
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }
}

public class PoSBajade {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        System.out.print("Enter your name: ");
        String customerName = scanner.nextLine();

        Product apple = new Product("Apple", 0.5);
        Product banana = new Product("Banana", 0.3);
        Product orange = new Product("Orange", 0.25);
        Product grape = new Product("Grape", 0.7);
        Product watermelon = new Product("Watermelon", 2.0);

        ShoppingCart cart = new ShoppingCart();

        int choice;
        do {
            System.out.println("Welcome to Kobe's Fruit Store");
            System.out.println("Main Menu:");
            System.out.println("[1] Buy product");
            System.out.println("[2] Check Cart");
            System.out.println("[3] Remove item from Cart");
            System.out.println("[4] Proceed to Checkout");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            clearConsole();

            switch (choice) {
                case 1:
                    int buyChoice;
                    do {
                        System.out.println("Available Fruits:");
                        System.out.println("[1] Apple $" + decimalFormat.format(apple.getPrice()));
                        System.out.println("[2] Banana $" + decimalFormat.format(banana.getPrice()));
                        System.out.println("[3] Orange $" + decimalFormat.format(orange.getPrice()));
                        System.out.println("[4] Grape $" + decimalFormat.format(grape.getPrice()));
                        System.out.println("[5] Watermelon $" + decimalFormat.format(watermelon.getPrice()));
                        System.out.println("[6] Back");

                        System.out.print("Enter your choice: ");
                        buyChoice = scanner.nextInt();
                        clearConsole();

                        if (buyChoice >= 1 && buyChoice <= 5) {
                            System.out.print("Enter quantity: ");
                            int quantity = scanner.nextInt();
                            Product selectedProduct;
                            switch (buyChoice) {
                                case 1:
                                    selectedProduct = apple;
                                    break;
                                case 2:
                                    selectedProduct = banana;
                                    break;
                                case 3:
                                    selectedProduct = orange;
                                    break;
                                case 4:
                                    selectedProduct = grape;
                                    break;
                                case 5:
                                    selectedProduct = watermelon;
                                    break;
                                default:
                                    selectedProduct = null;
                                    break;
                            }
                            if (selectedProduct != null) {
                                cart.addItem(selectedProduct, quantity);
                                System.out.println("Added to cart: " + selectedProduct.getName() + " x" + quantity);
                                clearConsole();
                            }
                        }
                    } while (buyChoice != 6);
                    break;
                case 2:
                    System.out.println("Items in Cart:");
                    for (CartItem item : cart.getItems()) {
                        System.out.println(item.getProduct().getName() + " x" + item.getQuantity() +
                                " - Total Price: $" + decimalFormat.format(item.getTotalPrice()));
                    }
                    System.out.println("Total Price in Cart: $" + decimalFormat.format(cart.getTotalPrice()));
                    break;
                case 3:
                    if (!cart.getItems().isEmpty()) {
                        System.out.println("Items in Cart:");
                        int index = 1;
                        for (CartItem item : cart.getItems()) {
                            System.out.println("[" + index + "] " + item.getProduct().getName() +
                                    " x" + item.getQuantity() + " - Total Price: $" +
                                    decimalFormat.format(item.getTotalPrice()));
                            index++;
                        }
                        System.out.println("Enter the index of the item to remove: ");
                        int removeIndex = scanner.nextInt();
                        if (removeIndex >= 1 && removeIndex <= cart.getItems().size()) {
                            cart.getItems().remove(removeIndex - 1);
                            System.out.println("Item removed from cart.");
                        } else {
                            System.out.println("Invalid index.");
                        }
                    } else {
                        System.out.println("Cart is empty.");
                    }
                    break;
                case 4:
                double totalPrice = cart.getTotalPrice();
                System.out.println("Total Price: $" + decimalFormat.format(totalPrice));

                double amountPaid;
                do {
                    System.out.print("Enter amount paid: ");
                    amountPaid = scanner.nextDouble();
                    if (amountPaid < totalPrice) {
                        System.out.println("The amount is not enough. Please enter a valid amount.");
                    }
                } while (amountPaid < totalPrice);

                double change = amountPaid - totalPrice;
                System.out.println("Change: $" + decimalFormat.format(change));

                clearConsole();
                System.out.println("============================================");
                System.out.println("============Kobe's Fruit Store==============");
                System.out.println("==========Fresh Fruits on Demand============");
                System.out.println("============================================");
                System.out.println("Receipt for " + customerName + ":");
                for (CartItem item : cart.getItems()) {
                    System.out.println(item.getProduct().getName() + " x" + item.getQuantity() +
                            " - Total Price: $" + decimalFormat.format(item.getTotalPrice()));
                }
                System.out.println("Total Price: $" + decimalFormat.format(totalPrice));
                System.out.println("Amount Paid: $" + decimalFormat.format(amountPaid));
                System.out.println("Change: $" + decimalFormat.format(change));
                System.out.println("Thank you for shopping "+ customerName + ".");
                System.out.println("============================================");
                break;
            default:
                System.out.println("Invalid choice. Please select again.");
        }
    } while (choice != 4);

    scanner.close();
}

    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
