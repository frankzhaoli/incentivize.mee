package zhaoli.xshiki.com.codered;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;

public class MainWindow extends AppCompatActivity {

    public String []dependentarray={"John","Jane","Doe","+ Add Dependent"};

    private ListView listView;
    private ArrayAdapter<String>arrayAdapter;

    //final Intent addingdependentintent=new Intent(this,AddingDepedentActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);
        setTitle("Incentiveize me");

        if(dependentarray!=null)
        {
            //Arrays.sort(dependentarray);
            listView=(ListView)findViewById(R.id.dependentlistview);
            arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.dependentlistview,dependentarray);
            listView.setAdapter(arrayAdapter);
        }

        //finds last element for new intent to add new dependent

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(dependentarray.length-1==position)
                {
                    Intent addingdependentintent=new Intent(getApplicationContext(), AddingDepedentActivity.class);
                    startActivity(addingdependentintent);
                }
                else
                {
                    Intent addingtask=new Intent((getApplicationContext()), CreateTaskPage.class);
                    startActivity(addingtask);
                }
            }
        });
    }

    private class downloadinfo extends AsyncTask<String,Integer,String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String...params)
        {
            try
            {
                final String webs="link.com";
                URL url=new URL(webs);
                URI uri=url.toURI();
                String validwebs=uri.toASCIIString();
                URL validURL=new URL(validwebs);

                HttpURLConnection connection=(HttpURLConnection)validURL.openConnection();
                JsonParser parser=new JsonParser();
                JsonElement element=parser.parse(new InputStreamReader((InputStream)connection.getContent()));
                JsonObject obj=element.getAsJsonObject();

                //create intent, save data to local, put into intent bundle
                Intent intentasdf=new Intent(getApplicationContext(),AddingDepedentActivity.class);
                String data="stuff";
                intentasdf.putExtra("data",data);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result)
        {

        }
    }
}
