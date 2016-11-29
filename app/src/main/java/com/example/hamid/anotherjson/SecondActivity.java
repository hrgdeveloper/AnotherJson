package com.example.hamid.anotherjson;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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


public class SecondActivity extends ActionBarActivity {
  ListView listView;
    myDatabase mydata;
    ArrayList<MovieModel> listitems;
    ProgressDialog progressDialog;
    movieAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
        mydata=new myDatabase(this);
        listView=(ListView)findViewById(R.id.listSecond);
      listitems=mydata.ReturnAll();
        if (listitems==null){
            Toast.makeText(getBaseContext(),"nothing found",Toast.LENGTH_SHORT).show();
        }else {
           adapter= new movieAdapter(this,R.layout.row,listitems);
            listView.setAdapter(adapter);
        }


    }
    public class myasyn extends AsyncTask<String, Void, Void> {


        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(SecondActivity.this);
            progressDialog.setIndeterminate(true);

            progressDialog.setMessage("loading...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {

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
//                    MovieModel movieModel = gson.fromJson(realobject.toString(), MovieModel.class);
//                    MovieModel movieModel = new MovieModel();
//
//                    movieModel.setMovie(realobject.getString("movie"));
//                    movieModel.setYear(realobject.getInt("year"));
//                    movieModel.setRating(realobject.getInt("rating"));
//                    movieModel.setDirector(realobject.getString("director"));
//                    movieModel.setDuration(realobject.getString("duration"));
//                    movieModel.setTagline(realobject.getString("tagline"));
//                    movieModel.setImage(realobject.getString("image"));
//                    movieModel.setStory(realobject.getString("story"));

                    String movie = realobject.getString("movie");
                    if (listitems!=null){
                        if(movie.equals(listitems.get(i).getMovie())){
                            Log.e("hamididid","hastt");
                        }
                    }else{
                        int year = realobject.getInt("year");
                        int rating = realobject.getInt("rating");
                        String director = realobject.getString("director");
                        String duration = realobject.getString("duration");
                        String tagline = realobject.getString("tagline");
                        String image = realobject.getString("image");
                        String story = realobject.getString("story");
                        mydata.INSERTDTA(year, duration, director, image, rating, story, movie, tagline);
                    }




                }

                bufferedReader.close();
                inputStream.close();
                connection.disconnect();
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
        protected void onPostExecute(Void resut) {
            listitems=mydata.ReturnAll();
            adapter= new movieAdapter(SecondActivity.this,R.layout.row,listitems);
            listView.setAdapter(adapter);
            progressDialog.dismiss();
            super.onProgressUpdate(resut);


        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            new myasyn().execute("http://jsonparsing.parseapp.com/jsonData/moviesData.txt");
            return true;
        }if (id==R.id.check_title){
            Toast.makeText(getBaseContext(),String.valueOf(listitems.get(0).getRating()),Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
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
            private TextView tvStory;

        }
    }

}
