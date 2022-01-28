package com.tzxx.common.tencentservice.bill.resolver;

import com.tzxx.common.tencentservice.bill.category.Bill;
import com.tzxx.common.tencentservice.bill.category.VerifyAuthenticity;
import com.tzxx.common.tencentservice.bill.model.VatInvoiceVerify;
import com.tzxx.common.tencentservice.bill.model.VatInvoiceVerifyErrorRecord;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhangliang
 * @date 2021/1/29.
 */
@Data
public class MixedInvoiceOcrResponseResolverResult {
    /**
     * 成功识别张数
     */
    private int successCount;
    /**
     * 失败识别张数
     */
    private int errorCount;
    /**
     * 每一类发票数据
     */
    private Map<Long, List<Bill>> billMap = new HashMap<>(16);
    /**
     * 所有发票
     */
    private List<Bill> bills = new ArrayList<>();
    /**
     * id和发票map
     */
    private  Map<String, Bill> idBillMap = new HashMap<>(16);

    private String imageUrl;

    private String imageBase64;

    public String getImageInfo(){
        return StringUtils.isNotBlank(imageUrl) ? imageUrl : imageBase64;
    }

    public <T> List<T> listBillByType(Long type){
        List<Bill> bills = billMap.get(type);
        if (bills != null && !bills.isEmpty()) {
            List<T> list = new ArrayList<>();
            for (Bill bill : bills) {
                list.add((T) bill);
            }
            return list;
        }
        return new ArrayList<>();
    }

    /**
     * 合并所有的增值税发票（包含增值税普通发票（卷票））
     */
    public static List<VatInvoiceVerify> mergeVatInvoiceForVerify(List<MixedInvoiceOcrResponseResolverResult> resolverResults){
        List<VatInvoiceVerify> vatInvoiceVerifies = new ArrayList<>();
        for (MixedInvoiceOcrResponseResolverResult resolverResult : resolverResults) {
            List<Bill> bills = resolverResult.getBills();
            for (Bill bill : bills) {
                if(bill instanceof VerifyAuthenticity){
                    VerifyAuthenticity verifyAuthenticity = (VerifyAuthenticity ) bill;
                    VatInvoiceVerify vatInvoiceVerify = new VatInvoiceVerify();
                    vatInvoiceVerify.setImageUrl(resolverResult.getImageUrl());
                    vatInvoiceVerify.setImageBase64(resolverResult.getImageBase64());
                    vatInvoiceVerify.setInvoiceNo(verifyAuthenticity.invoiceNoForVerify());
                    vatInvoiceVerify.setInvoiceCode(verifyAuthenticity.invoiceCodeForVerify());
                    vatInvoiceVerify.setInvoiceDate(verifyAuthenticity.billDateForVerify());
                    vatInvoiceVerify.setAdditional(verifyAuthenticity.additionalForVerify());
                    vatInvoiceVerify.setBillId(bill.id());
                    vatInvoiceVerifies.add(vatInvoiceVerify);
                }
            }
        }
        return vatInvoiceVerifies;
    }


    /**
     * 移除校验失败的所有的增值税发票（包含增值税普通发票（卷票））
     */
    public static void markVerifyFailData(List<VatInvoiceVerifyErrorRecord> verifyErrorRecords,List<MixedInvoiceOcrResponseResolverResult> resolverResults){
        Map<String, MixedInvoiceOcrResponseResolverResult> resolverResultMap = resolverResults.stream().collect(Collectors.toMap(MixedInvoiceOcrResponseResolverResult::getImageInfo, Function.identity()));
        for (VatInvoiceVerifyErrorRecord verifyErrorRecord : verifyErrorRecords) {
            //找到校验失败的那张图片的解析数据
            MixedInvoiceOcrResponseResolverResult resolverResult = resolverResultMap.get(verifyErrorRecord.getImageInfo());
            String billId = verifyErrorRecord.getBillId();
            //标记真伪
            Bill bill = resolverResult.getIdBillMap().get(billId);
            if (bill != null) {
                bill.settingAuthenticity(false);
            }
        }
    }
}
