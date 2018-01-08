package core;

//Lav om til en singleton
//evt bedre navne til værdier og objekter

public class ChanceCardLogic {
	private static int index;
	private Entities entities;
	private PropertiesIO config;
	private int[] chanceCards;
	private Player[] players;
	private Field[] fields;
	private Property property;
	private Street normal;
	private String chanceCardMessage;

	public ChanceCardLogic () {
		index = 0;
		entities = Entities.getInstance();
		config = entities.getConfig();
		chanceCards = entities.getChanceCardArr();
		players = entities.getPlayers();
		fields = entities.getFieldArr();
		property = null;
		normal = null;
		chanceCardMessage = null;
	}

	public void setIndex (int value) {
	    this.index = value;
    }

	public int getIndex () {
		return this.index;
	}
	
	public String getCard(Player currentPlayer) {
		switch (chanceCards[getIndex()]) {
			case 0:
				currentPlayer.addPrisonCard();
				chanceCardMessage = config.getTranslation("chance1");
				break;
			case 1:
				currentPlayer.setEndPosition(10);
				chanceCardMessage = config.getTranslation("chance2");
				break;
			case 2:
				if (currentPlayer.getEndPosition() == 2) {
					currentPlayer.setEndPosition(5);
				}

				if (currentPlayer.getEndPosition() == 7) {
					currentPlayer.setEndPosition(15);
				}

				if (currentPlayer.getEndPosition() == 17 || currentPlayer.getEndPosition() == 21) {
					currentPlayer.setEndPosition(25);
				}

				if (currentPlayer.getEndPosition() == 33) {
					currentPlayer.setEndPosition(35);
				}

				if (currentPlayer.getEndPosition() == 36) {
					currentPlayer.setEndPosition(5);
					currentPlayer.getAccount().deposit(4000);
				}

				property = (Property) fields[currentPlayer.getEndPosition()];

				if (property.isOwned()) {
                    currentPlayer.getAccount().withdraw(8000);
                    property.getOwner().getAccount().deposit(8000);
                }
				chanceCardMessage = config.getTranslation("chance3");
				break;
			case 3:
				currentPlayer.getAccount().deposit(1000);
				chanceCardMessage = config.getTranslation("chance4");
				break;
			case 4:
				currentPlayer.getAccount().withdraw(200);
				chanceCardMessage = config.getTranslation("chance5");
				break;
			case 5:
				currentPlayer.getAccount().deposit(1000);
				chanceCardMessage = config.getTranslation("chance6");
				break;
			case 6:
				int estateTax = 0;

				for (int i = 0; i < fields.length; i++) {
					if (fields[i].getClass().getSimpleName() == "Normal") {
						normal = (Street) fields[i];
						if (normal.getOwner() == currentPlayer) {
							if (normal.getHouseCounter() == 5) {
								estateTax += 2300;
							} else {
								estateTax += (800 * (normal.getHouseCounter()));
							}
						}
					}
				}
				currentPlayer.getAccount().withdraw(estateTax);
				chanceCardMessage = config.getTranslation("chance7");
				break;
			case 7:
				currentPlayer.setEndPosition(0);
				chanceCardMessage = config.getTranslation("chance8");
				break;
			case 8:
				currentPlayer.getAccount().deposit(500);
				chanceCardMessage = config.getTranslation("chance9");
				break;
			case 9:
				currentPlayer.getAccount().deposit(3000);
				chanceCardMessage = config.getTranslation("chance10");
				break;
			case 10:
				currentPlayer.setEndPosition(39);
				chanceCardMessage = config.getTranslation("chance11");
				break;
			case 11:
				int amountOfStepsBack = currentPlayer.getEndPosition();
				if (amountOfStepsBack == 0) {
					currentPlayer.setEndPosition(39);
				}
				else {
					currentPlayer.setEndPosition(amountOfStepsBack - 3);
				}
				chanceCardMessage = config.getTranslation("chance12");
				break;
			case 12:
				int playerValue = 0;
				for (int i = 0; i < fields.length; i++) {
					if (fields[i].getClass().getSimpleName() == "Normal") {
						if (currentPlayer == property.getOwner()) {
							property = (Property) fields[i];
							normal = (Street) fields[i];
							playerValue += (property.getBaseValue());
							if (normal.getHouseCounter() > 0) {
								int buildPrice = normal.getBuildPrice();
								for (int j = 0; j < normal.getHouseCounter(); j++) {
									playerValue += buildPrice;
								}
							}
						}
					}

					else if (fields[i].getClass().getSimpleName() == "Shipping" || fields[i].getClass().getSimpleName() == "Brewery") {
						if (currentPlayer == property.getOwner()) {
							playerValue += (property.getBaseValue());
						}
					}
				}

				if (playerValue <= 15000)
					currentPlayer.getAccount().deposit(15000);
				chanceCardMessage = config.getTranslation("chance13");
				break;
			case 13:
				currentPlayer.getAccount().deposit(200);
				chanceCardMessage = config.getTranslation("chance14");
				break;
			case 14:
				currentPlayer.getAccount().withdraw(2000);
				chanceCardMessage = config.getTranslation("chance15");
				break;
			case 15:
				estateTax = 0;

				for (int i = 0; i < fields.length; i++) {
					if (fields[i].getClass().getSimpleName() == "Normal") {
						normal = (Street) fields[i];
						if (normal.getOwner() == currentPlayer) {
							if (normal.getHouseCounter() == 5) {
								estateTax += 2000;
							}
							else {
								estateTax += (500*(normal.getHouseCounter()));
							}
						}
					}
				}
				currentPlayer.getAccount().withdraw(estateTax);
				chanceCardMessage = config.getTranslation("chance16");
				break;
			case 16:
				if (3 == currentPlayer.getEndPosition() || 8 == currentPlayer.getEndPosition()) {
					currentPlayer.getAccount().deposit(4000);
				}
				currentPlayer.setEndPosition(12);
				chanceCardMessage = config.getTranslation("chance17");
				break;
			case 17:
				currentPlayer.getAccount().withdraw(1000);
				chanceCardMessage = config.getTranslation("chance18");
				break;
			case 18:
				currentPlayer.getAccount().deposit(1000);
				chanceCardMessage = config.getTranslation("chance19");
				break;
			case 19:
				currentPlayer.getAccount().deposit(1000);
				chanceCardMessage = config.getTranslation("chance20");
				break;
			case 20:
				currentPlayer.getAccount().withdraw(200);
				chanceCardMessage = config.getTranslation("chance21");
				break;
			case 21:
				currentPlayer.getAccount().withdraw(3000);
				chanceCardMessage = config.getTranslation("chance22");
				break;
			case 22:
				currentPlayer.getAccount().withdraw(3000);
				chanceCardMessage = config.getTranslation("chance23");
				break;
			case 23:
				currentPlayer.getAccount().withdraw(1000);
				chanceCardMessage = config.getTranslation("chance24");
				break;
			case 24:
				if (!(3 == currentPlayer.getEndPosition())) {
					currentPlayer.getAccount().deposit(4000);
				}
				currentPlayer.setEndPosition(6);
				chanceCardMessage = config.getTranslation("chance25");
				break;
			case 25:
				int	present = 0;
				for (int i = 0; i < players.length; i++) {
					if (!(players[i] == currentPlayer)) {
						players[i].getAccount().withdraw(200);
						present += 200;
					}
				}
				currentPlayer.getAccount().deposit(present);
				chanceCardMessage = config.getTranslation("chance26");
				break;
			case 26:
				if (34 == currentPlayer.getEndPosition() || 37 == currentPlayer.getEndPosition()) {
					currentPlayer.getAccount().deposit(4000);
				}
				currentPlayer.setEndPosition(6);
				chanceCardMessage = config.getTranslation("chance27");
				break;
		}

		index += 1;
		if (index > 31)
		    setIndex(0);

		return chanceCardMessage;
	}
}
