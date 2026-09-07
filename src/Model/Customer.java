package Model;

import java.util.ArrayList;
import java.util.List;

public class Customer
{
	private String customerID;
	private String name;
	private int phoneNum;
	private List<Pet> petsBought;
	
	public Customer (String customerID, String name, int phoneNum) 
	{
		this.customerID = customerID;
		this.name = name;
		this.phoneNum = phoneNum;
		this.petsBought = new ArrayList<>();
		
	}
	
	public String getCustomerID() {
	    return customerID;
	}

	public String getName() {
	    return name;
	}

	public int getPhoneNum() {
	    return phoneNum;
	}

	public List<Pet> getPetsBought() {
	    return petsBought;
	}
	
	public void addPurchasedPet(Pet p) 
	{
		petsBought.add(p);
	}
	
	public double getTotalSpent() 
	{
		double total = 0;
		for(Pet p : petsBought ) 
		{
			total += p.getPrice();
		}
		
		return total;
	}
	
	
	
	

}
