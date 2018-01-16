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
        //The property on auction passed as parameter
        Property propertyOnAuction = (Property) field;
        //initialising an array for all bidders in the current auction
        bidders = new Player[players.length];
        //initialising the highest bid, so the default highest bid depends on the property price
        highestBid = propertyOnAuction.getBuyValue()-1;

        //Loop is going through players and checking who can bet in this auction
        for (int i = 0; i < players.length; i++) {
            if (!(players[i] == playerNotIncluded))
                if (players[i].getAccount().canAfford(highestBid)) { // only include those who can afford to bid
                    playersInAuction++;
                    bidders[i] = players[i]; // Fill out bidders array with player refrences
                }
        }

        //The auctions starts only if there are at least one that can bet
        if (playersInAuction != 0) {
            guiController.writeMessage(PropertiesIO.getTranslation("auctionon")+ " "+ field.getName());

            //Runs auction until the auctionstatus is changed
            while (auctionStatus) {
                boolean noMoreRounds = true;
                //Goes through all bidders
                for (Player bidder : bidders) {
                    if (!(bidder == null)) { // Make sure it is not a null player
                        //if player has the highest bet
                        if (bidder != whoHasTheHighestBid) { 
                            //checks if bidder can afford highest bet
                            if (bidder.getAccount().canAfford(highestBid + 1)) { // Can he afford atleast the current highest bid
                                switch (guiController.requestPlayerChoiceButtons(PropertiesIO.getTranslation("auctionplayer")+" " + bidder.getName() + " "+PropertiesIO.getTranslation("auctionbid")+"?", PropertiesIO.getTranslation("yesbutton"), PropertiesIO.getTranslation("nobutton"))) {
                                    case "Ja":
                                        //The amount the player bets
                                        int bid = guiController.requestIntegerInput(bidder.getName() + PropertiesIO.getTranslation("auctiontopbid") + " "+ highestBid + PropertiesIO.getTranslation("auctionyourbid"),0,bidder.getAccount().getBalance());
                                        //can player afford the bet
                                        if (bidder.getAccount().canAfford(bid)) {
                                            //is bet higher then highest bet
                                            if (bid > highestBid) { // Make sure his bid is higher than the current highest bid
                                                highestBid = bid;
                                                whoHasTheHighestBid = bidder;
                                                noMoreRounds = false;
                                            }
                                        }else { // Player can't afford the bid he placed
                                            bid = guiController.requestIntegerInput(bidder.getName() + " " + PropertiesIO.getTranslation("auctiontoohigh") +" " + highestBid + PropertiesIO.getTranslation("auctionyourbid"),0,bidder.getAccount().getBalance());
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
                            guiController.writeMessage(PropertiesIO.getTranslation("auctionalreadyhighestbid"));
                        }
                    }
                }
                // By now we have went through all the players atleast once.
                if (noMoreRounds)
                    auctionStatus = false; // Auction status changed and thus we get out of the while loop
            }

            //Checks if there is a winner
            if (whoHasTheHighestBid != null) { // We have a winner
                wonAuction(propertyOnAuction);
                guiController.writeMessage(whoHasTheHighestBid.getName() + " "+ PropertiesIO.getTranslation("auctionhighestbid")+" "+ propertyOnAuction.getName());
            } else // No one bid
                guiController.writeMessage(PropertiesIO.getTranslation("auctionnobids"));
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
