package com.siestech.booksharing.Practical;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.siestech.booksharing.R;

public class FragmentDemoActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_fragment_demo);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.verticalContainer, DemoFragment.newInstance("Vertical fragment"))
                .replace(R.id.horizontalOne, DemoFragment.newInstance("Left fragment"))
                .replace(R.id.horizontalTwo, DemoFragment.newInstance("Right fragment"))
                .commit();
    }
    public static class DemoFragment extends Fragment{
        private String label;
        static DemoFragment newInstance(String x){DemoFragment f=new DemoFragment();Bundle b=new Bundle();b.putString("label",x);f.setArguments(b);return f;}
        @Nullable @Override public View onCreateView(android.view.LayoutInflater i, android.view.ViewGroup p, Bundle b){
            TextView t=new TextView(requireContext());
            t.setText(getArguments()==null?"Fragment":getArguments().getString("label","Fragment"));
            t.setGravity(17); t.setTextColor(getResources().getColor(R.color.text_primary)); t.setTextSize(15); return t;
        }
    }
}
