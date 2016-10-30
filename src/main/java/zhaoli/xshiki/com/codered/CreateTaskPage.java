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

public class CreateTaskPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task_page);
        setTitle("Create a New Task");
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
        EditText dependentusername=(EditText)findViewById(R.id.dnedittext);
        EditText taskname=(EditText)findViewById(R.id.tnedittext);
        EditText description=(EditText)findViewById(R.id.desedittext);
        EditText points=(EditText)findViewById(R.id.pointsedittext);

        String dependentstring=dependentusername.getText().toString();
        String tasknamestring=taskname.getText().toString();
        String descriptionstring=description.getText().toString();
        String numofpoints=points.getText().toString();

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
                    //http://www.alphabranding.com/codered1/insertTask.php?username=child&taskname=write&description=writing&state=pending&points=10
                    final String webs="http://www.alphabranding.com/codered1/insertTask.php?username="+dependentstring+"&taskname="+tasknamestring+"&description="+descriptionstring+"&state=pending&points="+numofpoints;
                    URL url=new URL(webs);
                    URI uri=url.toURI();
                    String validwebs=uri.toASCIIString();
                    URL validURL=new URL(validwebs);

                    HttpURLConnection connection=(HttpURLConnection)validURL.openConnection();
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    message=connection.getResponseMessage();

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
                Toast.makeText(getApplicationContext(), "Task not saved."+message+exception, Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Task Created.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
