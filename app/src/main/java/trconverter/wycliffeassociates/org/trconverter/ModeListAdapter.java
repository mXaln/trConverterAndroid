package trconverter.wycliffeassociates.org.trconverter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.wycliffeassociates.trConverter.Mode;

import java.util.List;

public class ModeListAdapter extends BaseAdapter {
    Activity mainActivity;
    List<Mode> modes;
    LayoutInflater layoutInflater;

    public ModeListAdapter(Activity activity, List<Mode> modes) {
        this.mainActivity = activity;
        this.modes = modes;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return modes.size();
    }

    @Override
    public Object getItem(int position) {
        return modes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return modes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public boolean isEnabled(int position) {
        return super.isEnabled(position);
    }

    private static class ViewHolder {
        TextView projectText;
        RadioGroup radioGroup;
        RadioButton verseButton;
        RadioButton chunkButton;

        Boolean isEditable = null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Mode item = modes.get(position);
        Boolean empty = item.mode.isEmpty();

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.mode_list_cell, null);
            viewHolder.projectText = convertView.findViewById(R.id.projectTextView);
            viewHolder.radioGroup = convertView.findViewById(R.id.radioGroup);
            viewHolder.verseButton = convertView.findViewById(R.id.verseRadio);
            viewHolder.chunkButton = convertView.findViewById(R.id.chunkRadio);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (viewHolder.isEditable == null) {
            viewHolder.isEditable = empty;
        }

        viewHolder.projectText.setText(item.projectName);
        if (!viewHolder.isEditable) {
            viewHolder.projectText.setTextColor(Color.GRAY);
        } else {
            viewHolder.projectText.setTextColor(empty ? Color.RED : Color.BLACK);
            viewHolder.projectText.setTypeface(null, Typeface.BOLD);
        }

        viewHolder.verseButton.setChecked(item.mode.equals("verse"));
        if (!viewHolder.isEditable) {
            viewHolder.verseButton.setEnabled(false);
        }

        viewHolder.chunkButton.setChecked(item.mode.equals("chunk"));
        if (!viewHolder.isEditable) {
            viewHolder.chunkButton.setEnabled(false);
        }

        if (viewHolder.isEditable) {
            viewHolder.verseButton.setOnClickListener((View v) -> {
                item.mode = "verse";
                viewHolder.projectText.setTextColor(Color.BLACK);
            });

            viewHolder.chunkButton.setOnClickListener((View v) -> {
                item.mode = "chunk";
                viewHolder.projectText.setTextColor(Color.BLACK);
            });
        }

        return convertView;
    }
}
