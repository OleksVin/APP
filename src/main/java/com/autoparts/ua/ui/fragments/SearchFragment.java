package com.autoparts.ua.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.navigation.Navigation;
import com.google.android.gms.ads.AdRequest;
import androidx.appcompat.widget.SearchView;
import dagger.hilt.android.AndroidEntryPoint;

import com.autoparts.ua.R;
import com.autoparts.ua.databinding.FragmentSearchBinding;
import com.autoparts.ua.ui.adapters.VehicleAdapter;
import com.autoparts.ua.ui.viewmodels.MainViewModel;

@AndroidEntryPoint
public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private MainViewModel viewModel;
    private VehicleAdapter vehicleAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setupRecyclerView();
        setupAdMob();
        setupSearch();
        observeData();

        return root;
    }

    private void setupRecyclerView() {
        vehicleAdapter = new VehicleAdapter(vehicle -> {
            Bundle bundle = new Bundle();
            bundle.putInt("vehicleId", vehicle.id);
            // Navigation буде працювати після створення navigation graph
            // Navigation.findNavController(binding.getRoot())
            //         .navigate(R.id.action_search_to_parts, bundle);
        });

        binding.recyclerViewVehicles.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewVehicles.setAdapter(vehicleAdapter);
    }

    private void setupAdMob() {
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);
    }

    private void setupSearch() {
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.searchVehicles(query).observe(getViewLifecycleOwner(),
                        vehicles -> vehicleAdapter.setVehicles(vehicles));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 2) {
                    viewModel.searchVehicles(newText).observe(getViewLifecycleOwner(),
                            vehicles -> vehicleAdapter.setVehicles(vehicles));
                }
                return true;
            }
        });
    }

    private void observeData() {
        viewModel.getAllVehicles().observe(getViewLifecycleOwner(), vehicles -> {
            vehicleAdapter.setVehicles(vehicles);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}