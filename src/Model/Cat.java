package Model;

public class Cat extends Pet
{
	public Cat (String petID, String name, String breed, int age, double price) 
	{
		super(petID, name, breed, age, price);
	}
	
	@Override
	public String getType()
	{
		return "Cat";
	}
}
