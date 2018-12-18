package org.uwcchina.uwccscvrexperience;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class CVideoAdapter extends ArrayAdapter {
    private final Activity context;
    private List<CVideo> cVideos;
    private List<Bitmap> thumbs;

    CVideoAdapter(Activity context, List<CVideo> cVideos, List<Bitmap> thumbs) {
        super(context, R.layout.video_list_item, cVideos);

        this.context = context;
        this.cVideos = cVideos;
        this.thumbs = thumbs;
    }

    @NonNull
    public View getView(int position, View view, final ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.video_list_item, null, true);

        ConstraintLayout videoListItemContainer = rowView.findViewById(R.id.videoListItemContainer);
        TextView nameTextView = rowView.findViewById(R.id.video_item_title);
        TextView descriptionTextView = rowView.findViewById(R.id.videoItemDescription);
        ImageButton nextButton = rowView.findViewById(R.id.videoItemNextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, CVideoActivity.class));
            }
        });

        if (thumbs != null && thumbs.size() != 0 && thumbs.get(position) != null) {
            videoListItemContainer.setBackground(new BitmapDrawable(rowView.getResources(),
                    thumbs.get(position)));
        }
        nameTextView.setText(cVideos.get(position).getTitle());
        descriptionTextView.setText(cVideos.get(position).getDescription());

        return rowView;
    }

    public void refreshCVideos(List<CVideo> cVideos) {
        this.cVideos.clear();
        this.cVideos.addAll(cVideos);
        notifyDataSetChanged();
    }
}
