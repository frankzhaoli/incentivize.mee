package zhaoli.xshiki.com.codered;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Sign Up");
    }

    public void saveinfo(View view)
    {
        new signup().execute("");
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

    private class signup extends AsyncTask<String,Integer,String>
    {
        EditText firstname=(EditText)findViewById(R.id.firstnameedittext);
        EditText lastname=(EditText)findViewById(R.id.lastnameedittext);
        EditText username=(EditText)findViewById(R.id.adusedittext);
        EditText password=(EditText)findViewById(R.id.passwordeditext);
        EditText password2=(EditText)findViewById(R.id.passwordedit2);

        String firstnamestring=firstname.getText().toString();
        String lastnamestring=lastname.getText().toString();

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

        String test="";
        String message;
        @Override
        protected String doInBackground(String...params)
        {
            try
            {
                if(passwordstring.equals(passwordstring2))
                {
                    //http://www.alphabranding.com/codered1/insertUser.php?username=testuser&password=testuser&role=admin
                    final String webs="http://www.alphabranding.com/codered1/insertUser.php?username="+usernamestring+"&password="+passwordstring+"&role=admin";

                    URL url=new URL(webs);
                    URI uri=url.toURI();
                    String validwebs=uri.toASCIIString();
                    URL validURL=new URL(validwebs);

                    HttpURLConnection connection=(HttpURLConnection)validURL.openConnection();
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    message=connection.getResponseMessage();
                }
                /*
                if(role.equals("admin"))
                {
                    Intent intent=new Intent(getApplicationContext(),MainWindow.class);
                    startActivity(intent);
                }
                */
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
                Toast.makeText(getApplicationContext(), "Submission failed. Please try a different username.", Toast.LENGTH_SHORT).show();

            else if(passwordstring.equals(passwordstring2))
            {
                Toast.makeText(getApplicationContext(), "Submission failed. Please try a different username.", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),MainWindow.class);
                startActivity(intent);
            }

        }
    }

}
