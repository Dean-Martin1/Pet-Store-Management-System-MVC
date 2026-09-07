package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

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
import Model.Pet;

public class MainView extends JFrame{
	
	private PetStoreController controller;
	private JTable petTable; 
	
	public MainView(PetStoreController controller)
	{
		this.controller = controller;
		
		setTitle("Happy Paws Pet Store Management");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Pets Inventory", createPetPanel());
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
        	}
        	catch(NumberFormatException error)
        	{
        		JOptionPane.showMessageDialog(this, "Age and price shuld be numbers", "Error", JOptionPane.ERROR_MESSAGE);
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
}
