package core;

/**
 * Created by Magnus & Nicolai
 *
 */
public class AuctionController {
    private Player[] bidders;
    private boolean auctionStatus;
    private int highestBid;
    private Player whoHasTheHighestBid;
    private GUIController guiController = GUIController.getInstance();

    AuctionController() {
        whoHasTheHighestBid = null;
    }

    public void startAuction (Player playerNotIncluded, Field field, Player[] players) {
        auctionStatus = true;
        int playersInAuction = 0;
        Property propertyOnAuction = (Property) field;
        bidders = new Player[players.length];
        highestBid = propertyOnAuction.getBuyValue()-1;

        for (int i = 0; i < players.length; i++) {
            if (!(players[i] == playerNotIncluded))
                if (players[i].getAccount().canAfford(highestBid)) {
                    playersInAuction++;
                    bidders[i] = players[i];
                }
        }

        if (!(playersInAuction == 0)) {
            guiController.writeMessage("Auktion på "+ field.getName());

            while (auctionStatus) {
                boolean noMoreRounds = true;
                for (Player bidder : bidders) {
                    if (!(bidder == null)) {
                        if (!(bidder == whoHasTheHighestBid)) {
                            if (bidder.getAccount().canAfford(highestBid + 1)) {
                                switch (guiController.requestPlayerChoiceButtons("Vil spilleren: " + bidder.getName() + " byde?", PropertiesIO.getTranslation("yesbutton"), PropertiesIO.getTranslation("nobutton"))) {
                                    case "Ja":
                                        int bid = guiController.requestIntegerInput(bidder.getName() + "'s turn, Top bid: " + highestBid + ", Dit bud: ",0,200000);
                                        if (bidder.getAccount().canAfford(bid)) {
                                            if (bid > highestBid) {
                                                highestBid = bid;
                                                whoHasTheHighestBid = bidder;
                                                noMoreRounds = false;
                                            }
                                        }
                                        else {
                                            bid = guiController.requestIntegerInput(bidder.getName() + " bet were too high try again, highest bid " + highestBid + ", Dit bud: ",0,200000);
                                            if (bid > highestBid) {
                                                highestBid = bid;
                                                whoHasTheHighestBid = bidder;
                                                noMoreRounds = false;
                                            }
                                        }
                                        break;
                                    case "Nej":
                                        break;
                                }
                            }
                        } else {
                            guiController.writeMessage("Du har allerede de højeste bud");
                        }
                    }
                }

                if (noMoreRounds)
                    auctionStatus = false;
            }

            if (!(whoHasTheHighestBid == null)) {
                wonAuction(propertyOnAuction);
                guiController.writeMessage(whoHasTheHighestBid.getName() + " havde det højste bud på " + propertyOnAuction.getName());
            } else
                guiController.writeMessage("Ingen har budt, auktionen slutter.");
        }
    }

    private void wonAuction(Property propertyOnAuction) {
        whoHasTheHighestBid.getAccount().withdraw(highestBid);
        propertyOnAuction.setOwner(whoHasTheHighestBid);
        guiController.updatePlayerBalance(whoHasTheHighestBid.getGuiId(),whoHasTheHighestBid.getAccount().getBalance());
        guiController.setOwner(whoHasTheHighestBid.getGuiId(),propertyOnAuction.getId());
    }
}
