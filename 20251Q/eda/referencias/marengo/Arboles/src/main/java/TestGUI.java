import core.BST;    

// bajar el paquete nativo  
// https://gluonhq.com/products/javafx/ 

// en el VM poner el lib del paquete nativo
// --module-path C:\Users\lgomez\Downloads\javafx-sdk-11.0.2\lib --add-modules javafx.fxml,javafx.controls


import controller.GraphicsTree;
import core.Person;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

public class TestGUI extends Application {

	public static void main(String[] args) {
		// GUI
		launch(args);
	}

    @Override
	public void start(Stage stage) {
		stage.setTitle("Drawing the BST");
		StackPane root = new StackPane();
		Scene scene = new Scene(root, 500, 500);

		BST<Integer> myTree = createModel();

		GraphicsTree<Integer> c = new GraphicsTree<>(myTree);

		c.widthProperty().bind(scene.widthProperty());
		c.heightProperty().bind(scene.heightProperty());
	
		root.getChildren().add(c);
		stage.setScene(scene);
		stage.show();
		

	}

    
	private BST<Integer> createModel() {
		BST<Integer> myTree = new BST<>();
		myTree = new BST<>();
		myTree.insert(5);
		myTree.insert(70);
		myTree.insert(30);
		myTree.insert(70);
		myTree.insert(20);
		myTree.insert(40);
		myTree.insert(80);
		myTree.insert(90);
		myTree.insert(85);
		System.out.println(myTree.getCommonNodeWithRepeated(80,85));

		return myTree;
	}
	
	
	private BST<Integer> createModel2() {
		BST<Integer> myTree = new BST<>();
	myTree.insert(35);
	myTree.insert(20);
	myTree.insert(40);
	myTree.insert(15);
	myTree.insert(22);
	myTree.insert(8);
	myTree.insert(27);
	myTree.insert(25);
	myTree.insert(74);
	myTree.insert(55);
	myTree.delete(35);
	
	return myTree;
	}

	private BST<Person> createModelPerson() {
		BST<Person> myTree = new BST<>();
		myTree.insert(new Person("Pedro",50));
		myTree.insert(new Person("Juan",60));
		myTree.insert(new Person("Pepe",80));
		myTree.insert(new Person("Sofia",20));
		myTree.insert(new Person("Valentin",70));
		//myTree.delete(new Person("Pedro",50));

		return myTree;
	}
	

}