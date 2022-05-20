package com.neilsmyth.navigationtest.ui.main;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.neilsmyth.navigationtest.R;

public class MainFragment extends Fragment
{

    private MainViewModel mViewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel

        Button button = getView().findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                EditText userText = getView().findViewById(R.id.userText);
                MainFragmentDirections.ActionMainFragmentToSecondFragment action =
                        MainFragmentDirections.actionMainFragmentToSecondFragment();

                action.setMessage(userText.getText().toString());
                Navigation.findNavController(view).navigate(action);
                //Navigation.findNavController(view)
                // .navigate(R.id.action_mainFragment_to_secondFragment);
            }
        });
    }


    //@Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    public static MainFragment newInstance()
    {
        return new MainFragment();
    }

}
