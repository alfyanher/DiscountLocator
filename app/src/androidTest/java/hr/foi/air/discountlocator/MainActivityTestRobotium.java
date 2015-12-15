package hr.foi.air.discountlocator;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;
import android.widget.TextView;

import com.robotium.solo.Solo;

public class MainActivityTestRobotium extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    public MainActivityTestRobotium() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    public void testWorkflow() {
        solo.waitForActivity(MainActivity.class, 5000);

        //asserting if listView exists
        boolean lvExists = solo.waitForView(R.id.elv_stores_and_discounts); //method one
        assertEquals("ListView containing stores does not exist", lvExists, true);
        ListView lv = (ListView)solo.getView(R.id.elv_stores_and_discounts); //method two
        assertNotNull("ListView containing stores does not exist", lv);

        //selecting store and discounts
        solo.clickOnText("Super Nova");
        solo.clickOnText("30");

        //assert discount details are correct
        TextView detailsDesc = (TextView) solo.getView(R.id.discount_details_description);
        String expected = "For two paid products, the third product (with the lowest price) is given for free in all stores of Super Nova shopping center.";
        String real = detailsDesc.getText().toString();
        assertEquals("Description details are not correct", expected, real);

        //go back
        solo.goBack();
    }

    public void testWorkflowRecorder() {
        solo.waitForActivity(hr.foi.air.discountlocator.MainActivity.class, 2000);

        //checking details of Super Nova discount of 30%
        solo.clickOnText(java.util.regex.Pattern.quote("Super Nova"));
        solo.clickOnText(java.util.regex.Pattern.quote("30"));
        solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));

        //searching for discounts of Armani suits
        solo.clickOnView(solo.getView(android.widget.ImageView.class, 0)); //open menu
        solo.clickInList(1, 0); //click on search icon
        solo.waitForDialogToOpen(5000);
        solo.clickOnView(solo.getView(hr.foi.air.discountlocator.R.id.search_term));
        solo.clearEditText((android.widget.EditText) solo.getView(hr.foi.air.discountlocator.R.id.search_term));
        solo.enterText((android.widget.EditText) solo.getView(hr.foi.air.discountlocator.R.id.search_term), "Armani");
        solo.clickOnView(solo.getView(hr.foi.air.discountlocator.R.id.btnSearchDiscounts));  //click on search button
        solo.clickOnText(java.util.regex.Pattern.quote("Varteks")); //expand found item

        //changing application settings
        solo.clickOnView(solo.getView(android.widget.ImageView.class, 0));
        solo.clickInList(2, 0);  //click settings option
        solo.waitForActivity(hr.foi.air.discountlocator.AppPreferenceActivity.class);
        solo.clickInList(4, 0);  //changing radius
        solo.waitForDialogToOpen(5000);
        solo.clickOnView(solo.getView(android.R.id.text1, 2));  //select 2km item
        solo.goBack();  //close settings

        //open navigation drawer and select maps item
        solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
        solo.clickOnText("Discount Map");
        solo.goBack();

        //close the app
        solo.goBack();

        //The following code is recorder by Robotium Recorder.
        //As it can be seen this code is not very suitable for human reading.
        //Additional asserts can be added where necessary.
    }
}