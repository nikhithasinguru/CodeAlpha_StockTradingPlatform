import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Stock {
    private String symbol;
    private double price;


    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return symbol + ": $" + price;
    }
}

class Portfolio {
    private Map<String, Integer> holdings = new HashMap<>();

    public void buyStock(String symbol, int quantity) {
        holdings.put(symbol, holdings.getOrDefault(symbol, 0) + quantity);
    }

    public void sellStock(String symbol, int quantity) {
        if (holdings.containsKey(symbol)) {
            int currentQuantity = holdings.get(symbol);
            if (currentQuantity >= quantity) {
                holdings.put(symbol, currentQuantity - quantity);
                if (holdings.get(symbol) == 0) {
                    holdings.remove(symbol);
                }
            } else {
                System.out.println("Not enough shares to sell.");
            }
        } else {
            System.out.println("No such stock in portfolio.");
        }
    }

    public void printPortfolio(Map<String, Stock> market) {
        if (holdings.isEmpty()) {
            System.out.println("Your portfolio is empty.");
        } else {
            double totalValue = 0;
            System.out.println("Portfolio:");
            for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
                String symbol = entry.getKey();
                int quantity = entry.getValue();
                Stock stock = market.get(symbol);
                double value = quantity * stock.getPrice();
                totalValue += value;
                System.out.printf("%s: %d shares, Total Value: $%.2f%n", symbol, quantity, value);
            }
            System.out.printf("Total Portfolio Value: $%.2f%n", totalValue);
        }
    }
}

public class StockTradingPlatform {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize some stocks
        Map<String, Stock> market = new HashMap<>();
        market.put("AAPL", new Stock("AAPL", 150.00));
        market.put("GOOGL", new Stock("GOOGL", 2800.00));
        market.put("AMZN", new Stock("AMZN", 3400.00));

        Portfolio portfolio = new Portfolio();

        while (true) {
            System.out.println("\nStock Trading Platform");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Market Data:");
                    for (Stock stock : market.values()) {
                        System.out.println(stock);
                    }
                    break;

                case 2:
                    System.out.print("Enter stock symbol to buy: ");
                    String buySymbol = scanner.nextLine().toUpperCase();
                    System.out.print("Enter quantity to buy: ");
                    int buyQuantity = scanner.nextInt();
                    scanner.nextLine();  // Consume newline

                    if (market.containsKey(buySymbol)) {
                        portfolio.buyStock(buySymbol, buyQuantity);
                        System.out.println("Bought " + buyQuantity + " shares of " + buySymbol);
                    } else {
                        System.out.println("Stock symbol not found.");
                    }
                    break;

                case 3:
                    System.out.print("Enter stock symbol to sell: ");
                    String sellSymbol = scanner.nextLine().toUpperCase();
                    System.out.print("Enter quantity to sell: ");
                    int sellQuantity = scanner.nextInt();
                    scanner.nextLine();  // Consume newline

                    if (market.containsKey(sellSymbol)) {
                        portfolio.sellStock(sellSymbol, sellQuantity);
                        System.out.println("Sold " + sellQuantity + " shares of " + sellSymbol);
                    } else {
                        System.out.println("Stock symbol not found.");
                    }
                    break;

                case 4:
                    portfolio.printPortfolio(market);
                    break;

                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}