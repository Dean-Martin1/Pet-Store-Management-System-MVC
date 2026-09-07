package Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.Pet;
import Model.Dog;
import Model.Cat;
import Model.Customer;

public class PetStoreController {
	
	private Map<String, Pet> pets = new HashMap<>();
	private Map<String, Customer> customers = new HashMap<>();
	
	public boolean authenticate(String username, String password)
	{
		return "admin".equals(username) && "admin123".equals(password);
	}
	
	public void addPet(String type, String petID, String name, String breed, int age, double price) throws Exception
	{
		if(pets.containsKey(petID)) throw new Exception("Error: A pet with this ID is already existing!");
		
		if(type.equalsIgnoreCase("dog"))
		{
			pets.put(petID, new Dog( petID,  name,  breed,  age,  price));
		}
		else if (type.equalsIgnoreCase("cat"))
		{
			pets.put(petID, new Cat( petID,  name,  breed,  age,  price));
		}
		else
		{
			throw new Exception("Error: Invalid pet type");
		}
	}
	
	public List<Pet> searchPets(String searchTerm)
	{
		if(searchTerm == null || searchTerm.trim().isEmpty())
		{
			return new ArrayList<>(pets.values());
		}
		
		String lowerSearchTerm = searchTerm.toLowerCase();
		List<Pet> searchResults = new ArrayList<>();
		
		for(Pet p : pets.values())
		{
			if(p.getName().toLowerCase().equals(lowerSearchTerm) ||
					p.getType().toLowerCase().equals(lowerSearchTerm))
			{
				searchResults.add(p);
			}
		}
		
		return searchResults;
		
	}
	
	public List<Pet> getAllPets()
	{
		return new ArrayList<>(pets.values());
	}
	
	public void registerCuststomer(String customerID, String name, int phoneNum) throws Exception
	{
		if(customerID == null || customerID.trim().isEmpty()) throw new Exception("Error: Customer ID cannot be null or empty");
		if(customers.containsKey(customerID)) throw new Exception("Error: Customer ID i already existing");
		
		customers.put(customerID, new Customer(customerID, name, phoneNum));
	}
	
	public List<Customer> getAllCustomers() 
	{
		return new ArrayList<>(customers.values());
	}
	
	public Customer getCustomer(String id)
	{
		return customers.get(id);
	}
	
	public void sellPet(String petID, String customerID) throws Exception
	{
		Pet pet = pets.get(petID);
		Customer customer = customers.get(customerID);
		
		if(pet == null) throw new Exception("Error: Pet ID not found");
		if(customer == null) throw new Exception("Error: Customer ID not found");
		if(pet.IsSold()) throw new Exception("Error: The Pet Has Been Already Sold");
		
		pet.setSold(true);
		customer.addPurchasedPet(pet);
		
	}
	
	
	
	
	
	
	


}
