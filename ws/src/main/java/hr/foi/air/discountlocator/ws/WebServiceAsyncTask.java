package hr.foi.air.discountlocator.ws;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebServiceAsyncTask extends AsyncTask <WebServiceParams, Void, AsyncTaskInnerResults> {

    @Override
    protected AsyncTaskInnerResults doInBackground(WebServiceParams[] params) {
        AsyncTaskInnerResults asyncTaskInnerResult = new AsyncTaskInnerResults();

        asyncTaskInnerResult.wsResult = "";

        String url = "http://cortex.foi.hr/mtl/courses/air/"
                + params[0].serviceName + ".php";

        String urlParameters = "";
        urlParameters += "method=" + params[0].methodName;
        urlParameters += "&" + "json=" + params[0].jsonParams;
        byte[] postData = urlParameters.getBytes();

        HttpURLConnection c = null;

        try {
            c = (HttpURLConnection)(new URL(url)).openConnection();
            c.setDoOutput(true);
            c.setDoInput(true);
            c.setUseCaches(false);
            c.setRequestMethod("POST");
            c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            c.setRequestProperty("charset", "utf-8");
            c.setRequestProperty("Content-Length", ""+postData.length);

            //sending post parameters
            DataOutputStream dw = new DataOutputStream(c.getOutputStream());
            dw.write(postData);

            //reading web service response
            InputStream is = new BufferedInputStream(c.getInputStream());
            BufferedReader dr = new BufferedReader(new InputStreamReader(is));
            String line = "";
            while ((line = dr.readLine()) != null)
            {
                asyncTaskInnerResult.wsResult += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (c != null)
                c.disconnect();
        }

        asyncTaskInnerResult.handler = params[0].resultHandler;
        asyncTaskInnerResult.targetAttribute = params[0].targetAttribute;

        return asyncTaskInnerResult;
    }

    @Override
    protected void onPostExecute(AsyncTaskInnerResults asyncTaskInnerResults) {
        super.onPostExecute(asyncTaskInnerResults);

        //implement response meta-analysis here

        //implement caller notification on task completed and send results
    }
}
