package com.meier.fragmentlifecycles;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MySkeletonFragment extends Fragment
{
    public MySkeletonFragment()
    {
        // Required empty public constructor
    }

    //----------------------------------------------------------------------

    // Called when the Fragment is attached to its parent Activity.
    //@Override
    public void onAttach(Context context)
    {
        super.onAttach((Activity) context);
        // Get a reference to a Context representing
        // the parent component.
    }

    // Called to do the initial creation of the Fragment.
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Initialize the Fragment.
    }

    // Called once the Fragment has been created in order for it to
    // create its user interface.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Create, or inflate the Fragment's UI, and return it.
        // If this Fragment has no UI then return null.
        return inflater.inflate(R.layout.my_skeleton_fragment_layout,
                container, false);
    }

    // Called once the parent Activity and the Fragment's UI have
    // been created.
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        // Complete the Fragment initialization – particularly anything
        // that requires the parent Activity to be initialized or the
        // Fragment's view to be fully inflated.
    }

    // Called at the start of the visible lifetime.
    @Override
    public void onStart()
    {
        super.onStart();
        // Apply any required UI change now that the Fragment is visible.
    }

    // Called at the start of the active lifetime.
    @Override
    public void onResume()
    {
        super.onResume();
        // Resume any paused UI updates, threads, or processes required
        // by the Fragment but suspended when it became inactive.
    }

    // Called at the end of the active lifetime.
    @Override
    public void onPause()
    {
        super.onPause();
        // Suspend UI updates, threads, or CPU intensive processes
        // that don't need to be updated when the Activity isn't
        // the active foreground activity.
        // Persist all edits or state changes
        // as after this call the process is likely to be killed.
    }

    // Called to save UI state changes at the
    // end of the active lifecycle.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate, onCreateView, and
        // onCreateView if the parent Activity is killed and restarted.
    }

    // Called at the end of the visible lifetime.
    @Override
    public void onStop()
    {
        super.onStop();
        // Suspend remaining UI updates, threads, or processing
        // that aren't required when the Fragment isn't visible.
    }

    // Called when the Fragment's View has been detached.
    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        // Clean up resources related to the View.
    }

    // Called at the end of the full lifetime.
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        // Clean up any resources including ending threads,
        // closing database connections etc.
    }

    // Called when the Fragment has been detached from its parent Activity.
    @Override
    public void onDetach()
    {
        super.onDetach();
        // Clean up any references to the parent Activity
        // including references to its Views or classes. Typically setting
        // those references to null.
    }

}
