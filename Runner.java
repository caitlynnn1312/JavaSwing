
// Name: Caitlyn Nunez-Nole
// Date: 11/14/24
// Description: A check splitter
import javax.swing.*;
import java.text.DecimalFormat;

public class Runner {
    static DecimalFormat df = new DecimalFormat("0.00"); // Formats numbers to fit 0.00
    static void evenSplit(double x, int y) {
        JOptionPane.showMessageDialog(null, "Each person has to pay a total of: $" + df.format(x / y)); // People/Total to split the bill evenly
    } 

    static void byOrder(int y) {
        for (int i = 0; i <= y; i++) { // Will create an individual receipt per customer
            double total = 0.0;
            String name = JOptionPane.showInputDialog("Customer # " + (i + 1) + " please enter your name: ");
            String orderAmount = JOptionPane.showInputDialog("How many items did you order (include drinks):");
            int order = Integer.parseInt(orderAmount);
            for (int j = 0; j < order; j++) {
                String itemCost = JOptionPane.showInputDialog("Please enter the cost of item #" + (j + 1) + ":");
                double item = Double.parseDouble(itemCost);
                total = total + item;
            }
            String tip = JOptionPane.showInputDialog("Would you like to add tip? (y/n): ");
            if (tip.equals("y")) { // Tip suggestions
                JOptionPane.showMessageDialog(null,
                "\n1) 5%    $" + df.format(.05 * total) +
                "\n2) 10%    $" + df.format(.1 * total) +
                "\n3) 15%    $" + df.format(.15 * total) +
                "\n4) 20%    $" + df.format(.2 * total) +
                "\n5) 25%    $" + df.format(.25 * total) +
                "\n6) Other");
                double[] tips = {(.05 * total), (.1 * total), (.15 * total), (.2 * total), (.25 * total)}; // Array that aligns with the tip options
                String tipAmount = JOptionPane.showInputDialog("Please select one of the options above:");
                int tipVar = Integer.parseInt(tipAmount);
                switch (tipVar) { // User can chose tip suggestions or chose their own amount
                    case 1, 2, 3, 4, 5:
                        total += tips[tipVar-1]; // Using the value in the array that aligns witht he number chosen 
                        break;
                    case 6:
                        String custom = JOptionPane.showInputDialog("How much would you like to tip?: $");
                        double customTip = Double.parseDouble(custom);
                        total += customTip;
                        break;
                }
            }
            JOptionPane.showMessageDialog(null, name + " your total is: $" + df.format(total)); // df.format uses the DecimalFormat library variable declared at the top
        }
    }

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Welcome to SplitIt!");
        String totalInput = JOptionPane.showInputDialog("Please enter the total bill:");
        String peopleInput = JOptionPane.showInputDialog("Please enter the total number of people:");
        double totalBill = Double.parseDouble(totalInput); // Converting string to int because JOptionPane can only accept strings
        int people = Integer.parseInt(peopleInput);
        String choice = JOptionPane.showInputDialog(
                "Would you like to:\n1) Split the bill evenly\n2) Each person pays for what they ordered");
        if (choice.equals("1")) {
            evenSplit(totalBill, people);
        } else if (choice.equals("2")) {
            byOrder(people);
        }
        JOptionPane.showMessageDialog(null, "Thank you for using SplitIt!");
    }
}