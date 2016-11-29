//package com.example.hamid.anotherjson;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.RatingBar;
//import android.widget.TextView;
//
//import java.util.List;
//
///**
// * Created by hamid on 12/11/2015.
// */
//public class Myadapter extends BaseAdapter {
//    List<MovieModel> movieModels;
//    Context context;
//    int resource;
//    LayoutInflater inflater;
//    public Myadapter(Context context,int resource, List<MovieModel> movieModels){
//        this.movieModels=movieModels;
//        this.context=context;
//        this.resource=resource;
//        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//
//    }
//
//    @Override
//    public int getCount() {
//        return movieModels.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return movieModels.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if(convertView==null){
//            convertView= inflater.inflate(resource,null);
//
//        }
//        ImageView imageIcon =(ImageView)convertView.findViewById(R.id.iv_icon);
//        TextView tvMovie=(TextView)convertView.findViewById(R.id.tv_movie);
//        TextView tvTagline=(TextView)convertView.findViewById(R.id.tv_tag);
//        TextView tvYear=(TextView)convertView.findViewById(R.id.tv_year);
//        TextView tvDuration=(TextView)convertView.findViewById(R.id.tv_duration);
//        TextView tvDirector=(TextView)convertView.findViewById(R.id.tv_director);
//        RatingBar ratingBar=(RatingBar)convertView.findViewById(R.id.ratingbar);
//        TextView tvCast=(TextView)convertView.findViewById(R.id.tv_cast);
//        TextView tvStory=(TextView)convertView.findViewById(R.id.tv_story);
//        tvMovie.setText(movieModels.get(position).getMovie());
//        tvTagline.setText(movieModels.get(position).getTagline());
//        tvYear.setText("year :  " + movieModels.get(position).getYear());
//        tvDuration.setText(movieModels.get(position).getDuration());
//        tvDirector.setText(movieModels.get(position).getDirector());
//        StringBuffer stringBuffer = new StringBuffer();
//        for(MovieModel.Cast cast : movieModels.get(position).getCastlist()){
//            stringBuffer.append(cast.getName()+ " , ");
//        }
//        tvCast.setText(stringBuffer.toString());
//        tvStory.setText(movieModels.get(position).getStory());
//        ratingBar.setRating(movieModels.get(position).getRating()/2);
//
//        return convertView;
//    }
//}
