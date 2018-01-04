package core;

public class ChanceCardLogic {
	private int[] chanceCardsArray = new int[31];
	private Entities entities;
	private PropertiesIO config = new PropertiesIO("config.properties");
	private Property property;

	public ChanceCardLogic () {

	}
	
	public String getCard(Player currentPlayer) {
		String returnbesked = "";
		int chancecardid  = entities.getChanceId();
		switch (chancecardid) {
			case 0:
				currentPlayer.addPrisonCard();
				returnbesked = config.getTranslation("chance1");
				break;
			case 1:
				currentPlayer.setEndPosition(11);
				currentPlayer.isPrison();
				returnbesked = config.getTranslation("chance2");
				break;
			case 2:
				int playerCurrentPosition = currentPlayer.getEndPosition();
				if (playerCurrentPosition == 3) {
					currentPlayer.setEndPosition(6);
				}

				if (playerCurrentPosition == 8) {
					currentPlayer.setEndPosition(16);
				}

				if (playerCurrentPosition == 18 || playerCurrentPosition == 22) {
					currentPlayer.setEndPosition(26);
				}

				if (playerCurrentPosition == 34) {
					currentPlayer.setEndPosition(36);
				}

				if (playerCurrentPosition == 37) {
					currentPlayer.setEndPosition(6);
					currentPlayer.getAccount().deposit(4000); //husk at checke med gameLogicController i forholdsvis til start penge
				}
				//(Ikke lavet endnu) hvis den ejes skal spilleren betale 2 gange den leje (brug field array referancerne)
				returnbesked = config.getTranslation("chance3");
				break;
			case 3:
				currentPlayer.getAccount().deposit(1000);
				returnbesked = config.getTranslation("chance4");
				break;
			case 4:
				currentPlayer.getAccount().withdraw(200);
				returnbesked = config.getTranslation("chance5");
				break;
			case 5:
				currentPlayer.getAccount().deposit(1000);
				returnbesked = config.getTranslation("chance6");
				break;
			case 6:
				//loop der går igennem alle fields og checker ejeren og antal huse og hoteler
				//800 pr hus og 2300 pr hotel
				returnbesked = config.getTranslation("chance7");
				break;
			case 7:
				currentPlayer.setEndPosition(1);
				returnbesked = config.getTranslation("chance8");
				break;
			case 8:
				currentPlayer.getAccount().deposit(500);
				returnbesked = config.getTranslation("chance9");
				break;
			case 9:
				currentPlayer.getAccount().deposit(3000);
				returnbesked = config.getTranslation("chance10");
				break;
			case 10:
				currentPlayer.setEndPosition(40);
				returnbesked = config.getTranslation("chance11");
				break;
			case 11:
				int amountOfStepsBack = currentPlayer.getEndPosition();
				if (amountOfStepsBack == 0) {
					currentPlayer.setEndPosition(40);
				}
				currentPlayer.setEndPosition(amountOfStepsBack);
				returnbesked = config.getTranslation("chance12");
				break;
			case 12:
				//vent med at implentere
				returnbesked = config.getTranslation("chance13");
				break;
			case 13:
				currentPlayer.getAccount().deposit(200);
				returnbesked = config.getTranslation("chance14");
				break;
			case 14:
				currentPlayer.getAccount().withdraw(2000);
				returnbesked = config.getTranslation("chance15");
				break;
			case 15:
				//loop der går igennem alle fields og checker ejeren og antal huse og hoteler
				//pr. hus 500 og 2000 pr hotel
				returnbesked = config.getTranslation("chance16");
				break;
			case 16:
				if (3 == currentPlayer.getEndPosition() || 8 == currentPlayer.getEndPosition()) {
					currentPlayer.getAccount().deposit(4000);
				}
				currentPlayer.setEndPosition(12);
				returnbesked = config.getTranslation("chance17");
				break;
			case 17:
				currentPlayer.getAccount().withdraw(1000);
				returnbesked = config.getTranslation("chance18");
				break;
			case 18:
				currentPlayer.getAccount().deposit(1000);
				returnbesked = config.getTranslation("chance19");
				break;
			case 19:
				currentPlayer.getAccount().deposit(1000);
				returnbesked = config.getTranslation("chance20");
				break;
			case 20:
				currentPlayer.getAccount().withdraw(200);
				returnbesked = config.getTranslation("chance21");
				break;
			case 21:
				currentPlayer.getAccount().withdraw(3000);
				returnbesked = config.getTranslation("chance22");
				break;
			case 22:
				currentPlayer.getAccount().withdraw(3000);
				returnbesked = config.getTranslation("chance23");
				break;
			case 23:
				currentPlayer.getAccount().withdraw(1000);
				returnbesked = config.getTranslation("chance24");
				break;
			case 24:
				currentPlayer.setEndPosition(6);
				returnbesked = config.getTranslation("chance22");
				break;
			case 25:
				break;
			case 26:
				break;
			case 27:
				break;
			case 28:
				break;
			case 29:
				break;
			case 30:
				break;
			case 31:
				break;
			default:
				returnbesked = "fejl";
				break;
		}
		return returnbesked;
	}

}
