package com.siestech.booksharing.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.siestech.booksharing.Adapter.NotificationAdapter;
import com.siestech.booksharing.Model.NotificationModel;
import com.siestech.booksharing.R;
import java.util.ArrayList;

public class NotificationFragment extends Fragment {
    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notification, container, false);
        ArrayList<NotificationModel> list = new ArrayList<>();
        list.add(new NotificationModel(R.drawable.profile1, "<b>Shreya</b> shared a new book with BorrowBox", "just now"));
        list.add(new NotificationModel(R.drawable.profile2, "<b>Snehal</b> is offering a book to borrow", "2 min ago"));
        list.add(new NotificationModel(R.drawable.profile1, "Your borrow request is ready to send", "today"));
        androidx.recyclerview.widget.RecyclerView rv = v.findViewById(R.id.notification2RV);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        rv.setAdapter(new NotificationAdapter(list, requireContext()));
        return v;
    }
}
