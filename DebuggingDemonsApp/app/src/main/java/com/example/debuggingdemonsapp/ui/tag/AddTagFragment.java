package com.example.debuggingdemonsapp.ui.tag;

import com.example.debuggingdemonsapp.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.debuggingdemonsapp.model.Tag;

/**
 * This is a class that allows the user to define a new Tag
 */
public class AddTagFragment extends DialogFragment {
    private EditText editTagName;
    private OnFragmentInteractionListener listener;

    /**
     * This is an interface that ensures a listener that implements this interface
     * will execute onOkPressed() when necessary
     */
    public interface OnFragmentInteractionListener {
        void onOkPressed(Tag tag);
    }

    /**
     * This attaches the AddTagFragment to a given Context
     * @param context
     *     Context to which the AddTagFragment should be attached
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (OnFragmentInteractionListener) getParentFragment();
        assert listener != null;
    }

    /**
     * This creates a Dialog that displays an EditText for the user to enter a Tag name
     * @param savedInstanceState
     *     The last saved instance state of the Fragment, or null if
     *     this is a freshly created Fragment
     *
     * @return
     *     Returns Dialog with EditText
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_add_tag, null);
        editTagName = view.findViewById(R.id.tag_name_editText);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // return a Dialog
        return builder
                .setView(view)
                .setTitle("Add Tag")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    // pass new Tag with given name in EditText to TagFragment when "OK" is clicked
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String tagName = editTagName.getText().toString();

                        if (tagName.length() > 0) {
                            listener.onOkPressed(new Tag(tagName));
                        }
                    }
                }).create();
    }
}