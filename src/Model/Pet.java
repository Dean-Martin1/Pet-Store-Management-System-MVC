package Model;

public abstract class Pet 
{
	private String petID;
	private String name;
	private String breed;
	private int age;
	private double price;
	private boolean isSold;
	
	public Pet (String petID, String name, String breed, int age, double price) 
	{
		this.petID = petID;
		this.name = name;
		this.breed = breed;
		this.age = age;
		this.price = price;
		this.isSold = false;
	}
	
	public String getPetID() {
	    return petID;
	}

	public String getName() {
	    return name;
	}

	public abstract String getType();

	public String getBreed() {
	    return breed;
	}

	public int getAge() {
	    return age;
	}

	public double getPrice() {
	    return price;
	}
	
	public boolean IsSold()
	{
		return isSold;
	}
	public void setSold(boolean sold)
	{
		this.isSold = sold;
	}
	
	
	
}
