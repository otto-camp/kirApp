package com.example.kirapp.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kirapp.R;
import com.example.kirapp.adapters.AdvertAdapter;
import com.example.kirapp.models.Advert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainPageFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdvertAdapter advertAdapter;
    private List<Advert> adverts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);
        recyclerView = view.findViewById(R.id.advert_view);
        buildView();
        return view;
    }


    private void buildView() {
        String createdAt = LocalDate.now().toString();
        String updatedAt = LocalDate.now().toString();

        ArrayList<Advert> adverts1 = new ArrayList<>();
        adverts1.add(new Advert("examp", createdAt, updatedAt, "asdgasdgasd", 60.99, true));
        advertAdapter = new AdvertAdapter(adverts1, requireContext());

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(advertAdapter);
    }
}