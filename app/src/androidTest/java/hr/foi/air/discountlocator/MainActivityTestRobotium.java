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
}