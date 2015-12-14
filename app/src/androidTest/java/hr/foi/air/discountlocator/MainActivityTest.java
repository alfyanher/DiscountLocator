package hr.foi.air.discountlocator;


import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import hr.foi.air.discountlocator.db.Store;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Activity activity;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        activity.finish();
        super.tearDown();
    }

    @SmallTest
    public void testRun() {
        //asserting if listView exists
        ListView lv = (ListView)activity.findViewById(R.id.elv_stores_and_discounts);
        assertNotNull("ListView containing stores does not exist", lv);
        assertEquals("Number of enumerated stores is not 2", 2, lv.getCount());

        //asserting first store object from listView
        Store s = (Store)lv.getAdapter().getItem(0);
        assertNotNull("Store object at index 0 does not exist", s);
        assertEquals("Unexpected object at index 0", "Super Nova", s.getName().toString());

        //aserting second store object from listView (different test approach)
        View child = lv.getChildAt(1);
        TextView name = (TextView)child.findViewById(R.id.store_name);
        String strName = name.getText().toString();
        assertEquals("Store name is not correct", "Varteks", strName);


        //So writing these tests is very non practical and time consuming.
        //Some of the problems are:
        // - how to be sure that activity is opened
        // - how to be sure that data is loaded from the ws or database
        // - how to be sure that there is any data to display
        //There must be another way...
    }
}
