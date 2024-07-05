package com.zp.utils;

import com.zp.model.common.ComboBoxData;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * EnumUtils
 *
 * @author ZP
 * 
 */
public class EnumUtil {

    /**
     * convertEnumToComboBoxData 把枚举类转换为前端下拉框level/value格式，参数为对应的字段名
     * @param enumClass Class
     * @param levelFieldName String
     * @param valueFieldName String
     * @return List<ComboBoxData>
     */
    public static <T extends Enum<T>> List<ComboBoxData> convertEnumToComboBoxData(Class<T> enumClass, String levelFieldName, String valueFieldName) {
        List<ComboBoxData> comboBoxDataList = new ArrayList<>();
        for (T enumConstant : enumClass.getEnumConstants()) {
            ComboBoxData comboBoxData = new ComboBoxData();
            try {
                Field levelField = enumConstant.getDeclaringClass().getDeclaredField(levelFieldName);
                levelField.setAccessible(true);
                comboBoxData.setLevel((String) levelField.get(enumConstant));

                Field valueField = enumConstant.getDeclaringClass().getDeclaredField(valueFieldName);
                valueField.setAccessible(true);
                comboBoxData.setValue((String) valueField.get(enumConstant));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            comboBoxDataList.add(comboBoxData);
        }
        return comboBoxDataList;
    }

    /**
     * getEnumByField 根据指定字段和字段值获取枚举
     * @param enumClass Class
     * @param fieldName String
     * @param fieldValue Object
     * @return T
     */
    public static <T extends Enum<T>> T getEnumByField(Class<T> enumClass, String fieldName, Object fieldValue) {
        try {
            Field field = enumClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            for (T enumConstant : enumClass.getEnumConstants()) {
                Object value = field.get(enumConstant);
                if (value.equals(fieldValue)) {
                    return enumConstant;
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
