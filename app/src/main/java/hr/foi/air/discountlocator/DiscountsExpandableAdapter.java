package hr.foi.air.discountlocator;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import hr.foi.air.discountlocator.db.Discount;
import hr.foi.air.discountlocator.db.Store;

public class DiscountsExpandableAdapter extends BaseExpandableListAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<Store> parentItems;
    private ArrayList<Discount> childItems;

    public DiscountsExpandableAdapter(ArrayList<Store> stores,
                                      ArrayList<Discount> discounts)
    {
        this.parentItems = stores;
        this.childItems = discounts;
    }

    public void setInflater(LayoutInflater inflater, Activity activity)
    {
        this.activity = activity;
        this.inflater = inflater;
    }

    @Override
    public int getGroupCount() {
        if (parentItems == null)
            return 0;
        else
            return parentItems.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int size = 0;
        for (Discount discount : childItems)
        {
            if (discount.getStoreId() ==
                    parentItems.get(groupPosition).getRemoteId())
            {
                size++;
            }
        }
        return size;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return parentItems.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childItems.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return ((Store)getGroup(groupPosition)).getRemoteId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return ((Discount)getChild(groupPosition, childPosition)).getRemoteId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.elv_store_item, null);

        TextView nameView = null;
        TextView descView = null;
        ImageView imageView = null;

        nameView = (TextView)convertView.findViewById(R.id.store_name);
        descView = (TextView)convertView.findViewById(R.id.store_description);
        imageView = (ImageView)convertView.findViewById(R.id.logo);

        nameView.setText(((Store) getGroup(groupPosition)).getName());
        descView.setText(((Store) getGroup(groupPosition)).getDescription());
        //imageView... - Homework. Check possible solutions at: http://tiny.cc/air2015vj4hs


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        TextView nameView = null;
        TextView descView = null;
        TextView discountView = null;

        if (convertView == null)
            convertView = inflater.inflate(R.layout.elv_discount_item, null);

        nameView = (TextView)convertView.findViewById(R.id.discount_name);
        descView = (TextView)convertView.findViewById(R.id.discount_description);
        discountView = (TextView)convertView.findViewById(R.id.discount_value);

        Discount childDiscount = null;
        int position = 0;
        for (Discount discount: childItems)
        {
            if (discount.getStoreId() == parentItems.get(groupPosition).getRemoteId())
            {
                if (position == childPosition)
                {
                    childDiscount = discount;
                    break;
                }
                position++;
            }
        }

        nameView.setText(childDiscount.getName());
        descView.setText(childDiscount.getDescription());
        discountView.setText("" + childDiscount.getDiscountValue());

        return convertView;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
