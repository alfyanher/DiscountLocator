package hr.foi.air.discountlocator;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import hr.foi.air.discountlocator.loaders.SearchDataLoader;

public class SearchDialog extends Dialog implements android.view.View.OnClickListener{

    private Context context;
    private Button btnSearch;

    public SearchDialog(Context context) {
        super(context);
        this.context = context;
        this.setTitle(R.string.search_dialog_title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_dialog);
        btnSearch = (Button) findViewById(R.id.btnSearchDiscounts);
        btnSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        EditText text = (EditText) findViewById(R.id.search_term);

        SearchDataLoader dl = new SearchDataLoader();
        dl.LoadData((Activity) context);
        String searchText = text.getText().toString();
        if(searchText.trim().equals(""))
            dl.searchData(" ");
        else
            dl.searchData(text.getText().toString().toLowerCase());

        this.dismiss();
    }
}