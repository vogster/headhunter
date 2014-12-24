package com.unlogicon.headhunter.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import com.androidquery.AQuery;
import com.unlogicon.headhunter.R;

import java.util.ArrayList;
import java.util.Map;

import static com.unlogicon.headhunter.Utils.getHashMapResource;

/**
 * Created by nik on 08.10.14.
 */
public class DialogFragmentSelect extends DialogFragment {

    private AQuery aq;

    private ArrayList<String> selectedItems = new ArrayList<String>();
    private ArrayList<String> items = new ArrayList<String>();
    private ArrayList<Integer> selectedPosition = new ArrayList<Integer>();
    private Map map;
    private int count;
    private boolean[] checkedItems;

    public static final String KEY_POSITION = "position";

    public static DialogFragmentSelect newInstance(ArrayList<Integer> position) {
        DialogFragmentSelect frag = new DialogFragmentSelect();
        Bundle args = new Bundle();
        args.putIntegerArrayList(KEY_POSITION, position);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        aq = new AQuery(getActivity());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        items.clear();
        selectedPosition.clear();
        count = 0;


        Tags tags = Tags.valueOf(getTag());
        switch (tags) {
            case EMPLOYMENT:
                builder.setTitle(getActivity().getString(R.string.employment));
                map = getHashMapResource(getActivity(), R.xml.employment, "employment");
                for (Object key : map.keySet()) {
                    items.add(map.get(key).toString());
                }
                count = items.size();
                checkedItems  = new boolean[count];
                for (Integer i : getArguments().getIntegerArrayList(KEY_POSITION)) {
                    checkedItems[i] = true;
                }

                builder.setMultiChoiceItems(items.toArray(new String[items.size()]), checkedItems, new MultiChoiceItems());
                break;
            case SCHEDULE:
                builder.setTitle(getActivity().getString(R.string.schedule));
                map = getHashMapResource(getActivity(), R.xml.schedule, "schedule");
                for (Object key : map.keySet()) {
                    items.add(map.get(key).toString());
                }
                count = items.size();
                checkedItems  = new boolean[count];
                for (Integer i : getArguments().getIntegerArrayList(KEY_POSITION)) {
                    checkedItems[i] = true;
                }

                builder.setMultiChoiceItems(items.toArray(new String[items.size()]), checkedItems, new MultiChoiceItems());
                break;
        }


        builder.setPositiveButton(getActivity().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent();
                intent.putExtra(FragmentSearch.KEY_SELECTED, selectedPosition);
                for (int i = 0; i < checkedItems.length; i++) {
                    if (checkedItems[i]){
                        selectedPosition.add(i);
                    }
                }
                getTargetFragment().onActivityResult(getTargetRequestCode(), getActivity().RESULT_OK, intent);

            }
        });
        builder.setNegativeButton(getActivity().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Nothing =(
            }
        });
        return builder.create();
    }

    public enum Tags {
        EMPLOYMENT, SCHEDULE
    }

    public class MultiChoiceItems implements DialogInterface.OnMultiChoiceClickListener{

        @Override
        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
            if (isChecked) {
                    selectedItems.add(items.get(which));
            } else {
                    selectedItems.remove(items.get(which));
            }
        }
    }

}