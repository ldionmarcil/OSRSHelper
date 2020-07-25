package com.infonuascape.osrshelper.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import androidx.annotation.Nullable;

import com.infonuascape.osrshelper.R;
import com.infonuascape.osrshelper.adapters.TrackerTableAdapter;
import com.infonuascape.osrshelper.controllers.MainFragmentController;
import com.infonuascape.osrshelper.enums.TrackerTime;
import com.infonuascape.osrshelper.models.players.PlayerSkills;

public class XPTrackerPeriodFragment extends OSRSPagerFragment {
	private static final String TAG = "XPTrackerPeriodFragment";

	private final static String EXTRA_TRACKER_TIME = "EXTRA_TRACKER_TIME";
	private TrackerTime time;
	private TrackerTableAdapter tableFiller;
	private TableLayout tableLayout;
	private View progressBar;
	private View emptyView;

	public static XPTrackerPeriodFragment newInstance(final TrackerTime period) {
		XPTrackerPeriodFragment fragment = new XPTrackerPeriodFragment();
		Bundle b = new Bundle();
		b.putSerializable(EXTRA_TRACKER_TIME, period);
		fragment.setArguments(b);
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View view = inflater.inflate(R.layout.xp_tracker_period, null);

		progressBar = view.findViewById(R.id.progressbar);
		emptyView = view.findViewById(R.id.empty_view);

		tableLayout = view.findViewById(R.id.table_tracking);
		tableFiller = new TrackerTableAdapter(getContext(), tableLayout);

		return view;
	}

	@Override
	public void onPageVisible() {
		if(time == null) {
			time = (TrackerTime) getArguments().getSerializable(EXTRA_TRACKER_TIME);
		}
		XPTrackerFragment xpTrackerFragment = getXPTrackerFragment();
		if (tableFiller != null && tableFiller.isEmpty() && xpTrackerFragment != null && xpTrackerFragment.getPlayerSkills(time) != null) {
			populateTable();
		}
	}

	public void onTrackingError() {
		if(getView() != null) {
			tableLayout.removeAllViews();
			progressBar.setVisibility(View.GONE);
			emptyView.setVisibility(View.VISIBLE);
		}
	}

	public void onUpdatingSuccessful() {
		if(getView() != null) {
			tableLayout.removeAllViews();
			progressBar.setVisibility(View.VISIBLE);
			emptyView.setVisibility(View.GONE);
		}
	}

	public void onForceRepopulate() {
		populateTable();
	}

	@Override
	public void refreshDataOnPreferencesChanged() {
		super.refreshDataOnPreferencesChanged();
		populateTable();
	}

	private void populateTable() {
		XPTrackerFragment xpTrackerFragment = getXPTrackerFragment();
		PlayerSkills playerSkills = xpTrackerFragment != null ? xpTrackerFragment.getPlayerSkills(time) : null;
		if (getView() != null) {
			progressBar.setVisibility(View.GONE);
			emptyView.setVisibility(View.GONE);
			if (playerSkills != null && !TextUtils.isEmpty(playerSkills.lastUpdate)) {
				tableFiller.fill(playerSkills);
			} else {
				emptyView.setVisibility(View.VISIBLE);
			}
		}
	}

	private XPTrackerFragment getXPTrackerFragment() {
		final OSRSFragment currentFragment = MainFragmentController.getInstance().getCurrentFragment();
		if(currentFragment instanceof XPTrackerFragment) {
			return ((XPTrackerFragment) currentFragment);
		}
		return null;
	}

	public void reloadData() {
		populateTable();
	}
}