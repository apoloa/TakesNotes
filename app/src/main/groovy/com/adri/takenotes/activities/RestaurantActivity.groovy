package com.adri.takenotes.activities

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import com.adri.takenotes.R
import com.adri.takenotes.fragment.NewTableClientFragment
import com.adri.takenotes.model.ClientTable
import com.adri.takenotes.model.Dish
import com.adri.takenotes.model.Restaurant
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectView
import com.arasthel.swissknife.annotations.OnClick
import com.arasthel.swissknife.annotations.OnItemClick
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.lang.ref.WeakReference

class RestaurantActivity extends AppCompatActivity implements NewTableClientFragment.OnTableClientAddedListener {

    final String TAG = RestaurantActivity.class.getCanonicalName()
    final int ACTIVITY = 1234
    private Restaurant restaurant
    ArrayAdapter<ClientTable> adapter

    @InjectView(R.id.list_table_clients)
    ListView tableClients

    @OnClick(R.id.button_add_client)
    void addNewTableClient() {
        startNewTableDialog()
    }

    @OnItemClick(R.id.list_table_clients)
    void tableClientsClick(int position) {
        ClientTable table = restaurant.getTables().get(position)
        startDetailTableClient(table)
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)
        SwissKnife.inject(this)

        restaurant = Restaurant.getInstance()

        adapter = new ArrayAdapter<ClientTable>(
                this,
                android.R.layout.simple_list_item_1,
                restaurant.tables)

        tableClients.setAdapter(adapter)
        AsyncTask<Void, Integer, String> menuDownloader = new MenuDownloader()
        menuDownloader.execute()
    }

    void startNewTableDialog() {
        NewTableClientFragment dialog = new NewTableClientFragment()
        dialog.setListener(this)
        dialog.show(getFragmentManager(), null)
    }

    void startDetailTableClient(ClientTable table) {
        Intent tableClientIntent = new Intent(this, TableClientActivity.class)
        tableClientIntent.putExtra(TableClientActivity.EXTRA_TABLE, table.numberTable)
        startActivityForResult(tableClientIntent, ACTIVITY)
    }

    @Override
    void onTableAddedListener(String name) {
        restaurant.addNewTable(name)
        adapter.notifyDataSetChanged()
        Log.d(TAG, "Added new Table")
    }


    @Override
    void onTableCancelListener() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ACTIVITY) {
            if (resultCode == RESULT_OK){
                int code = data.getIntExtra(TableClientActivity.EXTRA_TABLE, 0)
                ClientTable table = Restaurant.getInstance().getTable(code)
                restaurant.tables.remove(table)
                adapter.notifyDataSetChanged()
            }

        }
    }

    private class MenuDownloader extends AsyncTask<Object, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute()

        }

        @Override
        protected String doInBackground(Object[] params) {
            URL url;
            InputStream input = null
            try {
                url = new URL(Restaurant.URL_PATH)
                HttpURLConnection con = (HttpURLConnection) (url.openConnection())
                con.connect();
                int responseLength = con.getContentLength();
                byte[] data = new byte[1024]
                long currentBytes = 0;
                int downloadedBytes;
                input = con.getInputStream()
                StringBuilder sb = new StringBuilder();
                while ((downloadedBytes = input.read(data)) != -1) {
                    if (isCancelled()) {
                        input.close()
                        return null
                    }

                    sb.append(new String(data, 0, downloadedBytes))
                    if (responseLength > 0) {
                        currentBytes += downloadedBytes
                    }
                }
                return sb.toString()
            } catch (Exception e) {
                e.printStackTrace()
            }
            finally {
                if (input != null) {
                    try {
                        input.close()
                    } catch (IOException e) {
                        e.printStackTrace()
                    }
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);
            try {
                JSONObject jsonRoot = new JSONObject(data);
                JSONArray dishes = jsonRoot.getJSONArray("items");

                for (int i = 0; i < dishes.length(); i++) {
                    JSONObject dishObject = dishes.getJSONObject(i);
                    int id = dishObject.getInt("id")
                    String name = dishObject.getString("name")
                    String description = dishObject.getString("description")
                    String url = dishObject.getString("image")
                    float price = Float.valueOf(dishObject.getString("price"));
                    JSONArray jsonAllergens = dishObject.getJSONArray("allergens");
                    LinkedList<String> allergensList = new LinkedList<>()
                    for (int j = 0; j < jsonAllergens.length(); j++) {
                        allergensList.add(jsonAllergens.getString(j));
                    }

                    Dish dish = new Dish(id, name, description, price, 0, allergensList, "", url)

                    dish.addAllergens(allergensList)
                    Restaurant.getInstance().dishes.add(dish)
                }
                Snackbar.make(findViewById(android.R.id.content), "Menu descargado", Snackbar.LENGTH_LONG).show();
                Restaurant.getInstance().downloadedMenu = true;
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }
}
