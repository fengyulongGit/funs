package com.autils.framework.ui.view.dialog;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.autils.api.response.model.Area;
import com.autils.api.response.model.Cities;
import com.autils.api.response.model.Province;
import com.autils.framework.R;
import com.autils.framework.ui.adapter.AddressPickerAdapter;
import com.autils.framework.ui.view.wheelview.OnWheelChangedListener;
import com.autils.framework.ui.view.wheelview.OnWheelScrollListener;
import com.autils.framework.ui.view.wheelview.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengyulong on 2018/5/18.
 */
public class AddressPickerBottomDialog {
    private BottomSheetDialog bottomSheetDialog;

    private OnAddressSelectListener mListener;

    private List<? extends Area> provinceList = new ArrayList<>();
    private List<? extends Area> cityList = new ArrayList<>();
    private List<? extends Area> districtList = new ArrayList<>();

    private AddressPickerAdapter provinceAdapter;
    private AddressPickerAdapter cityAdapter;
    private AddressPickerAdapter areaAdapter;

    private int currentProvinceItem = 0;
    private int currentCityItem = 0;
    private int currentDistrictItem = 0;

    private int maxsize = 14;
    private int minsize = 12;

    private Context context;
    private View view;

    private boolean hideThirdPicker = false;

    private AddressPickerBottomDialog() {
    }

    public AddressPickerBottomDialog(Context context, List<? extends Area> provinceList) {
        this.context = context;
        this.provinceList = provinceList;
        bottomSheetDialog = new BottomSheetDialog(context);
        setContentView();
    }

    public AddressPickerBottomDialog(Context context, List<Area> provinceList, boolean hideThirdPicker) {
        this.context = context;
        this.provinceList = provinceList;
        this.hideThirdPicker = hideThirdPicker;
        bottomSheetDialog = new BottomSheetDialog(context);
        setContentView();
    }

    private void setContentView() {
        view = LayoutInflater.from(context).inflate(R.layout.view_address_selector_pop_layout, null);
        initPicker();
        bottomSheetDialog.setContentView(view);

        view.findViewById(R.id.wv_address_area).setVisibility(hideThirdPicker ? View.GONE : View.VISIBLE);

        view.findViewById(R.id.btn_myinfo_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        view.findViewById(R.id.btn_myinfo_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClick(provinceList.get(currentProvinceItem), cityList.get(currentCityItem), districtList.get(currentDistrictItem));
                }
                dismiss();
            }
        });
    }

    public void show() {
        if (bottomSheetDialog != null && !bottomSheetDialog.isShowing()) {
            bottomSheetDialog.show();
        }
    }

    private void dismiss() {
        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        }
    }

    private void initPicker() {
        initProvinceAdapter();
        initCitiesAdapter();
        initDistrictsAdapter();

        WheelView wv_address_province = (WheelView) view.findViewById(R.id.wv_address_province);
        wv_address_province.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = provinceAdapter.getItemText(wheel.getCurrentItem()).toString();
                setTextViewSize(currentText, provinceAdapter);
                currentProvinceItem = wheel.getCurrentItem();
                currentCityItem = 0;
                currentDistrictItem = 0;
                initCitiesAdapter();
                initDistrictsAdapter();
            }
        });

        wv_address_province.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = provinceAdapter.getItemText(wheel.getCurrentItem()).toString();
                setTextViewSize(currentText, provinceAdapter);
            }
        });

        WheelView wv_address_city = (WheelView) view.findViewById(R.id.wv_address_city);

        wv_address_city.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = cityAdapter.getItemText(wheel.getCurrentItem()).toString();
                setTextViewSize(currentText, cityAdapter);
                currentCityItem = wheel.getCurrentItem();
                currentDistrictItem = 0;
                initDistrictsAdapter();
            }
        });

        wv_address_city.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = cityAdapter.getItemText(wheel.getCurrentItem()).toString();
                setTextViewSize(currentText, cityAdapter);
            }
        });

        WheelView wv_address_area = (WheelView) view.findViewById(R.id.wv_address_area);
        wv_address_area.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = areaAdapter.getItemText(wheel.getCurrentItem()).toString();
                setTextViewSize(currentText, cityAdapter);
                currentDistrictItem = wheel.getCurrentItem();
            }
        });

        wv_address_area.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = areaAdapter.getItemText(wheel.getCurrentItem()).toString();
                setTextViewSize(currentText, areaAdapter);
            }
        });

    }

    private void initProvinceAdapter() {
        provinceAdapter = new AddressPickerAdapter(context, provinceList, currentProvinceItem, maxsize, minsize);
        WheelView wv_address_province = (WheelView) view.findViewById(R.id.wv_address_province);
        wv_address_province.setVisibleItems(5);
        wv_address_province.setViewAdapter(provinceAdapter);
        wv_address_province.setCurrentItem(currentProvinceItem);
    }

    private void initCitiesAdapter() {
        if (null != provinceList) {
            Province province = (Province) provinceList.get(currentProvinceItem);
            cityList = province.getAreas();
            cityAdapter = new AddressPickerAdapter(context, cityList, 0, maxsize, minsize);

            WheelView wv_address_city = (WheelView) view.findViewById(R.id.wv_address_city);
            wv_address_city.setVisibleItems(5);
            wv_address_city.setViewAdapter(cityAdapter);
            wv_address_city.setCurrentItem(0);
            setTextViewSize("0", cityAdapter);
        }
    }

    private void initDistrictsAdapter() {
        if (null != cityList) {
            Cities currentCity = (Cities) cityList.get(currentCityItem);
            districtList = currentCity.getAreas();
            areaAdapter = new AddressPickerAdapter(context, districtList, 0, maxsize, minsize);

            WheelView wv_address_area = (WheelView) view.findViewById(R.id.wv_address_area);
            wv_address_area.setVisibleItems(5);
            wv_address_area.setViewAdapter(areaAdapter);
            wv_address_area.setCurrentItem(0);
            setTextViewSize("0", areaAdapter);
        }
    }


    /**
     * 设置字体大小
     */
    private void setTextViewSize(String currentItemText, AddressPickerAdapter adapter) {
        if (null != adapter) {
            ArrayList<View> arrayList = adapter.getTestViews();
            String currentText;
            for (View view : arrayList) {
                TextView textView = (TextView) view;
                currentText = textView.getText().toString();
                if (currentItemText.equalsIgnoreCase(currentText)) {
                    textView.setTextSize(14f);
                } else {
                    textView.setTextSize(12f);
                }
            }
        }
    }


    public AddressPickerBottomDialog setAddressListener(OnAddressSelectListener onAddressListener) {
        this.mListener = onAddressListener;
        return this;
    }

    public interface OnAddressSelectListener {
        void onClick(Area province, Area city, Area districts);
    }
}
