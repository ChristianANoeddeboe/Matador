package core;

/**
 *
 * @author Magnus Stjernborg Koch s175189 & Nicolai Kammersgård s143780
 *
 */
public class TradeController {
    private GUIController guiController = GUIController.getInstance();

    public void startTrade (Player currentPlayer, FieldController fieldController, Player[] players) {
        String[] choices = new String[players.length-1];
        int counter = 0;
        for (int i = 0; i < players.length; i++) {
            if (!(players[i] == currentPlayer)) {
                choices[counter++] = players[i].getName();
            }
        }
        String response = guiController.requestPlayerChoiceButtons("Hvem vil du gerne trade med: ",choices);
        for (int i = 0; i < players.length; i++) {
            if (players[i].getName().equals(response)) {
            	if (fieldController.FieldsOwned(players[i]).length > 0) {
            		response = guiController.requestPlayerChoiceButtons("Hvem vil du gerne trade med: ", fieldController.FieldsOwned(players[i]));
            	}
            }
        }

        Field[] fields = fieldController.getFieldArr();

        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getName().equals(response)) {
                Property property = (Property) fields[i];
                int amountOffered = 0;
                boolean cannotAfford = true;
                do {
                	amountOffered = guiController.requestIntegerInput("Hvor mange penge tilbyder du: "); //TODO Ret til at bruge PropetiesIO
                	if(currentPlayer.getAccount().canAfford(amountOffered)) {
                		cannotAfford = false;
                	}	
                } while(cannotAfford);
                
                response = guiController.requestPlayerChoiceButtons(property.getOwner().getName()+" accpetere du tilbuddet på "+amountOffered+" for "+property.getName()+"?","yes","no");
                if (response.equals("yes")) {
                    currentPlayer.getAccount().withdraw(amountOffered);
                    property.getOwner().getAccount().deposit(amountOffered);
                    guiController.updatePlayerBalance(currentPlayer.getGuiId(), currentPlayer.getAccount().getBalance());
                    guiController.updatePlayerBalance(property.getOwner().getGuiId(), property.getOwner().getAccount().getBalance());
                    property.setOwner(currentPlayer);
                    guiController.setOwner(currentPlayer.getGuiId(), property.getId());
                }
            }
        }
    }
}
