package com.zl.easyexcel.template;

import com.alibaba.excel.EasyExcel;
import lombok.Data;

import java.io.File;
import java.math.BigDecimal;

/**慈善活动支出和管理费用的比例
 * @author zhangliang
 * @date 2020/10/14.
 */
@Data
public class CharityActivityExpendDTO {
    private BigDecimal preYearActualIncomeAmount;
    private BigDecimal preYearIncomeAmountAfterAdjust;
    private BigDecimal currentYearExpendAmount;
    private BigDecimal currentYearExpendAmountForCharityActivity;
    private BigDecimal currentYearManagementExpendAmount;
    private BigDecimal currentYearOtherExpendAmount;
    private BigDecimal percentOfCurrentYearExpendAmountForCharityActivityInPreYearIncome;
    private BigDecimal percentOfCurrentYearManagementExpendAmountInCurrentYearExpendAmount;

    public static void main(String[] args) {
        CharityActivityExpendDTO dto = new CharityActivityExpendDTO();
        dto.setPreYearActualIncomeAmount(new BigDecimal("10"));
        dto.setPreYearIncomeAmountAfterAdjust(new BigDecimal("110"));
        dto.setCurrentYearExpendAmount(new BigDecimal("1110"));
        dto.setCurrentYearExpendAmountForCharityActivity(new BigDecimal("1110"));
        dto.setCurrentYearManagementExpendAmount(new BigDecimal("0"));
        dto.setCurrentYearOtherExpendAmount(new BigDecimal("0"));
        dto.setPercentOfCurrentYearExpendAmountForCharityActivityInPreYearIncome(new BigDecimal("0"));
        dto.setPercentOfCurrentYearManagementExpendAmountInCurrentYearExpendAmount(new BigDecimal("0"));


        String templateFileName =
                CharityActivityExpendDTO.class.getResource("/").getPath() + "exceltemplate/charityActivityExpend.xlsx";

        // 方案1 根据对象填充
        String fileName = CharityActivityExpendDTO.class.getResource("/").getPath() + "simpleFill" + System.currentTimeMillis() + ".xlsx";
        // 这里 会填充到第一个sheet， 然后文件流会自动关闭

        EasyExcel.write(fileName).withTemplate(templateFileName).sheet().doFill(dto);
    }
}