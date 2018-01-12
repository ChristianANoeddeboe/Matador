package core;

/**
 * @author Magnus Stjernborg Koch - s175189
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

    /**
     * This function is called when a new auction needs to start. Its public so other controllers can call the function
     */
    public void startAuction (Player playerNotIncluded, Field field, Player[] players) {
        //auction is now started
        auctionStatus = true;
        //amount of players in auction
        int playersInAuction = 0;
        //The property on auction
        Property propertyOnAuction = (Property) field;
        //initialising an array for all bidders in the current auction
        bidders = new Player[players.length];
        //initialising the highest bid, so the default highest bid depends on the property price
        highestBid = propertyOnAuction.getBuyValue()-1;

        //Loop is going through players and checking who can bet in this auction
        for (int i = 0; i < players.length; i++) {
            if (!(players[i] == playerNotIncluded))
                if (players[i].getAccount().canAfford(highestBid)) {
                    playersInAuction++;
                    bidders[i] = players[i];
                }
        }

        //The auctions starts only if there are at least one that can bet
        if (!(playersInAuction == 0)) {
            guiController.writeMessage("Auction on card: " + field.getName());

            //Runs auction until the auctionstatus is changed
            while (auctionStatus) {
                boolean noMoreRounds = true;
                //Goes through all bidders
                for (Player bidder : bidders) {
                    if (!(bidder == null)) {
                        //if player has the highest bet
                        if (!(bidder == whoHasTheHighestBid)) {
                            //checks if bidder can afford highest bet
                            if (bidder.getAccount().canAfford(highestBid + 1)) {
                                switch (guiController.requestPlayerChoiceButtons("Vil spilleren: " + bidder.getName() + " byde?", PropertiesIO.getTranslation("yesbutton"), PropertiesIO.getTranslation("nobutton"))) {
                                    case "Ja":
                                        //The amount the player bets
                                        int bid = guiController.requestIntegerInput(bidder.getName() + "'s turn, Top bid: " + highestBid + ", your bid: ",0,200000);
                                        //can player afford the bet
                                        if (bidder.getAccount().canAfford(bid)) {
                                            //is bet higher then highest bet
                                            if (bid > highestBid) {
                                                highestBid = bid;
                                                whoHasTheHighestBid = bidder;
                                                noMoreRounds = false;
                                            }
                                        }
                                        else {
                                            bid = guiController.requestIntegerInput(bidder.getName() + " bet were too high try again, highest bid " + highestBid + ", your bid: ",0,200000);
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
                            guiController.writeMessage("You already have the highest bid XD");
                        }
                    }
                }

                if (noMoreRounds)
                    auctionStatus = false;
            }

            //Checks if there is a winner
            if (!(whoHasTheHighestBid == null)) {
                wonAuction(propertyOnAuction);
                guiController.writeMessage(whoHasTheHighestBid.getName() + " had the highest bid on " + propertyOnAuction.getName());
            } else
                guiController.writeMessage("No one made a bet. The auction is now closed");
        }
    }

    /**
     * When someone has won the auction house the player will be set has owner of the property
     */
    private void wonAuction(Property propertyOnAuction) {
        //Takes amount which the player bed from his account
        whoHasTheHighestBid.getAccount().withdraw(highestBid);
        //Set him as the new owner of the property
        propertyOnAuction.setOwner(whoHasTheHighestBid);
        guiController.updatePlayerBalance(whoHasTheHighestBid.getGuiId(),whoHasTheHighestBid.getAccount().getBalance());
        guiController.setOwner(whoHasTheHighestBid.getGuiId(),propertyOnAuction.getId());
    }
}
