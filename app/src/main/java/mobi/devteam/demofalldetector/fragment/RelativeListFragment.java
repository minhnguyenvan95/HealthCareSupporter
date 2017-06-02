package mobi.devteam.demofalldetector.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobi.devteam.demofalldetector.R;
import mobi.devteam.demofalldetector.activity.CreateUpdateRelativeActivity;
import mobi.devteam.demofalldetector.adapter.RelativeAdapter;
import mobi.devteam.demofalldetector.model.Relative;

public class RelativeListFragment extends Fragment {
    @BindView(R.id.rccv_relative_list)
    RecyclerView recyclerViewRelatives;

    private RelativeAdapter mAdapter;
    private GridLayoutManager mLayoutManager;
    private ArrayList<Relative> relatives;

    private FirebaseAuth mAuth;
    private DatabaseReference relative_data;

    public RelativeListFragment() {
        // Required empty public constructor
    }

    public static RelativeListFragment newInstance() {
        RelativeListFragment fragment = new RelativeListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(R.string.tittle_manage_relatives);

//        ((MainActivity) getActivity()).showFab();
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_relative_list, container, false);
        ButterKnife.bind(this, view);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerViewRelatives.setLayoutManager(mLayoutManager);
        recyclerViewRelatives.setHasFixedSize(true);

        mAuth = FirebaseAuth.getInstance();
        relative_data = FirebaseDatabase.getInstance().getReference("relatives");

        initData();

        return view;
    }

    private void initData() {
        relatives = new ArrayList<>();
        //setDummyData();
        mAdapter = new RelativeAdapter(getActivity(), recyclerViewRelatives, relatives);
        recyclerViewRelatives.setAdapter(mAdapter);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        relative_data.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, Relative>> t = new GenericTypeIndicator<HashMap<String, Relative>>() {
                };
                HashMap<String, Relative> value = dataSnapshot.getValue(t);

                relatives.clear();
                if (value != null) {
                    relatives.addAll(value.values());
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Error when getting data : " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setDummyData() {
        Relative relative = new Relative();
        relative.setId(Calendar.getInstance().getTimeInMillis());
        relative.setName("Thái Thanh");
        relative.setPhone("01234567890");

        Relative relative1 = new Relative();
        relative1.setId(Calendar.getInstance().getTimeInMillis());
        relative1.setName("Văn Minh");
        relative1.setPhone("090909567890");

        relatives.add(relative);
        relatives.add(relative1);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick(R.id.fab_add_relative)
    public void add() {
        Intent intent = new Intent(this.getContext(), CreateUpdateRelativeActivity.class);
        startActivity(intent);
    }

}