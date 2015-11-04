package hr.foi.air.discountlocator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

        convertView.setOnClickListener(onClickListener);
        convertView.setTag(new Object[]{childDiscount.getRemoteId(), groupPosition});
        convertView.setOnLongClickListener(onLongClickListener);

        return convertView;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent discountActivityIntent = new Intent(activity, DiscountDetailsActivity.class);
            Object[] tagData = (Object[]) v.getTag();
            discountActivityIntent.putExtra("id", (Long) tagData[0]);
            activity.startActivity(discountActivityIntent);
        }
    };

    private View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(final View view) {
            // create Yes/No dialog
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
            dialogBuilder.setMessage(activity.getResources().getString(R.string.q_delete_discount))
                    .setPositiveButton(activity.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:

                                    // remove child element from the list, and delete it from database
                                    Object[] tagData = (Object[]) view.getTag();

                                    Discount discountToDelete = null;
                                    for (Discount d : childItems) {
                                        if(d.getRemoteId() == (Long) tagData[0]){
                                            discountToDelete = d;
                                        }
                                    }
                                    childItems.remove(discountToDelete);
                                    discountToDelete.delete();

                                    // if there are no more discounts, delete parent store
                                    // (remove from list and delete from database)

                                    if(getChildrenCount((Integer)tagData[1]) == 0){
                                        Store s = ((Store)getGroup((Integer)tagData[1]));
                                        if(s.discounts().size() == 0){
                                            s.delete();
                                        }
                                        parentItems.remove(getGroup((Integer)tagData[1]));

                                    }

                                    // propagate changes
                                    notifyDataSetChanged();
                                    break;
                            }
                        }
                    })
                    .setNegativeButton(activity.getResources().getString(R.string.no), null)
                    .show();

            // return true to say "event handled"
            return true;
        }
    };

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
