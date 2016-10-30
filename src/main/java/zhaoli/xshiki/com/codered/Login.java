package zhaoli.xshiki.com.codered;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class Login extends AppCompatActivity {

    private String usernamestring;
    private String passwordstring;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    public void next(View view)
    {
        new downloadinfo().execute("");
        //Intent intent=new Intent(this,MainWindow.class);
        //startActivity(intent);
    }

    public void signuppage(View view)
    {
        Intent signup=new Intent(this,SignUpActivity.class);
        startActivity(signup);
    }

    private class downloadinfo extends AsyncTask<String,Integer,String>
    {
        EditText username=(EditText)findViewById(R.id.usernameloginedittext);
        EditText password=(EditText)findViewById(R.id.passwordloginedittext);

        String usernamestring=username.getText().toString();
        String passwordstring=password.getText().toString();

        Exception exception=null;
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
                //http://www.alphabranding.com/codered1/userLogin.php?username=test&password=test
                final String webs="http://www.alphabranding.com/codered1/userLogin.php?username="+usernamestring+"&password="+passwordstring;
                URL url=new URL(webs);
                URI uri=url.toURI();
                String validwebs=uri.toASCIIString();
                URL validURL=new URL(validwebs);

                HttpURLConnection connection=(HttpURLConnection)validURL.openConnection();
                JsonParser parser=new JsonParser();
                JsonElement element=parser.parse(new InputStreamReader((InputStream)connection.getContent()));
                JsonObject obj=element.getAsJsonObject();
                String role=obj.get("role").getAsString();
                String usernamejson=obj.get("username").getAsString();
                if(role.equals("admin"))
                {
                    Intent intent=new Intent(getApplicationContext(),MainWindow.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent=new Intent(getApplicationContext(),DependentWindow.class);
                    intent.putExtra("username",usernamejson);
                    startActivity(intent);
                }

                //create intent, save data to local, put into intent bundle
                Intent intentasdf=new Intent(getApplicationContext(),AddingDepedentActivity.class);
                String data="stuff";
                intentasdf.putExtra("data",data);
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
            if(exception!=null)
            {
                Toast.makeText(getApplicationContext(), "Incorrect Username or Password.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
