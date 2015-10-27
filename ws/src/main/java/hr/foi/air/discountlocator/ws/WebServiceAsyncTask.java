package hr.foi.air.discountlocator.ws;

import android.os.AsyncTask;

public class WebServiceAsyncTask extends AsyncTask <WebServiceParams, Void, AsyncTaskInnerResults> {

    @Override
    protected AsyncTaskInnerResults doInBackground(WebServiceParams[] params) {
        AsyncTaskInnerResults asyncTaskInnerResult = new AsyncTaskInnerResults();

        //implement ws call here

        return asyncTaskInnerResult;
    }

    @Override
    protected void onPostExecute(AsyncTaskInnerResults asyncTaskInnerResults) {
        super.onPostExecute(asyncTaskInnerResults);

        //implement response meta-analysis here

        //implement caller notification on task completed and send results
    }
}
