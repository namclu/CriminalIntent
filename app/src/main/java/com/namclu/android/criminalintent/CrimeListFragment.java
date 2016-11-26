package com.namclu.android.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by namlu on 01-Oct-16.
 */

public class CrimeListFragment extends Fragment {

    /*
     * @param mCrimeRecyclerView
     * @param mAdapter
     * @param mSubtitleVisible - Variable to track if Show Subtitle button is visible or not
     */
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private boolean mSubtitleVisible;

    // 13.8: By calling setHasOptionsMenu(), explicitly tells FragmentManager that fragment should
    //      receive a call to onCreateOptionsMenu()
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    // Fragment lifecycle which creates and returns the view hierarchy associated with the fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // public View inflate(int resource, ViewGroup root, boolean attachToRoot)
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    // Fragment lifecycle which makes fragment begin interacting with user
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    // 13.8 Inflating a menu resource
    // Override onCreateOptionsMenu( Menu, MenuInflater) to inflate the menu defined in fragment_crime_list.xml.
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // inflate(int menuRes, Menu menu);
        inflater.inflate(R.menu.fragment_crime_list, menu);

        // 13.18: Triggers a recreation of action items when user presses the "Show Subtitle"
        //      action item
        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    // 13.12: Implement onOptionsItemSelected(MenuItem) to respond to selection of MenuItem
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // 13.12: Respond to the "+" icon being pressed and returns a new Crime object
            case R.id.menu_item_new_crime:
                Crime crime = new Crime();
                // .get(Context) returns an instance of CrimeLab
                CrimeLab.get(getActivity()).addCrime(crime);
                // .newIntent(Context, UUID)
                Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId());
                startActivity(intent);
                return true;
            // 13.16: Respond to Show Subtitle action item
            case R.id.menu_item_show_subtitle:
                // 13.18:
                // Switch boolean value of mSubtitleVisible
                // invalidateOptionsMenu() declares option menu has changed, so should be recreated
                //      onCreateOptionsMenu(Menu) will be called the next time it needs to be displayed
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();

                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // 13.15 Setting the toolbar's subtitle
    public void updateSubtitle() {
        // Crime.get(Context) returns a CrimeLab object
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        // CrimeLab.getCrimes returns a List<Crime>
        int crimeCount = crimeLab.getCrimes().size();
        // getString(int resId, Object... formatArgs) generates the subtitle string
        String subtitle = getString(R.string.subtitle_format, crimeCount);

        // 13.19: Showing or hiding the subtitle
        if (!mSubtitleVisible) {
            subtitle = null;
        }

        // Activity that is hosting CrimeFragmentList is cast to AppCompatActivity since CriminalIntent
        //      uses the AppCompat library, all activities will be subclass of AppCompatActivity
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    // Called whenever onCreateView is triggered
    private void updateUI() {
        // .get(Context context) returns an instance of CrimeLab
        // getActivity() returns a FragmentActivity
        CrimeLab crimeLab = CrimeLab.get(getActivity());

        // Get a List of Crime objects
        List<Crime> crimes = crimeLab.getCrimes();

        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);

            // setAdapter(Adapter adapter) - Sets a new adapter to provide child views on demand
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

    }

    // Defines ViewHolder which describes an item view and metadata about its place within the RecyclerView
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;
        private Crime mCrime;

        // itemView is the View for an entire row
        public CrimeHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_check_box);
        }

        // Given a Crime, CrimeHolder can update the title, date and solved state
        public void bindCrime(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }

        // onClick() launches a new CrimePagerActivity
        @Override
        public void onClick(View view) {
            Intent intent = CrimePagerActivity.newIntent(getActivity() , mCrime.getId());
            startActivity(intent);
        }
    }

    // Defines Adapter which binds a data set to views that are displayed within a RecyclerView
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        // Called by RecyclerView when it needs a new View to display an item
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            // .inflate(int resource, ViewGroup root, boolean attachToRoot)
            View view = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);
        }

        // Binds a ViewHolder's View to model object
        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bindCrime(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}
