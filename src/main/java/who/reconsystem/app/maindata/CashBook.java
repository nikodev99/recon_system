package who.reconsystem.app.maindata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import who.reconsystem.app.root.config.Functions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CashBook {
    private long id;
    private String majorOffice;
    private String office;
    private String countryOffice;
    private String countryName;
    private String sourceEImprest;
    private String sourceParent;
    private String budgetCenter;
    private String imprestAccount;
    private String transactionType;
    private LocalDate eImprestDate;
    private LocalDate glPeriod;
    private LocalDate invoiceDate;
    private String payeeName;
    private String currencyCode;
    private BigDecimal amountLC;
    private BigDecimal conversionRate;
    private BigDecimal amountUSD;
    private String paymentCategory;
    private String voucherNumber;
    private String documentNumber;
    private String checkNumber;
    private String referenceNumber;
    private String description;
    private String reconciliationStatus;
    private String poNumber;
    private String expenditureType;
    private LocalDateTime addedDate;

    public static CashBook populate(List<String> data) {
        return CashBook.builder()
                .id(Long.parseLong(data.get(0)))
                .majorOffice(data.get(1))
                .office(data.get(2))
                .countryOffice(data.get(3))
                .countryName(data.get(4))
                .sourceEImprest(data.get(5))
                .sourceParent(data.get(6))
                .budgetCenter(data.get(7))
                .imprestAccount(data.get(8))
                .transactionType(data.get(9))
                .eImprestDate(Functions.date(data.get(10)))
                .glPeriod(Functions.date(data.get(11)))
                .invoiceDate(Functions.date(data.get(12)))
                .payeeName(data.get(13))
                .currencyCode(data.get(14))
                .amountLC(new BigDecimal(data.get(15)))
                .conversionRate(new BigDecimal(data.get(16)))
                .amountUSD(new BigDecimal(data.get(17)))
                .paymentCategory(data.get(18))
                .voucherNumber(data.get(19))
                .documentNumber(data.get(20))
                .checkNumber(data.get(21))
                .referenceNumber(data.get(22))
                .description(data.get(23))
                .reconciliationStatus(data.get(24))
                .poNumber(data.get(25))
                .expenditureType(data.get(26))
                .addedDate(Functions.dateTime(data.get(27)))
                .build();
    }
}
