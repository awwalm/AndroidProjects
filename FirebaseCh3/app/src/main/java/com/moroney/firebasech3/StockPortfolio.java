package com.moroney.firebasech3;


import java.util.ArrayList;

// creates a basic data class containing three strings, with a constructor to initialize them
public class StockPortfolio
{
    public String portfolioName;
    public String portfolioOwner;
    public String portfolioContact;

    public ArrayList<Stock> portfolioHoldings;

    public StockPortfolio(){    };

    public StockPortfolio(String name, String owner, String contact)
    {
        this.portfolioName = name;
        this.portfolioOwner = owner;
        this.portfolioContact = contact;
    }

}
