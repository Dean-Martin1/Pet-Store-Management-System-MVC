package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import Controller.PetStoreController;
import Model.Customer;
import Model.Pet;

public class MainView extends JFrame{
	
	private PetStoreController controller;
	private JTable petTable; 
	private JTable customerTable;
	
	public MainView(PetStoreController controller)
	{
		this.controller = controller;
		
		setTitle("Happy Paws Pet Store Management");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Pets Inventory", createPetPanel());
        tabs.addTab("Customers", createCustomerPanel());
        tabs.addTab("Sales Desk", createSalesPanel());
        add(tabs);
	}
	
	
	
	private JPanel createPetPanel()
	{
		JPanel panel = new JPanel(new BorderLayout());
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField searchField = new JTextField(20);
        JButton searchBtn = new JButton("Search Name/Type");
        topPanel.add(new JLabel("Search:")); topPanel.add(searchField); topPanel.add(searchBtn);
        
        petTable = new JTable(new PetTableModel(controller.getAllPets()));
        
        searchBtn.addActionListener(e -> {
        	petTable.setModel(new PetTableModel(controller.searchPets(searchField.getText())));
        });
        
        panel.add(topPanel, BorderLayout.NORTH);
        
        
        panel.add(new JScrollPane(petTable), BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new GridLayout(2, 7, 5, 5));
        String[] types = {"Dog", "Cat"};
        JComboBox<String> typeCB = new JComboBox<>(types);
        JTextField idF = new JTextField(); JTextField nameF = new JTextField();
        JTextField breedF = new JTextField(); JTextField ageF = new JTextField();
        JTextField priceF = new JTextField();

        bottomPanel.add(new JLabel("Type")); bottomPanel.add(new JLabel("ID"));
        bottomPanel.add(new JLabel("Name")); bottomPanel.add(new JLabel("Breed"));
        bottomPanel.add(new JLabel("Age")); bottomPanel.add(new JLabel("Price ($)"));
        bottomPanel.add(new JLabel("")); // Spacer

        bottomPanel.add(typeCB); bottomPanel.add(idF); bottomPanel.add(nameF);
        bottomPanel.add(breedF); bottomPanel.add(ageF); bottomPanel.add(priceF);

        JButton addBtn = new JButton("Add Pet");
        bottomPanel.add(addBtn);
        
        addBtn.addActionListener(e->{
        	try
        	{
        		
        		controller.addPet((String)typeCB.getSelectedItem(), idF.getText(), nameF.getText(),
                        breedF.getText(), Integer.parseInt(ageF.getText()), Double.parseDouble(priceF.getText()));
        		JOptionPane.showMessageDialog(this, "Pet Added Successfully!");
        		refreshTables();
        	}
        	catch(NumberFormatException error)
        	{
        		JOptionPane.showMessageDialog(this, "Age and price should be numbers", "Error", JOptionPane.ERROR_MESSAGE);
        	}
        	catch(Exception error)
        	{
        		JOptionPane.showMessageDialog(this, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        	}
        });
        
        panel.add(bottomPanel, BorderLayout.SOUTH);
        
        return panel;
        
	}
	
	// --- Flat Table Models (No 2D Arrays) ---
    private static class PetTableModel extends AbstractTableModel {
        private final List<Pet> list;
        private final String[] cols = {"ID", "Type", "Name", "Breed", "Age", "Price", "Status"};

        public PetTableModel(List<Pet> list)
        {
        	this.list = list; 
        }
        @Override public int getRowCount() { return list.size(); }
        @Override public int getColumnCount() { return cols.length; }
        @Override public String getColumnName(int i) { return cols[i]; }
        @Override public Object getValueAt(int r, int c) {
            Pet p = list.get(r);
            switch(c) {
                case 0: return p.getPetID();
                case 1: return p.getType();
                case 2: return p.getName();
                case 3: return p.getBreed();
                case 4: return p.getAge();
                case 5: return "$" + p.getPrice();
                case 6: return p.IsSold() ? "SOLD" : "Available";
                default: return null;
            }
        }
    }
    
    
    private JPanel createCustomerPanel()
    {
    	JPanel panel = new JPanel(new BorderLayout());
        customerTable = new JTable(new CustomerTableModel(controller.getAllCustomers()));
        panel.add(new JScrollPane(customerTable), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        JTextField idF = new JTextField(8);
        JTextField nameF = new JTextField(12);
        JTextField phoneF = new JTextField(10);
        
        bottomPanel.add(new JLabel("Cust ID:")); bottomPanel.add(idF);
        bottomPanel.add(new JLabel("Name:")); bottomPanel.add(nameF);
        bottomPanel.add(new JLabel("Phone:")); bottomPanel.add(phoneF);

        JButton addBtn = new JButton("Register");
        JButton profileBtn = new JButton("View Purchases");
        
        addBtn.addActionListener(e -> {
        	try
        	{
        		controller.registerCuststomer(idF.getText(), nameF.getText(), Integer.parseInt(phoneF.getText()));
        		JOptionPane.showMessageDialog(this, "Customer Registration Successfull");
        		refreshTables();
        	}
        	catch(NumberFormatException error)
        	{
        		JOptionPane.showMessageDialog(this, "Phone Number should be numbers", "Error", JOptionPane.ERROR_MESSAGE);
        	}
        	catch(Exception error)
        	{
        		JOptionPane.showMessageDialog(this, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        	}
        });
        
        profileBtn.addActionListener(e -> {
        	int row = customerTable.getSelectedRow();
        	
        	if(row == -1)
        	{
        		JOptionPane.showMessageDialog(this, "Select a cutomer from the table first");
        		return;
        	}
        	
        	String custID = (String)customerTable.getValueAt(row, 0);
        	Customer c = controller.getCustomer(custID);
        	
        	String history = "Total Spent = $" + c.getTotalSpent() + "\n\nPurchased Pets \n";
        	
        	for(Pet p : c.getPetsBought())
        	{
        		history += "- "+ p.getName() + " (" + p.getType() + ") : $"+ p.getPrice() + "\n";
        	}
        	
        	JOptionPane.showMessageDialog(this, history, "Profile: "+ c.getName(), JOptionPane.INFORMATION_MESSAGE);
        	
        });
        
        bottomPanel.add(addBtn);
        bottomPanel.add(profileBtn);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    
    private JPanel createSalesPanel()
    {
    	JPanel panel = new JPanel(new GridLayout(4, 2, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JTextField petIdF = new JTextField();
        JTextField custIdF = new JTextField();

        panel.add(new JLabel("Enter Pet ID:")); panel.add(petIdF);
        panel.add(new JLabel("Enter Customer ID:")); panel.add(custIdF);

        JButton sellBtn = new JButton("Process Sale");
        
        sellBtn.addActionListener(e -> {
        	try
        	{
        		controller.sellPet(petIdF.getText(), custIdF.getText());
        		JOptionPane.showMessageDialog(this, "Sale Successful! Pet marked as sold");
        	}
        	catch(Exception error)
        	{
        		JOptionPane.showMessageDialog(this, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        	}
        });
        panel.add(new JLabel(""));
        panel.add(sellBtn);
        return panel;
    }
    
    private void refreshTables()
    {
    	petTable.setModel(new PetTableModel(controller.getAllPets()));
    	customerTable.setModel(new CustomerTableModel(controller.getAllCustomers()));
    }
    
    private static class CustomerTableModel extends AbstractTableModel {
        private final List<Customer> list;
        private final String[] cols = {"ID", "Name", "Phone", "Pets Bought"};

        public CustomerTableModel(List<Customer> list) 
        { 
        	this.list = list; 
        }
        @Override public int getRowCount() { return list.size(); }
        @Override public int getColumnCount() { return cols.length; }
        @Override public String getColumnName(int i) { return cols[i]; }
        @Override public Object getValueAt(int r, int c) {
            Customer cust = list.get(r);
            switch(c) {
                case 0: return cust.getCustomerID();
                case 1: return cust.getName();
                case 2: return cust.getPhoneNum();
                case 3: return cust.getPetsBought().size();
                default: return null;
            }
        }
    }
}
