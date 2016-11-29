package com.example.hamid.anotherjson;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hamid.anotherjson.MovieModel;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    Button hit;
    String myJsonResult;
    TextView tv_data;
    ListView listview;
    int neww ;
    ProgressDialog progressDialog;

    movieAdapter adapter;
//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView) findViewById(R.id.lv_movies);
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);


//                new myasyn().execute("http://develop-your-app.comlu.com/newjson.php");
//              new myasyn().execute("http://jsonparsing.parseapp.com/jsonData/moviesDemoList.txt");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                new myasyn().execute("http://jsonparsing.parseapp.com/jsonData/moviesData.txt");
            } else {
                Toast.makeText(getBaseContext(), "شما به اینترنت دسترسی ندارید", Toast.LENGTH_SHORT).show();
            }

            return true;
        }if(id==R.id.gotitile){
            startActivity(new Intent(this,SecondActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public class myasyn extends AsyncTask<String, Void, List<MovieModel>> {


        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setIndeterminate(true);

            progressDialog.setMessage("loading...");
            progressDialog.show();
        }

        @Override
        protected List<MovieModel> doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                String finaljson = stringBuilder.toString();
                JSONObject jsonObject = new JSONObject(finaljson);
                JSONArray jsonArray = jsonObject.getJSONArray("movies");

                List<MovieModel> movieModelList = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject realobject = jsonArray.getJSONObject(i);
                    MovieModel movieModel = gson.fromJson(realobject.toString(), MovieModel.class);
//                    MovieModel movieModel = new MovieModel();
//
//                    movieModel.setMovie(realobject.getString("movie"));
//                    movieModel.setYear(realobject.getInt("year"));
//                    movieModel.setRating((float) realobject.getDouble("rating"));
//                    movieModel.setDirector(realobject.getString("director"));
//                    movieModel.setDuration(realobject.getString("duration"));
//                    movieModel.setTagline(realobject.getString("tagline"));
//                    movieModel.setImage(realobject.getString("image"));
//                    movieModel.setStory(realobject.getString("story"));
//                    List<MovieModel.Cast> castlist = new ArrayList<>();
//                    for(int j = 0 ; j<realobject.getJSONArray("cast").length();j++){
//                       ;
//                       MovieModel.Cast cast = new MovieModel.Cast();
//                        cast.setName(realobject.getJSONArray("cast").getJSONObject(j).getString("name"));
//                        castlist.add(cast);
//                    }
//                    movieModel.setCastlist(castlist);
                    movieModelList.add(movieModel);


                }

                bufferedReader.close();
                inputStream.close();
                connection.disconnect();
                return movieModelList;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<MovieModel> resut) {
   if (resut!=null){
       adapter = new movieAdapter(MainActivity.this, R.layout.row, resut);
       listview.setAdapter(adapter);
//            myadapter = new Myadapter(MainActivity.this,R.layout.row,resut);
//            listview.setAdapter(myadapter);
       progressDialog.cancel();
   }else {
       Toast.makeText(getBaseContext(),"khata  dobare talash konid ", Toast.LENGTH_SHORT).show();
       progressDialog.cancel();
   }



        }


    }

    class movieAdapter extends ArrayAdapter {
        List<MovieModel> movieModels;
        int resource;
        Context context;
        LayoutInflater inflater;

        public movieAdapter(Context context, int resource, List<MovieModel> movieModels) {
            super(context, resource, movieModels);
            this.movieModels = movieModels;
            this.resource = resource;
            this.context = context;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Viewholder holder = null;
            if (convertView == null) {
                holder = new Viewholder();
                convertView = inflater.inflate(R.layout.row, null);
                holder.imageIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
                holder.tvMovie = (TextView) convertView.findViewById(R.id.tv_movie);
                holder.tvTagline = (TextView) convertView.findViewById(R.id.tv_tag);
                holder.tvYear = (TextView) convertView.findViewById(R.id.tv_year);
                holder.tvDuration = (TextView) convertView.findViewById(R.id.tv_duration);
                holder.tvDirector = (TextView) convertView.findViewById(R.id.tv_director);
                holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingbar);
                holder.tvCast = (TextView) convertView.findViewById(R.id.tv_cast);
                holder.tvStory = (TextView) convertView.findViewById(R.id.tv_story);

                convertView.setTag(holder);
            } else {
                holder = (Viewholder) convertView.getTag();
            }
            final ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);

            ImageLoader.getInstance().displayImage(movieModels.get(position).getImage(), holder.imageIcon, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    progressBar.setVisibility(View.GONE);

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    progressBar.setVisibility(View.GONE);

                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    progressBar.setVisibility(View.GONE);

                }
            });
            holder.tvMovie.setText(movieModels.get(position).getMovie());
            holder.tvTagline.setText(movieModels.get(position).getTagline());
            holder.tvYear.setText("year :  " + movieModels.get(position).getYear());
            holder.tvDuration.setText(movieModels.get(position).getDuration());
            holder.tvDirector.setText(movieModels.get(position).getDirector());
            StringBuffer stringBuffer = new StringBuffer();
            //TODO ye done mesal az in loopa ke befahmim che shod
//        String[] values = new String[3];
//        values[0] = "Dot";
//        values[1] = "Net";
//        values[2] = "Perls";
//
//        for (String value : values) {
//            System.out.println(value);
//        }
//    }
// }
//
            // Output
            //
//         Dot
            // Net
            // Perls
            for (MovieModel.Cast cast : movieModels.get(position).getCastlist()) {
                stringBuffer.append(cast.getName() + " , ");
            }
            holder.tvCast.setText(stringBuffer.toString());
            holder.tvStory.setText(movieModels.get(position).getStory());
            holder.ratingBar.setRating(movieModels.get(position).getRating() / 2);

            return convertView;
        }

        class Viewholder {
            private ImageView imageIcon;
            private TextView tvMovie;
            private TextView tvTagline;
            private TextView tvYear;
            private TextView tvDuration;
            private TextView tvDirector;
            private RatingBar ratingBar;
            private TextView tvCast;
            private TextView tvStory;

        }
    }
}






