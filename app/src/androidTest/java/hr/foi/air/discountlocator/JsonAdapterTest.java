package hr.foi.air.discountlocator;

import android.test.ActivityInstrumentationTestCase2;

import java.util.List;

import hr.foi.air.discountlocator.core.JsonAdapter;
import hr.foi.air.discountlocator.db.Store;

public class JsonAdapterTest extends ActivityInstrumentationTestCase2<MainActivity> {
    MainActivity mainActivity;
    public JsonAdapterTest()
    {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mainActivity = getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        mainActivity.finish();
        super.tearDown();
    }

    public void testJsonAdapterStores() {
        List<Store> resultEmptyString = JsonAdapter.getStores("");
        List<Store> resultEmptyObject = JsonAdapter.getStores("{}");
        List<Store> resultEmptyArray = JsonAdapter.getStores("[]");
        List<Store> resultWrongObject = JsonAdapter.getStores("{'test':'1'}");
        //...write other test cases here
        List<Store> resultNormal = JsonAdapter.getStores("[{'id':'1','name':'Super Nova','description':'Veliko blagdansko sni\\u017eenje u odabranim du\\u0107anima u centru Super Nova.','imgUrl':'http:\\/\\/cortex.foi.hr\\/mtl\\/courses\\/air\\/img\\/slika1.png','longitude':'16307138','latitude':'46319970'},{'id':'2','name':'Varteks','description':'Popust na sve kolekcije ljetne odje\\u0107e u Varteks Outlet du\\u0107anu.','imgUrl':'http:\\/\\/cortex.foi.hr\\/mtl\\/courses\\/air\\/img\\/slika2.png','longitude':'16343460','latitude':'46292420'}]");


        assertNull(resultEmptyString);      //empty string
        assertNull(resultEmptyObject);      //empty object
        assertNotNull(resultEmptyArray);            //empty array should return new empty array
        assertEquals(resultEmptyArray.size(), 0);
        assertNotNull(resultWrongObject);           //wrong array should return new empty array
        assertEquals(resultWrongObject.size(), 0);

        assertEquals(resultNormal.size(), 2);       //normal data should return new array with stores
        assertEquals(resultNormal.get(0).getName().equals("Super Nova"), true);

        //The software architect should define the results for all test cases.
        //The TDD means that developer will get these failing tests and will create getStores method to pass all test.
    }
}
