package com.demos.kotlin.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.demos.kotlin.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String text = "BlankFragment---------";


    public BlankFragment() {
        // Required empty public constructor
        Log.e("fragment", "-------empty constructor");
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("fragment", "-------fragment onCreate");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("fragment", "-------fragment onCreateView");
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @Override
    public void onStart() {
        Log.e("TYH", text + "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.e("TYH", text + "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.e("TYH", text + "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.e("TYH", text + "onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.e("TYH", text + "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.e("TYH", text + "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.e("TYH", text + "onDetach");
        super.onDetach();
    }

}
