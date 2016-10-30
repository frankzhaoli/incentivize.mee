package zhaoli.xshiki.com.codered;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class AddingDepedentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_depedent);
        setTitle("Add a Dependent");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void click(View view)
    {
        new downloadinfo().execute("");
    }

    private class downloadinfo extends AsyncTask<String,Integer,String>
    {
        EditText username=(EditText)findViewById(R.id.adusedittext);
        EditText password=(EditText)findViewById(R.id.adpwedittext);
        EditText password2=(EditText)findViewById(R.id.adpwedittext2);

        String usernamestring=username.getText().toString();
        String passwordstring=password.getText().toString();
        String passwordstring2=password2.getText().toString();

        Exception exception=null;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_SHORT).show();
        }

        String message;
        @Override
        protected String doInBackground(String...params)
        {
            try
            {
                if(passwordstring.equals(passwordstring2))
                {
                    //http://www.alphabranding.com/codered1/insertUser.php?username=testuser&password=testuser&role=admin
                    final String webs="http://www.alphabranding.com/codered1/insertUser.php?username="+usernamestring+"&password="+passwordstring+"&role=dependent";
                    URL url=new URL(webs);
                    URI uri=url.toURI();
                    String validwebs=uri.toASCIIString();
                    URL validURL=new URL(validwebs);

                    HttpURLConnection connection=(HttpURLConnection)validURL.openConnection();
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    message=connection.getResponseMessage();
                }
            }
            catch (Exception e)
            {
                exception=e;
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result)
        {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            if(exception!=null)
            {
                Toast.makeText(getApplicationContext(), "Incorrect Username or Password.", Toast.LENGTH_SHORT).show();
            }

            if(passwordstring.equals(passwordstring2))
            {
                Intent intent=new Intent(getApplicationContext(),MainWindow.class);
                startActivity(intent);
            }
        }
    }
}
