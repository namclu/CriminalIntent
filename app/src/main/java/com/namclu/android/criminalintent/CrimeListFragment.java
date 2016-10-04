package com.namclu.android.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by namlu on 01-Oct-16.
 */

public class CrimeListFragment extends Fragment {

    private static final int REQUEST_CRIME = 1;

    /*
     * @param mCrimeRecyclerView
     * @param mAdapter
     * @param mItemPosition captures the item list position user last clicked on
     *
     */
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private int mItemPosition;

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

    // Called whenever onCreateView is triggered
    private void updateUI() {
        // .get(Context context) returns an instance of CrimeLab
        // getActivity() returns a FragmentActivity
        CrimeLab crimeLab = CrimeLab.get(getActivity());

        // Get a List of Crime objects
        List<Crime> crimes = crimeLab.getCrimes();

        // When updating UI, update only the position user last clicked on
        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);

            // setAdapter(Adapter adapter) - Sets a new adapter to provide child views on demand
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            // Use notifyDataSetChanged() as a last resort. Always more efficient to use more specific
            //  change events such as notifyItemChanged(int), notifyItemInserted(int)
            mAdapter.notifyItemChanged(mItemPosition);
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

        // onClick() launches a new CrimeActivity that is hosting a CrimeFragment
        @Override
        public void onClick(View view) {
            // When the user selects a Fragment from list, capture its position on the list
            // .getChildAdapterPosition(View child)
            mItemPosition = mCrimeRecyclerView.getChildAdapterPosition(view);

            Intent intent = CrimeActivity.newIntent(getActivity() , mCrime.getID());
            startActivity(intent);

            // TODO - remove log
            Log.d("CrimeListFragment", "Position: " + mItemPosition);
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
