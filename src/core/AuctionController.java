package core;

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
            guiController.writeMessage("Auction on card: " + field.getName());

            while (auctionStatus) {
                boolean noMoreRounds = true;
                for (Player bidder : bidders) {
                    if (!(bidder == null)) {
                        if (!(bidder == whoHasTheHighestBid)) {
                            if (bidder.getAccount().canAfford(highestBid + 1)) {
                                switch (guiController.requestPlayerChoiceButtons("Do " + bidder.getName() + " wants to bid?", "yes", "no")) {
                                    case "yes":
                                        int bid = guiController.requestIntegerInput(bidder.getName() + "Top bid: " + highestBid + ", your bid: ");
                                        if (bidder.getAccount().canAfford(bid)) {
                                            if (bid > highestBid) {
                                                highestBid = bid;
                                                whoHasTheHighestBid = bidder;
                                                noMoreRounds = false;
                                            }
                                        }
                                        else {
                                            bid = guiController.requestIntegerInput(bidder.getName() + "Too high bid try again, highest bid " + highestBid + ", your bid: ");
                                            if (bid > highestBid) {
                                                highestBid = bid;
                                                whoHasTheHighestBid = bidder;
                                                noMoreRounds = false;
                                            }
                                        }
                                        break;
                                    case "no":
                                        break;
                                }
                            }
                        } else {
                            guiController.writeMessage("You already have the highest bid XD");
                        }
                    }
                }

                if (noMoreRounds)
                    auctionStatus = false;
            }

            if (!(whoHasTheHighestBid == null)) {
                wonAuction(propertyOnAuction);
                guiController.writeMessage(whoHasTheHighestBid.getName() + " had the highest bid on " + propertyOnAuction.getName());
            } else
                guiController.writeMessage("No one made a bet. The auction is now closed");
        }
    }

    private void wonAuction(Property propertyOnAuction) {
        whoHasTheHighestBid.getAccount().withdraw(highestBid);
        propertyOnAuction.setOwner(whoHasTheHighestBid);
        guiController.setOwner(whoHasTheHighestBid.getGuiId(),propertyOnAuction.getId());
    }
}
