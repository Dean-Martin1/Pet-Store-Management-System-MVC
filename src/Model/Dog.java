package Model;

public class Dog extends Pet
{
	public Dog (String petID, String name, String breed, int age, double price) 
	{
		super(petID, name, breed, age, price);
	}
	
	@Override
	public String getType()
	{
		return "Dog";
	}
}
