package zhaoli.xshiki.com.codered;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class DependentWindow extends AppCompatActivity {

    private String usernamejson;

    public String[]list;
    public String[]idarray;
    private ListView listView;
    private ArrayAdapter<String>adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dependent_window);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null)
        {
            usernamejson=bundle.get("username").toString();
        }

        TextView textview=(TextView)findViewById(R.id.textView2);
        textview.append(" "+usernamejson+".");

        new downloadinfo().execute("");
    }

    private class downloadinfo extends AsyncTask<String,Integer,String>
    {
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
                //http://www.alphabranding.com/codered1/allTasksForUser.php?username=child
                final String webs="http://www.alphabranding.com/codered1/allTasksForUser.php?username="+usernamejson;
                URL url=new URL(webs);
                URI uri=url.toURI();
                String validwebs=uri.toASCIIString();
                URL validURL=new URL(validwebs);

                HttpURLConnection connection=(HttpURLConnection)validURL.openConnection();
                JsonParser parser=new JsonParser();
                JsonElement element=parser.parse(new InputStreamReader((InputStream)connection.getContent()));
                JsonArray array=element.getAsJsonArray();
                //JsonArray array=obj.get();
                list=new String[array.size()];
                idarray=new String[array.size()];

                for(int i=0;i<array.size();i++)
                {
                    JsonObject object=array.get(i).getAsJsonObject();
                    String id=object.get("idtasks").getAsString();
                    String name=object.get("name").getAsString();
                    String description=object.get("description").getAsString();
                    String state=object.get("state").getAsString();
                    String points=object.get("points").getAsString();
                    list[i]=name+" "+description+" "+state+": "+points+" points.";
                    idarray[i]=id;
                }
            }
            catch (Exception e)
            {
                exception=e;
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result)
        {
            //listView=(ListView)findViewById(R.id.listview5);
            //adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,list);
            //listView.setAdapter(adapter);

            if(exception==null)
            {
                if(list!=null)
                {
                    listView=(ListView)findViewById(R.id.listview5);
                    adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.dependentlistview,list);
                    listView.setAdapter(adapter);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    //finish();
                }

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        DialogInterface.OnClickListener dialogClickListener=new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int num)
                            {
                                switch(num)
                                {
                                    case DialogInterface.BUTTON_POSITIVE:
                                    {
                                        try
                                        {
                                            //http://www.alphabranding.com/codered1/updateTask.php?idtasks=1&state=complete
                                            final String webs="http://www.alphabranding.com/codered1/updateTask.php?idtasks=16&state=complete";
                                            URL url=new URL(webs);
                                            URI uri=url.toURI();
                                            String validwebs=uri.toASCIIString();
                                            URL validURL=new URL(validwebs);

                                            HttpURLConnection connection=(HttpURLConnection)validURL.openConnection();
                                            connection.setDoOutput(true);
                                            connection.setRequestMethod("POST");
                                            String message=connection.getResponseMessage();
                                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                        }
                                        catch(Exception e)
                                        {
                                            e.printStackTrace();
                                        }
                                    }
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        break;
                                }
                            }
                        };
                        //DependentWindow.this
                        AlertDialog.Builder builder=new AlertDialog.Builder(DependentWindow.this);
                        builder.setTitle("Confirm");
                        builder.setMessage("Did you finish the task?");
                        builder.setNegativeButton("No",dialogClickListener);
                        builder.setPositiveButton("Yes",dialogClickListener).show();
                    }
                });
            }
        }
    }
}
