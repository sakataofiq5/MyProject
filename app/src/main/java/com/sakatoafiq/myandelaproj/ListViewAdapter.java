package com.sakatoafiq.myandelaproj;

import java.util.ArrayList;
import android.app.Activity;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;


public class ListViewAdapter extends ArrayAdapter <ListItem> {

    private Context mContext;
    private int layoutResourceId;
    private ArrayList <ListItem> mListData = new ArrayList <ListItem>();

    public ListViewAdapter(Context mContext, int layoutResourceId, ArrayList<ListItem> mListData) {
		super(mContext, layoutResourceId, mListData);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.mListData = mListData;
    }

    public void setListData(ArrayList<ListItem> mListData) {
        this.mListData = mListData;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();

			holder.fromSymbolTextView = (TextView) row.findViewById(R.id.list_item_fromSymbol);
            holder.toSymbolTextView = (TextView) row.findViewById(R.id.list_item_toSymbol);
            holder.priceTextView = (TextView) row.findViewById(R.id.list_item_title);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        ListItem item = mListData.get(position);

		String priceholder = Double.toString(item.getPrice());
		holder.fromSymbolTextView.setText(item.getFromSymbol());
		holder.toSymbolTextView.setText(item.getToSymbol());
        holder.priceTextView.setText(priceholder);


        return row;
    }

    static class ViewHolder {
		TextView fromSymbolTextView;
        TextView toSymbolTextView;
        TextView priceTextView;

    }
}
