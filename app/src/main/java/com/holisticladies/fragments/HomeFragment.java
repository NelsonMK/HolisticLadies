package com.holisticladies.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.holisticladies.R;
import com.holisticladies.viewPager.Events;
import com.holisticladies.viewPager.Products;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private Context context;

    private void setUpViewPager(ViewPager viewPager){
      Adapter adapter = new Adapter(getChildFragmentManager());
      adapter.addFragment(new Events(), "Events");
      adapter.addFragment(new Products(), "Products");
      viewPager.setAdapter(adapter);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context = getContext();


        //Toolbar toolbar = view.findViewById(R.id.toolbar);

        ViewPager viewPager = view.findViewById(R.id.viewPager);
        setUpViewPager(viewPager);

        TabLayout tabLayout = view.findViewById(R.id.packagetablayout);
        tabLayout.setupWithViewPager(viewPager);

    }

    static class  Adapter extends FragmentPagerAdapter{
        private final List<Fragment> fragments = new ArrayList<>();
        private final List<String> fragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm){
            super(fm);
        }

        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            fragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position){
            return fragments.get(position);
        }

        @Override
        public int getCount(){
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position){
            return fragmentTitles.get(position);
        }
    }

}
