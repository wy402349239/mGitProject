package com.project.git.com.gitproject.are;

import com.contrarywind.interfaces.IPickerViewData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 全国区域信息
 * @Author: jx_wy
 * @Date: 2020/12/14 9:03 PM
 */
public class DsjAreaEntity {

    List<DsjProvince> provinces = new ArrayList<>();

    public List<DsjProvince> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<DsjProvince> provinces) {
        this.provinces = provinces;
    }

    private ArrayList<ArrayList<String>> cities = new ArrayList<>();//全国的二维城市
    private ArrayList<ArrayList<ArrayList<String>>> areas = new ArrayList<>();//全国的三维区县

    public ArrayList<ArrayList<String>> getCities() {
        return cities;
    }

    public void setCities(ArrayList<ArrayList<String>> cities) {
        this.cities = cities;
    }

    public ArrayList<ArrayList<ArrayList<String>>> getAreas() {
        return areas;
    }

    public void setAreas(ArrayList<ArrayList<ArrayList<String>>> areas) {
        this.areas = areas;
    }

    public void initAreas() {
        if (provinces == null || provinces.isEmpty()) {
            return;
        }
        for (int i = 0; i < provinces.size(); i++) {
            List<DsjCity> cs = provinces.get(i).getCity();//单个省份的数据
            if (cs == null || cs.isEmpty()) {
                continue;
            }
            ArrayList<String> city = new ArrayList<>();//单个省份的city
            ArrayList<ArrayList<String>> area = new ArrayList<>();//单个省份的区域二维集合
            for (int j = 0; j < cs.size(); j++) {
                city.add(cs.get(j).getName());
                ArrayList<String> list = new ArrayList<>(cs.get(j).getArea());
                area.add(list);
            }
            cities.add(city);
            areas.add(area);
        }
    }

    public static class DsjProvince implements IPickerViewData, Serializable {
        String name;
        List<DsjCity> city = new ArrayList<>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<DsjCity> getCity() {
            return city;
        }

        public void setCity(List<DsjCity> city) {
            this.city = city;
        }

        @Override
        public String getPickerViewText() {
            return name;
        }
    }

    public static class DsjCity implements Serializable {
        String name;
        List<String> area = new ArrayList<>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getArea() {
            return area;
        }

        public void setArea(List<String> area) {
            this.area = area;
        }
    }
}