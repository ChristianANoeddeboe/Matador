package core;


public class GameController {
	
	public static void main(String Args[]) {
		Entities entities = Entities.getInstance();
		
		Field fields[] = entities.getFieldArr();
		for ( int i = 0 ; i < fields.length ; i++) {
			System.out.println(fields[i].getName());
		}
		
		
		
	}
	
	public GameController() {
		
	}
	
}

