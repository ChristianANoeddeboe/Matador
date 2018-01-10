package core;

import gui_fields.GUI_Ownable;

/**
 * Created by magnus
 *
 */
public class AuctionController {
    private Player[] bidders;
    private boolean auctionStatus;
    private int highestBid;
    private Player whoHasTheHighestBid;
    private GUIController guiController = GUIController.getInstance();

    public AuctionController() {
    }

    public void startAuction (Player playerNotIncluded, Field field, Player[] players) {
        auctionStatus = true;
        int playersInAuction = 0;
        for (int i = 0; i < players.length; i++) {
            if (!(players[i] == playerNotIncluded))
                playersInAuction++;
        }
        bidders = new Player[playersInAuction];
        for (int j = 0; j < players.length; j++) {
            if (!(players[j] == playerNotIncluded))
                bidders[j] = players[j];
        }

        Street streetOnAuction = (Street) field;
        highestBid = streetOnAuction.getBuyValue()-1;

        while (auctionStatus == true) {
            guiController.writeMessage("Auktion på kortet: "+field.getName());
            //skal måske laves om, måske til en seperat funktion.. I forhold til at spilleren skal havde mulighden for at
            //tilbage til dont bid
            for (int i = 0; i < bidders.length; i++) {
                if (bidders[i].getAccount().canAfford(highestBid+1)) {
                    switch (guiController.requestPlayerChoiceButtons("It is " + bidders[i].getName() + "'s turn to choose between:",
                            new String[]{"Bid", "Dont bid"})) {
                        case "Bid":
                            int bid = 0;
                            do {
                                bid = Integer.parseInt(guiController.requestStringInput("Amount of money to bid:"));
                            }

                            while (bid > highestBid);
                            highestBid = bid;
                            whoHasTheHighestBid = bidders[i];
                            break;
                        case "Dont bid":
                            break;
                    }
                }

                else {
                    guiController.requestPlayerChoiceButtons(bidders[i].getName()+" do not have enough money to bid", "ok");
                }
            }
        }

    }
}
