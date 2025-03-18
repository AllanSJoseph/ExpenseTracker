package com.example.expensetracker.ui;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.expensetracker.R;
import com.example.expensetracker.ui.reports.ReportViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ReportsFragment extends Fragment {

    private LineChart dailyExpTrendLineChart;
    private PieChart expCatSharePieChart;
    private ReportViewModel reportViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reports, container, false);

        expCatSharePieChart = view.findViewById(R.id.cat_share_chart);
        dailyExpTrendLineChart = view.findViewById(R.id.daily_exp_trend_chart);
        reportViewModel = new ViewModelProvider(this).get(ReportViewModel.class);

        reportViewModel.getCategoryShare().observe(getViewLifecycleOwner(), cursor -> {
            loadPieChart(cursor);
        });

        reportViewModel.getDailyExpenseTrend().observe(getViewLifecycleOwner(), cursor -> {
            loadLineChart(cursor);
        });


        reportViewModel.loadReportData();


        return view;
    }


    public void loadLineChart(Cursor cursor){
        ArrayList<Entry> lineChartEntries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        int index = 0;
        if(cursor != null && cursor.moveToFirst()){
            do{
                String date = cursor.getString(0);
                double amount = cursor.getDouble(1);

                lineChartEntries.add(new Entry( index, (float) amount));
                labels.add(date);
                index++;
            }while (cursor.moveToNext());
            cursor.close();
        }

        LineDataSet dataSet = new LineDataSet(lineChartEntries, "Daily Expense Trend");
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);
        dataSet.setLineWidth(3f);
        dataSet.setCircleColor(Color.RED);
        dataSet.setCircleRadius(5f);
        dataSet.setDrawValues(true);

        LineData lineData = new LineData(dataSet);
        dailyExpTrendLineChart.setData(lineData);
        dailyExpTrendLineChart.getDescription().setEnabled(false);
        dailyExpTrendLineChart.setTouchEnabled(true);
        dailyExpTrendLineChart.setDragEnabled(true);
        dailyExpTrendLineChart.setScaleEnabled(true);

        XAxis xAxis = dailyExpTrendLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labels.size());
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                if (index >= 0 && index < labels.size()){
                    return labels.get(index);
                }
                return "";
            }

        });

        YAxis leftAxis = dailyExpTrendLineChart.getAxisLeft();
        leftAxis.setTextSize(12f);
        dailyExpTrendLineChart.getAxisRight().setEnabled(false);  // Disable right Y-Axis

        dailyExpTrendLineChart.animateX(1000);
        dailyExpTrendLineChart.invalidate();

    }


    public void loadPieChart(Cursor cursor){
        ArrayList<PieEntry> entries = new ArrayList<>();

        if(cursor != null && cursor.moveToFirst()){
            do{
                String category = cursor.getString(0);
                double amount = cursor.getDouble(1);
                entries.add(new PieEntry((float)amount,category));
            }while (cursor.moveToNext());
            cursor.close();
        }

        PieDataSet dataSet = new PieDataSet(entries, "Expense Categories");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(12f);
        dataSet.setValueTextColor(Color.BLACK);

        PieData pieData = new PieData(dataSet);
        expCatSharePieChart.setData(pieData);
        expCatSharePieChart.getDescription().setEnabled(false);
        expCatSharePieChart.setCenterText("Expenses");
        expCatSharePieChart.animateY(1000);
        expCatSharePieChart.setEntryLabelTextSize(12f);

        Legend legend = expCatSharePieChart.getLegend();
        legend.setTextSize(12f);
        legend.setWordWrapEnabled(true);

    }
}
