package core;


public class GameController {
	
	public static void main(String Args[]) {
		Entities entities = Entities.getInstance();
		
		Field fields[] = entities.getFieldArr();
		System.out.println(((Normal) fields[1]).getBaseValue());
		
		
		
	}
	
	public GameController() {
		
	}
	
}

