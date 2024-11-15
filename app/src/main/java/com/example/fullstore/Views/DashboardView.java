package com.example.fullstore.Views;
import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fullstore.Adapter.DashboardAdapter;
import com.example.fullstore.R;
import com.example.fullstore.models.DashboardCard;
import java.util.List;

public class DashboardView extends LinearLayout {

    private RecyclerView recyclerView;
    private FragmentActivity activity;

    public DashboardView(Context context) {
        super(context);
        init(context);
    }

    public DashboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DashboardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);

        LayoutInflater.from(context).inflate(R.layout.view_dashboard, this, true);

        recyclerView = findViewById(R.id.recyclerViewDashboard);
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
    }

    public void setFragmentActivity(FragmentActivity activity) {
        this.activity = activity;
    }

    public void setDashboardCards(List<DashboardCard> dashboardCards) {
        DashboardAdapter adapter = new DashboardAdapter(dashboardCards, activity);
        recyclerView.setAdapter(adapter);
    }
}
