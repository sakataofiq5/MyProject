package com.sakatoafiq.myandelaproj;

public class ListItem {
    private Double _price;
    private String _from_symbol;
	private String _to_symbol;

    public ListItem() {
        super();
    }
	public Double getPrice() {
        return _price;
    }

    public void setPrice(Double price) {
        this._price = price;
    }

	public String getFromSymbol() {
        return _from_symbol;
    }

    public void setFromSymbol(String from_symbol) {
        this._from_symbol= from_symbol;
    }

    public String getToSymbol() {
        return _to_symbol;
    }

    public void setToSymbol(String to_symbol) {
        this._to_symbol = to_symbol;
    }


}

