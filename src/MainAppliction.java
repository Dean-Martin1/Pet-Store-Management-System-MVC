import Controller.PetStoreController;
import View.LoginView;

public class MainAppliction {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		PetStoreController controller = new PetStoreController();
		
		try {
            // Populate Initial Dummy Data
            controller.addPet("Dog", "P001", "Buddy", "Golden Retriever", 2, 500.00);
            controller.addPet("Cat", "P002", "Luna", "Siamese", 1, 300.00);
            controller.addPet("Dog", "P003", "Max", "Bulldog", 4, 450.00);
            
            controller.registerCuststomer("C001", "Alice Smith", 5550101);
            controller.registerCuststomer("C002", "Bob Johnson", 5550202);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		new LoginView(controller).setVisible(true);
	}

}
