import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InternetBankingGUI {
    private double balance;
    private String accountHolderName;
    private JFrame frame;
    private JLabel balanceLabel;
    private JLabel accountHolderLabel;

    // Constructor to set up the GUI
    public InternetBankingGUI(String accountHolderName, double initialBalance) {
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;

        // Create the frame
        frame = new JFrame("Internet Banking System");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Center the frame on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        // Create components
        accountHolderLabel = new JLabel("Account Holder: " + accountHolderName);
        accountHolderLabel.setFont(new Font("Arial", Font.BOLD, 14));
        accountHolderLabel.setHorizontalAlignment(SwingConstants.CENTER);

        balanceLabel = new JLabel("Current Balance: $" + balance);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton depositButton = new JButton("Deposit Money");
        JButton withdrawButton = new JButton("Withdraw Money");
        JButton checkBalanceButton = new JButton("Check Balance");
        JButton exitButton = new JButton("Exit");

        // Add action listeners
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                depositMoney();
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                withdrawMoney();
            }
        });

        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBalanceLabel();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Thank you for using Internet Banking!");
                System.exit(0);
            }
        });

        // Layout setup
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 10, 10));
        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(checkBalanceButton);
        buttonPanel.add(exitButton);

        // Add components to frame
        frame.setLayout(new BorderLayout(10, 10));
        frame.add(accountHolderLabel, BorderLayout.NORTH);
        frame.add(balanceLabel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Make the frame visible
        frame.setVisible(true);
    }

    // Method to deposit money
    private void depositMoney() {
        String input = JOptionPane.showInputDialog(frame, "Enter amount to deposit:");
        if (input != null) {
            try {
                double amount = Double.parseDouble(input);
                if (amount > 0) {
                    balance += amount;
                    JOptionPane.showMessageDialog(frame, "Successfully deposited $" + amount);
                    updateBalanceLabel();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid amount. Please enter a positive value.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid number.");
            }
        }
    }

    // Method to withdraw money
    private void withdrawMoney() {
        String input = JOptionPane.showInputDialog(frame, "Enter amount to withdraw:");
        if (input != null) {
            try {
                double amount = Double.parseDouble(input);
                if (amount > 0 && amount <= balance) {
                    balance -= amount;
                    JOptionPane.showMessageDialog(frame, "Successfully withdrew $" + amount);
                    updateBalanceLabel();
                } else if (amount > balance) {
                    JOptionPane.showMessageDialog(frame, "Insufficient balance.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid amount. Please enter a positive value.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid number.");
            }
        }
    }

    // Method to update the balance label
    private void updateBalanceLabel() {
        balanceLabel.setText("Current Balance: $" + balance);
    }

    // Main method to launch the application
    public static void main(String[] args) {
        // Prompt the user to create an account
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String accountHolderName = JOptionPane.showInputDialog(null, "Enter your name to create an account:");
                if (accountHolderName == null || accountHolderName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Account creation failed. Exiting program.");
                    System.exit(0);
                }

                String initialDepositInput = JOptionPane.showInputDialog(null, "Enter an initial deposit amount:");
                double initialDeposit = 0;
                try {
                    initialDeposit = Double.parseDouble(initialDepositInput);
                    if (initialDeposit < 0) {
                        JOptionPane.showMessageDialog(null, "Initial deposit cannot be negative. Exiting program.");
                        System.exit(0);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid deposit amount. Exiting program.");
                    System.exit(0);
                }

                new InternetBankingGUI(accountHolderName, initialDeposit);
            }
        });
    }
}
